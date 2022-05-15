package com.jsy.learn.algorithm.heap;

/**
 * 堆结构：
 * 1）堆结构就是用数组实现的完全二叉树结构: 每层肯定有左
 * index下标找节点计算公式：
 * 		index父下标 ： (index-1)/2
 * 		index左子下标：2 * index + 1
 * 	 	index右子下标：2 * index + 2
 *
 * 2）大根堆： 完全二叉树中如果每棵子树的最大值都在顶部
 *      10
 *    /    \
 *   3      6
 *  / \    / \
 * 1   2  4   5
 *
 * 3）小跟堆： 完全二叉树中如果每棵子树的最小值都在顶部
 *       1
 *     /    \
 *    3      6
 *   / \    / \
 *  3   4  7   8
 *
 *
 * 4）堆结构的heapInsert与heapify操作
 * 5）堆结构的增大和减少
 * 6）优先级队列结构，就是堆结构
 */
public class C05_大根堆_Heap01 {

	/**
     * 大根堆实现
	 */
	public static class MyMaxHeap {
		private int[] heap;       //数组
		private final int limit;  //堆大小=数组长度
		private int heapSize;     //堆中元素个数

		public MyMaxHeap(int limit) {
			heap = new int[limit];
			this.limit = limit;
			heapSize = 0;
		}

		public boolean isEmpty() {
			return heapSize == 0;
		}

		public boolean isFull() {
			return heapSize == limit;
		}

		/**
         * 添加
		 */
		public void push(int value) {
			if (heapSize == limit) {//堆已满
				throw new RuntimeException("heap is full");
			}
			//先放到堆最后元素后
			heap[heapSize] = value;
			//于父节点比较替换
			heapInsert(heap, heapSize++);
		}
		//index：      新元素位置
		//index父位置： (index - 1) / 2
		private void heapInsert(int[] arr, int index) {
			//结束条件：1.移动元素 <= 父  2.index == 0 也成立
			while (arr[index] > arr[(index - 1) / 2]) {
				swap(arr, index, (index - 1) / 2); //父子交换
				index = (index - 1) / 2;  //移动下标变化
			}
		}

		/**
		 * 大根堆中返回最大值且删除，结构依然为大根堆
		 */
		public int pop() {
			int ans = heap[0];    //取出最大值
			swap(heap, 0, --heapSize); //最大根于最后节点交换
			heapify(heap, 0, heapSize);//大根堆结构维护
			return ans;
		}

		private void heapify(int[] arr, int index, int heapSize) {
			int left = index * 2 + 1; //左子节点
			while (left < heapSize) { //未越界
				// 找出左右子节点最大元素下标 = 右子节点未越界 && 右子节点元素 > 左子节点元素 ？右子节点下标 ：左子节点下标
				int largest = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;
				// 父子比较
				largest = arr[largest] > arr[index] ? largest : index;
				// 父大不交换
				if (largest == index) {
					break;
				}
				//子大 交换
				swap(arr, largest, index);
				//当前节点转换为子节点
				index = largest;
				//找到左子节点
				left = index * 2 + 1;
			}
		}

		private void swap(int[] arr, int i, int j) {
			int tmp = arr[i];
			arr[i] = arr[j];
			arr[j] = tmp;
		}

	}

	public static class RightMaxHeap {
		private int[] arr;
		private final int limit;
		private int size;

		public RightMaxHeap(int limit) {
			arr = new int[limit];
			this.limit = limit;
			size = 0;
		}

		public boolean isEmpty() {
			return size == 0;
		}

		public boolean isFull() {
			return size == limit;
		}

		public void push(int value) {
			if (size == limit) {
				throw new RuntimeException("heap is full");
			}
			arr[size++] = value;
		}

		public int pop() {
			int maxIndex = 0;
			for (int i = 1; i < size; i++) {
				if (arr[i] > arr[maxIndex]) {
					maxIndex = i;
				}
			}
			int ans = arr[maxIndex];
			arr[maxIndex] = arr[--size];
			return ans;
		}

	}

	public static void main(String[] args) {
		int value = 1000;
		int limit = 100;
		int testTimes = 1000000;
		for (int i = 0; i < testTimes; i++) {
			int curLimit = (int) (Math.random() * limit) + 1;
			MyMaxHeap my = new MyMaxHeap(curLimit);
			RightMaxHeap test = new RightMaxHeap(curLimit);
			int curOpTimes = (int) (Math.random() * limit);
			for (int j = 0; j < curOpTimes; j++) {
				if (my.isEmpty() != test.isEmpty()) {
					System.out.println("Oops!");
				}
				if (my.isFull() != test.isFull()) {
					System.out.println("Oops!");
				}
				if (my.isEmpty()) {
					int curValue = (int) (Math.random() * value);
					my.push(curValue);
					test.push(curValue);
				} else if (my.isFull()) {
					if (my.pop() != test.pop()) {
						System.out.println("Oops!");
					}
				} else {
					if (Math.random() < 0.5) {
						int curValue = (int) (Math.random() * value);
						my.push(curValue);
						test.push(curValue);
					} else {
						if (my.pop() != test.pop()) {
							System.out.println("Oops!");
						}
					}
				}
			}
		}
		System.out.println("finish!");

	}

}
