package com.jsy.learn;

import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

public class HelloWorldServlet extends HttpServlet 
{
  public void service( HttpServletRequest req, HttpServletResponse res ) throws IOException {
    PrintWriter out = res.getWriter();
    out.println( "Hello, World!" );
    out.close();
  }
}
