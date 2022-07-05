package com.jsy.learn.zxadapter.request.enums;

public enum ActionAlias {

    /**
     * 创建
     */
    create,

    /**
     * 变配
     */
    modify,

    /**
     * 续费
     */
    renew,

    /**
     * 删除（释放）
     */
    release,

    /**
     * 停止
     */
    stop,

    /**
     * 查询
     */
    query,

    /**
     * 创建交换机
     */
    createVSwitch,

    /**
     * 查询交换机
     */
    describeVSwitch,

    /**
     * 删除交换机
     */
    deleteVSwitch,

    /**
     * 修改交换机
     */
    modifyVSwitch,

    /**
     * 查询路由器列表
     */
    describeVRouter,

    /**
     * 修改路由器信息
     */
    modifyVRouter,

    /**
     * 查询详情
     */
    queryDetail,

    /**
     * 查询列表
     */
    queryList,

    /**
     * 查询类型
     */
    queryType,

    /**
     * 查询树
     */
    queryTree,

    describe,

    describeRegion,

    describeZone,

    modifyPayType,

    consoleUrl,

    /**
     * 查询某一可用区的资源列表
     */
    describeAvailableResource,

    /**
     * 创建虚拟交换机
     */
    createVswitch,

    /**
     * 查询交换机
     */
    describeVswitch,

    /**
     * 查询服务实例属性
     */
    describeAttribute,
    /**
     * 创建磁盘
     */
    createDisk,
    /**
     * 删除磁盘
     */
    deleteDisk,
    /**
     * 查询磁盘
     */
    describeDisk,
    /**
     * 挂在磁盘
     */
    attachDisk,
    /**
     * 卸载磁盘
     */
    detachDisk,
    /**
     * 扩容磁盘
     */
    resizeDisk,
    /**
     * 申请弹性公网IP
     */
    allocateEipAddress,
    /**
     * 修改弹性公网IP信息
     */
    modifyEipAddressAttribute,
    /**
     * 删除指定EIP
     */
    releaseEipAddress,
    /**
     * 查询指定地域已创建的EIP
     */
    describeEipAddresses,
    /**
     * Apsara
     */
    queryImageList,

    queryRole,

    queryVswitchList,

    queryZone,

    queryRegion,

    /* aliyun aas */

    /**
     * 创建账户AccessKey
     */
    createAccount,

    /**
     * 为阿里云帐号创建AK
     */
    createAKForAccount,

    /**
     * 列出合作伙伴创建的帐号
     */
    listAccount,

    /**
     * 查询阿里云账号AK列表
     */
    listAccessKeysForAccount,

    updateStatusForAccount,

    /* aliyun ecs */
    /**
     * 查询实例规格
     */
    describeInstanceTypes,

    /**
     * 查询镜像资源
     */
    describeImages,

    /**
     * 创建安全组
     */
    createSecurityGroup,

    /**
     * 删除安全组
     */
    deleteSecurityGroup,

    /**
     * 安全组列表信息
     */
    describeSecurityGroups,

    /**
     * 查询云服务器 ECS 提供的实例规格族资源
     */
    describeInstanceTypeFamilies,

    /**
     * 升级或降低预付费实例规格
     */
    modifyPrePaid,

    /**
     * 按量付费实例的实例规格和公网带宽大小。
     */
    modifyPostPaid,

    /**
     *
     */
    modifyAttribute,

    /**
     * 修改实例状态
     */
    modifyStatus,
    /**
     * 修改实例名称
     */
    modifyName,
    modifyInternetSpec,
    modifyInstanceSpec,
    modifyVersion,

    /**
     * 创建路由器接口
     */
    createRouterInterface,

    /* slb */

    /**
     * 修改负载均衡的实例规格
     */
    modifyInstance,

    /**
     * 修改公网负载均衡实例的计费方式
     */
    modifyInternet,
    /**
     * 获取动态参数
     */
    dynamicParam,
    /**
     * 动态参数校验
     */
    dynamicValid
}
