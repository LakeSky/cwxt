package com.kzh.system.security.entity;

import com.kzh.generate.common.*;
import com.kzh.generate.common.Query;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

/**
 * User: kzh
 * Date: 13-4-23
 * Time: 下午3:57
 */
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @Name("id")
    private String id;
    @Name("用户名")
    @Edit
    @Query
    @Show
    private String username;
    @Name("密码")
    @Edit
    private String password;
    @Name("未过期")
    @Edit
    @Show
    @Dict(values = {"true", "是", "false", "否"})
    private boolean accountNonExpired;
    @Name("未锁定")
    @Edit
    @Show
    @Dict(values = {"true", "是", "false", "否"})
    private boolean accountNonLocked;
    @Name("是否信任")
    @Edit
    @Show
    @Dict(values = {"true", "是", "false", "否"})
    private boolean credentialsNonExpired;
    @Name("是否可用")
    @Edit
    @Show
    @Dict(values = {"true", "是", "false", "否"})
    private boolean enabled;
    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @Transient
    private Collection<? extends GrantedAuthority> authorities;

    public User() {

    }

    public User(String username, String password, boolean enables,
                boolean accountNonExpired, boolean credentialsNonExpired,
                boolean accountNonLocked, Collection<GrantedAuthority> grantedAuths) {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set getRoles() {
        return roles;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
