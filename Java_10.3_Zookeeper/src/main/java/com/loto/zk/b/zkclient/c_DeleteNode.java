package com.loto.zk.b.zkclient;

import org.I0Itec.zkclient.ZkClient;

/**
 * Author：蓝田_Loto
 * <p>Date：2021-10-19 21:44</p>
 * <p>PageName：b_CreateNode.java</p>
 * <p>Function：创建节点 - zkclient </p>
 */

public class c_DeleteNode {
    public static void main(String[] args) {
        // 建立会话
        // zkClient 通过对 zookeeperAPI 内部封装，将这个异步创建会话的过程同步化了
        ZkClient zkClient = new ZkClient("192.168.31.246:2181");
        System.out.println("会话被创建了..");

        // 递归删除节点：先删除所有⼦节点（如果存在），再删除父节点
        String path = "/td-zkclient/c1";
        zkClient.createPersistent(path + "/c11");
        zkClient.deleteRecursive(path);
        System.out.println("递归删除成功");
    }
}
