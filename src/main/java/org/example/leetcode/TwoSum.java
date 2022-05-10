package org.example.leetcode;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class TwoSum {

    public static int[] twoSum(int[] nums, int target) {
        HashMap<Integer,Integer> hashMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (hashMap.containsKey(target - nums[i])) {
                return new int[]{i, hashMap.get(target - nums[i])};
            }
            hashMap.put(nums[i], i);
        }
        return new int[2];
    }

    public static void main(String[] args) {
        int[] ints = new int[]{3,2,4};
        int[] ints1 = twoSum(ints, 6);
        System.out.println(ints1[0] + "," + ints1[1]);
    }

}
