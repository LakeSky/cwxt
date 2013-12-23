package com.kzh.busi.employee.entity;

import com.kzh.generate.auto.QField;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table
public class Employee {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int id;

    @QField(name = "姓名", type = "text", actions = "show,query,edit", nullable = false)
    private String name;
    @QField(name = "联系方式", type = "text", actions = "show,query,edit", nullable = false)
    private String phone;
    @QField(name = "性别", type = "dict", actions = "edit,query",
            dictValues = {"male", "男", "femal", "女"})
    private String sex;
    @QField(name = "身份证号", type = "text", actions = "show,query,edit")
    private String identity_card_id;
    @QField(name = "银行卡号", type = "text", actions = "show,query,edit")
    private String bank_card_id;
    @QField(name = "编制", type = "text", actions = "show,query,edit")
    private String post;
    @QField(name = "入职日期", type = "date", actions = "show,query,edit")
    private String entry_date;
    @QField(name = "录入时间", type = "date", actions = "show")
    private String record_date;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIdentity_card_id() {
        return identity_card_id;
    }

    public void setIdentity_card_id(String identity_card_id) {
        this.identity_card_id = identity_card_id;
    }

    public String getBank_card_id() {
        return bank_card_id;
    }

    public void setBank_card_id(String bank_card_id) {
        this.bank_card_id = bank_card_id;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getEntry_date() {
        return entry_date;
    }

    public void setEntry_date(String entry_date) {
        this.entry_date = entry_date;
    }

    public String getRecord_date() {
        return record_date;
    }

    public void setRecord_date(String record_date) {
        this.record_date = record_date;
    }
}
