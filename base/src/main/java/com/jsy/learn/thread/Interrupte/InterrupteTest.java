package com.jsy.learn.thread.Interrupte;

/**
 * @Author jiangshaoyue
 * @Date 2019/6/18 9:06
 */
public class InterrupteTest {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("kaishi");
                    //Thread.sleep(10000);
                    while(!Thread.interrupted()){
                        System.out.println("jinru ");
                        throw new InterruptedException();
                    }
                } catch (InterruptedException e) {
                    System.out.println("yichang ");
                    e.printStackTrace();

                }
            }
        });

        thread.start();
        Thread.sleep(1000);
        thread.interrupt();

    }
}
