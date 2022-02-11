package com.loto.zk.a.zkapi;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Author：蓝田_Loto
 * <p>Date：2021-10-19 19:47</p>
 * <p>PageName：a_CreateSession.java</p>
 * <p>Function：建立会话</p>
 */

public class a_CreateSession implements Watcher {
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    /**
     * 建立会话
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper("192.168.31.246:2181", 5000, new a_CreateSession());
        System.out.println(zooKeeper.getState());

        // 计数工具类：CountDownLatch（不让main方法结束，让线程处于等待阻塞）
        countDownLatch.await();

        System.out.println("客户端与服务端会话真正建立了");
    }

    /**
     * 回调方法：处理来自服务器端的 watcher 通知
     */
    @Override
    public void process(WatchedEvent watchedEvent) {
        // 当连接创建了，服务端发送给客户端 SyncConnected 事件
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            // 解除主程序在CountDownLatch上的等待阻塞
            System.out.println("process方法执行了...");
            countDownLatch.countDown();
        }
    }
}
