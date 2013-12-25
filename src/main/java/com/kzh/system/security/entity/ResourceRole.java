package com.kzh.system.security.entity;

import com.kzh.generate.common.*;
import com.kzh.generate.common.Query;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "resource_role")
public class ResourceRole {
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @Column(length = 50)
    private String id;
    @Name("菜单")
    @Show
    @Query
    @Edit
    @Dict(type = "dynamic", values = {"select id,menu_name from Resource"})
    @Column(length = 50)
    private String resource_id;
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

    public String getResource_id() {
        return resource_id;
    }

    public void setResource_id(String resource_id) {
        this.resource_id = resource_id;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }
}
