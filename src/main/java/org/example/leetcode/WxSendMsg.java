package org.example.leetcode;

import cn.hutool.http.HttpUtil;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class WxSendMsg {

    public static void main(String[] args) throws InterruptedException {
        // 好友昵称
        String friendNickName = "文件传输助手";
        Timer timer = new Timer();
        Timer timer2 = new Timer();
        Date date = getDate(14, 20, 21);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                searchPerson(friendNickName);
            }
        },date);

        Date date2 = getDate(17, 30, 00);
        timer2.schedule(new TimerTask() {
            @Override
            public void run() {
                searchPerson(friendNickName);
            }
        },date2);

    }

    //获取执行时间
    public static Date getDate(Integer hour,Integer minute,Integer second){
        //设置执行时间
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);//每天

        calendar.set(year, month, day, hour, minute, second);
        Date date = calendar.getTime();
        //如果启动时间超过执行时间，则明天执行
        if (date.before(new Date())) {
            date = addDay(date, 1);
        }
        return date;
    }

    // 增加或减少天数
    public static Date addDay(Date date, int num) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.DAY_OF_MONTH, num);
        return startDT.getTime();
    }

    private static void searchPerson(String friendNickName)  {
        // 创建Robot对象
        Robot robot = getRobot();
        //打开微信 Ctrl+Alt+W
        assert robot != null;
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_ALT);
        robot.keyPress(KeyEvent.VK_W);
        //释放Ctrl按键，像Ctrl，退格键，删除键这样的功能性按键，在按下后一定要释放
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_ALT);

        // 该延迟不能少，否则无法搜索
        robot.delay(1000);

        // Ctrl + F 搜索指定好友
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_F);
        robot.keyRelease(KeyEvent.VK_CONTROL);

        // 将好友昵称发送到剪切板
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable tText = new StringSelection(friendNickName);
        clip.setContents(tText, null);
        // 以下两行按下了ctrl+v，完成粘贴功能
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.delay(1000);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.delay(1000);

        // 发送消息
        try {
            sendMsg();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void sendMsg() throws InterruptedException {
        sendMsg("下班了，记得打卡!");
    }

    private static void sendMsg(String msg) {
        // 创建Robot对象
        Robot robot = getRobot();
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        // 将字符串复制到剪切板
        Transferable tText = new StringSelection(msg);
        clip.setContents(tText, null);
        // 以下两行按下了ctrl+v，完成粘贴功能
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        // 回车发送
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.delay(1000);
    }

    private static Robot getRobot(){
        // 创建Robot对象
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        return robot;
    }

}
