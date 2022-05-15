package com.jsy.learn.algorithm.link;

/**
 * 给定两个可能有环也可能无环的单链表，头节点head1和head2。请实现一个函数，如果两个链表相交，
 * 请返回相交的 第一个节点。如果不相交，返回null
 * 【要求】: 如果两个链表长度之和为N，时间复杂度请达到O(N)，额外空间复杂度 请达到O(1)。
 *
 * 单链表 只有一个next指针,只能有一个环
 *  1 -> 2 -> 3 -> 4     1 -> 2 -> 3 -> 4
 *       |_ _ _ _ _|     |_ _ _ _ _ _ _ |
 */
public class C02_单链表环与相交_FindFirstIntersectNode {

	public static class Node {
		public int value;
		public Node next;

		public Node(int data) {
			this.value = data;
		}
	}

	public static Node getIntersectNode(Node head1, Node head2) {
		if (head1 == null || head2 == null) {
			return null;
		}
		// 找到链表第一个入环节点，如果无环，返回null
		Node loop1 = getLoopNode(head1);
		Node loop2 = getLoopNode(head2);
		// 两链表都无环
		if (loop1 == null && loop2 == null) {
			return noLoop(head1, head2);
		}
		// 两链表都有环
		if (loop1 != null && loop2 != null) {
			return bothLoop(head1, loop1, head2, loop2);
		}
		// 一链表有环,一链表无环,不可能相交
		return null;
	}

	// 找到链表第一个入环节点，如果无环，返回null
	// 方案二: 遍历链表放入set,放入前判断是否节点是否存在,存在则为环开始节点
	public static Node getLoopNode(Node head) {
		if (head == null || head.next == null || head.next.next == null) {
			return null;
		}
		// n1 慢  n2 快
		Node n1 = head.next; // n1 -> slow
		Node n2 = head.next.next; // n2 -> fast
		while (n1 != n2) { // 快慢指针相同时即环开始节点
			if (n2.next == null || n2.next.next == null) { //有 .next==null 说明无环
				return null;
			}
			n2 = n2.next.next;
			n1 = n1.next;
		}
		//快指针变慢指针,重头开始 两慢指针相遇即相交节点
		n2 = head; // n2 -> walk again from head
		while (n1 != n2) {
			n1 = n1.next;
			n2 = n2.next;
		}
		return n1;
	}

	// 如果两个链表都无环，返回第一个相交节点，如果不想交，返回null
	// 方案二: 用hashset, 遍历其中一条放入set,遍历另一条判断节点在set中则表示相交节点
	public static Node noLoop(Node head1, Node head2) {
		if (head1 == null || head2 == null) {
			return null;
		}
		Node cur1 = head1;
		Node cur2 = head2;
		int n = 0;
		while (cur1.next != null) {
			n++;  //记录链表1长度
			cur1 = cur1.next;
		}
		while (cur2.next != null) {
			n--;  //算出 链表1长度 - 链表2长度
			cur2 = cur2.next;
		}
		if (cur1 != cur2) { //链表1 链表2 尾节点为同一个 即不相交
			return null;
		}
		// n  :  链表1长度减去链表2长度的值
		cur1 = n > 0 ? head1 : head2; // 谁长，谁的头变成cur1
		cur2 = cur1 == head1 ? head2 : head1; // 谁短，谁的头变成cur2
		n = Math.abs(n); //取绝对值
		while (n != 0) { //取长数组的第n-1个节点,次处向后节点数于另个一个链表节点数相同
			n--;
			cur1 = cur1.next;
		}
		while (cur1 != cur2) { //同时遍历  相等 即 相交节点
			cur1 = cur1.next;
			cur2 = cur2.next;
		}
		return cur1;
	}

	/**
	 * 两个有环链表，返回第一个相交节点，如果不想交返回null
	 * 情况一: 不相交
	 * 情况二: 相交于环前     1 ->
	 *                           2 - > 3 -> 4
	 *                                 | _ _|
	 *                     9 ->
	 *      判断是否为此情况: 两链表入环节点是同一个   loop1 != loop2
	 *      第一个相交节点 :  把无环的最终节点改为入环节点即可
	 * 情况三: 相交于环上    1-> 2 - > 3 -> 4
	 * 	 				  | _ _ _ _ _ _ _|
	 * 	 	判断是否为此情况: loop1开始遍历,再次遇到loop1即循环一遍,未碰到loop2  反之未情况一
	 * 	    第一个相交节点 :  loop1 loop2 都是第一个相交节点,返回任何一个都可以
	 *
	 * @param head1   链表1 头
	 * @param loop1   链表1 入环节点
	 * @param head2   链表2 头
	 * @param loop2   链表2 入环节点
	 * @return
	 */
	public static Node bothLoop(Node head1, Node loop1, Node head2, Node loop2) {
		Node cur1 = null;
		Node cur2 = null;
		if (loop1 == loop2) {
			cur1 = head1;
			cur2 = head2;
			int n = 0;
			while (cur1 != loop1) {
				n++;
				cur1 = cur1.next;
			}
			while (cur2 != loop2) {
				n--;
				cur2 = cur2.next;
			}
			cur1 = n > 0 ? head1 : head2;
			cur2 = cur1 == head1 ? head2 : head1;
			n = Math.abs(n);
			while (n != 0) {
				n--;
				cur1 = cur1.next;
			}
			while (cur1 != cur2) {
				cur1 = cur1.next;
				cur2 = cur2.next;
			}
			return cur1;
		} else {
			cur1 = loop1.next;
			while (cur1 != loop1) {
				if (cur1 == loop2) {
					return loop1;
				}
				cur1 = cur1.next;
			}
			return null;
		}
	}

	public static void main(String[] args) {
		// 1->2->3->4->5->6->7->null
		Node head1 = new Node(1);
		head1.next = new Node(2);
		head1.next.next = new Node(3);
		head1.next.next.next = new Node(4);
		head1.next.next.next.next = new Node(5);
		head1.next.next.next.next.next = new Node(6);
		head1.next.next.next.next.next.next = new Node(7);

		// 0->9->8->6->7->null
		Node head2 = new Node(0);
		head2.next = new Node(9);
		head2.next.next = new Node(8);
		head2.next.next.next = head1.next.next.next.next.next; // 8->6
		System.out.println(getIntersectNode(head1, head2).value);

		// 1->2->3->4->5->6->7->4...
		head1 = new Node(1);
		head1.next = new Node(2);
		head1.next.next = new Node(3);
		head1.next.next.next = new Node(4);
		head1.next.next.next.next = new Node(5);
		head1.next.next.next.next.next = new Node(6);
		head1.next.next.next.next.next.next = new Node(7);
		head1.next.next.next.next.next.next = head1.next.next.next; // 7->4

		// 0->9->8->2...
		head2 = new Node(0);
		head2.next = new Node(9);
		head2.next.next = new Node(8);
		head2.next.next.next = head1.next; // 8->2
		System.out.println(getIntersectNode(head1, head2).value);

		// 0->9->8->6->4->5->6..
		head2 = new Node(0);
		head2.next = new Node(9);
		head2.next.next = new Node(8);
		head2.next.next.next = head1.next.next.next.next.next; // 8->6
		System.out.println(getIntersectNode(head1, head2).value);

	}

}
