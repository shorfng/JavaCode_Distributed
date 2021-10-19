package com.loto.zk.b.zkclient;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

/**
 * Author：蓝田_Loto
 * <p>Date：2021-10-19 23:40</p>
 * <p>PageName：e_operate.java</p>
 * <p>Function：</p>
 */

public class e_operate {
    public static void main(String[] args) throws InterruptedException {
        // 建立会话
        // zkClient 通过对 zookeeperAPI 内部封装，将这个异步创建会话的过程同步化了
        ZkClient zkClient = new ZkClient("192.168.31.246:2181");
        System.out.println("会话被创建了..");

        // 判断节点是否存在
        String path = "/td-zkClient-Ep";
        boolean exists = zkClient.exists(path);

        // 创建临时节点
        if (!exists) {
            zkClient.createEphemeral(path, "123");
        }

        // 读取节点内容
        Object o = zkClient.readData(path);
        System.out.println(o);

        // 注册监听
        zkClient.subscribeDataChanges(path, new IZkDataListener() {
            // 当节点数据内容发生变化时，执行的回调方法
            public void handleDataChange(String s, Object o) throws Exception {
                System.out.println(s + "该节点内容被更新，更新的内容" + o);
            }

            // 当节点被删除时，会执行的回调方法
            public void handleDataDeleted(String s) throws Exception {
                System.out.println(s + "该节点被删除");
            }
        });

        // 更新节点内容
        zkClient.writeData(path, "456");
        Thread.sleep(1000);

        // 删除节点
        zkClient.delete(path);
        Thread.sleep(1000);
    }
}
