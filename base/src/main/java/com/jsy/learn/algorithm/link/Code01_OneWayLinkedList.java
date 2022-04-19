package com.jsy.learn.algorithm.link;

/**
 * 单向链表
 */
public class Code01_OneWayLinkedList<T> {

    Node first;
    int size;

    class Node<T> {
        public T data;
        public Node next;
        public Node(T data) {this.data = data;}
    }

    //尾添加
    void addLast(T t){
        if(first == null){
            first = new Node(t);
            return;
        }

        Node cur = first;
        while(cur.next != null){
            cur = cur.next;
        }
        cur.next = new Node(t);
    }

    //翻转
    public void reverse() {
        if(first == null){
            return;
        }

        //1 <- 2 <- 3 <- 4
        Node pre = null;        //前节点
        Node cur = first;        //当前节点
        Node next = null;       //下节点

        while(cur != null){
            next = cur.next;   //记录下节点  2

            cur.next = pre;    //next    -> 前节点   指向   当前节点
            pre = cur;         //变量右移 -> 当前节点 转为    前节点
            cur = next;        //变量右移 -> 下节点   转为   当前节点
        }
        first = pre;
    }

    /**
     * 删除元素  1
     * 1 <- 2 <- 1 < 1 <- 4
     * 2.遇到1后,其前节点next = 其后不为1节点
     */
    void delete(T t){
        if(first == null){
            return;
        }

        //1.遍历找出第一个不为1的节点设置为头结点,此前为1节点跳过即删除了
        Node cur = first;
        while(cur != null){
            if( ! cur.data.equals(t) ){
                first = cur;
                break;
            }
        }

        //2 <- 1 < 1 <- 4
        Node pre = null;
        while(cur != null){
            if(! cur.data.equals(t)){
                pre = cur;
                cur = cur.next;
            }else{
                pre.next = cur.next;
                cur = cur.next;
            }
        }
    }

    void myPrint(){
        if(first == null){
            System.out.println("{}");
            return;
        }

        Node cur = first;
        while(cur != null){
            System.out.print(cur.data + " ");
            cur = cur.next;
        }
        System.out.println();
    }
    public static void main(String[] args) {
        Code01_OneWayLinkedList<Integer> linkList = new Code01_OneWayLinkedList();
        linkList.addLast(1);
        linkList.addLast(2);
        linkList.addLast(1);
        linkList.addLast(1);
        linkList.addLast(4);
        linkList.myPrint();

        linkList.reverse();
        linkList.myPrint();

        linkList.delete(1);
        linkList.myPrint();
    }

}
