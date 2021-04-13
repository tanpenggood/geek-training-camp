package com.itplh.projects.user.web.listener;

import com.itplh.projects.user.orm.jpa.JpaDemo;
import com.itplh.projects.user.sql.DBConnectionManager;
import com.itplh.projects.user.sql.DBConnectionManagerFactory;
import com.itplh.web.context.JndiComponentContext;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.logging.Logger;

/**
 * 仅用于测试
 * 测试通过ComponentContext进行依赖查找
 * 测试JPA操作
 * 测试读取ServletContext配置
 * 测试读取JNDI配置
 *
 * @author: tanpenggood
 * @date: 2021-03-10 01:49
 */
@Deprecated
public class TestingListener implements ServletContextListener {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        JndiComponentContext jndiComponentContext = JndiComponentContext.getInstance();
        // test get connection
        DBConnectionManager dbConnectionManager = DBConnectionManagerFactory.getInstance().getDBConnectionManager();
        dbConnectionManager.getConnection();
        // test jpa 操作数据库
        JpaDemo.testUser(dbConnectionManager.getEntityManager());
        // test read property by servlet context
        testGetPropertyByServletContext(servletContextEvent.getServletContext());
        // test read property by JNDI
        testGetPropertyByJNDI(jndiComponentContext);

        // test send/receive activemq message
        ConnectionFactory connectionFactory = jndiComponentContext.getComponent("jms/activemq-factory");
        testJms(connectionFactory);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    private void testGetPropertyByJNDI(JndiComponentContext jndiComponentContext) {
        String propertyName = "application.name";
        logger.info(String.format("JNDI Property[%s]:%s", propertyName, jndiComponentContext.lookupComponent(propertyName)));
    }

    private void testGetPropertyByServletContext(ServletContext servletContext) {
        String propertyName = "application.name";
        logger.info(String.format("ServletContext Property[%s]:%s", propertyName, servletContext.getInitParameter(propertyName)));
    }

    private void testJms(ConnectionFactory connectionFactory) {
        Connection connection = null;
        Session session = null;
        MessageProducer producer = null;
        MessageConsumer consumer = null;
        try {
            // Create a Connection
            connection = connectionFactory.createConnection();
            connection.start();

            // Create a Session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (Topic or Queue)
            Destination destination = session.createQueue("TEST.FOO");

            // Create a MessageProducer from the Session to the Topic or Queue
            producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            // Create a messages
            String text = "Hello world!" + " timestamp: " + System.currentTimeMillis();
            TextMessage message = session.createTextMessage(text);

            // Tell the producer to send the message
            producer.send(message);
            System.out.format("[%s] Send message: %s\n", Thread.currentThread().getName(), message.getText());

            // Create a MessageConsumer from the Session to the Topic or Queue
            consumer = session.createConsumer(destination);

            // sync received message
            consumer.setMessageListener(m -> {
                TextMessage msg = (TextMessage) m;
                try {
                    System.out.format("[%s] Async Received message: %s\n", Thread.currentThread().getName(), msg.getText());
                } catch (JMSException ignore) {
                }
            });

            // Wait for a message
            // Cannot synchronously receive a message when a MessageListener is set
            TextMessage syncMessage = (TextMessage) consumer.receive(1000);
            System.out.format("[%s] Received message: %s\n", Thread.currentThread().getName(), syncMessage.getText());
        } catch (Exception ignore) {
            // Cannot synchronously receive a message when a MessageListener is set
            System.err.println(ignore.getMessage());
        } finally {
            if (consumer != null) {
                try {
                    consumer.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
            if (producer != null) {
                try {
                    producer.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
            if (session != null) {
                try {
                    session.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
