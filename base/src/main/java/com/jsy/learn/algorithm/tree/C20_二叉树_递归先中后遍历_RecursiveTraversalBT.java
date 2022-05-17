package com.jsy.learn.algorithm.tree;

/**
 *              1
 *            /   \
 *          2      3
 *         / \    / \
 *        4   5  6   7
 *  先序: 任何子树的处理顺序都是，先头节点、再左子树、然后右子树  1 2 4 5 3 6 7
 *  中序：任何子树的处理顺序都是，先左子树、再头节点、然后右子树  4 2 5 1 6 3 7
 *  后序：任何子树的处理顺序都是，先左子树、再右子树、然后头节点  4 5 2 6 7 3 1
 *
 *  处理方式: 基于 递归序 做不同的处理
 *  递归序: 	1 2 4 4 4 2 5 5 5 2
 *         	1 3 6 6 6 3 7 7 7 3 1
 *  		f(Node head) {
 * 				pre(head.left);
 * 				pre(head.right);
 */
public class C20_二叉树_递归先中后遍历_RecursiveTraversalBT {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int v) {
			value = v;
		}
	}

	//先序遍历
	public static void pre(Node head) {
		if (head == null) {
			return;
		}
		System.out.println(head.value);
		pre(head.left);
		pre(head.right);
	}

	//中序遍历
	public static void in(Node head) {
		if (head == null) {
			return;
		}
		in(head.left);
		System.out.println(head.value);
		in(head.right);
	}
	//后序遍历
	public static void pos(Node head) {
		if (head == null) {
			return;
		}
		pos(head.left);
		pos(head.right);
		System.out.println(head.value);
	}

	public static void main(String[] args) {
		Node head = new Node(1);
		head.left = new Node(2);
		head.right = new Node(3);
		head.left.left = new Node(4);
		head.left.right = new Node(5);
		head.right.left = new Node(6);
		head.right.right = new Node(7);

		pre(head);
		System.out.println("========");
		in(head);
		System.out.println("========");
		pos(head);
		System.out.println("========");

	}

}
