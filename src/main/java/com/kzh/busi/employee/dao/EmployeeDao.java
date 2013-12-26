package com.kzh.busi.employee.dao;

import com.kzh.busi.employee.entity.Employee;
import com.kzh.generate.auto.entity.FieldInfo;
import com.kzh.generate.common.DateTime;
import com.kzh.util.excel.Excel;
import com.kzh.util.hibernate.BaseDao;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Component;

import java.io.File;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

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

    public void importExcel(File file, List fields) throws Exception {
        List<String[]> list = Excel.obtainFirstSheetAllColumn(file, true);
        for (int j = 0; j < list.size(); j++) {
            String[] listStrs = list.get(j);
            Employee employee = new Employee();
            for (int i = 0; i < fields.size(); i++) {
                FieldInfo fieldInfo = (FieldInfo) fields.get(i);
                Object o = employee;
                String str = fieldInfo.getName();
                Class typeClass = o.getClass().getDeclaredField(str).getType();
                Method method = o.getClass().getMethod("set" + StringUtils.capitalize(str), o.getClass().getDeclaredField(str).getType());
                if (StringUtils.isNotBlank(listStrs[i])) {
                    if (typeClass.equals(int.class)) {
                        method.invoke(o, Integer.parseInt(listStrs[i]));
                    }
                    if (typeClass.equals(Date.class)) {
                        DateTime dateTime = o.getClass().getDeclaredField(str).getAnnotation(DateTime.class);
                        SimpleDateFormat sim;
                        if (dateTime != null) {
                            sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        } else {
                            sim = new SimpleDateFormat("yyyy-MM-dd");
                        }
                        method.invoke(o, sim.parse(listStrs[i]));
                    }
                    if (typeClass.equals(boolean.class)) {
                        if (StringUtils.isNotBlank(listStrs[i]) && !listStrs[i].trim().equals("false")) {
                            method.invoke(o, true);
                        } else {
                            method.invoke(o, false);
                        }
                    }
                    if (typeClass.equals(String.class)) {
                        method.invoke(o, listStrs[i]);
                    }
                }
            }
            getCurrentSession().save(employee);
        }
    }

    public Object initEntityFromMap(Object o, Map map) throws Exception {
        Set keyset = map.keySet();
        Iterator it = keyset.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            Class typeClass = o.getClass().getDeclaredField(str).getType();
            Method method = o.getClass().getMethod("set" + StringUtils.capitalize(str), o.getClass().getDeclaredField(str).getType());
            String[] strs = (String[]) map.get(str);
            if (StringUtils.isNotBlank(strs[0])) {
                if (typeClass.equals(int.class)) {
                    method.invoke(o, Integer.parseInt(strs[0]));
                }
                if (typeClass.equals(Date.class)) {
                    DateTime dateTime = o.getClass().getDeclaredField(str).getAnnotation(DateTime.class);
                    SimpleDateFormat sim;
                    if (dateTime != null) {
                        sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    } else {
                        sim = new SimpleDateFormat("yyyy-MM-dd");
                    }
                    method.invoke(o, sim.parse(strs[0]));
                }
                if (typeClass.equals(boolean.class)) {
                    if (StringUtils.isNotBlank(strs[0]) && !strs[0].trim().equals("false")) {
                        method.invoke(o, true);
                    } else {
                        method.invoke(o, false);
                    }
                }
                if (typeClass.equals(String.class)) {
                    method.invoke(o, strs[0]);
                }
            }
        }

        return o;
    }
}
