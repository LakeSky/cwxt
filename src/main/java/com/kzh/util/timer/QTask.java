package com.kzh.util.timer;

import javax.servlet.ServletContext;
import java.util.Calendar;
import java.util.TimerTask;

/**
 * Created with IntelliJ IDEA.
 * User: kzh
 * Date: 13-11-30
 * Time: 上午11:40
 * To change this template use File | Settings | File Templates.
 */
public class QTask extends TimerTask {
    /*private static final int C_SCHEDULE_HOUR = 0;
    private static boolean isRunning = false;
    private static boolean flag = true;*/
    private ServletContext context;

    public QTask(ServletContext context) {
        this.context = context;
    }

    @Override
    public void run() {
        //accessDataService = new UsmAccessDataService();
        Calendar cal = Calendar.getInstance();
        System.out.println("正在执行任务");
        System.out.println("任务执行结束");
    }

}
