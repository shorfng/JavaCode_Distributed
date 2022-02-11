package com.loto.zk.b.zkclient;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;

/**
 * Author：蓝田_Loto
 * <p>Date：2021-10-19 22:02</p>
 * <p>PageName：d_GetNodeChildren.java</p>
 * <p>Function：获取子节点、注册监听事件 - zkclient</p>
 */

public class d_GetNodeChildren {
    public static void main(String[] args) throws InterruptedException {
        // 建立会话
        // zkClient 通过对 zookeeperAPI 内部封装，将这个异步创建会话的过程同步化了
        ZkClient zkClient = new ZkClient("192.168.31.246:2181");
        System.out.println("会话被创建了..");

        // 获取子节点列表
        List<String> children = zkClient.getChildren("/td-zkclient");
        System.out.println(children);

        // 注册监听事件
        // 客户端可以对一个不存在的节点进行子节点变更的监听，只要该节点的子节点列表发生变化，或者该节点本身被创建或者删除，都会触发监听
        zkClient.subscribeChildChanges("/td-zkclient-get", new IZkChildListener() {
            public void handleChildChange(String parentPath, List<String> list) {
                System.out.println(parentPath + "的子节点列表发生了变化，变化后的子节点列表为" + list);
            }
        });

        // 测试
        zkClient.createPersistent("/td-zkclient-get");
        Thread.sleep(1000);

        zkClient.createPersistent("/td-zkclient-get/c1");
        Thread.sleep(1000);
    }
}
