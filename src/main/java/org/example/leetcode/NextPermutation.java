package org.example.leetcode;

/**
 * 31. 下一个排列
 * 实现获取 下一个排列 的函数，算法需要将给定数字序列重新排列成字典序中下一个更大的排列（即，组合出下一个更大的整数）。
 *
 * 如果不存在下一个更大的排列，则将数字重新排列成最小的排列（即升序排列）。
 *
 * 必须 原地 修改，只允许使用额外常数空间。
 *
 *
 *
 * 示例 1：
 *
 * 输入：nums = [1,2,3]
 * 输出：[1,3,2]
 * 示例 2：
 *
 * 输入：nums = [3,2,1]
 * 输出：[1,2,3]
 */
public class NextPermutation {

    public static void nextPermutation(int[] nums) {
        int length = nums.length, temp = 0, j = -1;

        //1.找到最后一位小于其后一位的下标
        for (int i = length - 1; i > 0; i--) {
            if (nums[i] > nums[i - 1]) {
                j = i - 1;
                break;
            }
        }

        if (j >= 0) {
            //2.找到比他大并且差值最小的一位，交换位置
            int min = nums[j + 1], k = j + 1;
            for (int i = j; i < length; i++) {
                if (nums[i] > nums[j] && nums[i] <= min) {
                    min = nums[i];
                    k = i;
                }
            }
            temp = min;
            nums[k] = nums[j];
            nums[j] = temp;

            //3.反转在他之后的数组
            reverse(nums, j + 1, length);
        }else {
            reverse(nums, 0, length);
        }

    }

    //反转数组
    public static void reverse(int[] nums, int left, int right) {
        int temp = 0;
        for (int i = left, j = 0; i < (right + left) / 2; i++) {
            temp = nums[i] ;
            nums[i] = nums[right - j - 1];
            nums[right - j - 1] = temp;
            j++;
        }
    }

    public static void main(String[] args) {
        int[] ints = {2,3,1,3,3};
        nextPermutation(ints);
        for (int i = 0; i < ints.length; i++) {
            System.out.println(ints[i]);
        }
    }

}
