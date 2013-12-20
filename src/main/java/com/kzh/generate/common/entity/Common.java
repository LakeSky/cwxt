package com.kzh.generate.common.entity;

import com.kzh.generate.common.Edit;
import com.kzh.generate.common.Name;
import com.kzh.generate.common.Show;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class Common {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Name("id")
    private int id;

    @Name("表名")
    @Show
    @Edit
    @Column
    private String name;

    @Name("录入时间")
    private Date record_date;

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

    public Date getRecord_date() {
        return record_date;
    }

    public void setRecord_date(Date record_date) {
        this.record_date = record_date;
    }
}
