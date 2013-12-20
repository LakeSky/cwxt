package com.kzh.system.security;

import com.kzh.system.security.entity.Resource;
import com.kzh.system.security.entity.Role;
import com.kzh.system.security.entity.User;
import com.kzh.system.security.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Transactional
public class MyUserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserDao userDao;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userDao.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        Collection<GrantedAuthority> grantedAuths = obtionGrantedAuthorities(user);
        user.setAuthorities(grantedAuths);
        return user;
    }

    //取得用户的权限
    private Set<GrantedAuthority> obtionGrantedAuthorities(User user) {
        Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            authSet.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authSet;
    }
}