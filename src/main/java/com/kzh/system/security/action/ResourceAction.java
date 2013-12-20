package com.kzh.system.security.action;

import com.kzh.system.security.MySecurityMetadataSource;
import com.kzh.system.security.dao.ResourceDao;
import com.kzh.system.security.dao.RoleDao;
import com.kzh.system.security.dao.UserDao;
import com.kzh.system.security.entity.Resource;
import com.kzh.system.security.entity.Role;
import com.kzh.util.PrintWriter;
import com.kzh.util.struts.BaseAction;
import org.apache.struts2.convention.annotation.ResultPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

@ResultPath("/pages/system/security/")
public class ResourceAction extends BaseAction {
    @Autowired
    private ResourceDao service;
    @Autowired
    private UserDao userDao;
    private Resource menu;
    private Role role;
    private String id = "0";
    private List menuList;
    private List roleList = new ArrayList();
    private String roleId;
    //添加的菜单id
    private String addIds;
    //删除的菜单id
    private String delIds;

    //当权限关系发生改变的时候重新加载资源权限对应关系
    public String updateResourceMap() {
        MySecurityMetadataSource.resourceMap = service.findAll();
        return SUCCESS;
    }

    //-------get/set----------------------------------------
    public String queryAllMenu() {
        PrintWriter.printListWithJson(service.queryAllMenu());
        return SUCCESS;
    }

    public String save() {
        service.save(menu);
        PrintWriter.print("保存成功");
        return SUCCESS;
    }

    public String queryMenuList() {
        this.setMenuList(service.queryMenuList(id, this.getUserDetails()));
        PrintWriter.printListWithJson(menuList);
        return SUCCESS;
    }

    public String execute() {

        return SUCCESS;
    }

    //查询角色列表
    public String queryRoles() {
        UserDetails userDetails = getUserDetails();
        roleList = userDao.queryRoles(userDetails.getUsername());
        PrintWriter.printListWithJson(roleList);
        return SUCCESS;
    }

    public String queryMenuTree() {
        List list = service.queryMenuTree(role.getId());
        return PrintWriter.printListWithJson(list);
    }

    public String updateMenuTree() {
        service.updateMenuTree(role.getId(), addIds, delIds);
        return SUCCESS;
    }

    public String editMenuTree() {
        return "edit";
    }

    //--------------get/set----------------------------------------------------------

    public Resource getMenu() {
        return menu;
    }

    public void setMenu(Resource menu) {
        this.menu = menu;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List getMenuList() {
        return menuList;
    }

    public void setMenuList(List menuList) {
        this.menuList = menuList;
    }

    public ResourceDao getService() {
        return service;
    }

    public void setService(ResourceDao service) {
        this.service = service;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public List getRoleList() {
        return roleList;
    }

    public void setRoleList(List roleList) {
        this.roleList = roleList;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getAddIds() {
        return addIds;
    }

    public void setAddIds(String addIds) {
        this.addIds = addIds;
    }

    public String getDelIds() {
        return delIds;
    }

    public void setDelIds(String delIds) {
        this.delIds = delIds;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
