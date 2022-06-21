package org.example.leetcode.game;

import org.example.leetcode.game.level.Level;
import org.example.leetcode.game.level.LevelOne;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    Player h;
    List<Enemy> enemys = new ArrayList<Enemy>(); // 怪物集合
    boolean hasBoss = true; // 是否有boss
    Enemy boss = new Enemy(); // Boss
    boolean automatic = true; // 是否自动
    int currLevelNum = 1; // 当前关卡数
    int totalLevelNum = 2; // 总关卡数

    public Game(String name, String weapon, boolean automatic) {
        h = new Player(name, weapon);
        LevelOne levelOne = new LevelOne(true);
        boss = levelOne.getBoss();
        enemys = levelOne.getEnemys();
//        enemys.add(new Monster(1));
//        enemys.add(new Monster(2));
//        enemys.add(new Monster(3));
//        enemys.add(new Monster(2));
//        enemys.add(new Monster(3));
//        enemys.add(new Monster(3));
//        enemys.add(new Monster(4));
//        enemys.add(new Monster(5));
//        enemys.add(new Monster(1));
//        enemys.add(new Vampire(4));
//        enemys.add(new Vampire(1));
        this.automatic = automatic;
    }

    public void start() {
        // 游戏手动打
        while (true) {
            // 生成一个随机数 0-5
            int ran = GameUtil.getNumber(0, enemys.size());
            h.fight(enemys.get(ran));
            // 玩家死亡 游戏结束
            if (!h.isLive()) {
                if (h.getMedicine() > 0) {
                    System.out.println("❤❤❤喝了瓶药，恢复一下");
                    h.setLife(100);
                    h.setLive(true);
                    h.setMedicine(h.getMedicine() - 1);
                }else {
                     end();
                     break;
                }
            }

            // 如果当前对手是死亡
            if (!enemys.get(ran).isLive()) {
                if (enemys.get(ran).isBoss()) {
                    // boss死亡
                    hasBoss = false;
                }
                // 将此对手移除集合
                enemys.remove(enemys.get(ran));
            }
            // 判断集合大小 如果小于等于0 证明所有的对手都死亡了
            if (enemys.size() <= 0 && !hasBoss && currLevelNum == totalLevelNum) {
                System.out.println("恭喜全部通关！！！");
                end();
                break;
            } else if (enemys.size() <= 0 && !hasBoss && currLevelNum < totalLevelNum) {
                // 进入下一关
                System.out.println("===========开始进入下一关卡===========");
                currLevelNum++;
                Level level = new Level(currLevelNum);
                enemys = level.getEnemys();
                boss = level.getBoss();
                hasBoss = true;
            } else if (enemys.size() <= 0) {
                //加入最终boss
                System.out.println("--------！！！BOSS出现了！！！--------");
                enemys.add(boss);
            }

            try {
                if (!automatic) {
                    Scanner s = new Scanner(System.in);
                    System.out.println("是否继续寻找对手？[Y][N]");
                    String next = s.next();
                    if (next.equals("Y") || next.equals("y")) {
                        System.out.println("-----------正在寻找对手--------------");
                        Thread.sleep(1000);
                    } else {
                        System.out.println("逃跑成功");
                        return;
                    }
                } else {
                    System.out.println("-----------正在寻找对手--------------");
                    Thread.sleep(2000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    public void end() {
        System.out.println("Game Over!!!");
    }

}
