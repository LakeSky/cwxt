package com.kzh.system.statistics.action;

import com.kzh.system.statistics.OnlineCounterListener;
import com.kzh.util.PrintWriter;
import com.kzh.util.struts.BaseAction;
import org.apache.struts2.convention.annotation.ResultPath;

@ResultPath("/pages/system/statistics/")
public class ChartAction extends BaseAction {
    public String obtainChartData() {
        PrintWriter.printListWithJson(OnlineCounterListener.getList());
        return SUCCESS;
    }
}
