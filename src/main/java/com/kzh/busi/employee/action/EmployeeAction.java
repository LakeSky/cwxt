package com.kzh.busi.employee.action;

import com.kzh.busi.employee.dao.EmployeeDao;
import com.kzh.busi.employee.entity.Employee;
import com.kzh.generate.auto.service.FieldService;
import com.kzh.util.PrintWriter;
import com.kzh.util.excel.Excel;
import com.kzh.util.struts.BaseAction;
import com.opensymphony.xwork2.ActionContext;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.ResultPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ResultPath("/pages/busi/employee")
@Component
public class EmployeeAction extends BaseAction {
    @Autowired
    private FieldService fieldService;
    @Autowired
    private EmployeeDao dao;

    private List listAllFields;
    private String jsonAllFields;

    private String o = "Employee";
    private Map entityMap;
    private Map exportEntityMap;
    private String delIds;
    private String idField;
    private String multiNames = "";
    private File queryExcel;
    private File importExcel;
    private File zhuanCunExcel;

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
        JsonConfig cfg = new JsonConfig();
        cfg.setExcludes(new String[]{"handler", "hibernateLazyInitializer"});
        JSONArray jsonArray = JSONArray.fromObject(dao.queryByHql(clazz, entityMap), cfg);
        PrintWriter.print(jsonArray.toString());
        return null;
    }

    public String execute() {
        Class clazz = fieldService.obtainClass(o);
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

    //转存用到的多个名字查询和导入excel查询方式
    public String zhuanCun() {
        Class clazz = fieldService.obtainClass(o);
        listAllFields = fieldService.obtainAllFields(clazz);
        jsonAllFields = JSONArray.fromObject(listAllFields).toString();
        idField = fieldService.obtainIdField(clazz);
        return "zhuan";
    }

    //转存导出
    public String zhuanCunExport() {
        return "zhuanExport";
    }

    public String initMultiNames() throws Exception {
        Class clazz = fieldService.obtainClass(o);
        listAllFields = fieldService.obtainAllFields(clazz);
        jsonAllFields = JSONArray.fromObject(listAllFields).toString();
        idField = fieldService.obtainIdField(clazz);

        if (queryExcel != null) {
            List list = Excel.obtainFirstSheetFirstColumn(queryExcel);
            for (Object str : list) {
                if (str != null && StringUtils.isNotBlank(str.toString())) {
                    multiNames += str.toString() + ",";
                }
            }
        }
        if (StringUtils.isNotBlank(multiNames)) {
            multiNames = multiNames.substring(0, multiNames.length() - 1);
        }
        return "zhuan";
    }

    public String multQuery() throws Exception {
        JSONArray jsonArray = JSONArray.fromObject(dao.multQuery(multiNames));
        PrintWriter.print(jsonArray.toString());
        return null;
    }

    public String exportExcel() throws Exception {
        //String[] head = {"姓名", "联系方式", "性别", "身份证号", "银行卡号", "银行卡号", "职称"};
        String[] head = {"姓名", "身份证号", "银行卡号"};
        Class clazz = fieldService.obtainClass(o);
        List exchangedList = dao.queryByHqlForExport(clazz, exportEntityMap);
        List<String[]> contents = new ArrayList<String[]>();
        contents.add(head);
        String[] content;
        for (int i = 0; i < exchangedList.size(); i++) {
            Employee exchanged = (Employee) exchangedList.get(i);
            content = new String[head.length];
            content[0] = exchanged.getName();
            content[1] = exchanged.getIdentity_card_id();
            content[2] = exchanged.getBank_card_id();
            contents.add(content);
        }
        String fileName = Excel.simpleExportExcel(contents);
        getResponse().sendRedirect("download.do?fileName=" + fileName);
        return null;
    }

    //导出转存的数据
    public String exportZhunCunData() throws Exception {
        try {
            List misMatchingNames = dao.checkZhuanCunName(zhuanCunExcel);
            if (misMatchingNames.size() > 0) {
                PrintWriter.printListWithJsonAppendHtml(misMatchingNames,
                        "<br/><div style='color:red;margin-top:10px;'>提示：上面的名字有错误请更正后重新上传</div>");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            PrintWriter.print("<div style='color:red;'>程序卖萌了，快联系木头吧</div>");
            return null;
        }
        String fileName = Excel.simpleExportExcel(dao.exportZhunCunData(zhuanCunExcel));
        getResponse().sendRedirect("download.do?fileName=" + fileName);
        return SUCCESS;
    }


    public String importEmployee() {
        listAllFields = fieldService.obtainAllFields(Employee.class);
        return "import";
    }

    public String importExcel() throws Exception {
        dao.importExcel(importExcel, fieldService.obtainAllFields(Employee.class));
        listAllFields = fieldService.obtainAllFields(Employee.class);
        return "import";
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

    public EmployeeDao getDao() {
        return dao;
    }

    public void setDao(EmployeeDao dao) {
        this.dao = dao;
    }

    public String getMultiNames() {
        return multiNames;
    }

    public void setMultiNames(String multiNames) {
        this.multiNames = multiNames;
    }

    public File getQueryExcel() {
        return queryExcel;
    }

    public void setQueryExcel(File queryExcel) {
        this.queryExcel = queryExcel;
    }

    public File getImportExcel() {
        return importExcel;
    }

    public void setImportExcel(File importExcel) {
        this.importExcel = importExcel;
    }

    public File getZhuanCunExcel() {
        return zhuanCunExcel;
    }

    public void setZhuanCunExcel(File zhuanCunExcel) {
        this.zhuanCunExcel = zhuanCunExcel;
    }

    public Map getExportEntityMap() {
        return exportEntityMap;
    }

    public void setExportEntityMap(String exportEntityMap) {
        JSONObject jsonObject = JSONObject.fromObject(exportEntityMap);
//        jsonObject.putAll(this.exportEntityMap);
        this.exportEntityMap = jsonObject;
    }
}
