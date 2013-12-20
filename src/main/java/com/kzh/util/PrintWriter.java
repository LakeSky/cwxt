package com.kzh.util;

import net.sf.json.JSONArray;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class PrintWriter {

    public static String print(String str) {
        HttpServletResponse response = ServletActionContext.getResponse();
        /*
           调用getWriter之前未设置编码(既调用setContentType或者setCharacterEncoding方法设置编码),
           ttpServletResponse则会返回一个用默认的编码(既ISO-8859-1)编码的PrintWriter实例。这样就会
           成中文乱码。而且设置编码时必须在调用getWriter之前设置,不然是无效的。
        */
        response.setContentType("text/html;charset=utf-8");
        //response.setCharacterEncoding("UTF-8");
        java.io.PrintWriter out = null;
        try {
            out = response.getWriter();
            out.println(str);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.flush();
            out.close();
        }

        return "success";
    }
    public static String printListWithJson(List list) {
        JSONArray jsonArray = JSONArray.fromObject(list);
        HttpServletResponse response = ServletActionContext.getResponse();
        /*
           调用getWriter之前未设置编码(既调用setContentType或者setCharacterEncoding方法设置编码),
           ttpServletResponse则会返回一个用默认的编码(既ISO-8859-1)编码的PrintWriter实例。这样就会
           成中文乱码。而且设置编码时必须在调用getWriter之前设置,不然是无效的。
        */
        response.setContentType("text/html;charset=utf-8");
        //response.setCharacterEncoding("UTF-8");
        java.io.PrintWriter out = null;
        try {
            out = response.getWriter();
            out.println(jsonArray.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.flush();
            out.close();
        }

        return "success";
    }
}
