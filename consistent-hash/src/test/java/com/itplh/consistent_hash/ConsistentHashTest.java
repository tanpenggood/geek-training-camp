package com.itplh.consistent_hash;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author: tanpenggood
 * @date: 2021-05-18 03:06
 */
public class ConsistentHashTest {

    // 机器节点IP前缀
    private static final String IP_PREFIX = "192.168.0.";

    @Test
    public void testConsistentHashLoadBalance() {
        System.out.println("-----MD5HashAlgorithm");
        testConsistentHashLoadBalance(new MD5HashAlgorithm(), 10, 500, 30000);
        System.out.println("-----MurMurHashAlgorithm");
        testConsistentHashLoadBalance(new MurMurHashAlgorithm(), 10, 500, 30000);
    }

    /**
     * @param hashAlgorithm         hash算法
     * @param numberOfPhysicalNodes 物理节点数量
     * @param numberOfReplicas      单个节点的副本数量
     * @param numberOfRequests      请求数量
     */
    private static void testConsistentHashLoadBalance(HashAlgorithm hashAlgorithm,
                                                      int numberOfPhysicalNodes,
                                                      int numberOfReplicas,
                                                      int numberOfRequests) {
        // ip counter mapping
        Map<String, LongAdder> ipCounterMapping = new HashMap<>();

        // 真实机器节点
        List<Node> realNodes = new ArrayList<>();
        for (int i = 1; i <= numberOfPhysicalNodes; i++) {
            String ip = IP_PREFIX + i;
            realNodes.add(new Node(ip, "node" + i));
            ipCounterMapping.put(ip, new LongAdder());
        }

        // 每台真实机器引入n个虚拟节点
        ConsistentHash<Node> consistentHash = new ConsistentHash<>(hashAlgorithm, realNodes, numberOfReplicas);

        // 将请求尽可能均匀的分配到各个物理节点上
        for (int i = 0; i < numberOfRequests; i++) {
            // 使用UUID拼接请求ID
            String requestId = UUID.randomUUID().toString() + i;
            // ip节点被分配次数加1
            ipCounterMapping.get(consistentHash.get(requestId).getIp()).increment();
        }

        // 打印每个ip被分配的次数
        realNodes.forEach(node -> System.out.format("%s 节点记录条数：%s\n", node.getIp(), ipCounterMapping.get(node.getIp())));
    }

}
