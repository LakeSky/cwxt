package com.kzh.system.security.dao;

import com.kzh.system.security.entity.Role;
import com.kzh.util.hibernate.BaseDao;
import com.kzh.system.security.entity.User;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * User: kzh
 * Date: 13-4-23
 * Time: 下午4:03
 */
@Service
@Transactional
public class UserDao extends BaseDao {

    public User findByName(String userName) {
        String hql = "from User t where t.username=:userName";
        Query query = this.getSessionFactory().getCurrentSession().createQuery(hql);
        query.setString("userName", userName);
        List list = query.list();
        return list.size() > 0 ? (User) list.get(0) : null;
    }

    public List queryRoles(String userName) {
        User user = findByName(userName);
        String hql = "select t1.id,t1.name,t1.zh_name from Role t1,User_Role t2 where t1.id=t2.role_id and t2.user_id=:userId";
//        Query query = this.getSessionFactory().getCurrentSession().createQuery(hql);
        SQLQuery query = this.getSessionFactory().getCurrentSession().createSQLQuery(hql);
        query.setString("userId", user.getId());
        query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }

}
