package com.kzh.generate.auto.entity;

import com.kzh.generate.common.*;
import com.kzh.generate.auto.QField;
import com.kzh.generate.common.Query;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * User: kzh
 * Date: 13-10-16
 * Time: 上午10:19
 */
@Entity
@Table
public class Auto {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int id;

    @QField(name = "姓名", type = "text", actions = "show,query,edit", nullable = false)
    private String name;

    @QField(name = "年龄", type = "text", actions = "show,edit", nullable = false)
    private int age;

    @QField(name = "出生日期", type = "date", actions = "show,edit,query", nullable = false)
    private Date birthday;

    @QField(name = "故事", type = "textarea", actions = "edit", nullable = false)
    private String story;

    @QField(name = "性别", type = "dict", actions = "edit,query",
            nullable = false, dictValues = {"male", "男", "femal", "女"})
    private String sex;

    /*@Name("国家")
    @Edit
    @Dict(type = "dynamic", values = {"select name,zh_name from country"})
    @Show
    private String country;*/

    @QField(name = "提醒时间", type = "time", actions = "show,edit", nullable = false)
    private Date alarm_time;

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getAlarm_time() {
        return alarm_time;
    }

    public void setAlarm_time(Date alarm_time) {
        this.alarm_time = alarm_time;
    }


}
