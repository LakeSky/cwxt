package com.kzh.util.timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Timer;

/**
 * Created with IntelliJ IDEA.
 * User: kzh
 * Date: 13-11-30
 * Time: 上午11:38
 * To change this template use File | Settings | File Templates.
 */
public class TimerListener implements ServletContextListener {
    private Timer timer;
    private QTask task;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        timer = new java.util.Timer(true);
        task = new QTask(servletContextEvent.getServletContext());
        System.out.println("定时器已启动");
        timer.schedule(task, 0, 60 * 1000);
        System.out.println("已经添加任务调度表");

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        timer.cancel();
        System.out.println("定时器已销毁");
    }
}
