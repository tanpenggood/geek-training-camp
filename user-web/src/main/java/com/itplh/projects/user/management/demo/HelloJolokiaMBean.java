package com.itplh.projects.user.management.demo;

/**
 * @author: tanpenggood
 * @date: 2021-03-15 20:39
 */
public interface HelloJolokiaMBean {
    String getName();

    void setName(String name);

    void printHello();

    void printHello(String whoName);
}
