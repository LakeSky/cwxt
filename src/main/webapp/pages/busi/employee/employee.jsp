<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<%@include file="/component/head.jsp" %>
<body class="easyui-layout" style="border:none;padding: 1px;margin: 1px;">
<div data-options="region:'center'" fit="true" style="border:none;padding: 1px;margin: 1px;">
    <s:hidden name="idField" id="idField"/>
    <s:hidden name="delIds" id="delIds"/>
    <s:hidden name="o" id="o"/>
    <s:hidden name="jsonAllFields" id="jsonAllFields"/>
    <s:form id="exportForm" action="" method="post">
        <s:hidden name="exportEntityMap" id="exportEntityMap"/>
    </s:form>
    <div id="tb" style="padding-top:5px;padding-left:20px;height:auto;">
        <div style="margin-bottom:5px">
            <a href="#" onclick="add()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
            <a href="#" onclick="edit();" class="easyui-linkbutton" iconCls="icon-edit" plain="true">编辑</a>
            <a href="#" onclick="del();" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
            <a href="#" onclick="exportExcel();" class="easyui-linkbutton" iconCls="icon-redo" plain="true">仅导出名字|身份证|银行卡号</a>
            <a href="#" onclick="exportExcelAllField();" class="easyui-linkbutton" iconCls="icon-redo" plain="true">导出全部字段</a>
            <%--<a href="#" onclick="exportExcel();" class="easyui-linkbutton" iconCls="icon-redo" plain="true">导入</a>--%>
            <%--<a href="#" onclick="test();" class="easyui-linkbutton" iconCls="icon-ok" plain="true">测试</a>--%>
            <a href="#" onclick="showDefinePanel();" class="easyui-linkbutton" iconCls="icon-tip" plain="true">
                <span id="definePanelControl">显示自定义面板</span>
            </a>
        </div>
        <% int i = 0;%>
        <div style="padding-left: 5px;">
            <table style="border: 1px;">
                <s:iterator value="listAllFields" id="field" status="st">
                    <s:if test="#field.actions.contains('query')">
                        <% if (i % 3 == 0) {%>
                        <tr>
                            <% }%>
                            <s:if test="#field.type=='dict'">
                                <td><a href="#" class="easyui-linkbutton" plain="true">${field.zh_name}:</a></td>
                                <td><s:select id="query_%{#field.name}" list="#field.map" cssStyle="vertical-align:middle"/></td>
                            </s:if>
                            <s:if test="#field.type=='date'">
                                <td><a href="#" class="easyui-linkbutton" plain="true">${field.zh_name}:</a></td>
                                <td>
                                    <s:textfield id="query_start_%{#field.name}" cssClass="easyui-datebox" cssStyle="width:100px;vertical-align:middle"/>
                                    - <s:textfield id="query_end_%{#field.name}" cssClass="easyui-datebox" cssStyle="width:100px;vertical-align:middle"/>
                                </td>
                            </s:if>
                            <s:if test="#field.type=='time'">
                                <td><a href="#" class="easyui-linkbutton" plain="true">${field.zh_name}:</a></td>
                                <td>
                                    <s:textfield id="query_start_%{#field.name}" cssClass="easyui-datetimebox" cssStyle="width:100px;vertical-align:middle"/>
                                    - <s:textfield id="query_end_%{#field.name}" cssClass="easyui-datetimebox" cssStyle="width:100px;vertical-align:middle"/>
                                </td>
                            </s:if>
                            <s:if test="#field.type=='text'">
                                <td><a href="#" class="easyui-linkbutton" plain="true">${field.zh_name}:</a></td>
                                <td><s:textfield id="query_%{#field.name}" cssStyle="vertical-align:middle"/></td>
                            </s:if>
                            <s:if test="#field.type=='textarea'">
                                <td><a href="#" class="easyui-linkbutton" plain="true">${field.zh_name}:</a></td>
                                <td><s:textfield id="query_%{#field.name}" cssStyle="vertical-align:middle"/></td>
                            </s:if>
                            <% i++;
                                if (i % 3 == 0) {%>
                        </tr>
                        <% }%>
                    </s:if>
                </s:iterator>
                <%if (i % 3 != 0) {%>
                <td colspan="3">
                    <a href="#" onclick="loadData()" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                </td>
                </tr>
                <% } else {%>
                <tr>
                    <td colspan="3">
                        <a href="#" onclick="loadData()" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                    </td>
                </tr>
                <% }%>
            </table>
        </div>
        <div id="definePanel" style="display: none;padding: 1px;background-color: white">
            <fieldset>
                <legend>要显示的字段</legend>
                <s:iterator value="listAllFields" id="field" status="st">
                    <s:if test="#field.actions.contains('show')">
                        <input type="checkbox" checked=true id="show_${field.name}" onclick="showDefineColumn()">${field.zh_name}
                    </s:if>
                </s:iterator>
            </fieldset>
        </div>
    </div>

    <table id="dg" class="easyui-datagrid" style="height: auto;border: 1px;" title="" data-options="
				rownumbers:true,
				singleSelect:false,
				autoRowHeight:true,
				pagination:true,
				pageSize:10,
				<%--fit:true,--%>
                toolbar:'#tb'">
        <thead>
        <tr>
            <th data-options="field:'ck',checkbox:true"></th>
            <th sortable="true" field="${idField}" width="0" hidden="true"></th>
            <s:iterator value="listAllFields" id="field" status="st">
                <s:if test="#field.actions.contains('show')">
                    <s:if test="#field.type=='dict'">
                        <s:select cssStyle="display: none" id="dict_%{#field.name}" list="#field.map"/>
                        <th sortable="true" field="${field.name}" formatter="Common.${field.name}Formatter">
                                ${field.zh_name}
                        </th>
                    </s:if>
                    <s:elseif test="#field.type=='date'">
                        <th sortable="true" field="${field.name}" formatter="Common.DateFormatter">
                                ${field.zh_name}
                        </th>
                    </s:elseif>
                    <s:elseif test="#field.type=='time'">
                        <th sortable="true" field="${field.name}" formatter="Common.DateTimeFormatter">
                                ${field.zh_name}
                        </th>
                    </s:elseif>
                    <s:else>
                        <th sortable="true" field="${field.name}">
                                ${field.zh_name}
                        </th>
                    </s:else>
                </s:if>
            </s:iterator>
        </tr>
        </thead>
    </table>

    <div id="dd" class="easyui-dialog" title="My Dialog" style="width:400px;height:200px;"
         data-options="width:400,height:400,resizable:true,modal:true,closed:true,cache:false">
        <form id="form" action="employee" method="post">
            <table id="editTable">
                <s:iterator value="listAllFields" id="field" status="st">
                    <s:if test="#field.actions.contains('edit')">
                        <tr>
                            <td>
                                <s:if test="#field.nullable==false">
                                    ${field.zh_name}<span style="color: red;">*</span>
                                </s:if>
                                <s:else>
                                    ${field.zh_name}
                                </s:else>
                            </td>
                            <td>
                                <s:if test="#field.nullable==false">
                                    <s:if test="#field.type=='dict'">
                                        <s:select id="%{#field.name}" cssClass="easyui-validatebox" list="#field.map" data-options="required:true"/>
                                    </s:if>
                                    <s:elseif test="#field.type=='date'">
                                        <s:textfield id="%{#field.name}" cssClass="easyui-datebox" data-options="required:true"/>
                                    </s:elseif>
                                    <s:elseif test="#field.type=='time'">
                                        <s:textfield id="%{#field.name}" cssClass="easyui-datetimebox" data-options="required:true"/>
                                    </s:elseif>
                                    <s:elseif test="#field.type=='textarea'">
                                        <s:textarea id="%{#field.name}" cssClass="easyui-validatebox" cssStyle="width: 200px;" data-options="required:true"/>
                                    </s:elseif>
                                    <s:else>
                                        <s:textfield id="%{#field.name}" cssClass="easyui-validatebox" data-options="required:true"/>
                                    </s:else>
                                </s:if>
                                <s:else>
                                    <s:if test="#field.type=='dict'">
                                        <s:select id="%{#field.name}" list="#field.map"/>
                                    </s:if>
                                    <s:elseif test="#field.type=='date'">
                                        <s:textfield id="%{#field.name}" cssClass="easyui-datebox"/>
                                    </s:elseif>
                                    <s:elseif test="#field.type=='time'">
                                        <s:textfield id="%{#field.name}" cssClass="easyui-datetimebox"/>
                                    </s:elseif>
                                    <s:elseif test="#field.type=='textarea'">
                                        <s:textarea id="%{#field.name}" cssStyle="width: 200px;"/>
                                    </s:elseif>
                                    <s:else>
                                        <s:textfield id="%{#field.name}"/>
                                    </s:else>
                                </s:else>
                            </td>
                        </tr>
                    </s:if>
                </s:iterator>
            </table>
        </form>
    </div>
