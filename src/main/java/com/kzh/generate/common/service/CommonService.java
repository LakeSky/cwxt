package com.kzh.generate.common.service;

import com.kzh.generate.common.DateTime;
import com.kzh.generate.common.Dict;
import com.kzh.generate.common.Name;
import com.kzh.generate.common.entity.Common;
import com.kzh.system.ApplicationConstant;
import com.kzh.util.hibernate.BaseDao;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * User: kzh
 * Date: 13-10-9
 * Time: 下午5:04
 */
@Component
@Transactional
public class CommonService extends BaseDao {
    //扫描所有需要自动生成页面的entity默认为entity包下面的所有类
    public static Map<String, String> entityMap = null;

    public void save(Object o) {
        getCurrentSession().saveOrUpdate(o);
    }

    public void delete(Object o) {
        getCurrentSession().delete(o);
    }

    public List query(Object o) {
        Class clazz = o.getClass();
        Criteria criteria = getCurrentSession().createCriteria(clazz);
        /*criteria.add(Restrictions.eq("lotteryActivity.id", lotteryActivity.getId()));
        criteria.add(Restrictions.eq("org_id", orgId));
        criteria.addOrder(Order.asc("awards_rank"));*/
        return criteria.list();

    }

    public void del(String ids, Class clazz) {
        String id = obtainIdField(clazz);
        Class typeClass = obtainIdType(clazz);
        String[] strIds = ids.split(",");
        /*for (int i = 0; i < strIds.length; i++) {
            String sql = "delete from " + clazz.getSimpleName() + " where " + id + " = ?";
            Table table = (Table) clazz.getAnnotation(Table.class);
            if (table != null && StringUtils.isNotBlank(table.name())) {
                sql = "delete from " + table.name() + " where " + id + " = ?";;
            }
            SQLQuery sqlQuery = getCurrentSession().createSQLQuery(sql);
            if (typeClass.equals(int.class)) {
                sqlQuery.setInteger(0, Integer.parseInt(strIds[i]));
            } else {
                sqlQuery.setString(0, strIds[i]);
            }
            sqlQuery.executeUpdate();
        }*/
        for (int i = 0; i < strIds.length; i++) {
            getCurrentSession().delete(getCurrentSession().get(clazz, strIds[i]));
        }
    }

    public List query(Class clazz, Map map) throws Exception {
        Map copyMap = new HashMap();
        if (map == null) {
            map = new HashMap();
        }
        copyMap.putAll(map);
        String hql = "from " + clazz.getSimpleName() + " t where 1=1";
        Set keyset = map.keySet();
        Iterator it = keyset.iterator();

        while (it.hasNext()) {
            String str = (String) it.next();
            String[] strs = (String[]) map.get(str);
            Class typeClass;
            if (str.startsWith("HHHHHHstart_") || str.startsWith("HHHHHHend_")) {
                typeClass = clazz.getDeclaredField(str.substring(str.indexOf("_") + 1)).getType();
                if (StringUtils.isNotBlank(strs[0])) {
                    copyMap.remove(str);
//                    SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
                    DateTime dateTime = clazz.getDeclaredField(str.substring(str.indexOf("_") + 1)).getAnnotation(DateTime.class);
                    SimpleDateFormat sim;
                    if (dateTime != null) {
                        sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    } else {
                        sim = new SimpleDateFormat("yyyy-MM-dd");
                    }
                    copyMap.put(str, sim.parse(strs[0]));
                }
            } else {
                typeClass = clazz.getDeclaredField(str).getType();
            }
            if (StringUtils.isNotBlank(strs[0])) {
                if (typeClass.equals(int.class)) {
                    hql += " and t." + str + "=:" + str;
                }
                if (typeClass.equals(Date.class)) {
                    if (str.startsWith("HHHHHHstart_")) {
                        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
                        hql += " and t." + str.substring(str.indexOf("_") + 1) + " >=:" + str;
                    } else {
                        hql += " and t." + str.substring(str.indexOf("_") + 1) + " <=:" + str;
                    }
                }
                if (typeClass.equals(String.class)) {
                    hql += " and t." + str + "=:" + str;
                }
            }
        }
        Query query = getCurrentSession().createQuery(hql);
        query.setProperties(copyMap);
        return query.list();
    }

