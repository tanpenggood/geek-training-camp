package com.itplh.projects.user.management.demo;

import com.itplh.projects.user.domain.User;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author: tanpenggood
 * @date: 2021-03-13 23:36
 */
public class UserMBeanTest {

    public static void main(String[] args) throws Exception {
        // 获取平台 MBean Server
        MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
        // 获取 ObjectName(domain)
        ObjectName objectName = new ObjectName("com.itplh.projects.user.management:type=User");
        // 创建 UserMBean 实例
        User user = new User();
        UserManager userMBean = new UserManager(user);
        // 注册UserMBean
        platformMBeanServer.registerMBean(userMBean, objectName);

        // 在JConsole MBean中操作User，观察控制台打印的数据变化
        while (true) {
            TimeUnit.SECONDS.sleep(2);
            System.out.println(user);
        }
    }

}
