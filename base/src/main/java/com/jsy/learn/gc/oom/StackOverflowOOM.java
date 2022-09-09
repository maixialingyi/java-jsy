package com.jsy.learn.gc.oom;

/**
 * 栈溢出
 * -Xss128k
 * Exception in thread "main" java.lang.StackOverflowError
 * at com.jsy.learn.gc.oom.StackOverflowOOM.stackLeak(StackOverflowOOM.java:13)
 * 	at com.jsy.learn.gc.oom.StackOverflowOOM.main(StackOverflowOOM.java:19)
 */
public class StackOverflowOOM {

    private int stackLength = 1;

    private void stackLeak() {
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) {
        StackOverflowOOM oom = new StackOverflowOOM();
        try {
            oom.stackLeak();
        } catch (Throwable e) {
            System.out.println("Stack length: " + oom.stackLength);
            throw e;
        }
    }

}
