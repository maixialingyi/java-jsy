package com.jsy.learn;

import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("EncodingFilter - init  encoding: "+ filterConfig.getInitParameter("encoding"));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("EncodingFilter - doFilter");
        filterChain.doFilter(servletRequest, servletResponse);//交给下一个过滤器
    }

    @Override
    public void destroy() {
        System.out.println("EncodingFilter - destroy");
    }
}
