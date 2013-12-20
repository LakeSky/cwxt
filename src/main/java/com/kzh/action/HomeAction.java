package com.kzh.action;

import com.kzh.system.statistics.OnlineCounterListener;
import com.kzh.util.struts.BaseAction;
import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.ApplicationMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

//首页
public class HomeAction extends BaseAction {
    private long count;

    //---------get/set----------------------

    public String execute() {
//        ActionContext actionContext = ActionContext.getContext();
        ApplicationMap applicationMap = (ApplicationMap) ActionContext.getContext().get(ServletActionContext.APPLICATION);
        applicationMap.get("session");
        ServletContext context = ServletActionContext.getServletContext();
        context.getAttribute("session");
        return SUCCESS;
    }

    public long getCount() {
        return OnlineCounterListener.getCount();
    }
}
