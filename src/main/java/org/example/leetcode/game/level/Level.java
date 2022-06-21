package org.example.leetcode.game.level;

import org.example.leetcode.game.Enemy;

import java.util.ArrayList;
import java.util.List;

public class Level {

    List<Enemy> enemys = new ArrayList<Enemy>(); // 怪物集合
    Enemy boss = new Enemy(); // Boss

    public Level(int levelNum) {
        switch (levelNum) {
            case 1:
                LevelOne levelOne = new LevelOne(true);
                this.enemys = levelOne.getEnemys();
                this.boss = levelOne.getBoss();
                break;
            case 2:
                LevelTwo levelTwo = new LevelTwo(true);
                this.enemys = levelTwo.getEnemys();
                this.boss = levelTwo.getBoss();
                break;
            default:
                System.out.println("关卡暂未开放");
                break;
        }
    }

    public Level() {
    }

    public List<Enemy> getEnemys() {
        return enemys;
    }

    public void setEnemys(List<Enemy> enemys) {
        this.enemys = enemys;
    }

    public Enemy getBoss() {
        return boss;
    }

    public void setBoss(Enemy boss) {
        this.boss = boss;
    }
}
