package com.jsy.learn.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 *
 */
public class ZooKeeperApi {
    private final String host = "127.0.0.1:2181";
    private final String hosts = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
    private final Stat stat = new Stat();
    private ZooKeeper zooKeeper = null;
    private final CountDownLatch countDownLatch = new CountDownLatch(1);

    /**
     * 创建连接
     */
    @Before
    public void createZookeeper() {
        try {
            zooKeeper = new ZooKeeper(host, 5000, new Watcher() {
                //session级别watch，跟path 、node没有关系 , watch连接事件触发 - 回调方法
                @Override
                public void process(WatchedEvent watchedEvent) {
                    Event.KeeperState keeperState = watchedEvent.getState();

                    switch (keeperState) {    //连接状态
                        case Unknown:
                            break;
                        case Disconnected:    //连接失败
                            break;
                        case NoSyncConnected:
                            break;
                        case SyncConnected:   //连接成功
                            System.out.println("连接成功");
                            countDownLatch.countDown();
                            break;
                        case AuthFailed:      //认证失败
                            break;
                        case ConnectedReadOnly:
                            break;
                        case SaslAuthenticated:
                            break;
                        case Expired:         //会话过期
                            break;
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 节点类型:
     * PERSISTENT：持久节点
     * PERSISTENT_SEQUENTIAL：持久顺序节点
     * EPHEMERAL：临时节点
     * EPHEMERAL_SEQUENTIAL：临时顺序节点
     * <p>
     * api类型: 同步 异步
     * 同步接口创建节点时需要考虑接口抛出异常的情况，
     * 异步接口的异常体现在回调函数的ResultCode响应码中，比同步接口更健壮。
     */
    @Test
    public void syncApi() {
        this.createZookeeper();
        //阻塞等待zookeeper创建成功
        countDownLatch.countDown();
        try {
            // 同步创建永久节点
            String pathName = zooKeeper.create("/snode", "1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println("同步创建节点成功： " + pathName);

            // 获取节点data, 不监听
            byte[] data = zooKeeper.getData("/snode",false,stat);
            System.out.println("同步获取数据:"+new String(data));

            // 获取节点data, 并注册监听,只监听一次,需重新设置
            byte[] data1 = zooKeeper.getData("/snode", new NodeWatcher(), stat);
            System.out.println("同步获取数据:"+new String(data1));

            Thread.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class NodeWatcher implements Watcher{
        @Override
        public void process(WatchedEvent watchedEvent) {
            Event.EventType type = watchedEvent.getType();
            switch (type) {
                case None:
                    break;
                case NodeCreated:
                    System.out.println("节点创建watch: " + watchedEvent);
                    break;
                case NodeDeleted:
                    System.out.println("节点删除watch: " + watchedEvent);
                    break;
                case NodeDataChanged:
                    System.out.println("节点的数据变更watch: " + watchedEvent);
                    break;
                case NodeChildrenChanged:
                    System.out.println("子节点的数据变更watch: " + watchedEvent);
                    break;
            }

            try {
                //重新注册监听  this表示NodeWatcher本身
                byte[] data = zooKeeper.getData(watchedEvent.getPath(), this, stat);
                System.out.println("data : "+ new String(data));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @Test
    public void asyncApi() throws Exception {
        String path = "/asnode";
        // 异步创建持久节点      AsyncCallback.StringCallback
        zooKeeper.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT,
                new CreateCallBack(), "ZooKeeper async create znode.");
        Thread.sleep(1000);

        // 异步获取节点数据      AsyncCallback.DataCallback
        zooKeeper.getData(path, true, new DataCallBack(), "异步获取节点数据");
        Thread.sleep(100);

        // 异步更新节点数据      AsyncCallback.StatCallback
        zooKeeper.setData(path, "test123456".getBytes(), -1, new StatCallBack(), "异步更新节点数据");
        Thread.sleep(100);

        // 异步创建持久子节点
        zooKeeper.create(path+"/sun", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT,
                new CreateCallBack(), "ZooKeeper async create znode.");
        Thread.sleep(100);

        // 获取子节点
        List<String> childrenList = zooKeeper.getChildren("/asnode", true);
        System.out.println("获取子节点：" + childrenList);
        Thread.sleep(100);

        // 异步删除, 有子节点的节点不可删除  -1表示忽略版本   AsyncCallback.VoidCallback
        zooKeeper.delete("/asnode", -1, new DeleteCallBack(), "ZooKeeper async delete znode");
        Thread.sleep(3000);
    }

    /**
     * 权限控制
     * ZooKeeper ZooDefs.Ids权限类型说明：
     * OPEN_ACL_UNSAFE：完全开放的ACL，任何连接的客户端都可以操作该属性znode
     * CREATOR_ALL_ACL：只有创建者才有ACL权限
     * READ_ACL_UNSAFE：只能读取ACL
     */
    /*@Test
    public void auth_control_API() throws Exception {
        *//*String path = "/zk-setData-test";
        zooKeeper = new ZooKeeper(host, 5000, new ZooKeeperApi());
        zooKeeper.addAuthInfo("digest", "zoo:true".getBytes());
        zooKeeper.create(path, "init".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);*//*
        // 1）无权限信息访问
//    ZooKeeper zooKeeper1 = new ZooKeeper(host, 5000, new ZooKeeperApi());
//    System.out.println("访问结果：" + new String(zooKeeper1.getData(path, true, stat)));
        // 2）错误权限信息访问
//    ZooKeeper zooKeeper2 = new ZooKeeper(host, 5000, new ZooKeeperApi());
//    zooKeeper2.addAuthInfo("digest", "zoo:false".getBytes());
//    System.out.println("访问结果：" + new String(zooKeeper2.getData(path, true, stat)));
        // 3）正确权限信息访问
        *//*ZooKeeper zooKeeper3 = new ZooKeeper(host, 5000, new ZooKeeperApi());
        zooKeeper3.addAuthInfo("digest", "zoo:true".getBytes());
        System.out.println("访问结果：" + new String(zooKeeper3.getData(path, true, stat)));
        Thread.sleep(Integer.MAX_VALUE);*//*
    }*/

    /**
     * 异步创建节点回调
     */
    class CreateCallBack implements AsyncCallback.StringCallback {
        /**
         * @param rc   服务端响应码 0：接口调用成功，-4：客户端与服务端连接已断开，-110：指定节点已存在，-112：会话已过期
         * @param path 调用接口时传入的节点路径（原样输出）
         * @param ctx  调用接口时传入的ctx值（原样输出）
         * @param name 实际在服务端创建的节点名
         */
        @Override
        public void processResult(int rc, String path, Object ctx, String name) {
            System.out.println("StringCallback 异步创建结果：rc=" + rc + "，path=" + path + "，ctx=" + ctx + "，name=" + name);
            switch (rc) {
                case 0:
                    System.out.println("异步创建节点成功：" + name);
                    break;
                case -4:
                    System.out.println("异步创建: 客户端与服务端连接已断开");
                    break;
                case -110:
                    System.out.println("异步创建: 指定节点已存在");
                    break;
                case -112:
                    System.out.println("异步创建: 会话已过期");
                    break;
                default:
                    System.out.println("异步创建: 服务端响应码" + rc + "未知");
                    break;
            }
        }
    }

    /**
     * 删除节点异步回调
     */
    class DeleteCallBack implements AsyncCallback.VoidCallback {
        /**
         * @param rc   服务端响应码 0：接口调用成功，-4：客户端与服务端连接已断开，-110：指定节点已存在，-112：会话已过期
         * @param path 调用接口时传入的节点路径（原样输出）
         * @param ctx  调用接口时传入的ctx值（原样输出）
         */
        @Override
        public void processResult(int rc, String path, Object ctx) {
            System.out.println("删除结果：rc=" + rc + "，path=" + path + "，ctx=" + ctx);
            switch (rc) {
                case 0:
                    System.out.println("异步删除: 成功");
                    break;
                case -4:
                    System.out.println("异步删除: 客户端与服务端连接已断开");
                    break;
                case -112:
                    System.out.println("异步删除: 会话已过期");
                    break;
                default:
                    System.out.println("异步删除: 服务端响应码" + rc + "未知");
                    break;
            }
        }
    }

    /**
     * 获取子节点异步回调
     */
    class ChildrenCallBack implements AsyncCallback.Children2Callback {
        /**
         * @param rc           服务端响应码 0：接口调用成功，-4：客户端与服务端连接已断开，-110：指定节点已存在，-112：会话已过期
         * @param path         调用接口时传入的节点路径（原样输出）
         * @param ctx          调用接口时传入的ctx值（原样输出）
         * @param childrenList 子节点列表
         * @param stat         节点状态，由服务器端响应的新stat替换
         */
        @Override
        public void processResult(int rc, String path, Object ctx, List<String> childrenList, Stat stat) {
            System.out.println("获取结果：rc=" + rc + "，path=" + path + "，ctx=" + ctx + "，childrenList=" + childrenList + "，stat=" + stat);
            switch (rc) {
                case 0:
                    System.out.println("子节点获取成功：" + childrenList);
                    break;
                case -4:
                    System.out.println("客户端与服务端连接已断开");
                    break;
                case -112:
                    System.out.println("会话已过期");
                    break;
                default:
                    System.out.println("服务端响应码" + rc + "未知");
                    break;
            }
        }
    }

    /**
     * 获取节点数据异步回调
     */
    class DataCallBack implements AsyncCallback.DataCallback {
        /**
         * @param rc   服务端响应码 0：接口调用成功，-4：客户端与服务端连接已断开，-110：指定节点已存在，-112：会话已过期
         * @param path 调用接口时传入的节点路径（原样输出）
         * @param ctx  调用接口时传入的ctx值（原样输出）
         * @param data 节点数据
         * @param stat 节点状态，由服务器端响应的新stat替换
         */
        @Override
        public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
            System.out.println("获取结果：rc=" + rc + "，path=" + path + "，ctx=" + ctx + "，data=" + new String(data) + "，stat=" + stat);
            System.out.println("czxid=" + stat.getCzxid() + "，mzxid=" + stat.getMzxid() + "，version=" + stat.getVersion());
            switch (rc) {
                case 0:
                    System.out.println("节点数据获取成功：" + new String(data));
                    break;
                case -4:
                    System.out.println("客户端与服务端连接已断开");
                    break;
                case -112:
                    System.out.println("会话已过期");
                    break;
                default:
                    System.out.println("服务端响应码" + rc + "未知");
                    break;
            }
        }
    }

    /**
     * 更新节点数据异步回调
     */
    class StatCallBack implements AsyncCallback.StatCallback {
        /**
         * @param rc   服务端响应码 0：接口调用成功，-4：客户端与服务端连接已断开，-110：指定节点已存在，-112：会话已过期
         * @param path 调用接口时传入的节点路径（原样输出）
         * @param ctx  调用接口时传入的ctx值（原样输出）
         * @param stat 节点状态，由服务器端响应的新stat替换
         */
        @Override
        public void processResult(int rc, String path, Object ctx, Stat stat) {
            System.out.println("更新结果：rc=" + rc + "，path=" + path + "，ctx=" + ctx + "，stat=" + stat);
            System.out.println("czxid=" + stat.getCzxid() + "，mzxid=" + stat.getMzxid() + "，version=" + stat.getVersion());
            switch (rc) {
                case 0:
                    System.out.println("节点数据设置成功");
                    break;
                case -4:
                    System.out.println("客户端与服务端连接已断开");
                    break;
                case -112:
                    System.out.println("会话已过期");
                    break;
                default:
                    System.out.println("服务端响应码" + rc + "未知");
                    break;
            }
        }
    }

    @Test
    public void syncApi001() {

        try {
            zooKeeper.create("/lock/test","".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL,new AsyncCallback.StringCallback(){
                @Override
                public void processResult(int i, String s, Object o, String s1) {
                    System.out.println(s);
                    System.out.println(s1);
                }
            },"ZooKeeper async create znode");


            zooKeeper.getChildren("/lock",false,new AsyncCallback.Children2Callback(){
                @Override
                public void processResult(int rc, String path, Object ctx, List<String> children, Stat stat) {
                    System.out.println(path);
                    System.out.println(children);
                }
            } ,"ZooKeeper async getChildren znode");
        } catch (Exception e) {
            e.printStackTrace();
        }

        while (true){

        }
    }

}
