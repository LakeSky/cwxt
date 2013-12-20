package com.kzh.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ParentPackage("json-default")
public class TestJsonAction extends ActionSupport {
    private String name;
    private List list;

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    @Action(results = {@Result(type = "json",params = {"root","list","excludeNullProperties","true"})})
    public String test() throws Exception {
        list = new ArrayList();
        Map map = new HashMap();
        map.put("id", 1);
        map.put("text", "firstMenu");
        map.put("state","closed");

        Map mapnew=new HashMap();
        mapnew.put("url","example.do");
        map.put("attributes",mapnew);
        /*List listc=new ArrayList();
        Map mapc = new HashMap();
        mapc.put("id", 1);
        mapc.put("text", "firstMenu");
        listc.add(mapc);
        map.put("children",listc);*/
        Map map1 = new HashMap();
        map1.put("id", 2);
        map1.put("text", "secondMenu");
        map1.put("attributes",mapnew);
        list.add(map);
        list.add(map1);

//        name = "helloworld";
        return SUCCESS;
    }
}
