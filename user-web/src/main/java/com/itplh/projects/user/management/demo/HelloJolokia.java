package com.itplh.projects.user.management.demo;

/**
 * @author: tanpenggood
 * @date: 2021-03-15 20:40
 */
public class HelloJolokia implements HelloJolokiaMBean {
    private String name;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void printHello() {
        System.out.println("HelloJolokia World, " + name);
    }

    @Override
    public void printHello(String whoName) {
        System.out.println("HelloJolokia , " + whoName);
    }
}
