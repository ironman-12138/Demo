package org.example.leetcode;

/**
 * 整数数组 nums 按升序排列，数组中的值 互不相同 。
 *
 * 在传递给函数之前，nums 在预先未知的某个下标 k（0 <= k < nums.length）上进行了 旋转，使数组变为 [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]（下标 从 0 开始 计数）。例如， [0,1,2,4,5,6,7] 在下标 3 处经旋转后可能变为 [4,5,6,7,0,1,2] 。
 *
 * 给你 旋转后 的数组 nums 和一个整数 target ，如果 nums 中存在这个目标值 target ，则返回它的下标，否则返回 -1 。
 *
 * 示例 1：
 * 输入：nums = [4,5,6,7,0,1,2], target = 0
 * 输出：4
 *
 * 示例 2：
 * 输入：nums = [4,5,6,7,0,1,2], target = 3
 * 输出：-1
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/search-in-rotated-sorted-array
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class NumSearch {

    public static int search(int[] nums, int target) {
        int length = nums.length, i = 0;
        if (length == 1) {
            if (nums[0] == target) {
                return 0;
            }else {
                return -1;
            }
        }
        for (; i < length; i++) {
            if (nums[i] == target) {
                return i;
            }
            if (i < length - 1 && nums[i] > nums[i + 1]) {
                break;
            }
        }
        if (i <= length - 1 && target > nums[i]) {
            return -1;
        }
        int left = i + 1, right = length - 1, temp = 0;
        while (left <= right) {
            if (target == nums[(left + right) / 2]) {
                return (left + right) / 2;
            }
            if (target > nums[(left + right) / 2]) {
                left = (left + right) / 2 + 1;
            }else {
                right = (left + right) / 2 - 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] ints = {3, 1};
        System.out.println(search(ints, 1));
    }

}
