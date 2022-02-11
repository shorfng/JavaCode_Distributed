package com.loto.zk.a.zkapi;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

/**
 * Author：蓝田_Loto
 * <p>Date：2021-10-19 21:21</p>
 * <p>PageName：d_UpdateNodeData.java</p>
 * <p>Function：修改节点数据</p>
 */

public class d_UpdateNodeData implements Watcher {
    private static ZooKeeper zooKeeper;

    /**
     * 建立会话
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        zooKeeper = new ZooKeeper("192.168.31.246:2181", 5000, new d_UpdateNodeData());
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
                updateNodeSync();
            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 更新数据节点内容
     */
    private void updateNodeSync() throws KeeperException, InterruptedException {
        byte[] data = zooKeeper.getData("/td-persistent", false, null);
        System.out.println("修改前的值：" + new String(data));

        // 修改/td-persistent 的数据 stat: 状态信息对象
        Stat stat = zooKeeper.setData("/td-persistent", "客户端修改了节点数据".getBytes(), -1);

        byte[] data2 = zooKeeper.getData("/td-persistent", false, null);
        System.out.println("修改后的值：" + new String(data2));
    }
}
