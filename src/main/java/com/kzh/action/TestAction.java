package com.kzh.action;

import com.kzh.util.struts.BaseAction;
import org.apache.struts2.convention.annotation.ResultPath;

@ResultPath("/")
public class TestAction extends BaseAction {
    private String classpath;

    public String execute() {
        classpath = this.getClass().getResource("/").toString();
        return SUCCESS;
    }

    //-----------------------------------------------
    public String getClasspath() {
        return classpath;
    }

    public void setClasspath(String classpath) {
        this.classpath = classpath;
    }
}
