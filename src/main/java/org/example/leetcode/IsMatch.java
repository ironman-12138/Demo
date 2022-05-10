package org.example.leetcode;

/**
 * 给你一个字符串 s 和一个字符规律 p，请你来实现一个支持 '.' 和 '*' 的正则表达式匹配。
 *
 * '.' 匹配任意单个字符
 * '*' 匹配零个或多个前面的那一个元素
 * 所谓匹配，是要涵盖 整个 字符串 s的，而不是部分字符串。
 *
 *  
 * 示例 1：
 *
 * 输入：s = "aa" p = "a"
 * 输出：false
 * 解释："a" 无法匹配 "aa" 整个字符串。
 * 示例 2:
 *
 * 输入：s = "aa" p = "a*"
 * 输出：true
 * 解释：因为 '*' 代表可以匹配零个或多个前面的那一个元素, 在这里前面的元素就是 'a'。因此，字符串 "aa" 可被视为 'a' 重复了一次。
 */
public class IsMatch {

    public static boolean isMatch(String s, String p) {
        char[] chars = p.toCharArray();
        char[] chars2 = s.toCharArray();
        int j = 0;
        for (int i = 0; i < chars.length; i++) {
            if (j == chars2.length) {
                return true;
            }
            if (chars[i] == '.') {
                j++;
            }else if (chars[i] == '*') {
                if (j > 0 && i > 0 && (chars2[j - 1] == chars[i - 1] || chars[i - 1] == '.')) {
                    j++;
                }else if (j != 0){
                    return false;
                }
            }else {
                if (chars[i] == chars2[j]) {
                    j++;
                }else if (i != 0){
                    return false;
                }
            }
        }
        if (j == chars2.length) {
            return true;
        }else {
            return false;
        }
    }

    public static void main(String[] args) {
        System.out.println(isMatch("aab", "c*a*b"));                //true
        System.out.println(isMatch("ab", ".*"));                    //true
        System.out.println(isMatch("mississippi", "mis*is*p*."));   //false
        System.out.println(isMatch("aa", "a"));                     //false
        System.out.println(isMatch("ab", ".*c"));                   //false
    }

}
