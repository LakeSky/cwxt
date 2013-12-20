package com.kzh.generate.common.entity;

import com.kzh.generate.common.*;
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
public class Test {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Name("id")
    private int id;

    @Name("姓名")
    @Show
    @Edit
    @Query
    @Column(nullable = false)
    private String name;

    @Name("年龄")
    @Show
    @Edit
    private int age;

    @Name("出生日期")
    @Show
    @Edit
    @Query
    @Column(nullable = false)
    private Date birthday;

    @Name("个人故事")
    @Edit
    @Column(length = 1000)
    private String story;

    @Name("姓别")
    @Edit
    @Query
    @Dict(values = {"male", "男", "femal", "女"})
    @Show
    private String sex;

    @Name("国家")
    @Edit
    @Dict(type = "dynamic", values = {"select name,zh_name from country"})
    @Show
    private String country;

    @Name("提醒时间")
    @Edit
    @Show
    @DateTime
    @Query
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getAlarm_time() {
        return alarm_time;
    }

    public void setAlarm_time(Date alarm_time) {
        this.alarm_time = alarm_time;
    }
}
