package com.loto.zk.c.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * Author：蓝田_Loto
 * <p>Date：2021-10-20 13:40</p>
 * <p>PageName：a_CreateSession.java</p>
 * <p>Function：</p>
 */


public class a_CreateSession {
    // 创建会话
    public static void main(String[] args) {
        // 不使用 fluent 编程风格
        RetryPolicy exponentialBackoffRetry = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient("192.168.31.246:2181", exponentialBackoffRetry);
        curatorFramework.start();
        System.out.println("会话1被建立了...");

        // 使用 fluent 编程风格
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString("192.168.31.246:2181")
                .sessionTimeoutMs(50000)
                .connectionTimeoutMs(30000)
                .retryPolicy(exponentialBackoffRetry)
                .namespace("base")  // 独立的命名空间 /base
                .build();

        client.start();
        System.out.println("会话2创建了...");
    }
}
