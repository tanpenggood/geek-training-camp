# week11

作业提交链接： https://jinshuju.net/f/IVQimK
        
## 作业一

> 通过 Java 实现两种 (以及) 更多的一致性 Hash 算法 
>
>(可选) 实现服务节点动态更新 
> 
> 参考：org.apache.dubbo.rpc.cluster.loadbalance.ConsistentHas hLoadBalance

1. 实现源码

    ```
    consistent-hash
        |- src
            |- main
                |- java
                    ｜- com.itplh.consistent_hash.HashAlgorithm           Hash算法接口
                    ｜- com.itplh.consistent_hash.MD5HashAlgorithm        Hash算法接口实现-MD5Hash
                    ｜- com.itplh.consistent_hash.MurMurHashAlgorithm     Hash算法接口实现-MurMurHash
                    ｜- com.itplh.consistent_hash.Node                    机器节点对象
                    ｜- com.itplh.consistent_hash.ConsistentHash          一致性Hash
    ```

2. 测试用例

    ```
    consistent-hash
        |- src
            |- test
                |- com.itplh.consistent_hash.ConsistentHashTest#testMD5ConsistentHashUseMurMur
                |- com.itplh.consistent_hash.ConsistentHashTest#testConsistentHashUseMD5
    ```
