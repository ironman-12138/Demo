package org.example.leetcode.game;

import java.util.Scanner;

public class TestGame {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("请输入玩家姓名：");
        String name = s.next();
        System.out.println("请输入玩家武器:");
        String w = s.next();
        boolean automatic = true;
//        System.out.println("是否自动？[Y][N]");
//        String a = s.next();
//        if (a.equals("Y") || a.equals("y")) {
//            automatic = true;
//        } else {
//            automatic = false;
//        }
        Game g = new Game(name, w, automatic);
        System.out.println("是否开始游戏？[Y][N]");
        String f = s.next();
        if (f.equals("Y") || f.equals("y")) {
            g.start();
        } else {
            System.out.println("结束");
        }

    }

}