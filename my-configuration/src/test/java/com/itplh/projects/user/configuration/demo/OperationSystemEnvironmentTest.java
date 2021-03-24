package com.itplh.projects.user.configuration.demo;

import org.junit.Test;

/**
 * @author: tanpenggood
 * @date: 2021-03-19 00:21
 */
public class OperationSystemEnvironmentTest {

    @Test
    public void testGetEnv() {
        System.getenv().forEach((k, v) -> System.out.println(k + "=" + v));
    }

}
