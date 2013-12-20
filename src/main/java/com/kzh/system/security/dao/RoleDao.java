package com.kzh.system.security.dao;

import com.kzh.system.security.entity.Role;
import com.kzh.util.hibernate.BaseDao;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Service;

import javax.persistence.Table;
import java.util.List;

@Service
public class RoleDao extends BaseDao {

    public void del(String ids) {
        String[] strIds = ids.split(",");
        for (int i = 0; i < strIds.length; i++) {
            /*String hql = "delete from Role where id = ?";
            Query query = getCurrentSession().createQuery(hql);
            query.setString(0, strIds[i]);
            query.executeUpdate();*/
            Role role = (Role) getCurrentSession().get(Role.class, strIds[i]);
            getCurrentSession().delete(role);
        }
    }

    public List queryBySql() {
        String sql = "select * from role";
        SQLQuery sqlQuery = getCurrentSession().createSQLQuery(sql);
        sqlQuery.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
        return sqlQuery.list();
    }

}
