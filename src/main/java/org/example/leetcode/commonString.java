package org.example.leetcode;

/**
 * 计算最长公共子序列长度
 */
public class commonString {

    public static int longestCommonSubsequence(String text1, String text2) {
        int l1 = text1.length(), l2 = text2.length();
        int[][] res = new int[l1][l2];
        for (int i = 0; i < l1; i++) {
            for (int j = 0; j < l2; j++) {
                if (text1.charAt(i) == text2.charAt(j)) {
                    if (i == 0 || j == 0) {
                        res[i][j] = 1;
                    }else {
                        res[i][j] = res[i - 1][j - 1] + 1;
                    }
                }else {
                    if (i == 0 && j != 0) {
                        res[i][j] = res[i][j - 1];
                    }else if (i != 0 && j == 0) {
                        res[i][j] = res[i - 1][j];
                    }else if (i == 0 && j == 0){
                        res[i][j] = 0;
                    }else {
                        res[i][j] = Math.max(res[i - 1][j], res[i][j - 1]);
                    }
                }
            }
        }
        System.out.println("-------------------------");
        printf(res);
        System.out.println("-------------------------");
        return res[l1 - 1][l2 - 1];
    }

    public static void printf(int[][] res) {
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[i].length; j++) {
                System.out.print(res[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        System.out.println(longestCommonSubsequence("abc", "def"));
    }
}
