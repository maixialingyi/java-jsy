package com.jsy.learn.zxadapter.request;

import com.jsy.learn.zxadapter.request.enums.ActionAlias;
import com.jsy.learn.zxadapter.request.enums.ServiceAlias;
import com.jsy.learn.zxadapter.request.enums.SupplierAlias;
import lombok.Data;
import org.springframework.http.HttpMethod;

@Data
public abstract class AbstractRequest {
    public AbstractRequest(SupplierAlias supplierAlias, ServiceAlias serviceAlias, ActionAlias actionAlias) {
        this.supplierAlias = supplierAlias;
        this.serviceAlias = serviceAlias;
        this.actionAlias = actionAlias;
    }

    /**
     * 供应商别名
     */
    private SupplierAlias supplierAlias;

    /**
     * 服务别名
     */
    private ServiceAlias serviceAlias;

    /**
     * 行为别名
     */
    private ActionAlias actionAlias;

    /**
     * 请求方式
     */
    private HttpMethod requestMethod;

    /**
     * 参数类型
     */
    private ParamType paramType = ParamType.PARAM;

    /**
     * 请求路径
     */
    protected String pathInfo;

    /**
     * 根请求地址URI
     */
    private String baseUri;

    /**
     * 回调标识
     */
    //private CallbackMark callbackMark;

    /**
     * 处理业务请求流水号
     *
     * @param requestId
     * 		业务请求流水号
     */
    public void handleWithRequestId(String requestId) {
    }

    /**
     * 获取中信实例ID
     *
     * @return 中信实例ID
     */
    public String getCiticInstanceId() {
        return null;
    }


    /**
     * 回调获取请求url
     *
     * @return 请求地址URI
     */
    public String requestPathInfo() {
        // 注：不能使用this.pathInfo。原因：实际特殊场景存在子类覆盖getPathInfo()方法的场景
        return this.baseUri + this.getPathInfo();
    }

    /**
     * 响应结果接收类
     *
     * @return 响应结果接收类
     */
    //public abstract Class<Resp> responseClass();

    public enum ParamType {
        //body请求
        JSON,
        //form请求
        FORM,
        //get query
        PARAM
    }

    /**
     * 供应商的租户id,生成逻辑由供应商维护,中信的租户id与供应商租户id的映射关系,在服务接入前由双方确认
     * ,supplierOrgId、supplierUserId 两者必须具备一个
     */
    private String supplierOrgId;

    /**
     * 供应商的用户id,生成逻辑由供应商维护,中信的用户id与供应商用户id的映射关系,
     * 在服务接入前由双方确认,supplierOrgId、supplierUserId 两者必须具备一个。
     */
    private String supplierUserId;

    /**
     * 控制台类型,根据传入的类型返回当前类型对应的控制台url。service,instance
     */
    //private ConsoleType consoleType;

    /**
     * 商品id，源数据由中信提供
     */
    private String serviceId;

    /**
     * 中信实例Id
     */
    private String instanceId;

    /**
     * 获取特殊服务回调路径(srvmgt)
     *
     * @return
     */
    public String getCallBackUrl() {
		//TODO: 回调及各种操作应属于不同接口。使用时根据接口使用，需要改进
        return null;
    }


}
