package com.jsy.learn.exception.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.SocketTimeoutException;

/**
 * @Author qiaojiangwei
 * @Date: 2020/9/10 8:47 下午
 */
@Slf4j
public class ExceptionUtil {

    /**
     * 处理超时异常
     * @param e    要处理的异常
     * @param e  抛出的异常
     */
    public static void handTimeoutException(Throwable e) throws Throwable {
        Throwable t = ExceptionUtils.getRootCause(e);
        if (e instanceof SocketTimeoutException ||
                t instanceof SocketTimeoutException ){
             throw e;
         }
    }

    public static boolean containsCause(Throwable e, Class<? extends Throwable> causeType) {
        return findCause(e, causeType) != null;
    }

    public static Throwable findCause(Throwable e, Class<? extends Throwable> causeType) {
        while (e != null && e.getCause() != e) {
            if (causeType.isInstance(e)) {
                return e;
            }
            e = e.getCause();
        }
        return null;
    }

    /**
     * 打印
     * @param throwable
     * @return
     */
    public static String getStackTrace(Throwable throwable) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }
}
