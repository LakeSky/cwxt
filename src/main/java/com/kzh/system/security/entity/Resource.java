package com.kzh.system.security.entity;

import com.kzh.generate.common.*;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Set;

/**
 * User: kzh
 * Date: 13-4-23
 * Time: 下午4:02
 */
@Entity
public class Resource {
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    private String id;
    @Name("是否菜单")
    @Show
    @com.kzh.generate.common.Query
    @Edit
    @Column(nullable = false)
    @Dict(values = {"true", "是", "false", "否"})
    private boolean is_menu;
    @Name("菜单名称")
    @Show
    @com.kzh.generate.common.Query
    @Edit
    @Column(nullable = false)
    private String menu_name;
    @Name("菜单id")
    @Show
    @Edit
    private String menu_id;
    @Name("菜单父id")
    @Show
    @Edit
    private String parent_id;
    @Name("url")
    @Show
    @Edit
    private String url;
    @Name("序号")
    @Show
    @Edit
    private int seq;
    @ManyToMany
    @JoinTable(name = "resource_role", joinColumns = @JoinColumn(name = "resource_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public String getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(String menu_id) {
        this.menu_id = menu_id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public boolean isIs_menu() {
        return is_menu;
    }

    public void setIs_menu(boolean is_menu) {
        this.is_menu = is_menu;
    }
}
