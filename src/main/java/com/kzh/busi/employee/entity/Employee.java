package com.kzh.busi.employee.entity;

import com.kzh.generate.common.DateTime;
import com.kzh.generate.common.Edit;
import com.kzh.generate.common.Name;
import com.kzh.generate.common.Show;
import com.kzh.generate.common.Query;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: kzh
 * Date: 13-12-21
 * Time: 上午10:24
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table
public class Employee {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Name("id")
    private int id;

    @Name("姓名")
    @Show
    @Edit
    @Column(nullable = false)
    @Query
    private String name;
    @Name("联系方式")
    @Show
    @Edit
    private String phone;
    @Name("性别")
    @Show
    @Edit
    private String sex;
    @Name("身份证号")
    @Show
    @Edit
    private String identity_card_id;
    @Name("银行卡号")
    @Show
    @Edit
    private String bank_card_id;
    @Name("编制")
    @Show
    @Edit
    private String post;
    @Name("入职日期")
    @Show
    @Edit
    @DateTime
    private String entry_date;
    @Name("录入时间")
    @Show
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
