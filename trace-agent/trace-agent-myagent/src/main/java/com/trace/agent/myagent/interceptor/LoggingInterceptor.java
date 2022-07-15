package com.trace.agent.myagent.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class LoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    public final static String MDC_TRACE_ID = "traceId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String traceId = request.getHeader(MDC_TRACE_ID);
        if (StringUtils.isEmpty(traceId)) {
            traceId = UUID.randomUUID().toString().replaceAll("-", "");
        }

        MDC.put(MDC_TRACE_ID, traceId);

        logger.info("LoggingInterceptor.preHandle，request：", request);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.info("LoggingInterceptor.afterCompletion");
        MDC.clear();
    }
}