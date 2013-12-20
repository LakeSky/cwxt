package com.kzh.system.security.entity;

import com.kzh.generate.common.Edit;
import com.kzh.generate.common.Name;
import com.kzh.generate.common.Show;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Set;

/**
 * User: kzh
 * Date: 13-4-23
 * Time: 下午3:58
 */
@Entity
public class Role {
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    private String id;

    @Name("角色")
    @Show
    @com.kzh.generate.common.Query
    @Edit
    private String name;
    @Name("角色名称")
    @Show
    @com.kzh.generate.common.Query
    @Edit
    private String zh_name;

    @ManyToMany
    @JoinTable(name = "resource_role", joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "resource_id"))
    private Set<Resource> resources;

    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set getResources() {
        return resources;
    }

    public void setResources(Set<Resource> resources) {
        this.resources = resources;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
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
