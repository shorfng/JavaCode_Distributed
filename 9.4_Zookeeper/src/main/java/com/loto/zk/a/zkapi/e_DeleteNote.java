package com.loto.zk.a.zkapi;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

/**
 * Author：蓝田_Loto
 * <p>Date：2021-10-19 21:29</p>
 * <p>PageName：e_DeleteNote.java</p>
 * <p>Function：删除节点</p>
 */

public class e_DeleteNote implements Watcher {
    private static ZooKeeper zooKeeper;

    /**
     * 建立会话
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        zooKeeper = new ZooKeeper("192.168.31.246:2181", 5000, new e_DeleteNote());
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

        // 当连接创建了，服务端发送给客户端 SyncConnected 事件
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            // 更新数据节点内容的方法
            try {
                deleteNoteSync();
            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 删除节点
     */
    private void deleteNoteSync() throws KeeperException, InterruptedException {
        // 判断节点是否存在
        Stat stat = zooKeeper.exists("/td-persistent/aaa", false);
        System.out.println(stat == null ? "该节点不存在" : "该节点存在");

        // 删除节点
        if (stat != null) {
            zooKeeper.delete("/td-persistent/aaa", -1);
        }

        Stat stat2 = zooKeeper.exists("/td-persistent/aaa", false);
        System.out.println(stat2 == null ? "该节点不存在" : "该节点存在");
    }
}
