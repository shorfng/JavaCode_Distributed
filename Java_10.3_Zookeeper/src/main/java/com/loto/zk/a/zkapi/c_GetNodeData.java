package com.loto.zk.a.zkapi;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.List;

/**
 * Author：蓝田_Loto
 * <p>Date：2021-10-19 21:01</p>
 * <p>PageName：c_GetNodeData.java</p>
 * <p>Function：获取节点数据</p>
 */

public class c_GetNodeData implements Watcher {
    private static ZooKeeper zooKeeper;

    /**
     * 建立会话
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        zooKeeper = new ZooKeeper("192.168.31.246:2181", 5000, new c_GetNodeData());
        System.out.println(zooKeeper.getState());

        Thread.sleep(Integer.MAX_VALUE);
        System.out.println("客户端与服务端会话真正建立了");
    }


    /**
     * 回调方法：处理来自服务器端的 watcher 通知
     */
    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("process方法执行了...");

        // 子节点列表发生改变时，服务器端会发出 NodeChildrenChanged 事件通知，会重新获取子节点列表
        // 同时注意：通知是一次性的，需要反复注册监听
        if (watchedEvent.getType() == Event.EventType.NodeChildrenChanged) {
            List<String> children = null;
            try {
                children = zooKeeper.getChildren("/td-persistent", true);
            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(children);
        }

        // 当连接创建了，服务端发送给客户端 SyncConnected 事件
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            // 获取节点数据的方法
            try {
                // 获取某个节点的内容
                getNodeData();

                // 获取节点的子节点列表
                getChildrens();
            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取某个节点的内容
     */
    private void getNodeData() throws KeeperException, InterruptedException {
        byte[] data = zooKeeper.getData("/td-persistent", false, null);
        System.out.println(new String(data));
    }

    /**
     * 获取某个节点的子节点列表
     */
    public static void getChildrens() throws KeeperException, InterruptedException {
        List<String> children = zooKeeper.getChildren("/td-persistent", true);
        System.out.println(children);
    }
}
