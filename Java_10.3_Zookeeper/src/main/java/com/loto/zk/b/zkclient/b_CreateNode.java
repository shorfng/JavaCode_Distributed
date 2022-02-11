package com.loto.zk.b.zkclient;

import org.I0Itec.zkclient.ZkClient;

/**
 * Author：蓝田_Loto
 * <p>Date：2021-10-19 21:44</p>
 * <p>PageName：b_CreateNode.java</p>
 * <p>Function：创建节点 - zkclient </p>
 */

public class b_CreateNode {
    public static void main(String[] args) {
        // 建立会话
        // zkClient 通过对 zookeeperAPI 内部封装，将这个异步创建会话的过程同步化了
        ZkClient zkClient = new ZkClient("192.168.31.246:2181");
        System.out.println("会话被创建了..");

        // 创建节点：是否要创建父节点，如果值为true，那么就会递归创建节点
        zkClient.createPersistent("/td-zkclient/c1", true);
        System.out.println("节点递归创建完成");
    }
}
