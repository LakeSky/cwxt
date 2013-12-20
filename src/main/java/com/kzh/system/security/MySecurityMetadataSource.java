package com.kzh.system.security;

import com.kzh.system.security.dao.ResourceDao;
import com.kzh.system.security.dao.RoleDao;
import com.kzh.system.security.entity.Resource;
import com.kzh.system.security.entity.Role;
import org.hibernate.internal.util.collections.ConcurrentReferenceHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;

//1 加载资源与权限的对应关系
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    public static Map<String, Collection<ConfigAttribute>> resourceMap = null;
    @Autowired
    private ResourceDao resourceDao;
    @Autowired
    private RoleDao roleDao;

    public static Map<String, Collection<ConfigAttribute>> getResourceMap() {
        return resourceMap;
    }

    public Collection<ConfigAttribute> getAllConfigAttributes() {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean supports(Class<?> clazz) {
        // TODO Auto-generated method stub
        return true;
    }

    public String save() {
        return null;
    }

    //加载所有资源与权限的关系
    private void loadResourceDefine() {
        if (resourceMap == null) {
            resourceMap = this.resourceDao.findAll();
            /*List<Resource> resources = this.resourceDao.findAll();
            for (Resource resource : resources) {
                Set<Role> roles = resource.getRoles();
                Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
                for (Role role : roles) {
                    //以权限名封装为Spring的security Object
                    ConfigAttribute configAttribute = new SecurityConfig(role.getName());
                    configAttributes.add(configAttribute);
                }
                resourceMap.put(resource.getUrl(), configAttributes);
            }*/
        }

        //Set<ConcurrentReferenceHashMap.Entry<String, Collection<ConfigAttribute>>> resourceSet = resourceMap.entrySet();
        //Iterator<ConcurrentReferenceHashMap.Entry<String, Collection<ConfigAttribute>>> iterator = resourceSet.iterator();
    }

    //返回所请求资源所需要的权限
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        int index = requestUrl.indexOf("&");
        if (index != -1 && index != 0) {
            requestUrl = requestUrl.substring(1, index);
        }
        if (requestUrl.length() != 1) {
            requestUrl = requestUrl.substring(1);
        }
        System.out.println("requestUrl is " + requestUrl);
        if (resourceMap == null) {
            loadResourceDefine();
        }
        return resourceMap.get(requestUrl);
    }
}