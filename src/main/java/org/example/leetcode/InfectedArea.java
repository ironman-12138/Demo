package org.example.leetcode;

/**
 * 武汉出现新型病毒，该病毒传播性极强。用一个二维矩阵表示人体细胞，'0'表示正常细胞，'*'表示受到病毒感染，
 * 病毒可以向周围正常细胞扩散（只能上下左右），求多少块区域受到了感染。
 * 举例：
 * 输入
 * 0 0 * * 0
 * 0 0 * 0 0
 * 0 0 0 0 *
 * * * 0 0 *
 * 0 * 0 0 0
 * 输出 ： 3
 * <p>
 * 请实现如下方法：
 * public int num(char[][] cells) {
 * }
 */
public class InfectedArea {

    public static int num(char[][] cells) {
        int res = 0;
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (i == 0 && cells[i][j] == '*') {
                    res++;
                    if (j + 1 < cells[i].length && cells[i][j + 1] == '*') {
                        j++;
                    }
                }
                if (i != 0 && cells[i][j] == '*') {
                    res++;
                    if (cells[i - 1][j] == '*') {
                        res--;
                    }
                    if (j + 1 < cells[i].length && cells[i][j + 1] == '*') {
                        j++;
                        if (cells[i - 1][j] == '*' || (j - 2 > 0 && cells[i][j - 2] == '*')) {
                            res--;
                        }
                    }
                    if (j + 1 < cells[i].length && cells[i][j + 1] == 0 && j - 2 > 0 && cells[i][j - 2] == '*') {
                        res--;
                    }
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        char[][] chars = new char[][]{
                                        {0,    0,  '*', '*', 0},
                                        {0,  0,  '*',  0 , 0},
                                        {0,    0,   0,   0, '*'},
                                        {'*', '*',  0,   0, '*'},
                                        {0,   0,  0,0, '*'}};
        System.out.println(num(chars));
    }

}
