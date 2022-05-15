package com.jsy.learn.algorithm.link;

/**
 * 将单向链表按某值划分成左边小、中间相等、右边大的形式
 * 笔试用: 把链表放入数组里，在数组上做partition 参考快速排序
 * 面试用: 分成小、中、大三部分，再把各个部分之间串起来  稳定
 */
public class C02_单链表荷兰国旗问题_SmallerEqualBigger {

	public static class Node {
		public int value;
		public Node next;

		public Node(int data) {
			this.value = data;
		}
	}

	public static Node listPartition1(Node head, int pivot) {
		if (head == null) {
			return head;
		}
		Node cur = head;
		int i = 0;
		while (cur != null) {
			i++;
			cur = cur.next;
		}
		Node[] nodeArr = new Node[i];
		i = 0;
		cur = head;
		for (i = 0; i != nodeArr.length; i++) {
			nodeArr[i] = cur;
			cur = cur.next;
		}
		arrPartition(nodeArr, pivot);
		for (i = 1; i != nodeArr.length; i++) {
			nodeArr[i - 1].next = nodeArr[i];
		}
		nodeArr[i - 1].next = null;
		return nodeArr[0];
	}

	public static void arrPartition(Node[] nodeArr, int pivot) {
		int small = -1;
		int big = nodeArr.length;
		int index = 0;
		while (index != big) {
			if (nodeArr[index].value < pivot) {
				swap(nodeArr, ++small, index++);
			} else if (nodeArr[index].value == pivot) {
				index++;
			} else {
				swap(nodeArr, --big, index);
			}
		}
	}

	public static void swap(Node[] nodeArr, int a, int b) {
		Node tmp = nodeArr[a];
		nodeArr[a] = nodeArr[b];
		nodeArr[b] = tmp;
	}


	/**
     *      [4 , 2, 3, 5, 6, 1, 3 ,0]
	 *    <区        =区            >区
	 *   sH 2       eH 3          bH 4
	 *      1       eT 3             5
	 *   sT 0                     bT 6
	 */
	public static Node listPartition2(Node head, int pivot) {
		Node sH = null; // small head   小于区域头  只会被第一个节点赋值一次,即头节点,后续节点next
		Node sT = null; // small tail   小于区域尾  只记录当前尾节点
		Node eH = null; // equal head   等于区域头
		Node eT = null; // equal tail   等于区域尾
		Node mH = null; // big head     大于区域头
		Node mT = null; // big tail     大于区域尾
		Node next = null; // save next node 临时保存下节点指针
		// every node distributed to three lists
		while (head != null) {
			next = head.next;  //

			head.next = null;
			if (head.value < pivot) { //小于区
				if (sH == null) { //区域为空即为头也为尾
					sH = head;    //只赋值一次即头节点
					sT = head;    //此时头同尾
				} else {            //区域不为空
					sT.next = head; //添加到列表尾节点
					sT = head;      //尾节点变为新节点
				}
			} else if (head.value == pivot) {
				if (eH == null) {
					eH = head;
					eT = head;
				} else {
					eT.next = head;
					eT = head;
				}
			} else {
				if (mH == null) {
					mH = head;
					mT = head;
				} else {
					mT.next = head;
					mT = head;
				}
			}
			head = next;
		}
		// small and equal reconnect
		if (sT != null) { // 如果有小于区域
			sT.next = eH;  // 小于区域尾 next 指向等于区域头
			//没有等于区域, 等于区域尾设置为 小于区域尾,为链接大于区域
			eT = eT == null ? sT : eT; // 下一步，谁去连大于区域的头，谁就变成eT
		}
		// 上面的if，不管跑了没有，et
		// all reconnect
		if (eT != null) { // 如果小于区域和等于区域，不是都没有
			eT.next = mH;
		}
		//小于 等于 大于 区域顺序判断, 有 则返回头
		return sH != null ? sH : (eH != null ? eH : mH);
	}

	public static void printLinkedList(Node node) {
		System.out.print("Linked List: ");
		while (node != null) {
			System.out.print(node.value + " ");
			node = node.next;
		}
		System.out.println();
	}

	public static void main(String[] args) {
		Node head1 = new Node(7);
		head1.next = new Node(9);
		head1.next.next = new Node(1);
		head1.next.next.next = new Node(8);
		head1.next.next.next.next = new Node(5);
		head1.next.next.next.next.next = new Node(2);
		head1.next.next.next.next.next.next = new Node(5);
		printLinkedList(head1);
		// head1 = listPartition1(head1, 4);
		head1 = listPartition2(head1, 5);
		printLinkedList(head1);

	}

}
