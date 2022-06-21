package org.example.leetcode.game.level;

import org.example.leetcode.game.Boss;
import org.example.leetcode.game.Enemy;
import org.example.leetcode.game.Monster;
import org.example.leetcode.game.Vampire;

import java.util.ArrayList;
import java.util.List;

/**
 * 关卡2
 */
public class LevelTwo extends Level{

    List<Enemy> enemys = new ArrayList<Enemy>(); // 怪物集合
    Enemy boss = new Enemy(); // Boss

    public LevelTwo(boolean hasBoss) {
        enemys.add(new Vampire(1));
        enemys.add(new Vampire(2));
        enemys.add(new Vampire(3));
        enemys.add(new Vampire(2));
        enemys.add(new Vampire(3));
        enemys.add(new Vampire(4));
        enemys.add(new Vampire(1));
        if (hasBoss) {
            this.boss = new Boss(2);
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
