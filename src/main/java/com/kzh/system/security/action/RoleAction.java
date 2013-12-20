package com.kzh.system.security.action;

import com.kzh.system.security.dao.RoleDao;
import com.kzh.system.security.entity.Role;
import com.kzh.util.PrintWriter;
import com.kzh.util.struts.BaseAction;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ResultPath;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@ResultPath("/pages/system/security/")
public class RoleAction extends BaseAction {
    @Autowired
    private RoleDao roleDao;
    private List roleList = new ArrayList();
    private String roleId;
    private String ids;
    private Role entity;

    //查询角色列表
    public String queryRoles() {
        roleList = roleDao.queryBySql();

        PrintWriter.printListWithJson(roleList);
        return SUCCESS;
    }

    public String del() {
        roleDao.del(ids.trim());
        return SUCCESS;
    }

    public String save() {
        if (entity.getId().equals("")) {
            entity.setId(null);
        }
        roleDao.save(entity);
        return SUCCESS;
    }

    public String edit() {
        if (entity != null && StringUtils.isNotBlank(entity.getId())) {
            entity = (Role) roleDao.getEntity(Role.class, entity.getId());
        }
        return "edit";
    }


    //---------get/set---------------------------

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public RoleDao getRoleDao() {
        return roleDao;
    }

    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public List getRoleList() {
        return roleList;
    }

    public void setRoleList(List roleList) {
        this.roleList = roleList;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public Role getEntity() {
        return entity;
    }

    public void setEntity(Role entity) {
        this.entity = entity;
    }
}
