package com.loto.zk.c.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class c_DeleteNode {
    // 创建会话
    public static void main(String[] args) throws Exception {
        RetryPolicy exponentialBackoffRetry = new ExponentialBackoffRetry(1000, 3);

        // 使用fluent编程风格
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString("192.168.31.246:2181")
                .sessionTimeoutMs(50000)
                .connectionTimeoutMs(30000)
                .retryPolicy(exponentialBackoffRetry)
                .namespace("base")  // 独立的命名空间 /base
                .build();

        client.start();
        System.out.println("会话2创建了");

        // 删除节点
        String path = "/td-curator";
        client.delete().deletingChildrenIfNeeded().withVersion(-1).forPath(path);
        System.out.println("删除成功，删除的节点" + path);
    }
}
