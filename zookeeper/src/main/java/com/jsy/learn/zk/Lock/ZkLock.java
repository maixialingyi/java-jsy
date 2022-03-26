package com.jsy.learn.zk.Lock;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ZkLock {
    ZooKeeper zk = ZkFactory.getZK();
    String threadName = Thread.currentThread().getName();
    CountDownLatch countDownLatch = new CountDownLatch(1);
    String lockNamePath = null;
    String viePath = null;
    String nodePath = null;
    String nodeName = null;
    String beforNodeName = null;

    public void lock(String lockName){
        try {
            lockNamePath = ZkFactory.ZK_LOCK_ROOT_PATH + lockName;
            viePath = ZkFactory.ZK_LOCK_ROOT_PATH + lockName + ZkFactory.VIE_PATH;
            // 创建临时有序节点
            nodePath = zk.create(viePath, threadName.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println(threadName + " 创建的临时有序节点全路径 "+nodePath);
            nodeName = nodePath.split("/")[3];
            System.out.println(threadName + " 创建的临时有序节点名 "+nodeName);

            List<String> childrenList = zk.getChildren(lockNamePath, false);
            // childrenList为乱序的,需从小到大排序
            Collections.sort(childrenList);
            System.out.println(threadName + " 获取所有临时有序节点名 : "+ childrenList);
            int index = childrenList.indexOf(nodeName);

            if(index == 0){ //是第一个则代表获取锁
                System.out.println(threadName + " 获取锁");
            }else{ //如果不为第一个,监控前一节点,阻塞等待监控触发结束阻塞
                //获取前节点
                beforNodeName = childrenList.get(index-1);
                //todo 存在判断前已经被删除的情况   处理这种情况
                Stat exists = zk.exists("/" + beforNodeName, new Watcher() {
                    @Override
                    public void process(WatchedEvent event) {
                        //节点删除事件
                        if (Event.EventType.NodeDeleted.equals(event.getType()) && event.getPath().equals(beforNodeName)) {
                            countDownLatch.countDown();
                            System.out.println(threadName + " 获取锁");
                        }
                    }
                });
                // 前节点已经被删除
                if(exists == null){
                    System.out.println(threadName + " 获取锁");
                    return;
                }
                //阻塞
                countDownLatch.await();
            }
        } catch (InterruptedException | KeeperException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * 解锁
     */
    public void unLock(){
        try {
            zk.delete(nodePath,-1);
            System.out.println(threadName + " 解锁 "+nodePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
