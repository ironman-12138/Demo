package org.example.leetcode.game;

public class Boss extends Enemy {

    public Boss(int t) {
        switch (t) {
            case 1:
                this.setType("熔岩BOSS");
                this.setLife(250);
                this.setAttack(45);
                this.setDefend(15);
                this.setMiss(20);
                this.setBoss(true);
                break;
            case 2:
                this.setType("吸血鬼伯爵");
                this.setLife(150);
                this.setAttack(50);
                this.setDefend(10);
                this.setMiss(75);
                this.setBoss(true);
                break;
            case 3:
                this.setType("神圣巨龙");
                this.setLife(280);
                this.setAttack(55);
                this.setDefend(15);
                this.setMiss(60);
                this.setBoss(true);
                break;
            default:
                System.out.println("输入错误");
                break;
        }
        this.setLive(true);
        this.setMaxLife(this.getLife());
        // maxLife=life;
    }
}
