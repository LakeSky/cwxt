package com.kzh.action;

import com.kzh.util.hibernate.BaseDao;
import com.kzh.util.hibernate.BaseService;
import com.kzh.util.struts.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;

public class SustainAction extends BaseAction {
    @Autowired
    private BaseService service;

    public String execute() {
        service.sustainDatabaseConnection();
        return null;
    }
}
