package com.trace.agent.myagent.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;


@Component
public class FeignInterceptor implements RequestInterceptor {

    public final static String MDC_TRACE_ID = "traceId";

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String traceId = MDC.get(MDC_TRACE_ID);

        System.out.println("发生请求:" + traceId);

        requestTemplate.header(MDC_TRACE_ID, traceId);
    }
}
