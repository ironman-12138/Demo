package org.example.leetcode;

/**
 * 在排序数组中查找元素的第一个和最后一个位置
 * 给定一个按照升序排列的整数数组 nums，和一个目标值 target。找出给定目标值在数组中的开始位置和结束位置。
 *
 * 如果数组中不存在目标值 target，返回 [-1, -1]。
 *
 * 进阶：
 *
 * 你可以设计并实现时间复杂度为 O(log n) 的算法解决此问题吗？
 *
 *
 * 示例 1：
 *
 * 输入：nums = [5,7,7,8,8,10], target = 8
 * 输出：[3,4]
 * 示例 2：
 *
 * 输入：nums = [5,7,7,8,8,10], target = 6
 * 输出：[-1,-1]
 */
public class SearchRange {

    public static int[] searchRange(int[] nums, int target) {
        int[] res = new int[]{-1,-1};
        if (nums.length == 0) {
            return res;
        }
        int right = nums.length, left = 0, mid = 0, length = nums.length;
        while (left <= right) {
            mid = (right + left) / 2;
            if (mid < 0 || mid >= length || nums[mid] == target) {
                break;
            }
            if (nums[mid] > target) {
                right = mid - 1;
            }
            if (nums[mid] < target) {
                left = mid + 1;
            }
        }
        if (left > right || mid >= length) {
            return res;
        }
        for (int i = mid; i >= 0; i--) {
            if (nums[i] != target) {
                res[0] = i + 1;
                break;
            }
            if (i == 0) {
                res[0] = 0;
            }
        }
        for (int i = mid; i < length; i++) {
            if (nums[i] != target) {
                res[1] = i - 1;
                break;
            }
            if (i == length - 1) {
                res[1] = length - 1;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int[] ints = new int[]{2,2};
        int[] ints1 = searchRange(ints, 2);
        for (int i = 0; i < ints1.length; i++) {
            System.out.println(ints1[i]);
        }
    }

}
