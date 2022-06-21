package org.example.leetcode.game;

public class Vampire extends Enemy {
    private int blood;// 吸血系数

    public Vampire(int t) {
        switch (t) {
            case 1:
                this.setType("青铜吸血鬼");
                this.setLife(70);
                this.setAttack(15);
                this.setDefend(2);
                this.setMiss(20);
                this.setBoss(false);
                break;
            case 2:
                this.setType("白银吸血鬼");
                this.setLife(80);
                this.setAttack(18);
                this.setDefend(4);
                this.setMiss(30);
                this.setBoss(false);
                break;
            case 3:
                this.setType("黄金吸血鬼");
                this.setLife(90);
                this.setAttack(20);
                this.setDefend(6);
                this.setMiss(60);
                this.setBoss(false);
                break;
            case 4:
                this.setType("铂金吸血鬼");
                this.setLife(100);
                this.setAttack(35);
                this.setDefend(10);
                this.setMiss(70);
                this.setBoss(false);
                break;
            default:
                System.out.println("输入错误");
                break;
        }
        this.setLive(true);
        this.setMaxLife(this.getLife());
        // maxLife=life;
    }

    public int getBlood() {
        return blood;
    }

    public void setBlood(int blood) {
        this.blood = blood;
    }

    // 吸血
    public void getBlood(int loseLife) {
        System.out.println("[+]" + this.getType() + ":吸血成功！");
        // 吸玩家失去的生命力*blood/100;
        int getBlood = loseLife * blood / 100;
        this.setLife(this.getLife() + getBlood);
        // life+=getBlood;
        if (this.getLife() >= this.getMaxLife()) {
            this.setLife(this.getMaxLife());
            // life=maxLife;
        }
    }

}
