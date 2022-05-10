package com.jsy.learn.algorithm;

import java.util.PriorityQueue;

/**
 * 已知一个几乎有序的数组。几乎有序是指，如果把数组排好顺序的话，
 * 每个元素移动的距离一定不超过k，并且k相对于数组长度来说是比较小的。
 *
 * 请选择一个合适的排序策略，对这个数组进行排序。
 */
public class C05_小根堆_SortArrayDistanceLessK {

	public void sortedArrDistanceLessK(int[] arr, int k) {
		// 默认小根堆,jdk自带，优先级队列即小跟堆实现
		PriorityQueue<Integer> heap = new PriorityQueue<>();
		// 数组长度<k -> 数组全放入堆
		// 数组长度>k -> 取k个元素放入堆
		int index = 0;
		for (; index <= Math.min(arr.length, k); index++) {
			heap.add(arr[index]);
		}

		// 第k+1个放入堆，获取弹出最小，即目前最小值，放入arr[0]
		// 第k+1个放入堆，获取弹出最小，即目前最小值，放入arr[1]
		int i = 0;
		for (; index < arr.length; i++, index++) {
			heap.add(arr[index]);
			arr[i] = heap.poll();
		}
		while (!heap.isEmpty()) {
			arr[i++] = heap.poll();
		}
	}

	public static void main(String[] args) {

		PriorityQueue<Integer> heap = new PriorityQueue<>();

		heap.add(8);
		heap.add(4);
		heap.add(4);
		heap.add(9);
		heap.add(10);
		heap.add(3);

		while (!heap.isEmpty()) {
			System.out.println(heap.poll());
		}

	}

}
