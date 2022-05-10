package com.jsy.learn.algorithm;

/**
 * 删除链表给定值
 */
public class C02_删除链表_DeleteGivenValue {

	public static class Node {
		public int value;
		public Node next;

		public Node(int data) {
			this.value = data;
		}
	}

	public static Node removeValue(Node head, int num) {
		while (head != null) {
			if (head.value != num) {
				break;
			}
			head = head.next;
		}
		Node pre = head;
		Node cur = head;
		while (cur != null) {
			if (cur.value == num) {
				pre.next = cur.next;
			} else {
				pre = cur;
			}
			cur = cur.next;
		}
		return head;
	}

}
