package com.jsy.learn;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ApiServlet extends HttpServlet {

    //http://localhost:8080/api?Action=queryById
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();

        String action = req.getParameter("Action");
        switch (action) {
            case "queryById":
                out.println("queryById");
                out.close();
                break;
            case "queryByName":
                out.println("queryByName");
                out.close();
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();

        String action = req.getParameter("Action");
        switch (action) {
            case "add":
                out.println("add");
                out.close();
                break;
            case "delete":
                out.println("delete");
                out.close();
                break;
        }
    }

}
