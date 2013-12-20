package com.kzh.generate.common.action;

import com.kzh.generate.common.Dict;
import com.kzh.generate.common.Edit;
import com.kzh.generate.common.Query;
import com.kzh.generate.common.Show;
import com.kzh.generate.common.service.CommonService;
import com.kzh.system.ApplicationConstant;
import com.kzh.util.PrintWriter;
import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.ResultPath;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * User: kzh
 * Date: 13-10-9
 * Time: 下午5:03
 */
@ResultPath("/pages/generate/common")
public class CommonAction extends ActionSupport {
    @Autowired
    private CommonService service;
    private String actionType;
    private String fieldNames;
    //可以展示的字段
    private List showFields;
    //可以编辑的字段
    private List editFields;
    //可以查询的字段
    private List queryFields;
    //字典字段
    private List dictFields;
    //逗号分割形式的编辑字段
    //private String strEditFields;
    //json形式的编辑字段
    private String jsonEditFields;
    //json形式的查询字段
    private String jsonQueryFields;
    //json形式的dict字段
    private String jsonDictFields;
    //id字段
    private String idField;
    //删除的id
    private String ids;
    private Map entityMap;

    @Action(value = "save", interceptorRefs = {@InterceptorRef("token"), @InterceptorRef("tokenSession")})
    public String save() throws Exception {
        Object o = service.obtainEntity(actionType.trim());
        o = service.initEntityFromMap(o, entityMap);
        service.save(o);
        return SUCCESS;
    }

    @Action(value = "update", interceptorRefs = {@InterceptorRef("token"), @InterceptorRef("tokenSession")})
    public String update() throws Exception {
        //Object o = service.obtainEntity(actionType.trim());
        Class clazz = service.obtainClass(actionType.trim());
        String str = ((Object[]) entityMap.get("id"))[0].toString();
        Object o = null;
        Class typeClass=service.obtainIdType(clazz);
        if (typeClass.equals(int.class)) {
            o = service.getEntity(clazz, Integer.valueOf(str));
        } else {
            o = service.getEntity(clazz, str);
        }
        o = service.initEntityFromMap(o, entityMap);
        service.update(o);
        return SUCCESS;
    }

    public String query() throws Exception {
        Class clazz = service.obtainClass(actionType);
        JSONArray jsonArray = JSONArray.fromObject(service.queryBySql(clazz, entityMap));
        PrintWriter.print(jsonArray.toString());
        return null;
    }

    public String execute() {
        Class clazz = service.obtainClass(actionType);
        fieldNames = service.obtainFiledNames(clazz);
        showFields = service.obtainFieldsByAnnotation(clazz, Show.class);
        editFields = service.obtainFieldsByAnnotation(clazz, Edit.class);
        queryFields = service.obtainFieldsByAnnotation(clazz, Query.class);
        dictFields = service.obtainFieldsByAnnotation(clazz, Dict.class);
//        strEditFields = service.listToStr(editFields);
        jsonEditFields = JSONArray.fromObject(editFields).toString();
        jsonQueryFields = JSONArray.fromObject(queryFields).toString();
        jsonDictFields = JSONArray.fromObject(dictFields).toString();
        idField = service.obtainIdField(clazz);
        return SUCCESS;
    }

    public String del() {
        Class clazz = service.obtainClass(actionType);
        service.del(ids, clazz);
        return SUCCESS;
    }

    //---------get/set--------------------

    public CommonService getService() {
        return service;
    }

    public void setService(CommonService service) {
        this.service = service;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getFieldNames() {
        return fieldNames;
    }

    public void setFieldNames(String fieldNames) {
        this.fieldNames = fieldNames;
    }

    public Map getEntityMap() {
        return entityMap;
    }

    public void setEntityMap(Map entityMap) {
        this.entityMap = entityMap;
    }

    public List getShowFields() {
        return showFields;
    }

    public void setShowFields(List showFields) {
        this.showFields = showFields;
    }

    public List getEditFields() {
        return editFields;
    }

    public void setEditFields(List editFields) {
        this.editFields = editFields;
    }

    public String getIdField() {
        return idField;
    }

    public void setIdField(String idField) {
        this.idField = idField;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getJsonEditFields() {
        return jsonEditFields;
    }

    public void setJsonEditFields(String jsonEditFields) {
        this.jsonEditFields = jsonEditFields;
    }

    public List getQueryFields() {
        return queryFields;
    }

    public void setQueryFields(List queryFields) {
        this.queryFields = queryFields;
    }

    public String getJsonQueryFields() {
        return jsonQueryFields;
    }

    public void setJsonQueryFields(String jsonQueryFields) {
        this.jsonQueryFields = jsonQueryFields;
    }

    public String getJsonDictFields() {
        return jsonDictFields;
    }

    public void setJsonDictFields(String jsonDictFields) {
        this.jsonDictFields = jsonDictFields;
    }

    public List getDictFields() {
        return dictFields;
    }

    public void setDictFields(List dictFields) {
        this.dictFields = dictFields;
    }
}
