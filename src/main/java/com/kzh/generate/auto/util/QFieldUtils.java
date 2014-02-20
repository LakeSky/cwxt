package com.kzh.generate.auto.util;

import com.kzh.generate.auto.QField;
import com.kzh.generate.auto.entity.FieldInfo;
import com.kzh.generate.common.DateTime;
import com.kzh.system.ApplicationConstant;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.persistence.Id;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

public class QFieldUtils {
    //扫描所有需要自动生成页面的entity默认为entity包下面的所有类
    public static Map<String, String> entityMap = null;

    //取得所有标注有QField标注的字段并获得响应的字段信息
    public static List obtainAllFields(Class clazz) {
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
                    /*if (qField.dictType().equals("dynamic")) {
                        List dictList = getCurrentSession().createSQLQuery(qField.dictValues()[0]).list();
                        for (int i = 0; i < dictList.size(); i++) {
                            Object[] strs = (Object[]) dictList.get(i);
                            if (strs[0] == null || strs[1] == null) {
                                continue;
                            }
                            map.put(strs[0].toString(), strs[1].toString());
                        }
                    }*/
                    fieldInfo.setMap(map);
                }

                list.add(fieldInfo);
            }
        }
        return list;
    }

    private static Map initEntityClass() {
        Map entityClass = new HashMap<String, String>();
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        String classpath = QFieldUtils.class.getResource("/").toString();
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

    public static Class obtainClass(String actionType) {
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

    public static Object obtainEntity(String actionType) throws Exception {
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

    public static Object initEntityFromMap(Object o, Map map) throws Exception {
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

    public static Class obtainIdType(Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Annotation annotation = field.getAnnotation(Id.class);
            if (annotation != null) {
                return field.getType();
            }
        }
        return null;
    }

    public static String obtainIdField(Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Annotation annotation = field.getAnnotation(Id.class);
            if (annotation != null) {
                return field.getName();
            }
        }
        return null;
    }
}
