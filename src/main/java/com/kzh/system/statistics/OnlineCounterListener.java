package com.kzh.system.statistics;

import com.kzh.system.statistics.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.*;

public class OnlineCounterListener implements HttpSessionListener {
    @Autowired
    private ChartService chartService;
    //在线人数
    private static long count = 0;
    private static List<long[]> list = new ArrayList<long[]>();

    private synchronized void increase() {
        count++;
        initChartData();
    }

    private synchronized void reduce() {
        count--;
        initChartData();
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        increase();
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        reduce();
    }

    private void initChartData() {
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        Date date = new Date();
        long[] ele = new long[2];
        ele[0] = date.getTime() + cal.getTimeZone().getRawOffset();
        ele[1] = count;
        list.add(ele);
    }
    //------------get/set-------------------------------------


    public static long getCount() {
        return count;
    }

    public static void setCount(long count) {
        OnlineCounterListener.count = count;
    }

    public static List<long[]> getList() {
        return list;
    }

    public static void setList(List<long[]> list) {
        OnlineCounterListener.list = list;
    }
}
