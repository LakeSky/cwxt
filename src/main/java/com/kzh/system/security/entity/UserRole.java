package com.kzh.system.security.entity;

import com.kzh.generate.common.*;
import com.kzh.generate.common.Query;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "user_role")
public class UserRole {
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @Column(length = 50)
    private String id;
    @Name("用户名")
    @Show
    @Query
    @Edit
    @Dict(type = "dynamic", values = {"select id,username from User"})
    @Column(length = 50)
    private String user_id;
    @Name("角色")
    @Show
    @Query
    @Edit
    @Dict(type = "dynamic", values = {"select id,name from Role"})
    @Column(length = 50)
    private String role_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