</div>
<script>
$(function () {
    loadData();
    $('#dg').datagrid({
        onDblClickRow: function (rowIndex, rowData) {
            editByDoubleClick(rowData);
        }
    });
});

function test() {
    alert("测试用");
    /*var arr = [];
     var jsonFieldNames = eval($('#jsonEditFields').val());
     var aa = "";
     for (var i = 0; i < jsonFieldNames.length; i++) {
     var type = jsonFieldNames[i].type;
     var val = "";
     if (type == "Date") {
     val = $("#" + jsonFieldNames[i].name).datebox('getValue');
     }
     else {
     val = $("#" + jsonFieldNames[i].name).val();
     }
     aa += "'entityMap." + jsonFieldNames[i].name + "':'" + val + "',";
     arr.push("'entityMap.'" + jsonFieldNames[i].name + "':" + val);
     }
     return arr;*/
}

function add() {
    $("#editTable input").val("");
    $('#dd').dialog({
        title: '添加',
        closed: false,
        cache: false,
        modal: true,
        iconCls: 'icon-add',
        buttons: [
            {
                text: '保存',
                iconCls: 'icon-ok',
                handler: function () {
                    if (!$('#form').form('validate')) {
                        return;
                    }
                    $.ajax({
                        type: "POST",
                        url: "employee!save.do?o=" + $('#o').val() + "&random=" + Math.random(),
                        data: initDataWithJson(),
                        async: false,
                        success: function (data) {
                            alert("保存成功");
                            loadData();
                        }
                    });
                    $('#dd').dialog('close');
                }
            },
            {
                text: '取消',
                handler: function () {
                    $('#dd').dialog('close');
                }
            }
        ]
    });
}

