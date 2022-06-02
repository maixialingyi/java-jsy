package com.jsy.learn;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Enumeration;

public class HelloWorldServlet extends HttpServlet {

    //不区分GET  POST  PUT  DELETE
    public void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        req.setCharacterEncoding("utf-8");

        System.out.println("请求方式" + req.getMethod());
        System.out.println("访问路径" + req.getServletPath());
        System.out.println("协议类型" + req.getProtocol());

        //读取消息头
        Enumeration e = req.getHeaderNames();
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            String value = req.getHeader(key);
            System.out.println(key + ":" + value);
        }

        //读取url后参数
        Enumeration p = req.getParameterNames();
        while (p.hasMoreElements()) {
            String key = (String) p.nextElement();
            String value = req.getParameter(key);
            System.out.println(key + ":" + value);
        }

        //响应
        PrintWriter out = resp.getWriter();
        out.println(LocalDate.now());
        out.close();
    }
}