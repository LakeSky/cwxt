package com.kzh.action;

import com.opensymphony.xwork2.ActionSupport;

public class ExampleAction extends ActionSupport {
    private String message;
    private String msg;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String execute() {
        this.setMessage("welcome to ssh action");
        return SUCCESS;
    }
    public String addName() {
        return SUCCESS;
    }

}
