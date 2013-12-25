package com.kzh.busi.employee.action;

import com.kzh.busi.employee.dao.EmployeeDao;
import com.kzh.busi.employee.entity.Employee;
import com.kzh.generate.auto.service.FieldService;
import com.kzh.util.PrintWriter;
import com.kzh.util.excel.Excel;
import com.kzh.util.struts.BaseAction;
import com.opensymphony.xwork2.ActionContext;
import net.sf.json.JSONArray;
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
    private String delIds;
    private String idField;
    private String multiNames = "";
    private File queryExcel;

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

    public String initMultiNames() throws Exception {
        Class clazz = fieldService.obtainClass(o);
        listAllFields = fieldService.obtainAllFields(clazz);
        jsonAllFields = JSONArray.fromObject(listAllFields).toString();
        idField = fieldService.obtainIdField(clazz);

        if (queryExcel != null) {
            List list = Excel.obtainFirstSheetAndCell(queryExcel);
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
        String[] head = {"姓名", "联系方式", "性别", "身份证号", "银行卡号", "银行卡号", "职称", "入职日期", "录入时间"};
        Employee entity = new Employee();
        List exchangedList = dao.multQuery(multiNames);
        List<String[]> contents = new ArrayList<String[]>();
        contents.add(head);
        String[] content;
        for (int i = 0; i < exchangedList.size(); i++) {
            Employee exchanged = (Employee) exchangedList.get(i);
            content = new String[head.length];
            content[0] = exchanged.getName();
            content[1] = exchanged.getPhone();
            content[2] = exchanged.getSex();
            content[4] = exchanged.getIdentity_card_id();
            content[5] = exchanged.getBank_card_id();
            content[6] = exchanged.getPost();
            SimpleDateFormat simDate = new SimpleDateFormat("yyyy-MM-dd");
            content[7] = simDate.format(exchanged.getEntry_date());
            SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            content[8] = sim.format(exchanged.getRecord_date());
            contents.add(content);
        }
        HttpServletResponse response = (HttpServletResponse) ActionContext.getContext().get(ServletActionContext.HTTP_RESPONSE);
//        HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
        String fileName = Excel.simpleExportExcel(contents);
        /*String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
                + request.getServerName() + ":" + request.getServerPort()
                + path + "/";*/
        response.sendRedirect("download.do?fileName=" + fileName);
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
}
