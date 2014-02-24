package com.kzh.busi.employee.entity;

import com.kzh.generate.auto.QField;
import com.kzh.system.ApplicationConstant;
import com.kzh.util.encrypt.AES;
import com.kzh.util.encrypt.AESCoder;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class Employee {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int id;

    @QField(name = "姓名", type = "text", actions = "show,query,edit", nullable = false)
    private String name;
    @QField(name = "联系方式", type = "text", actions = "show,query,edit")
    private String phone;
    @QField(name = "性别", type = "dict", actions = "edit,query", dictValues = {"male", "男", "femal", "女"})
    private String sex;
    @QField(name = "身份证号", type = "text", actions = "show,edit")
    private String identity_card_id;
    @QField(name = "银行卡号", type = "text", actions = "show,edit")
    private String bank_card_id;
    @QField(name = "性质", type = "dict", actions = "show,query,edit", dictType = "dynamic",
            dictValues = {"select distinct post,post from Employee"})
    private String post;
    @QField(name = "工作状态", type = "dict", actions = "show,edit,query", dictValues = {"在职", "在职", "退休", "退休"})
    private String work_state;
    @QField(name = "临工or正式", type = "dict", actions = "show,edit,query", dictValues = {"临工", "临工", "正式", "正式"})
    private String temporary_worker;
//    @QField(name = "入职日期", type = "date", actions = "show,query,edit")
//    private Date entry_date;
//    @QField(name = "录入时间", type = "time", actions = "show")
//    private Date record_date;


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

    public String getIdentity_card_id() throws Exception {
        byte[] decrypt = AES.parseHexStr2Byte(identity_card_id);
        return new String(AES.decrypt(decrypt, ApplicationConstant.encryptKey));
    }

    public void setIdentity_card_id(String identity_card_id) throws Exception {
        if (StringUtils.isNotBlank(identity_card_id)) {
            byte[] encrypt = AES.encrypt(identity_card_id.trim(), ApplicationConstant.encryptKey);
            this.identity_card_id = AES.parseByte2HexStr(encrypt);
        }
    }

    public String getBank_card_id() throws Exception {
        byte[] decrypt = AES.parseHexStr2Byte(bank_card_id);
        return new String(AES.decrypt(decrypt, ApplicationConstant.encryptKey));
    }

    public void setBank_card_id(String bank_card_id) throws Exception {
        if (StringUtils.isNotBlank(bank_card_id)) {
            byte[] encrypt = AES.encrypt(bank_card_id.trim(), ApplicationConstant.encryptKey);
            this.bank_card_id = AES.parseByte2HexStr(encrypt);
        }
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    /*public Date getEntry_date() {
        return entry_date;
    }

    public void setEntry_date(Date entry_date) {
        this.entry_date = entry_date;
    }

    public Date getRecord_date() {
        return record_date;
    }

    public void setRecord_date(Date record_date) {
        record_date = new Date();
        this.record_date = record_date;
    }*/

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWork_state() {
        return work_state;
    }

    public void setWork_state(String work_state) {
        this.work_state = work_state;
    }

    public String getTemporary_worker() {
        return temporary_worker;
    }

    public void setTemporary_worker(String temporary_worker) {
        this.temporary_worker = temporary_worker;
    }
}
