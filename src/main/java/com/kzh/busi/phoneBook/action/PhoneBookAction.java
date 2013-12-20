package com.kzh.busi.phoneBook.action;

import com.kzh.busi.phoneBook.service.PhoneBookService;
import com.kzh.util.PrintWriter;
import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONArray;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ResultPath;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: kzh
 * Date: 13-4-13
 * Time: 下午2:16
 */

@ResultPath("/pages/busi/phoneBook/")
public class PhoneBookAction extends ActionSupport {
    @Autowired
    private PhoneBookService service;

    private String message;

    private List phoneBookList;

    public List getPhoneBookList() {
        return phoneBookList;
    }

    public void setPhoneBookList(List phoneBookList) {
        this.phoneBookList = phoneBookList;
    }

    public String queryPhoneBookList() {
        this.setPhoneBookList(service.queryPhoneBookList());
        JSONArray jsonArray = JSONArray.fromObject(phoneBookList);

        PrintWriter.print(jsonArray.toString());
        return SUCCESS;
    }

    public String execute() {
        this.setMessage("haolea ");
        return SUCCESS;
    }

    public String queryData() {
        phoneBookList = new ArrayList();
        Map map = new HashMap<String, String>();
        map.put("String", "str");
        map.put("Integer", "int");
        map.put("Float", "float");
        Map map1 = new HashMap<String, String>();
        map1.put("Str1ing", "str");
        map1.put("Int1eger", "int");
        map1.put("Flo1at", "float");
        phoneBookList.add(map);
        phoneBookList.add(map1);
        return SUCCESS;
    }

    public String generatePhoneBookList() {
        List list = new ArrayList();

        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "zhangsan");
        map.put("age", "19");
        map.put("phone", "2012234234");

        Map<String, String> map1 = new HashMap<String, String>();
        map1.put("name", "zhangsan");
        map1.put("age", "19");
        map1.put("phone", "2012232");

        /*Random random = new Random();
        for (int i = 0; i < 800; i++) {
            Map mapNew = new HashMap();
            mapNew.put("name", random.nextDouble());
            mapNew.put("age", random.nextDouble());
            mapNew.put("phone", random.nextDouble());
            list.add(mapNew);
        }*/

        list.add(map);
        list.add(map1);
        /*Map mapResult = new HashMap();
        mapResult.put("rows", list);*/
        JSONArray jsonArray = JSONArray.fromObject(list);
        PrintWriter.print(jsonArray.toString());
        return SUCCESS;
    }

    public PhoneBookService getService() {
        return service;
    }

    public void setService(PhoneBookService service) {
        this.service = service;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
