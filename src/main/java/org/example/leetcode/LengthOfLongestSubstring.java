package org.example.leetcode;

import java.util.HashMap;

/**
 * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
 * 输入: s = "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 *
 * 方案：以每个字符为最后一位算出每个字符为最后一位的不含有重复字符的最长子串的长度。
 */
public class LengthOfLongestSubstring {

    public static int lengthOfLongestSubstring(String s) {
        HashMap hashMap = new HashMap();
        int res = 0;
        int max = 0;
        char[] chars = s.toCharArray();
        if (chars.length == 1) {
            return 1;
        }
        for (int i = 0; i < chars.length; i++) {
            max = Math.max(res, max);
            res = 0;
            hashMap = new HashMap();
            for (int j = i; j >= 0; j--) {
                if (hashMap.get(chars[j]) != null) {
                    break;
                }else {
                    res++;
                    hashMap.put(chars[j], 1);
                }
            }
        }
        max = Math.max(res, max);
        return max;
    }

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("au"));
    }

}
