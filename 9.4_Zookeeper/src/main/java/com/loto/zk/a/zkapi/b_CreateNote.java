package com.loto.zk.a.zkapi;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * Author：蓝田_Loto
 * <p>Date：2021-10-19 19:57</p>
 * <p>PageName：b_CreateNote.java</p>
 * <p>Function：创建节点</p>
 */

public class b_CreateNote implements Watcher {
    private static ZooKeeper zooKeeper;

    /**
     * 建立会话
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        zooKeeper = new ZooKeeper("192.168.31.246:2181", 5000, new b_CreateNote());
        System.out.println(zooKeeper.getState());

        Thread.sleep(Integer.MAX_VALUE);
        System.out.println("客户端与服务端会话真正建立了");
    }

    /**
     * 回调方法：处理来自服务器端的 watcher 通知
     */
    @Override
    public void process(WatchedEvent watchedEvent) {
        // 当连接创建了，服务端发送给客户端 SyncConnected 事件
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            System.out.println("process方法执行了...");

            // 创建节点
            try {
                createNoteSync();
            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建节点的方法
     */
    private static void createNoteSync() throws KeeperException, InterruptedException {
        // 持久节点
        String note_persistent = zooKeeper.create("/td-persistent", "持久节点内容".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        // 临时节点
        String note_ephemeral = zooKeeper.create("/td-ephemeral", "临时节点内容".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        // 持久顺序节点
        String note_persistent_sequential = zooKeeper.create("/td-persistent_sequential", "持久顺序节点内容".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);

        System.out.println("创建的持久节点" + note_persistent);
        System.out.println("创建的临时节点" + note_ephemeral);
        System.out.println("创建的持久顺序节点" + note_persistent_sequential);
    }
}
