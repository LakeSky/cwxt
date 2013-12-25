package com.kzh.busi.employee.dao;

import com.kzh.util.excel.Excel;
import com.kzh.util.hibernate.BaseDao;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public class EmployeeDao extends BaseDao {

    public List multQuery(String multnames) throws Exception {
        String hql = "from Employee t where t.name in(:nameList)";
        if (StringUtils.isBlank(multnames)) {
            hql = "from Employee";
            return getCurrentSession().createQuery(hql).list();
        }
        String[] names = multnames.split(",");
        Query query = getCurrentSession().createQuery(hql);
        query.setParameterList("nameList", names);
        return query.list();
    }
}
