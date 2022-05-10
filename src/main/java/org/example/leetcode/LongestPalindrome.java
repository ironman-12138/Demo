package org.example.leetcode;

/**
 * 给你一个字符串 s，找到 s 中最长的回文子串。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：s = "babad"
 * 输出："bab"
 * 解释："aba" 同样是符合题意的答案。
 */
public class LongestPalindrome {

    public static String longestPalindrome(String s) {
        char[] chars = s.toCharArray();
        int start = 0, end = 0;
        for (int i = 0; i < chars.length; i++) {
            int x = compare(s, i, i);
            int y = compare(s, i, i + 1);
            int max = Math.max(x, y);
            if (max > end - start) {
                start = i - (max - 1) / 2 > 0 ? i - (max - 1) / 2 : 0;
                end = i + max / 2;
            }
        }
        return s.substring(start, end + 1);
    }

    private static int compare(String s, int i, int j) {
        while (i >= 0 && j <s.length()) {
            if (s.charAt(i) == s.charAt(j)) {
                i--;
                j++;
            }else {
                break;
            }
        }
        return j - i - 1;
    }

    public static void main(String[] args) {
        System.out.println(longestPalindrome("babaab"));
    }

}
