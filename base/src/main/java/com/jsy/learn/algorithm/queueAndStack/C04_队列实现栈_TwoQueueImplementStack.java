package com.jsy.learn.algorithm.queueAndStack;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 两队列实现栈
 */
public class C04_队列实现栈_TwoQueueImplementStack {

	public static class TwoQueueStack<T> {
		public Queue<T> queue;
		public Queue<T> help;

		public TwoQueueStack() {
			queue = new LinkedList<>();
			help = new LinkedList<>();
		}

		public void push(T value) {
			queue.offer(value);
		}

		public T poll() {
			while (queue.size() > 1) {
				help.offer(queue.poll());
			}
			T ans = queue.poll();
			Queue<T> tmp = queue;
			queue = help;
			help = tmp;
			return ans;
		}

		public T peek() {
			while (queue.size() > 1) {
				help.offer(queue.poll());
			}
			T ans = queue.peek();
			Queue<T> tmp = queue;
			queue = help;
			help = tmp;
			help.offer(ans);
			return ans;
		}

		public boolean isEmpty() {
			return queue.isEmpty();
		}

	}

}
