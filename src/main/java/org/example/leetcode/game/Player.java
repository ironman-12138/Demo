package org.example.leetcode.game;

import org.example.utils.StringUtils;

//玩家
public class Player {

    private String name;
    private String weapon;// 武器
    private int life;// 生命值
    private boolean isLive;// 是否活着
    private int attack;// 攻击力
    private int defend;// 防御力
    private int exp;// 经验值
    private int level;// 等级
    private int maxLife;// 最大生命值
    private int miss;// 躲避系数
    private int medicine;// 回复药的数量

    int times;

    public Player() {
        // TODO Auto-generated constructor stub
    }

    // 为name 和武器赋值 并且初始化
    public Player(String name, String weapon) {
        this.name = name;
        this.weapon = weapon;
        life = 180;
        isLive = true;
        attack = 60;
        defend = 3;
        level = 1;
        exp = 0;
        maxLife = life;
        miss = 60;
        medicine = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeapon() {
        return weapon;
    }

    public void setWeapon(String weapon) {
        this.weapon = weapon;
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

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public int getMedicine() {
        return medicine;
    }

    public void setMedicine(int medicine) {
        this.medicine = medicine;
    }

    // 打Vampire
    public void fight(Enemy e) {
        // 在打之前判断一下小怪和玩家是否存活 如果一方死亡 那么程序终止
        if (!isLive || !e.isLive()) {

            return;// return 表示结束 方法

        }
        System.out.println("[-]" + name + "挥舞着" + weapon + "杀向" + e.getType());
        // m必须受伤
        e.injured(this);// this 表示当前对象
    }

    // 受伤Vampire
    public void injured(Vampire v) {
        int n = GameUtil.getNumber(1, 101);
        if (n < miss) {
            System.out.println("[-]" + "躲避成功");
            show();
            return;
        }
        System.out.println("[-]" + name + ": 掉血了");
        // 减少的生命是动态的值
        int loseLife = GameUtil.getLoseLife(v.getAttack(), defend);
        // int loseLife=m.attack-defend;
        life -= loseLife;
        if (life <= 0) {
            life = 0;
            dead();

        }
        show();
        // 当玩家受伤后 吸血
        v.getBlood(loseLife);

    }

    // 受伤Monster
    public void injured(Enemy e) {
        int n = GameUtil.getNumber(1, 101);
        if (n < miss) {
            System.out.println("[-]" + name + "闪躲成功");
            show();
            return;
        }
        System.out.println("[-]" + name + ":掉血");
        // 减少的生命是动态的值
        int loseLife = GameUtil.getLoseLife(e.getAttack(), defend);
        // int loseLife=m.attack-defend;
        if (loseLife > 0) {
            life -= loseLife;
        }
        if (life <= 0) {
            life = 0;
            dead();
        }
        show();
    }

    // 死亡
    public void dead() {
        System.out.println("[-]" + name + "你挂了");
        isLive = false;

    }

    // 显示状态
    public void show() {
        System.out.println(name + "生命值：" + life + "\n攻击力：" + attack + "\n防御力：" + defend);
    }

    // 升级方法
    public void addLevel() {
        attack += 5;
        defend += 5;
        level++;
        // 满血
//        life = maxLife;
        System.out.println("[-]" + "升级成功！当前的等级是：" + level);
        show();
    }

    // 增加经验方法 当对手死亡的时候 玩家增加经验 经验值=对手的生命值

    public void addExp(int life) {
        exp += life;// 加的经验值=对手的生命值
        // 判断当前经验值是否满足此等级升级所需的经验值
        int needExp = 0;
        for (int i = 1; i <= level; i++) {
            needExp += i * 100;
        }
        if (exp > needExp) {
            addLevel();
        }else {
            show();
        }
    }

    public void addMedicine() {
        int number = GameUtil.getNumber(1, 101);
        if (number <= 25) {
            System.out.println("[-]怪物掉落物品，捡到恢复药一瓶！");
            medicine += 1;
        }
    }
}
