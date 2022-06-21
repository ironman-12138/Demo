package org.example.leetcode.game;


public class Monster extends Enemy{
    public Monster(int t) {
        switch (t) {
            case 1:
                this.setType("青铜小怪");
                this.setLife(70);
                this.setAttack(10);
                this.setDefend(2);
                this.setMiss(10);
                this.setBoss(false);
                break;
            case 2:
                this.setType("白银小怪");
                this.setLife(80);
                this.setAttack(15);
                this.setDefend(4);
                this.setMiss(20);
                this.setBoss(false);
                break;
            case 3:
                this.setType("黄金小怪");
                this.setLife(90);
                this.setAttack(20);
                this.setDefend(6);
                this.setMiss(30);
                this.setBoss(false);
                break;
            case 4:
                this.setType("铂金小怪");
                this.setLife(100);
                this.setAttack(30);
                this.setDefend(10);
                this.setMiss(40);
                this.setBoss(false);
                break;
            case 5:
                this.setType("钻石小怪");
                this.setLife(150);
                this.setAttack(42);
                this.setDefend(12);
                this.setMiss(50);
                this.setBoss(false);
                break;
            default:
                System.out.println("输入错误");
                break;
        }
        this.setLive(true);
        this.setMaxLife(this.getLife());
        //maxLife=life;
    }
}