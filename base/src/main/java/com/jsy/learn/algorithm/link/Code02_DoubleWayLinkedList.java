package com.jsy.learn.algorithm.link;

import java.util.LinkedList;

/**
 * 双向链表
 */
public class Code02_DoubleWayLinkedList<T> {

    int size = 0;
    Node<T> first;
    Node<T> last;

    static class Node<T>{
        T data;
        Node next;
        Node prev;

        public Node(T data, Node prev, Node next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }

    //尾添加
    void addLast(T t){
        final Node<T> l = last;
        final Node<T> newNode = new Node<>(t,l,null);
        last = newNode;
        if (l == null) {
            first = newNode;
        }else {
            l.next = newNode;
        }
    }

    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
    }


}
