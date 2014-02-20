package com.kzh.generate.auto.service;

import com.kzh.generate.auto.QField;
import com.kzh.generate.auto.entity.FieldInfo;
import com.kzh.generate.common.DateTime;
import com.kzh.system.ApplicationConstant;
import com.kzh.util.hibernate.BaseDao;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
@Transactional
public class FieldService extends BaseDao {
    //扫描所有需要自动生成页面的entity默认为entity包下面的所有类
    public static Map<String, String> entityMap = null;

    public List obtainAllFields(Class clazz) {
        List list = new ArrayList();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            QField qField = field.getAnnotation(QField.class);
            if (qField != null) {
                FieldInfo fieldInfo = new FieldInfo();
                fieldInfo.setName(field.getName());
                fieldInfo.setZh_name(qField.name());
                fieldInfo.setType(qField.type());
                fieldInfo.setNullable(qField.nullable());
                fieldInfo.setActions(qField.actions());
                if (qField != null) {
                    if (qField.type().equals("textarea")) {
                        fieldInfo.setLength(qField.textareaLength());
                    }
                    if (qField.type().equals("date")) {
                        fieldInfo.setDateFormat(qField.dateFormat());
                    }
                    if (qField.type().equals("time")) {
                        fieldInfo.setTimeFormat(qField.timeFormat());
                    }
                    if (qField.type().equals("dict")) {
                        Map map = new LinkedHashMap();
                        map.put("", "");
                        if (qField.dictType().equals("static")) {
                            String[] strs = qField.dictValues();
                            for (int i = 0; i < strs.length; i += 2) {
                                map.put(strs[i], strs[i + 1]);
                            }
                        }
                        if (qField.dictType().equals("dynamic")) {
                            List dictList = getCurrentSession().createSQLQuery(qField.dictValues()[0]).list();
                            for (int i = 0; i < dictList.size(); i++) {
                                Object[] strs = (Object[]) dictList.get(i);
                                if (strs[0] == null || strs[1] == null) {
                                    continue;
                                }
                                map.put(strs[0].toString(), strs[1].toString());
                            }
                        }
                        fieldInfo.setMap(map);
                    }

                    list.add(fieldInfo);
                }
            }
        }
        return list;
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

    public Object obtainEntity(String actionType) throws Exception {
        if (entityMap == null) {
            entityMap = initEntityClass();
        }
        if (StringUtils.isNotBlank(actionType)) {
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

    public void del(String ids, Class clazz) {
        String[] strIds = ids.split(",");
        for (int i = 0; i < strIds.length; i++) {
            if (obtainIdType(clazz).equals(String.class)) {
                getCurrentSession().delete(getCurrentSession().get(clazz, strIds[i]));
            }
            if (obtainIdType(clazz).equals(int.class)) {
                getCurrentSession().delete(getCurrentSession().get(clazz, Integer.valueOf(strIds[i])));
            }
            if (obtainIdType(clazz).equals(long.class)) {
                getCurrentSession().delete(getCurrentSession().get(clazz, Long.valueOf(strIds[i])));
            }
        }
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

    public List queryByHql(Class clazz, Map map) throws Exception {
        Map copyMap = new HashMap();
        if (map == null) {
            map = new HashMap();
        }
        copyMap.putAll(map);
        String hql = "from " + StringUtils.capitalize(clazz.getSimpleName().toLowerCase()) + " t where 1=1";
        Table table = (Table) clazz.getAnnotation(Table.class);
        if (table != null && StringUtils.isNotBlank(table.name())) {
            hql = "from " + StringUtils.capitalize(clazz.getSimpleName().toLowerCase()) + " t where 1=1";
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
        //query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }
}
