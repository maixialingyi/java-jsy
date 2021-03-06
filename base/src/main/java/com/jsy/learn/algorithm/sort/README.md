#排序稳定性： 指同样大小的样本再排序之后不会改变相对次序  如：[4,1,2,1] 第一个1和第二个1排序后相对顺序不变
    对基础类型来说，稳定性毫无意义
    对非基础类型来说，稳定性有重要意义  如先按价格排序，再按评分，则破坏物美价廉

		时间复杂度	    额外空间复杂度		稳定性
选择排序		O(N^2)			O(1)		无
冒泡排序		O(N^2)			O(1)		有
插入排序		O(N^2)			O(1)		有
归并排序		O(N*logN)		O(N)		有
随机快排		O(N*logN)		O(logN)		无
堆排序		O(N*logN)		O(1)		无
========================================================
计数排序		O(N)			O(M)		有
基数排序		O(N)			O(N)		有

#排序算法总结
1）不基于比较的排序，对样本数据有严格要求，不易改写
2）基于比较的排序，只要规定好两个样本怎么比大小就可以直接复用
3）基于比较的排序，时间复杂度的极限是O(N*logN)
4）时间复杂度O(N*logN)、额外空间复杂度低于O(N)、且稳定的基于比较的排序是不存在的。
5）为了绝对的速度选快排、为了省空间选堆排、为了稳定性选归并

#常见的坑
1）归并排序的额外空间复杂度可以变成O(1)，“归并排序 内部缓存法”，但是将变得不再稳定。
2）“原地归并排序" 是垃圾贴，会让时间复杂度变成O(N^2)
3）快速排序稳定性改进，“01 stable sort”，但是会对样本数据要求更多。
4）在整型数组中，请把奇数放在数组左边，偶数放在数组右边，要求所有奇数之间原始的相对次序不变，所有偶数之间原始相对次序不变。
5）时间复杂度做到O(N)，额外空间复杂度做到O(1)

#jdk自带排序：根据稳定性走不同排序