    public List queryBySql(Class clazz, Map map) throws Exception {
        Map copyMap = new HashMap();
        if (map == null) {
            map = new HashMap();
        }
        copyMap.putAll(map);
        String hql = "select * from " + clazz.getSimpleName() + " t where 1=1";
        Table table = (Table) clazz.getAnnotation(Table.class);
        if (table != null && StringUtils.isNotBlank(table.name())) {
            hql = "select * from " + table.name() + " t where 1=1";
        }

        Set keyset = map.keySet();
        Iterator it = keyset.iterator();

        while (it.hasNext()) {
            String str = (String) it.next();
            String[] strs = (String[]) map.get(str);
            Class typeClass;
            if (str.startsWith("HHHHHHstart_") || str.startsWith("HHHHHHend_")) {
                typeClass = clazz.getDeclaredField(str.substring(str.indexOf("_") + 1)).getType();
                if (StringUtils.isNotBlank(strs[0])) {
                    copyMap.remove(str);
//                    SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
                    DateTime dateTime = clazz.getDeclaredField(str.substring(str.indexOf("_") + 1)).getAnnotation(DateTime.class);
                    SimpleDateFormat sim;
                    if (dateTime != null) {
                        sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    } else {
                        sim = new SimpleDateFormat("yyyy-MM-dd");
                    }
                    copyMap.put(str, sim.parse(strs[0]));
                }
            } else {
                typeClass = clazz.getDeclaredField(str).getType();
            }
            if (StringUtils.isNotBlank(strs[0])) {
                if (typeClass.equals(int.class)) {
                    hql += " and t." + str + "=:" + str;
                }
                if (typeClass.equals(Date.class)) {
                    if (str.startsWith("HHHHHHstart_")) {
                        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
                        hql += " and t." + str.substring(str.indexOf("_") + 1) + " >=:" + str;
                    } else {
                        hql += " and t." + str.substring(str.indexOf("_") + 1) + " <=:" + str;
                    }
                }
                if (typeClass.equals(String.class)) {
                    hql += " and t." + str + "=:" + str;
                }
            }
        }
        SQLQuery query = getCurrentSession().createSQLQuery(hql);
        query.setProperties(copyMap);
        query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }

    //目前只包含了name和type其他属性有待扩展
    public List obtainFieldInfo(Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        List list = new ArrayList();
        for (Field field : fields) {
            com.kzh.generate.common.entity.Field fieldInfo = new com.kzh.generate.common.entity.Field();
            fieldInfo.setName(field.getName());
            fieldInfo.setType(field.getType().getName());
            Annotation[] annotations = field.getAnnotations();
            for (int i = 0; i < annotations.length; i++) {
                if (annotations[i].annotationType().getName().toString().equals(Name.class.getName())) {
                    Name name = (Name) annotations[i];
                    fieldInfo.setZh_name(name.value());
                }
            }
            list.add(fieldInfo);
        }
        return list;
    }

