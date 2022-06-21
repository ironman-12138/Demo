package org.example.leetcode;

import java.util.Random;
import java.util.Scanner;

/**
 * 猜数字
 */
public class GuessNumber {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int min = 0;
        int max = 1000;
        int num = new Random().nextInt(max) + 1;
        int number = 0;
        System.out.print("请输入值：");
        int count = scanner.nextInt();
        number++;
        while (true) {
            if (count == num) {
                System.out.println("恭喜你猜对了！耗费次数：" + number);
                return;
            }else if (count > num) {
                System.out.println("-----继续-----");
                System.out.println(min + "~" + (count - 1));
                max = count - 1;
                System.out.print("请输入值：");
                count = scanner.nextInt();
                number++;
            }else {
                System.out.println("-----继续-----");
                System.out.println((count + 1) + "~" + max);
                min = count + 1;
                System.out.print("请输入值：");
                count = scanner.nextInt();
                number++;
            }
        }

    }

}
