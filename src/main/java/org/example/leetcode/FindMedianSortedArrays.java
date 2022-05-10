package org.example.leetcode;

/**
 * 给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的 中位数 。
 *
 * 算法的时间复杂度应该为 O(log (m+n)) 。
 *
 * 示例 1：
 *
 * 输入：nums1 = [1,3], nums2 = [2]
 * 输出：2.00000
 * 解释：合并数组 = [1,2,3] ，中位数 2
 */
public class FindMedianSortedArrays {

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int length = nums1.length;
        int length2 = nums2.length;
        int[] nums = new int[length + length2];
        int k = 0, i = 0, j = 0;
        while (j < length2 && i < length) {
            if (nums2[j] >= nums1[i]) {
                nums[k++] = nums1[i];
                i++;
            }else if (nums2[j] <= nums1[i]) {
                nums[k++] = nums2[j];
                j++;
            }
        }
        while (j < length2) {
            nums[k++] = nums2[j++];
        }
        while (i < length) {
            nums[k++] = nums1[i++];
        }
        if (nums.length % 2 == 0) {
            return (double)(Math.round(nums[nums.length / 2 - 1] + nums[nums.length / 2]) / 2.0);
        }else {
            return nums[nums.length / 2];
        }
    }

    public static void main(String[] args) {
        System.out.println(findMedianSortedArrays(new int[]{0,0}, new int[]{0,0}));
    }

}
