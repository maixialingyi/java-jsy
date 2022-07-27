package com.red.packet;

public class DependentDbImpl {

    /**
     * 活动表 {活动id,活动开始时间,活动结束时间,总金额,红包库存}
     */

    /**
     *  TODO:  DB事务 + DB锁方式
     *
     *  隔离级别: 考虑到并发性能,采用读已提交
     *  传播行为: 默认的REQUIRED，没有事务创建新事务，有则沿用
     *  @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
     *  {
     *      1. 加锁查询红包库存:    select for update
     *      2. 有库存,减红包库存:   update table set stock = stock - 1 where id = #{id}
     *      3. 添加被抢红包信息     inset into
     *  }
     */

    /**
     *  TODO:  DB事务 + 乐观锁version，只操作一次
     *
     *  @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
     *  {
     *      1. 查询红包库存:        select
     *      2. 有库存,减红包库存:    update table set   stock = stock - 1,
     * 		                                         version = version + 1
     * 		                                   where id = #{id}
     * 		                                         and version = #{version}
     *      3. 根据修改返回数量判断是否减库存成功
     *      4. 成功,添加被抢红包信息   inset into
     *  }
     */

    /**
     *  TODO:  DB事务 + 乐观锁version，设置执行时间范围
     *  记录开始时间
     *  while(true){
     *      生成当前时间
     *      if(开始时间 - 当前时间 > 执行时间) 结束
     *  }
     */

    /**
     *  TODO:  DB事务 + 乐观锁version，设置执行次数
     */

}
