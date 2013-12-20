package com.kzh.generate.common.entity;

import com.kzh.generate.common.Edit;
import com.kzh.generate.common.Name;
import com.kzh.generate.common.Query;
import com.kzh.generate.common.Show;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: kzh
 * Date: 13-10-17
 * Time: 下午8:23
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table
public class Country {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Name("id")
    private int id;
    @Name("英文国家名")
    @Show
    @Edit
    @Query
    private String name;
    @Name("中文国家名")
    @Show
    @Edit
    private String zh_name;

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

    public String getZh_name() {
        return zh_name;
    }

    public void setZh_name(String zh_name) {
        this.zh_name = zh_name;
    }
}
