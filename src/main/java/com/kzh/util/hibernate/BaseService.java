package com.kzh.util.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BaseService<T> {
    @Autowired
    private BaseDao baseDao;

    public void save(T t) {
        baseDao.save(t);
    }

    //通过执行简单的sql语句来维持数据库的持久连接
    public void sustainDatabaseConnection() {
        String sql = "select * from Test";
        baseDao.getCurrentSession().createSQLQuery(sql).list();
    }

    //-------------get/set---------------------------
    public BaseDao getBaseDao() {
        return baseDao;
    }

    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }
}
