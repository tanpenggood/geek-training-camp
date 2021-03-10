package com.itplh.projects.user.orm.jpa;

import com.itplh.projects.user.domain.User;
import org.apache.derby.jdbc.EmbeddedDataSource;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: tanpenggood
 * @date: 2021-03-10 15:21
 */
public class JpaDemo {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("emf", getProperties());
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        testUser(entityManager);
    }

    public static void testUser(EntityManager entityManager) {
        User user = new User();
        user.setName("tanpenggood");
        user.setPassword("******");
        user.setEmail("tanpengswpu@163.com");
        user.setPhoneNumber("13888888888");
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(user);
        transaction.commit();

        System.out.println(entityManager.find(User.class, user.getId()));
    }

    private static Map<String, Object> getProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.DerbyDialect");
        properties.put("hibernate.id.new_generator_mappings", false);
        properties.put("hibernate.connection.datasource", getDataSource());
        return properties;
    }

    private static DataSource getDataSource() {
        EmbeddedDataSource dataSource = new EmbeddedDataSource();
        dataSource.setDatabaseName("db/user-platform");
        dataSource.setCreateDatabase("create");
        return dataSource;
    }
}