function edit() {
    var rows = $("#dg").datagrid("getSelections");
    if (rows.length != 1) {
        alert("请选中一行数据");
        return;
    }

    var jsonFieldNames = eval($('#jsonAllFields').val());
    for (var i = 0; i < jsonFieldNames.length; i++) {
        var actions = jsonFieldNames[i].actions;
        if (actions.indexOf("edit") != -1) {
            var type = jsonFieldNames[i].type;
            if (type == "date") {
                var date = new Date();
                if (rows[0][jsonFieldNames[i].name] != null) {
                    date.setTime(rows[0][jsonFieldNames[i].name].time);
                    $("#" + jsonFieldNames[i].name).datebox('setValue', dateFormat(date, 'yyyy-MM-dd'));
                }
            }
            else if (type == "time") {
                var date = new Date();
                if (rows[0][jsonFieldNames[i].name] != null) {
                    date.setTime(rows[0][jsonFieldNames[i].name].time);
                    $("#" + jsonFieldNames[i].name).datebox('setValue', dateFormat(date, 'yyyy-MM-dd HH:mm:ss'));
                }
            }
            else {
                $("#" + jsonFieldNames[i].name).val(rows[0][jsonFieldNames[i].name]);
            }
        }
    }

    $('#dd').dialog({
        title: '编辑',
        closed: false,
        iconCls: 'icon-edit',
        buttons: [
            {
                text: '保存',
                iconCls: 'icon-ok',
                handler: function () {
                    if (!$('#form').form('validate')) {
                        return;
                    }
                    $.ajax({
                        type: "POST",
                        url: "employee!update.do?o=" +
                                $('#o').val() + "&entityMap." + $('#idField').val() + "="
                                + rows[0][$('#idField').val()] + "&random=" + Math.random(),
                        data: initDataWithJson(),
                        async: false,
                        success: function (data) {
                            alert("保存成功");
                            loadData();
                        }
                    });
                    $('#dd').dialog('close');
                }
            },
            {
                text: '取消',
                handler: function () {
                    $('#dd').dialog('close');
                }
            }
        ]
    });
}

