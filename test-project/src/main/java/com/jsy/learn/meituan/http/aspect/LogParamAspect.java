package com.jsy.learn.meituan.http.aspect;

import com.jsy.learn.meituan.http.exception.BaseBizException;
import com.jsy.learn.meituan.http.exception.BizCode;
import com.jsy.learn.meituan.util.ExceptionUtil;
import com.jsy.learn.meituan.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 统一日志打印切面，统一封装返回结果
 *
 * @author: songzeqi
 * @Date: 2018-06-27 下午3:15
 */
@Order(value = Ordered.LOWEST_PRECEDENCE - 1)
@Aspect
@Slf4j
@Component
public class LogParamAspect {


    /*@Around(value = "execution(public * com.mid.catexception.adapter.http.controller.TestExceptionController.*(..)) ||"
                + "execution(public * com.mid.catexception.adapter.http.controller.TestCheckExceptionController.*(..)) ||"
                + "execution(public * com.mid.catexception.adapter.http.controller.TestUnCheckExceptionController.*(..))"
    )*/
    @Around(value = "execution(public * com.mid.catexception.adapter.http.controller.TestExceptionController.*(..))")
    public Result logParam(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String className = joinPoint.getTarget().getClass().getName();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String methodName = methodSignature.getMethod().getName();
        String url = request.getRequestURL().toString();

        //请求日志
        String requestParam = logRequest(request, joinPoint, className, methodName, url);

        Object object;
        Result result;
        try {
            object = joinPoint.proceed();
            //cat监控 event business埋点
            //CatUtil.logHttpRequestEventSuccess(joinPoint);
            result = Result.success(BizCode.SUCCESS.getCode(),BizCode.SUCCESS.getMessage(),object);

        }  catch (BaseBizException e) {
            log.warn("Http请求返回业务异常, code={}, message={}, url={}, className={}, methodName={}, request={}", e.getCode(),
                    e.getMessage(), url, className, methodName, requestParam, ExceptionUtil.getStackTrace(e));
            //cat监控 event business埋点  监控报警层面，warn级别，累计到阈值报警
            //CatUtil.logHttpRequestFailure(joinPoint, e);
            result = Result.fail(e.getCode(), e.getBizCode().getMessage());
        } catch (Exception e) {
            //cat监控 event business埋点   监控报警层面，error级别，0容忍。
            //CatUtil.logHttpRequestFailure(joinPoint, e);
            log.error("Http请求返回运行时异常, message={}, url={}, className={}, methodName={}, request={}", e.getMessage(), url,
                    className, methodName, requestParam, e);
            result = Result.fail(BizCode.ERROR.getCode(),BizCode.ERROR.getMessage());
        }
        //响应日志
        logRequestResponse(result, className, methodName, url);
        return result;
    }

    private static String logRequest(HttpServletRequest httpRequest, ProceedingJoinPoint joinPoint, String className,
            String methodName, String url) {
        String paramsStr = "";
        Map<String, String> params = new HashMap<>();
        try {
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            // Get请求参数
            @SuppressWarnings("rawtypes")
            Enumeration enume = httpRequest.getParameterNames();
            while (null != enume && enume.hasMoreElements()) {
                Object element = enume.nextElement();
                if (null != element) {
                    String paramName = (String) element;
                    String paramValue = httpRequest.getParameter(paramName);
                    params.put(paramName, paramValue);
                }
            }

            // Post请求参数
            Object[] args = joinPoint.getArgs();
            for (int i = 0; i < args.length; ++i) {
                final Object curArg = args[i];
                MethodParameter param = new MethodParameter(methodSignature.getMethod(), i);
                RequestBody requestBody = param.getParameterAnnotation(RequestBody.class);
                if (requestBody != null) {
                    params.put("requestBody", curArg.toString());
                }
            }
            paramsStr = params.toString();
            log.info("Http请求参数，url={}, className={}, methodName={}, request={}", url, className, methodName, paramsStr);
        } catch (Exception e) {
            log.error("[Http请求]打印请求参数失败", e);
        }
        return paramsStr;
    }

    private static void logRequestResponse(Result result, String className, String methodName,
            String url) {
        try {
            log.info("Http请求返回结果，url={}, className={}, methodName={}, result={}", url, className, methodName,
                    result);
        } catch (Exception e) {
            log.error("[Http请求]打印请求结果失败", e);
        }
    }
}
