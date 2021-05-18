package com.itplh.consistent_hash;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 一致性Hash操作
 *
 * @author: tanpenggood
 * @date: 2021-05-18 01:55
 */
public class ConsistentHash<T> {

    // Hash函数接口
    private final HashAlgorithm hashAlgorithm;
    // 每个机器节点关联的虚拟节点数量
    private final int numberOfReplicas;
    // 环形虚拟节点
    private final SortedMap<Long, T> circle;

    /**
     * @param hashAlgorithm    hash算法
     * @param realNodes        真实节点
     * @param numberOfReplicas 单个节点的副本数量
     */
    public ConsistentHash(HashAlgorithm hashAlgorithm,
                          Collection<T> realNodes,
                          int numberOfReplicas) {
        this.hashAlgorithm = hashAlgorithm;
        this.numberOfReplicas = numberOfReplicas;
        this.circle = new TreeMap<>();
        for (T node : realNodes) {
            add(node);
        }
    }

    /**
     * 增加真实机器节点
     *
     * @param node T
     */
    public void add(T node) {
        for (int i = 0; i < this.numberOfReplicas; i++) {
            circle.put(this.hashAlgorithm.hash(node.toString() + i), node);
        }
    }

    public T get(String key) {
        if (circle.isEmpty()) {
            return null;
        }

        long hash = this.hashAlgorithm.hash(key);
        // 沿环的顺时针找到一个虚拟节点
        if (!circle.containsKey(hash)) {
            SortedMap<Long, T> tailMap = circle.tailMap(hash);
            hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
        }
        return circle.get(hash);
    }

}