function editByDoubleClick(row) {
    var rows = [];
    rows[0] = row;
    var jsonFieldNames = eval($('#jsonAllFields').val());
    for (var i = 0; i < jsonFieldNames.length; i++) {
        var actions = jsonFieldNames[i].actions;
        if (actions.indexOf("edit") != -1) {
            var type = jsonFieldNames[i].type;
            if (type == "date") {
                var date = new Date();
                if (rows[0][jsonFieldNames[i].name] != null) {
                    date.setTime(rows[0][jsonFieldNames[i].name].time);
                    $("#" + jsonFieldNames[i].name).datebox('setValue', dateFormat(date, 'yyyy-MM-dd'));
                }
            }
            else if (type == "time") {
                var date = new Date();
                if (rows[0][jsonFieldNames[i].name] != null) {
                    date.setTime(rows[0][jsonFieldNames[i].name].time);
                    $("#" + jsonFieldNames[i].name).datebox('setValue', dateFormat(date, 'yyyy-MM-dd HH:mm:ss'));
                }
            }
            else {
                $("#" + jsonFieldNames[i].name).val(rows[0][jsonFieldNames[i].name]);
            }
        }
    }

    $('#dd').dialog({
        title: '编辑',
        closed: false,
        iconCls: 'icon-edit',
        buttons: [
            {
                text: '保存',
                iconCls: 'icon-ok',
                handler: function () {
                    if (!$('#form').form('validate')) {
                        return;
                    }
                    $.ajax({
                        type: "POST",
                        url: "employee!update.do?o=" +
                                $('#o').val() + "&entityMap." + $('#idField').val() + "="
                                + rows[0][$('#idField').val()] + "&random=" + Math.random(),
                        data: initDataWithJson(),
                        async: false,
                        success: function (data) {
                            alert("保存成功");
                            loadData();
                        }
                    });
                    $('#dd').dialog('close');
                }
            },
            {
                text: '取消',
                handler: function () {
                    $('#dd').dialog('close');
                }
            }
        ]
    });
}

function del() {
    var rows = $("#dg").datagrid("getSelections");
    if (rows.length == 0) {
        alert("至少选择一行数据");
        return;
    }
    var rows = $("#dg").datagrid("getSelections");
    if (rows.length != 0) {
        if (confirm("确定要删除数据?")) {
            var delIds = "";
            for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
                delIds = delIds + row[$('#idField').val()] + ",";
            }
            $.ajax({
                type: "POST",
                url: "employee!del.do?o=" + $('#o').val() + " &random=" + Math.random(),
                data: {"delIds": delIds.substr(0, delIds.length - 1)},
                async: false,
                success: function (data) {
                    alert("删除成功");
                    loadData();
                }
            });
            $('#dd').dialog('close');
        }
    }
}

function exportExcel() {
    var form = $("#exportForm");
    form.attr("action", "employee!exportExcel.do");
    document.getElementById("exportEntityMap").value = initQueryDataWithJsonForPost();
    form.submit();
}

function exportExcelAllField() {
    var form = $("#exportForm");
    form.attr("action", "employee!exportExcelAllField.do");
    document.getElementById("exportEntityMap").value = initQueryDataWithJsonForPost();
    form.submit();
}

function importExcel() {

}

function initDataWithJson() {
    var jsonFieldNames = eval($('#jsonAllFields').val());
    var aa = "";
    for (var i = 0; i < jsonFieldNames.length; i++) {
        var actions = jsonFieldNames[i].actions;
        if (actions.indexOf("edit") != -1) {
            var val = "";
            var type = jsonFieldNames[i].type;
            if (type == "date") {
                val = $("#" + jsonFieldNames[i].name).datebox('getValue');
            }
            else if (type == "time") {
                val = $("#" + jsonFieldNames[i].name).datetimebox('getValue');
            }
            else {
                val = $("#" + jsonFieldNames[i].name).val();
            }
            aa += "'entityMap." + jsonFieldNames[i].name + "':'" + val.replace(/[\r\n]+/g, '\\n') + "',";
        }
    }
    var field = eval("({" + aa.substr(0, aa.length - 1) + "})");
    return field;
}

function initQueryDataWithJson() {
    var jsonFieldNames = eval($('#jsonAllFields').val());
    var aa = "";
    for (var i = 0; i < jsonFieldNames.length; i++) {
        var actions = jsonFieldNames[i].actions;
        if (actions.indexOf("query") != -1) {
            var type = jsonFieldNames[i].type;
            var val = "";
            if (type == "date") {
                var valStart = $("#query_start_" + jsonFieldNames[i].name).datebox('getValue');
                var valEnd = $("#query_end_" + jsonFieldNames[i].name).datebox('getValue');
                if ($.trim(valStart) == "" && $.trim(valEnd) == "") {
                    continue;
                }
                aa += "'entityMap.HHHHHHstart_" + jsonFieldNames[i].name + "':'" + valStart + "',";
                aa += "'entityMap.HHHHHHend_" + jsonFieldNames[i].name + "':'" + valEnd + "',";
            }
            else if (type == "time") {
                var valStart = $("#query_start_" + jsonFieldNames[i].name).datetimebox('getValue');
                var valEnd = $("#query_end_" + jsonFieldNames[i].name).datetimebox('getValue');
                if ($.trim(valStart) == "" && $.trim(valEnd) == "") {
                    continue;
                }
                aa += "'entityMap.HHHHHHstart_" + jsonFieldNames[i].name + "':'" + valStart + "',";
                aa += "'entityMap.HHHHHHend_" + jsonFieldNames[i].name + "':'" + valEnd + "',";
            }
            else {
                val = $("#query_" + jsonFieldNames[i].name).val();
                if ($.trim(val) == "") {
                    continue;
                }
                aa += "'entityMap." + jsonFieldNames[i].name + "':'" + val + "',";
            }
        }
    }
    var field = eval("({" + aa.substr(0, aa.length - 1) + "})");
    return field;
}

