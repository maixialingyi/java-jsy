package com.jsy.learn.algorithm.link;

import java.util.Arrays;

/**
 * 队列
 */
public class Code03_ArrayQueue {

    int arr[];
    int size;
    int pushIndex;
    int pollIndex;
    int length;

    public Code03_ArrayQueue(int length) {
        this.arr = new int[length];
        this.size = 0;
        this.pushIndex = 0;
        this.pollIndex = 0;
        this.length = length;
    }

    void push(int value){
        if(size == length){
            throw new RuntimeException("队列已满");
        }
        size ++;
        arr[pushIndex] = value;
        pushIndex = nextIndex(pushIndex);
    }

    int poll(){
        if(size == 0){
            throw new RuntimeException("队列为空");
        }
        size --;
        int out = arr[pollIndex];
        pollIndex = nextIndex(pollIndex);
        return out;
    }

    int nextIndex(int index){
        return index == (length -1) ? 0 : index +1;
    }

    @Override
    public String toString() {
        return "Code03_ArrayQueue{" +
                "arr=" + Arrays.toString(arr) +
                ", size=" + size +
                ", pushIndex=" + pushIndex +
                ", pollIndex=" + pollIndex +
                ", length=" + length +
                '}';
    }

    public static void main(String[] args) {
        Code03_ArrayQueue queue = new Code03_ArrayQueue(6);
        queue.push(1);
        System.out.println(queue);
        queue.push(2);
        System.out.println(queue);

        queue.poll();
        System.out.println(queue);
        queue.poll();
        System.out.println(queue);

        queue.push(3);
        System.out.println(queue);
    }
}
