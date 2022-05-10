package org.example.leetcode;

import java.util.Arrays;

/**
 * 排序算法
 * 1.选择排序
 * 2.冒泡排序
 * 3.插入排序
 * 4.希尔排序
 * 5.归并排序
 * 6.快速排序
 * 7.堆排序
 */
public class Sort {

    /**
     * 选择排序
     * 每次都从数组中选择最小的元素与数组起始位置元素做交换
     * @return 排好序的数组
     */
    public static int[] selectSort(int[] sortList) {
        int length = sortList.length, min = 0;
        for (int i = 0; i < length; i++) {
            min = i;
            for (int j = i; j < length; j++) {
                if (sortList[j] < sortList[min]) {
                    min = j;
                }
            }
            int t = sortList[i];
            sortList[i] = sortList[min];
            sortList[min] = t;
        }
        return sortList;
    }

    /**
     * 冒泡排序
     * 从头遍历依次比较相邻两个值，大的向后移动
     * @return 排好序的数组
     */
    public static int[] bubbleSort(int[] sortList) {
        int length = sortList.length;
        Boolean flag = false;  // 优化，当遍历中发现没有发生交换就表明已经排好序
        for (int i = length - 1; i > 0 && !flag; i--) {
            flag = true;
            for (int j = 0; j < i; j++) {
                if (sortList[j] > sortList[j + 1]) {
                    flag = false;
                    int t = sortList[j];
                    sortList[j] = sortList[j + 1];
                    sortList[j + 1] = t;
                }
            }
        }
        return sortList;
    }

    /**
     * 插入排序
     * 将右边的值插入到左边排好序的数组中
     * @return 排好序的数组
     */
    public static int[] insertSort(int[] sortList) {
        int length = sortList.length;
        for (int i = 1; i < length; i++) {
            for (int j = i; j > 0; j--) {
                if (sortList[j] < sortList[j - 1]) {
                    int t = sortList[j];
                    sortList[j] = sortList[j - 1];
                    sortList[j - 1] = t;
                }
            }
        }
        return sortList;
    }

    /**
     * 希尔排序
     * 插入排序的优化，希尔排序使用插入排序对间隔 h 的序列进行排序
     * @return 排好序的数组
     */
    public static int[] hillSort(int[] sortList) {
        int length = sortList.length, h = 4;
        //优化 h = h * 3 - 1
        while (h < length / 3) {
            h = h * 3 - 1;
        }
        for (int i = h; i > 0; i /= 2) {
            for (int j = i; j < length; j++) {
                if (sortList[j] < sortList[j - i]) {
                    swap(sortList, j, j - i);
                }
            }
        }
        return sortList;
    }

    /**
     * 归并排序
     * 递归的方式，对两组排好序的数组合并排序
     * @return 排好序的数组
     */
    public static void merge(int[] sortList, int left, int right) {
        if (left == right) {
            return;
        }
        //分成两半
        int mid = (left + right) / 2;
        //左边排序
        merge(sortList, left, mid);
        //右边排序
        merge(sortList, mid + 1, right);

        mergeSort(sortList, left, mid + 1, right);
    }

    public static void mergeSort(int[] arr, int left, int right, int border) {
        int mid = right - 1, i = 0, j = left;
        int[] k = new int[arr.length];
        while (left <= mid && right <= border) {
            if (arr[left] < arr[right]) {
                k[i++] = arr[left++];
            }else {
                k[i++] = arr[right++];
            }
        }
        while (left <= mid) {
            k[i++] = arr[left++];
        }
        while (right <= border) {
            k[i++] = arr[right++];
        }
        i = 0;
        for (; j < right; j++) {
            arr[j] = k[i++];
        }
    }

    /**
     * 双轴快速排序
     * 确定好中轴的位置，递归对中轴的左右两边排序
     * @return 排好序的数组
     */
    public static void quick(int[] sortList, int left, int right) {
        if (left >= right) return;
        int mid = right;  //中轴位置
        mid = quickSort(sortList, left, mid);
        System.out.println("执行中---->" + Arrays.toString(sortList) + "----left:" + left + "----mid:" + mid + "----right:" + right);
        //中轴位置左边快速排序
        quick(sortList, left, mid - 1);
        quick(sortList, mid + 1, right);
    }

    public static int quickSort(int[] arr, int i, int j) {
        int right = j - 1, left = i;
        while (left <= right) {
            while (left <= right && arr[left] <= arr[j]) left++;
            while (left <= right && arr[right] > arr[j]) right--;
            if (left < right) {
                swap(arr, left, right);
            }
        }
        //中轴数字交换到它对应的位置
        swap(arr, left, j);
        return left;
    }

    /**
     * 堆排序
     *
     * @return 排好序的数组
     */
    public static void heap(int[] sortList) {
        int l = sortList.length - 1;
        int h = (sortList.length - 1) / 2 - 1;  //最后一个父节点下标
        //完成大顶堆（父节点比左右子节点值大）
        for (int i = h; i >= 0; i--) {
            heapTree(sortList, l, i);
        }
        System.out.println("大顶堆---->" + Arrays.toString(sortList));
        //最后一个子节点与根节点交换 (保证最后开始的节点值比前面节点大)
        for (int i = l; i >= 0; i--) {
            swap(sortList, 0, i);
            //此时交换完，根节点不是大顶堆，对根节点做大顶堆运算
            heapTree(sortList, i - 1, 0);
        }
    }

    /**
     * 获得大顶堆树的数组
     * @param arr 数组
     * @param n 要排序的树中的节点数
     * @param i 父节点下标
     */
    public static void heapTree(int[] arr, int n, int i) {
        if (i >= n) {
            return;
        }
        int left = 2 * i + 1; //左子节点
        int right = 2 * i + 2; //右子节点
        int max = i;
        //左子节点和右子节点与父节点比较，最大的数与父节点交换，递归进行
        if (left <= n && arr[left] > arr[max]) {
            max = left;
        }
        if (right <= n && arr[right] > arr[max]) {
            max = right;
        }
        if (max != i) {
            swap(arr, i, max);
            heapTree(arr, n, max);
        }
    }

    public static void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static void main(String[] args) {
        int[] sortList = new int[]{2,41,12,4,43,21,9};
        sortList = new int[]{2,41,12,4,43,21,9};
//        sortList = new int[]{2,5,23,43,6,7,55};
//        sortList = new int[]{0, 2, 5, 7, 6, 43, 23};
//        merge(sortList, 0, sortList.length - 1);
//        quick(sortList, 0, sortList.length - 1);
        heap(sortList);
        System.out.println(Arrays.toString(sortList));
    }

}
