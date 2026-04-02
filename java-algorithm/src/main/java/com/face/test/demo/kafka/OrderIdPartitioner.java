package com.face.test.demo.kafka;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;

import java.util.List;
import java.util.Map;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-03-28  16:44
 */
public class OrderIdPartitioner implements Partitioner {
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        //1获取改topic所有可用分区列表
        List<PartitionInfo> partitions = cluster.partitionsForTopic(topic);
        int numPartitions = partitions.size();
        //2根据订单ID计算分区号
        String orderId = (String) key;
        //自定义路由策略 取模
        int partition = Math.abs(orderId.hashCode()) % numPartitions;

        return partition;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
