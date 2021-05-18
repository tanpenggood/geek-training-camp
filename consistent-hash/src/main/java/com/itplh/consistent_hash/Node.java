package com.itplh.consistent_hash;

/**
 * 模拟机器节点
 *
 * @author: tanpenggood
 * @date: 2021-05-18 01:55
 */
public class Node {
    private String ip;
    private String name;

    public Node(String ip, String name) {
        this.ip = ip;
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 使用IP当做hash的Key
     *
     * @return String
     */
    @Override
    public String toString() {
        return ip;
    }
}
