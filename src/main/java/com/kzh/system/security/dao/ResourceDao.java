package com.kzh.system.security.dao;

import com.kzh.system.security.entity.Resource;
import com.kzh.system.security.entity.ResourceRole;
import com.kzh.system.security.entity.Role;
import com.kzh.system.security.entity.UserRole;
import com.kzh.util.hibernate.BaseDao;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * User: kzh
 * Date: 13-4-23
 * Time: 下午4:01
 */
@Service
@Transactional
public class ResourceDao extends BaseDao {
    public Map findAll() {
        String hql = "from Resource";
        Query query = getCurrentSession().createQuery(hql);
        Map resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
        List<Resource> resources = query.list();
        for (Resource resource : resources) {
            Set<Role> roles = resource.getRoles();
            Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
            for (Role role : roles) {
                //以权限名封装为Spring的security Object
                ConfigAttribute configAttribute = new SecurityConfig(role.getName());
                configAttributes.add(configAttribute);
            }
            resourceMap.put(resource.getUrl(), configAttributes);
        }
        /*Collection<ConfigAttribute> configAttributes1 = new ArrayList<ConfigAttribute>();
        ConfigAttribute configAttribute1 = new SecurityConfig("ROLE_FORSLASH");
        configAttributes1.add(configAttribute1);
        resourceMap.put("/", configAttributes1);*/
        return resourceMap;
    }

    //根据父节点的id查询该节点下的所有菜单
    public List queryMenuList(String parentId, UserDetails userDetails) {
        String sql = "select " +
                "    t3.menu_id," +
                "    t3.menu_name," +
                "    t3.url," +
                "    (select " +
                "            count(*)" +
                "        from" +
                "            resource t6" +
                "        where" +
                "            t6.parent_id = t3.menu_id) child_count" +
                " from" +
                "    user t1," +
                "    role t2," +
                "    resource t3," +
                "    user_role t4," +
                "    resource_role t5" +
                " where" +
                "    t1.id = t4.user_id" +
                "        and t2.id = t4.role_id" +
                "        and t3.id = t5.resource_id" +
                "        and t2.id = t5.role_id" +
                "        and t3.parent_id='" + parentId +
                "'       and t1.username='" + userDetails.getUsername() +
                "' order by t3.seq";
        List list = this.getCurrentSession().createSQLQuery(sql).list();
        List menuList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            Map map = new HashMap<String, String>();
            Object[] objs = (Object[]) list.get(i);
            map.put("id", objs[0]);
            map.put("text", objs[1]);
            if (Integer.valueOf(objs[3].toString()) != 0) {
                map.put("state", "closed");
            }
            Map mapUrl = new HashMap<String, String>();
            mapUrl.put("url", objs[2]);
            map.put("attributes", mapUrl);
            menuList.add(map);
        }
        return menuList;
    }

    public List queryAllMenu() {
        String hql = "from Menu ";
        Query query = this.getSessionFactory().getCurrentSession().createQuery(hql);
        return query.list();
    }

    public List queryMenuTree(String roleId) {
        String hqlResources = "select r.id from Resource r,ResourceRole t where r.id=t.resource_id and t.role_id=:roleId";
        Query query = getCurrentSession().createQuery(hqlResources);
        query.setString("roleId", roleId);
        List haveResources = query.list();
        return queryChildMenu(haveResources, "0");
    }

    public List queryChildMenu(List haveResources, String parentId) {
        List tree = new ArrayList();
        String hqlParent = "from Resource t where t.parent_id=:parentId";
        Query query = getCurrentSession().createQuery(hqlParent);
        query.setString("parentId", parentId);
        List<Resource> parents = query.list();
        Map map = null;
        for (int i = 0; i < parents.size(); i++) {
            Resource parent = parents.get(i);
            map = new HashMap();
            map.put("id", parent.getId());
            map.put("menu_id", parent.getMenu_id());
            map.put("text", parent.getMenu_name());
            map.put("state", "open");
            if (StringUtils.isBlank(parent.getUrl())) {
                List list = queryChildMenu(haveResources, parent.getMenu_id());
                map.put("children", list);
            } else {
                if (haveResources.contains(parent.getId())) {
                    map.put("checked", true);
                } else {
                    map.put("checked", false);
                }
            }
            tree.add(map);
        }
        return tree;
    }

    public void updateMenuTree(String roleId, String addIds, String delIds) {
        if (StringUtils.isNotBlank(addIds)) {
            String[] idsAdd = addIds.split(",");
            for (int i = 0; i < idsAdd.length; i++) {
                if (StringUtils.isNotBlank(idsAdd[i])) {
                    ResourceRole resourceRole = new ResourceRole();
                    resourceRole.setRole_id(roleId);
                    resourceRole.setResource_id(idsAdd[i]);
                    getCurrentSession().save(resourceRole);
                }
            }
        }
        if (StringUtils.isNotBlank(delIds)) {
            String[] idsDel = delIds.split(",");
            for (int i = 0; i < idsDel.length; i++) {
                if (StringUtils.isNotBlank(idsDel[i])) {
                    String hql = "delete from ResourceRole t where t.role_id=:roleId and t.resource_id=:resourceId";
                    Query query = getCurrentSession().createQuery(hql);
                    query.setString("roleId", roleId);
                    query.setString("resourceId", idsDel[i]);
                    query.executeUpdate();
                }
            }
        }
    }
}
