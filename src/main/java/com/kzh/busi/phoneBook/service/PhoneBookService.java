package com.kzh.busi.phoneBook.service;

import com.kzh.util.hibernate.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kzh
 * Date: 13-4-13
 * Time: 下午2:40
 */
@Service
@Transactional
public class PhoneBookService extends BaseDao {
    public List queryPhoneBookList() {
        String hql = "from PhoneBook";
        return this.getSessionFactory().getCurrentSession().createQuery(hql).list();
    }

}
