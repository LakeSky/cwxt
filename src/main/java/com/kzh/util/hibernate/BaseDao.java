package com.kzh.util.hibernate;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Service
@Transactional
public class BaseDao<T> {
    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    protected void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getCurrentSession() {
        return this.getSessionFactory().getCurrentSession();
    }

    public void save(T t) {
        this.sessionFactory.getCurrentSession().saveOrUpdate(t);
    }

    public void update(T t) {
        this.getSessionFactory().getCurrentSession().update(t);
    }

    public T getEntity(Class clazz, Serializable serializable) {
        return (T) getCurrentSession().get(clazz, serializable);
    }

    public List query(Class clazz) {
        Criteria criteria = getCurrentSession().createCriteria(clazz);
        return criteria.list();
    }

    public void delete(Class clazz, Serializable serializable) {
        getCurrentSession().delete(getCurrentSession().get(clazz, serializable));
    }
}
