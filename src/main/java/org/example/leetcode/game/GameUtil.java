package org.example.leetcode.game;


public class GameUtil {
    //求减少生命值的方法  方法用static 修饰  ，调用时 类名.方法名
    public static int getLoseLife(int attack,int defend){
        return attack-defend;
    }
    //求a-b之间随机数方法
    public static int getNumber(int a,int b){
        //求任意两个数之间的随机数（int）
        return (int)(Math.random()*(b-a)+a);
    }

}
