package org.example.leetcode;

/**
 * 给你 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点 (i, ai) 。在坐标内画 n 条垂直线，垂直线 i 的两个端点分别为 (i, ai) 和 (i, 0) 。找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
 *
 * 说明：你不能倾斜容器。
 *
 * 输入：[1,8,6,2,5,4,8,3,7]
 * 输出：49
 * 解释：图中垂直线代表输入数组 [1,8,6,2,5,4,8,3,7]。在此情况下，容器能够容纳水（表示为蓝色部分）的最大值为 49。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/container-with-most-water
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class MaxArea {

    public static int maxArea(int[] height) {
        int max = 0, left = 0, right = height.length - 1;
        while (left < right) {
            //左边比右边小，向右移动才有可能找到最大值
            while (height[left] <= height[right] && left < right) {
                int a = height[left] * (right - left);
                max = Math.max(max, a);
                left++;
            }

            //右边比左边小，向左移动才有可能找到最大值
            while (height[left] >= height[right] && left < right) {
                int a = height[right] * (right - left);
                max = Math.max(max, a);
                right--;
            }
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println(maxArea(new int[]{1,2,1}));
    }

}
