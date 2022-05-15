package com.jsy.learn.algorithm.tree;

import java.util.Stack;
/**
 *              1
 *            /   \
 *          2      3
 *         / \    / \
 *        4   5  6   7
 *  先序: 任何子树的处理顺序都是，先头节点、再左子树、然后右子树  1 2 4 5 3 6 7
 *  中序：任何子树的处理顺序都是，先左子树、再头节点、然后右子树  4 2 5 1 6 3 7
 *  后序：任何子树的处理顺序都是，先左子树、再右子树、然后头节点  4 5 2 6 7 3 1
 */
public class C21_二叉树_非递归先中后遍历_UnRecursiveTraversalBT {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int v) {
			value = v;
		}
	}

	/**
	 *              1
	 *            /   \
	 *          2      3
	 *         / \    / \
	 *        4   5  6   7
	 *  先序: 任何子树的处理顺序都是，先头节点、再左子树、然后右子树  1 2 4 5 3 6 7
	 */
	public static void pre(Node head) {
		System.out.print("pre-order: ");
		if (head != null) {
			Stack<Node> stack = new Stack<Node>();
			stack.add(head);
			while (!stack.isEmpty()) {
				head = stack.pop();        //弹出栈,打印  1  2  4  5  3  6  7
				System.out.print(head.value + " ");
				if (head.right != null) {  //如有右,压入右 3  5  7
					stack.push(head.right);
				}
				if (head.left != null) {   //如有左,压入左 2  4  6
					stack.push(head.left);
				}
			}
		}
		System.out.println();
	}

	/**
	 *              1
	 *            /   \
	 *          2      3
	 *         / \    / \
	 *        4   5  6   7
	 *  中序：任何子树的处理顺序都是，先左子树、再头节点、然后右子树  4 2 5 1 6 3 7
	 */
	public static void in(Node head) {
		System.out.print("in-order: ");
		if (head != null) {
			Stack<Node> stack = new Stack<Node>();
			while (!stack.isEmpty() || head != null) {
				if (head != null) {
					stack.push(head);
					head = head.left;
				} else {
					head = stack.pop();
					System.out.print(head.value + " ");
					head = head.right;
				}
			}
		}
		System.out.println();
	}

	/**
	 *              1
	 *            /   \
	 *          2      3
	 *         / \    / \
	 *        4   5  6   7
	 *  后序：任何子树的处理顺序都是，先左子树、再右子树、然后头节点  4 5 2 6 7 3 1
	 */
	public static void pos1(Node head) {
		System.out.print("pos-order: ");
		if (head != null) {
			Stack<Node> s1 = new Stack<Node>();
			Stack<Node> s2 = new Stack<Node>();
			s1.push(head);
			while (!s1.isEmpty()) {
				head = s1.pop();
				s2.push(head);
				if (head.left != null) {
					s1.push(head.left);
				}
				if (head.right != null) {
					s1.push(head.right);
				}
			}
			while (!s2.isEmpty()) {
				System.out.print(s2.pop().value + " ");
			}
		}
		System.out.println();
	}

	/**
	 *              1
	 *            /   \
	 *          2      3
	 *         / \    / \
	 *        4   5  6   7
	 *  后序：任何子树的处理顺序都是，先左子树、再右子树、然后头节点  4 5 2 6 7 3 1
	 */
	public static void pos2(Node h) {
		System.out.print("pos-order: ");
		if (h != null) {
			Stack<Node> stack = new Stack<Node>();
			stack.push(h);
			Node c = null;
			while (!stack.isEmpty()) {
				c = stack.peek();
				if (c.left != null && h != c.left && h != c.right) {
					stack.push(c.left);
				} else if (c.right != null && h != c.right) {
					stack.push(c.right);
				} else {
					System.out.print(stack.pop().value + " ");
					h = c;
				}
			}
		}
		System.out.println();
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
		pos1(head);
		System.out.println("========");
		pos2(head);
		System.out.println("========");
	}

}
