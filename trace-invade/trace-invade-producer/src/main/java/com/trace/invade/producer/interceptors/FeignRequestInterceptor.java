package com.trace.invade.producer.interceptors;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Component
public class FeignRequestInterceptor implements RequestInterceptor {

    public final static String MDC_TRACE_ID = "traceId";

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String traceId = MDC.get(MDC_TRACE_ID);
        requestTemplate.header(MDC_TRACE_ID, traceId);
    }
}