    //用逗号分割的fieldname
    public String obtainFiledNames(Class clazz) {
        String str = "";
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            str += field.getName() + ",";
        }
        return str.substring(0, str.length() - 1);
    }

    public Object obtainEntity(String actionType) throws Exception {
        if (entityMap == null) {
            entityMap = initEntityClass();
        }
        if (StringUtils.isNotBlank(actionType)) {
//            String className = ApplicationConstant.generate_entity_pakage_name + actionType;
            String className = entityMap.get(actionType.trim());
            try {
                Class clazz = Class.forName(className);
                return clazz.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
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

    public String obtainFieldsStrByAnnotation(Class clazz, Class annotationClass) {
        Field[] fields = clazz.getDeclaredFields();
        String str = "";
        for (Field field : fields) {
            com.kzh.generate.common.entity.Field fieldInfo = new com.kzh.generate.common.entity.Field();
            fieldInfo.setName(field.getName());
            String typeName = field.getType().getName();
            int index = typeName.lastIndexOf(".");
            if (index != -1) {
                typeName = typeName.substring(index + 1);
            }
            fieldInfo.setType(typeName);
            Annotation[] annotations = field.getAnnotations();
            for (int i = 0; i < annotations.length; i++) {
                if (annotations[i].annotationType().equals(annotationClass)) {
                    str += field.getName() + ",";
                }
            }
        }
        return str.substring(0, str.length() - 1);
    }

    public List obtainFieldsByAnnotation(Class clazz, Class annotationClass) {
        List list = new ArrayList();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Annotation annotation = field.getAnnotation(annotationClass);
            if (annotation != null) {
                com.kzh.generate.common.entity.Field fieldInfo = new com.kzh.generate.common.entity.Field();
                fieldInfo.setName(field.getName());
                String typeName = field.getType().getName();
                int index = typeName.lastIndexOf(".");
                if (index != -1) {
                    typeName = typeName.substring(index + 1);
                }
                //长度，如果过长，则改变typename类型，
                //该类型将影响页面展示为相应的textarea
                Column column = field.getAnnotation(Column.class);
                if (column != null) {
                    if (column.length() >= 500) {
                        fieldInfo.setLength(column.length());
                    }
                    fieldInfo.setNotNull(!column.nullable());
                }
                Dict dict = field.getAnnotation(Dict.class);
                if (dict != null) {
                    Map map = new LinkedHashMap();
                    map.put("", "");
                    if (dict.type().equals("static")) {
                        String[] strs = dict.values();
                        for (int i = 0; i < strs.length; i += 2) {
                            map.put(strs[i], strs[i + 1]);
                        }
                    }
                    if (dict.type().equals("dynamic")) {
                        List dictList = getCurrentSession().createSQLQuery(dict.values()[0]).list();
                        for (int i = 0; i < dictList.size(); i++) {
                            Object[] strs = (Object[]) dictList.get(i);
                            map.put(strs[0].toString(), strs[1].toString());
                        }
                    }
                    fieldInfo.setMap(map);
                }
                DateTime dateTime = field.getAnnotation(DateTime.class);
                if (dateTime != null) {
                    typeName = "DateTime";
                }
                fieldInfo.setType(typeName);
                fieldInfo.setZh_name(field.getAnnotation(Name.class).value());
                list.add(fieldInfo);
            }
        }
        return list;
    }

    public String obtainIdField(Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Annotation annotation = field.getAnnotation(Id.class);
            if (annotation != null) {
                return field.getName();
            }
        }
        return null;
    }

    public Class obtainIdType(Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Annotation annotation = field.getAnnotation(Id.class);
            if (annotation != null) {
                return field.getType();
            }
        }
        return null;
    }

    public Class obtainClass(String actionType) {
        if (entityMap == null) {
            entityMap = initEntityClass();
        }
        if (StringUtils.isNotBlank(actionType)) {
            String className = entityMap.get(actionType.trim());
            try {
                return Class.forName(className);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private Map initEntityClass() {
        Map entityClass = new HashMap<String, String>();
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        String classpath = this.getClass().getResource("/").toString();
        try {
            Resource[] resources = resourcePatternResolver.getResources(ApplicationConstant.entity_pattern);
            for (int i = 0; i < resources.length; i++) {
                String source = resources[i].getURL().toString();
                String str = source.substring(classpath.length(), source.length() - 6).replace("/", ".");
                String className = str.substring(str.lastIndexOf(".") + 1);
                entityClass.put(className, str);
            }
        } catch (Exception e) {
            return null;
        }

        return entityClass;
    }
}
