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

    //-------------get/set---------------------------
    public BaseDao getBaseDao() {
        return baseDao;
    }

    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }
}
