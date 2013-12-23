package com.kzh.generate.auto.action;

import com.kzh.generate.auto.service.FieldService;
import com.kzh.util.PrintWriter;
import com.kzh.util.struts.BaseAction;
import net.sf.json.JSONArray;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.ResultPath;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@ResultPath("/pages/generate/auto")
public class AutoAction extends BaseAction {
    @Autowired
    private FieldService fieldService;

    private List listAllFields;
    private String jsonAllFields;

    private String o;
    private Map entityMap;
    private String delIds;
    private String idField;

    @Action(value = "save", interceptorRefs = {@InterceptorRef("token"), @InterceptorRef("tokenSession")})
    public String save() throws Exception {
        Object obj = fieldService.obtainEntity(o.trim());
        obj = fieldService.initEntityFromMap(obj, entityMap);
        fieldService.save(obj);
        return SUCCESS;
    }

    @Action(value = "update", interceptorRefs = {@InterceptorRef("token"), @InterceptorRef("tokenSession")})
    public String update() throws Exception {
        Class clazz = fieldService.obtainClass(o.trim());
        String str = ((Object[]) entityMap.get("id"))[0].toString();
        Object o = null;
        Class typeClass = fieldService.obtainIdType(clazz);
        if (typeClass.equals(int.class)) {
            o = fieldService.getEntity(clazz, Integer.valueOf(str));
        } else {
            o = fieldService.getEntity(clazz, str);
        }
        o = fieldService.initEntityFromMap(o, entityMap);
        fieldService.update(o);
        return SUCCESS;
    }

    public String query() throws Exception {
        Class clazz = fieldService.obtainClass(o);
        JSONArray jsonArray = JSONArray.fromObject(fieldService.queryBySql(clazz, entityMap));
        PrintWriter.print(jsonArray.toString());
        return null;
    }

    public String execute() {
        Class clazz = fieldService.obtainClass(o);
        /*fieldNames = fieldService.obtainFiledNames(clazz);
        allFields = fieldService.obtainAllFields(clazz);
        showFields = fieldService.obtainFieldsByAnnotation(clazz, Show.class);
        editFields = fieldService.obtainFieldsByAnnotation(clazz, Edit.class);
        queryFields = fieldService.obtainFieldsByAnnotation(clazz, Query.class);
        dictFields = fieldService.obtainFieldsByAnnotation(clazz, Dict.class);
        jsonEditFields = JSONArray.fromObject(editFields).toString();
        jsonQueryFields = JSONArray.fromObject(queryFields).toString();
        jsonDictFields = JSONArray.fromObject(dictFields).toString();*/
        listAllFields = fieldService.obtainAllFields(clazz);
        jsonAllFields = JSONArray.fromObject(listAllFields).toString();
        idField = fieldService.obtainIdField(clazz);
        return SUCCESS;
    }

    public String del() {
        Class clazz = fieldService.obtainClass(o);
        fieldService.del(delIds, clazz);
        return SUCCESS;
    }

    //-----get/set----------------------

    public FieldService getFieldService() {
        return fieldService;
    }

    public void setFieldService(FieldService fieldService) {
        this.fieldService = fieldService;
    }

    public List getListAllFields() {
        return listAllFields;
    }

    public void setListAllFields(List listAllFields) {
        this.listAllFields = listAllFields;
    }

    public String getJsonAllFields() {
        return jsonAllFields;
    }

    public void setJsonAllFields(String jsonAllFields) {
        this.jsonAllFields = jsonAllFields;
    }

    public String getO() {
        return o;
    }

    public void setO(String o) {
        this.o = o;
    }

    public Map getEntityMap() {
        return entityMap;
    }

    public void setEntityMap(Map entityMap) {
        this.entityMap = entityMap;
    }

    public String getDelIds() {
        return delIds;
    }

    public void setDelIds(String delIds) {
        this.delIds = delIds;
    }

    public String getIdField() {
        return idField;
    }

    public void setIdField(String idField) {
        this.idField = idField;
    }
}
