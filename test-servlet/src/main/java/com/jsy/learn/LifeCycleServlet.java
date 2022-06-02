package com.jsy.learn;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LifeCycleServlet extends HttpServlet {

    //在实例化Servlet后由tomcat自动调用
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        System.out.println("初始化LifeCycleServlet");
    }

    //每次访问都可以调用
    @Override
    protected void service(HttpServletRequest req,HttpServletResponse resp) {
        System.out.println("调用LifeCycleServlet");
    }

    //正常关闭tomcat时调用销毁
    @Override
    public void destroy() {
        super.destroy();
        System.out.println("销毁LifeCycleServlet");
    }

}
