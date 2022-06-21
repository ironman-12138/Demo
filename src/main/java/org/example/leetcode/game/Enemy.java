package org.example.leetcode.game;

public class Enemy {
    private String type;// 名称
    private int life;// 生命值
    private boolean isLive;// 是否活着
    private int attack;// 攻击力
    private int defend;// 防御力
    private int maxLife;//最大生命
    private int miss;// 躲避系数
    private boolean boss;// 是否是boss
    // 受伤
    public void injured(Player h) {
        int n = GameUtil.getNumber(1,101);
        if (n < miss) {
            System.out.println("[#]没打到");
            kill(h);// 还击
            return;
        }
        System.out.println("[#]" + type + ":受伤");
        // 生命值减少
        int loseLife = GameUtil.getLoseLife(h.getAttack(), defend);
        // int loseLife=g.getLoseLife(h.attack, defend);
        // int loseLife=h.attack-defend;
        life -= loseLife;// life=life-30;
        // 判断生命值是否小于0
        if (life <= 0) {
            life = 0;
            dead(h);// 死亡
        } else {
            show();// 显示状态
            kill(h);// 还击
        }
    }


    public String getType() {
        return type;
    }


    public void setType(String type) {
        this.type = type;
    }


    public int getLife() {
        return life;
    }


    public void setLife(int life) {
        this.life = life;
    }


    public boolean isLive() {
        return isLive;
    }


    public void setLive(boolean isLive) {
        this.isLive = isLive;
    }


    public int getAttack() {
        return attack;
    }


    public void setAttack(int attack) {
        this.attack = attack;
    }


    public int getDefend() {
        return defend;
    }


    public void setDefend(int defend) {
        this.defend = defend;
    }


    public int getMaxLife() {
        return maxLife;
    }


    public void setMaxLife(int maxLife) {
        this.maxLife = maxLife;
    }


    public int getMiss() {
        return miss;
    }


    public void setMiss(int miss) {
        this.miss = miss;
    }

    public boolean isBoss() {
        return boss;
    }

    public void setBoss(boolean boss) {
        this.boss = boss;
    }

    // 还击
    public void kill(Player h) {
        System.out.println("[#]" + type + ":开始反击，" + type + "还击" + h.getName());
        h.injured(this);
    }
    // 死亡
    public void dead(Player h) {
        isLive = false;
        System.out.println("[#]" + type + "挂了");
        // 有机会捡到恢复药
        h.addMedicine();
        // 调用hunter中添加经验值的方法
        h.addExp(maxLife);
    }

    // 显示状态
    public void show() {
        System.out.println("[#]"+type + "生命值是：" + life + "，是否活着" + isLive);

    }

}