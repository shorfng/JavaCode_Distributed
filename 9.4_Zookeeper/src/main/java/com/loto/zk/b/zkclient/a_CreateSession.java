package com.loto.zk.b.zkclient;

import org.I0Itec.zkclient.ZkClient;

/**
 * Author：蓝田_Loto
 * <p>Date：2021-10-19 19:47</p>
 * <p>PageName：a_CreateSession.java</p>
 * <p>Function：建立会话 - zkclient </p>
 */

public class a_CreateSession {
    /**
     * 建立会话
     */
    public static void main(String[] args) {
        // zkClient 通过对 zookeeperAPI 内部封装，将这个异步创建会话的过程同步化了
        ZkClient zkClient = new ZkClient("192.168.31.246:2181");
        System.out.println("会话被创建了..");
    }
}