function initQueryDataWithJsonForPost() {
    var jsonFieldNames = eval($('#jsonAllFields').val());
    var aa = "";
    for (var i = 0; i < jsonFieldNames.length; i++) {
        var actions = jsonFieldNames[i].actions;
        if (actions.indexOf("query") != -1) {
            var type = jsonFieldNames[i].type;
            var val = "";
            if (type == "date") {
                var valStart = $("#query_start_" + jsonFieldNames[i].name).datebox('getValue');
                var valEnd = $("#query_end_" + jsonFieldNames[i].name).datebox('getValue');
                if ($.trim(valStart) == "" && $.trim(valEnd) == "") {
                    continue;
                }
                aa += "'HHHHHHstart_" + jsonFieldNames[i].name + "':'" + valStart + "',";
                aa += "'HHHHHHend_" + jsonFieldNames[i].name + "':'" + valEnd + "',";
            }
            else if (type == "time") {
                var valStart = $("#query_start_" + jsonFieldNames[i].name).datetimebox('getValue');
                var valEnd = $("#query_end_" + jsonFieldNames[i].name).datetimebox('getValue');
                if ($.trim(valStart) == "" && $.trim(valEnd) == "") {
                    continue;
                }
                aa += "'HHHHHHstart_" + jsonFieldNames[i].name + "':'" + valStart + "',";
                aa += "'HHHHHHend_" + jsonFieldNames[i].name + "':'" + valEnd + "',";
            }
            else {
                val = $("#query_" + jsonFieldNames[i].name).val();
                if ($.trim(val) == "") {
                    continue;
                }
                aa += "'" + jsonFieldNames[i].name + "':'" + val + "',";
            }
        }
    }
    var field = "{" + aa.substr(0, aa.length - 1) + "}";
    return field;
}

function loadData() {
    var url = "employee!query.do?o=Employee&random=" + Math.random();
    $.ajax({
        type: "POST",
        url: url,
        data: initQueryDataWithJson(),
        async: true,
        success: function (data) {
            $('#dg').datagrid({loadFilter: pagerFilter}).datagrid('loadData', eval(data));
        }
    });

    $('#dg').pagination({pageNumber: 1});
}

var Common = {
    TimeFormatter: function (value, rec, index) {
        if (value == undefined) {
            return "";
        }
        var date = new Date();
        date.setTime(value.time);
        return date.toLocaleTimeString()
    },
    DateTimeFormatter: function (value, rec, index) {
        if (value == undefined) {
            return "";
        }
        var date = new Date();
        date.setTime(value.time);
        return date.toLocaleString();
    },
    DateFormatter: function (value, rec, index) {
        if (value == undefined) {
            return "";
        }
        var date = new Date();
        date.setTime(value.time);
        return date.toLocaleDateString();
    }
    <s:iterator value="dictFields" id="field">,
    ${field.name}Formatter: function (value, rec, index) {
        return $("#dict_" + "${field.name}" + " option[value=" + value + "]").text();
    }
    </s:iterator>
};

function showDefinePanel() {
    var definePanel = $('#definePanel');
    if (definePanel.css('display') == 'block') {
        $('#definePanelControl').html("显示自定义面板");
        definePanel.css('display', 'none');
    }
    else {
        $('#definePanelControl').html("隐藏自定义面板");
        definePanel.css('display', 'block');
    }
}

function showDefineColumn() {
    var id = event.srcElement.id;
    var str = id.substr('show_'.length);
    var dg = $('#dg');
    var columns = "," + dg.datagrid('getColumnFields') + ",";
    if (columns.indexOf("," + str + ",") != -1) {
        if (event.srcElement.checked) {
            dg.datagrid('showColumn', str);
        }
        else {
            dg.datagrid('hideColumn', str);
        }
    }
}

</script>
</body>
</html>