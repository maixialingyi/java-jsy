package com.jsy.learn.zk.Lock;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

public class ZkFactory {

    private static volatile ZooKeeper zk;
    private static String address = "127.0.0.1:2181";
    //private static String address = "192.168.150.11:2181,192.168.150.12:2181,192.168.150.13:2181,192.168.150.14:2181/testLock";
    //zk初始化: 创建连接(返回响应) + 同步数据
    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    /**
     * /zklockroot         锁根目录(持久节点)
     *      /应用id+锁名称   持久节点
     *          /vie       临时有序节点
     */
    public static String ZK_LOCK_ROOT_PATH = "/zklockroot";
    public static String VIE_PATH = "/vie";

    //配置中获取锁名称 伪代码
    private static List<String> lockNameList = new ArrayList<>();
    static{
        lockNameList.add("/appId"+"payLock");
        lockNameList.add("/appId"+"openUserLock");
    }


    public static ZooKeeper getZK(){
        if(zk == null){
            synchronized (ZkFactory.class){
                if(zk == null){
                    try {
                        zk = new ZooKeeper(address, 1000, new Watcher() { //session watch
                            @Override
                            public void process(WatchedEvent event) {
                                if(Event.KeeperState.SyncConnected.equals(event.getState())){
                                    System.out.println("zookeeper连接完成");
                                    // ----------创建锁根目录-----------
                                    try {
                                        // 判断根节点是否存在
                                        Stat rootStat = zk.exists(ZK_LOCK_ROOT_PATH, false);
                                        if (rootStat == null) {
                                            // 同步创建    已创建过会抛异常 NodeExistsException
                                            String s = zk.create(ZK_LOCK_ROOT_PATH, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                                            System.out.println("创建根节点完成: " + s);
                                        }
                                        // 生成锁节点
                                        for(String lockName : lockNameList){
                                            Stat lockNameStat = zk.exists(ZK_LOCK_ROOT_PATH + lockName, false);
                                            if (lockNameStat == null) {
                                                String s = zk.create(ZK_LOCK_ROOT_PATH + lockName, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                                                System.out.println("创建锁节点完成: " + s);
                                            }
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    //结束阻塞
                                    countDownLatch.countDown();
                                }
                            }
                        });
                        //创建连接,等待数据同步完成
                        countDownLatch.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return zk;
    }

    public static void main(String[] args) {
         ZkFactory.getZK();
    }
}
