package org.example.leetcode.game.level;

import org.example.leetcode.game.Boss;
import org.example.leetcode.game.Enemy;
import org.example.leetcode.game.Monster;
import org.example.leetcode.game.Vampire;

import java.util.ArrayList;
import java.util.List;

/**
 * 关卡1
 */
public class LevelOne extends Level{

    List<Enemy> enemys = new ArrayList<Enemy>(); // 怪物集合
    Enemy boss = new Enemy(); // Boss

    public LevelOne(boolean hasBoss) {
        enemys.add(new Monster(1));
        enemys.add(new Monster(2));
        enemys.add(new Monster(3));
        enemys.add(new Monster(2));
        enemys.add(new Monster(3));
        enemys.add(new Monster(3));
        enemys.add(new Monster(4));
        enemys.add(new Monster(5));
        enemys.add(new Monster(1));
        if (hasBoss) {
            this.boss = new Boss(1);
        }
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
