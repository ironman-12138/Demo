package org.example.leetcode;

import java.util.Arrays;

public class SortTwo {

    public static void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    //冒泡排序
    public static void bubbleSort(int[] arr) {
        int l = arr.length;
        boolean flag = false;
        for (int i = 0; i < l - 1; i++) {
            flag = false;
            for (int j = 0; j < l - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    flag = true;
                    swap(arr, j, j + 1);
                }
            }
            if (!flag) {
                return;
            }
        }
    }

    //选择排序
    public static void selectSort(int[] arr) {
        int l = arr.length, min = 0;
        for (int i = 0; i < l; i++) {
            min = i;
            for (int j = i; j < l; j++) {
                if (arr[j] < arr[min]) {
                    min = j;
                }
            }
            swap(arr, i, min);
        }
    }

    //插入排序
    public static void insertSort(int[] arr) {
        int l = arr.length;
        for (int i = 1; i < l; i++) {
            for (int j = i; j > 0; j--) {
                if (arr[j] < arr[j - 1]) {
                    swap(arr, j, j - 1);
                }
            }
        }
    }

    //归并排序
    public static void merge(int[] arr, int left, int right) {
        if (left == right) {
            return;
        }
        int mid = (left + right) / 2;

        //左边排序
        merge(arr, left, mid);
        //右边排序
        merge(arr, mid + 1, right);

        mergeSort(arr, left, mid + 1, right);

    }    
    
    public static void mergeSort(int[] arr, int left, int right, int border) {
        int[] k = new int[arr.length];
        int mid = right - 1, i = 0, l = left;
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

        for (i = 0; l <= border; l++) {
            arr[l] = k[i++];
        }
    }

    //双轴快速排序
    public static void quick(int[] arr, int left, int right) {
        if (left >= right) return;

        int i = quickSort(arr, left, right);
        //对轴的左边快排
        quick(arr, left, i - 1);
        //对轴的右边快排
        quick(arr, i + 1, right);
    }

    public static int quickSort(int[] arr, int i, int j) {
        int left = i, right = j - 1;
        while (left <= right) {
            while (left <= right && arr[left] <= arr[j]) left++;
            while (left <= right && arr[right] > arr[j]) right--;
            if (left < right) {
                swap(arr, left, right);
            }
        }
        //将j下标的值交换到它对应的位置
        swap(arr, left, j);
        return left;
    }

    public static void heap(int[] arr) {
        //先转成大顶堆树
        int l = arr.length;
        //最后一个非叶子节点下标
        int h = (arr.length) / 2 - 1;
        for (int i = h; i >= 0; i--) {
            heapTree(arr, l - 1, i);
        }

        //交换根节点和尾部节点
        for (int i = l - 1; i >= 0; i--) {
            swap(arr, 0, i);
            heapTree(arr, i - 1, 0);
        }
    }

    public static void heapTree(int[] arr, int n, int i) {
        if (i >= n) return;

        //左子节点下标
        int left = 2 * i + 1;
        //右子节点下标
        int right = 2 * i + 2;

        int max = i;
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

    public static void main(String[] args) {
        int[] arr = new int[]{2,41,12,4,43,21,9};
        int[] arr2 = new int[]{12,1,14,4,19,3,29};
        int[] arr3 = new int[]{12,21,14,8,3,31,2};
//        quick(arr, 0, arr.length - 1);
//        quick(arr2, 0, arr2.length - 1);
//        quick(arr3, 0, arr3.length - 1);
        heap(arr);
        heap(arr2);
        heap(arr3);
        System.out.println(Arrays.toString(arr));
        System.out.println(Arrays.toString(arr2));
        System.out.println(Arrays.toString(arr3));

    }

}
