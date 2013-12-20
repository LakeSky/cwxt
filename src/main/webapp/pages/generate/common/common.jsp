<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<%@include file="/component/head.jsp" %>
<body class="easyui-layout" style="border:none;padding: 1px;margin: 1px;">
<div data-options="region:'center'" fit="true" style="border:none;padding: 1px;margin: 1px;">

    <s:hidden name="idField" id="idField"/>
    <s:hidden name="ids" id="ids"/>
    <s:hidden name="actionType" id="actionType"/>
    <s:hidden name="fieldNames" id="fieldNames"/>
    <s:hidden name="jsonEditFields" id="jsonEditFields"/>
    <s:hidden name="jsonQueryFields" id="jsonQueryFields"/>
    <s:hidden name="jsonDictFields" id="jsonDictFields"/>
    <div id="tb" style="padding-top:5px;padding-left:20px;height:auto;">
        <div style="margin-bottom:5px">
            <a href="#" onclick="add()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
            <a href="#" onclick="edit();" class="easyui-linkbutton" iconCls="icon-edit" plain="true">编辑</a>
            <a href="#" onclick="del();" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
            <a href="#" onclick="exportExcel();" class="easyui-linkbutton" iconCls="icon-redo" plain="true">导出</a>
            <a href="#" onclick="test();" class="easyui-linkbutton" iconCls="icon-ok" plain="true">测试</a>
        </div>
        <div style="padding-left: 5px;">
            <s:iterator value="queryFields" id="field" status="st">
                <s:if test="#field.map.size>0">
                    ${field.zh_name}:<s:select id="query_%{#field.name}" list="#field.map" cssStyle="vertical-align:middle"/>
                </s:if>
                <s:elseif test="#field.type=='Date'">
                    ${field.zh_name}:
                    <s:textfield id="query_start_%{#field.name}" cssClass="easyui-datebox" cssStyle="width:100px;vertical-align:middle"/>
                    - <s:textfield id="query_end_%{#field.name}" cssClass="easyui-datebox" cssStyle="width:100px;vertical-align:middle"/>
                </s:elseif>
                <s:elseif test="#field.type=='DateTime'">
                    ${field.zh_name}:
                    <s:textfield id="query_start_%{#field.name}" cssClass="easyui-datetimebox" cssStyle="width:100px;vertical-align:middle"/>
                    - <s:textfield id="query_end_%{#field.name}" cssClass="easyui-datetimebox" cssStyle="width:100px;vertical-align:middle"/>
                </s:elseif>
                <s:else>
                    ${field.zh_name}:
                    <s:textfield id="query_%{#field.name}" cssStyle="vertical-align:middle"/>
                </s:else>
            </s:iterator>
            <s:if test="queryFields.size()>0">
                <a href="#" onclick="loadData()" class="easyui-linkbutton" iconCls="icon-search">查询</a>
            </s:if>
        </div>
    </div>

    <table id="dg" class="easyui-datagrid" style="height: auto;border: 1px;" title="" data-options="
				rownumbers:true,
				singleSelect:false,
				autoRowHeight:true,
				pagination:true,
				pageSize:20,
				<%--fit:true,--%>
                toolbar:'#tb'">
        <thead>
        <tr>
            <th data-options="field:'ck',checkbox:true"></th>
            <th field="${idField}" width="0" hidden="true"></th>
            <s:iterator value="showFields" id="field" status="st">
                <s:if test="#field.map.size>0">
                    <s:select cssStyle="display: none" id="dict_%{#field.name}" list="#field.map"/>
                    <th field="${field.name}" formatter="Common.${field.name}Formatter">${field.zh_name}</th>
                </s:if>
                <s:elseif test="#field.type=='Date'">
                    <th field="${field.name}" formatter="Common.DateFormatter">${field.zh_name}</th>
                </s:elseif>
                <s:elseif test="#field.type=='DateTime'">
                    <th field="${field.name}" formatter="Common.DateTimeFormatter">${field.zh_name}</th>
                </s:elseif>
                <s:else>
                    <th field="${field.name}">${field.zh_name}</th>
                </s:else>
            </s:iterator>
        </tr>
        </thead>
    </table>

    <div id="dd" class="easyui-dialog" title="My Dialog" style="width:400px;height:200px;"
         data-options="width:400,height:400,resizable:true,modal:true,closed:true,cache:false">
        <form id="form" action="common" method="post">
            <table id="editTable">
                <s:iterator value="editFields" id="field" status="st">
                    <tr>
                        <td>
                                ${field.zh_name}
                            <s:if test="#field.notNull">
                                <span style="color: red;padding: 0;margin: 0">*</span>
                            </s:if>
                        </td>
                        <td>
                            <s:if test="#field.map.size>0">
                                <s:select id="%{#field.name}" list="#field.map"/>
                            </s:if>
                            <s:elseif test="#field.type=='Date'">
                                <s:textfield id="%{#field.name}" cssClass="easyui-datebox"/>
                            </s:elseif>
                            <s:elseif test="#field.type=='DateTime'">
                                <s:textfield id="%{#field.name}" cssClass="easyui-datetimebox"/>
                            </s:elseif>
                            <s:elseif test="#field.length>=500">
                                <s:textarea id="%{#field.name}" cssStyle="width: 200px;"/>
                            </s:elseif>
                            <s:else>
                                <s:if test="#field.notNull">
                                    <s:textfield id="%{#field.name}" cssClass="easyui-validatebox"
                                                 data-options="required:true"/>
                                </s:if>
                                <s:else>
                                    <s:textfield id="%{#field.name}"/>
                                </s:else>
                            </s:else>
                        </td>
                    </tr>
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
    var arr = [];
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
    return arr;
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
                        url: "common!save.do?actionType=" + $('#actionType').val() + "&random=" + Math.random(),
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

    var jsonFieldNames = eval($('#jsonEditFields').val());
    for (var i = 0; i < jsonFieldNames.length; i++) {
        var type = jsonFieldNames[i].type;
        if (type == "Date") {
            var date = new Date();
            date.setTime(rows[0][jsonFieldNames[i].name].time);
            $("#" + jsonFieldNames[i].name).datebox('setValue', dateFormat(date, 'yyyy-MM-dd'));
        }
        else if (type == "DateTime") {
            var date = new Date();
            date.setTime(rows[0][jsonFieldNames[i].name].time);
            $("#" + jsonFieldNames[i].name).datebox('setValue', dateFormat(date, 'yyyy-MM-dd HH:mm:ss'));
        }
        else {
            $("#" + jsonFieldNames[i].name).val(rows[0][jsonFieldNames[i].name]);
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
                        url: "common!update.do?actionType=" +
                                $('#actionType').val() + "&entityMap." + $('#idField').val() + "="
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
    var jsonFieldNames = eval($('#jsonEditFields').val());
    for (var i = 0; i < jsonFieldNames.length; i++) {
        var type = jsonFieldNames[i].type;
        if (type == "Date") {
            var date = new Date();
            date.setTime(rows[0][jsonFieldNames[i].name].time);
            $("#" + jsonFieldNames[i].name).datebox('setValue', dateFormat(date, 'yyyy-MM-dd'));
        }
        else if (type == "DateTime") {
            var date = new Date();
            date.setTime(rows[0][jsonFieldNames[i].name].time);
            $("#" + jsonFieldNames[i].name).datebox('setValue', dateFormat(date, 'yyyy-MM-dd HH:mm:ss'));
        }
        else {
            $("#" + jsonFieldNames[i].name).val(rows[0][jsonFieldNames[i].name]);
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
                        url: "common!update.do?actionType=" +
                                $('#actionType').val() + "&entityMap." + $('#idField').val() + "="
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
            var ids = "";
            for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
                ids = ids + row[$('#idField').val()] + ",";
            }
            $.ajax({
                type: "POST",
                url: "common!del.do?actionType=" + $('#actionType').val() + " &random=" + Math.random(),
                data: {"ids": ids.substr(0, ids.length - 1)},
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

}

function initDataWithJson() {
    var jsonFieldNames = eval($('#jsonEditFields').val());
    var aa = "";
    for (var i = 0; i < jsonFieldNames.length; i++) {
        var type = jsonFieldNames[i].type;
        var val = "";
        if (type == "Date") {
            val = $("#" + jsonFieldNames[i].name).datebox('getValue');
        }
        else if (type == "DateTime") {
            val = $("#" + jsonFieldNames[i].name).datetimebox('getValue');
        }
        else {
            val = $("#" + jsonFieldNames[i].name).val();
        }
        aa += "'entityMap." + jsonFieldNames[i].name + "':'" + val.replace(/[\r\n]+/g, '\\n') + "',";
    }
    var field = eval("({" + aa.substr(0, aa.length - 1) + "})");
    return field;
}

function initQueryDataWithJson() {
    var jsonFieldNames = eval($('#jsonQueryFields').val());
    var aa = "";
    for (var i = 0; i < jsonFieldNames.length; i++) {
        var type = jsonFieldNames[i].type;
        var val = "";
        if (type == "Date") {
            var valStart = $("#query_start_" + jsonFieldNames[i].name).datebox('getValue');
            var valEnd = $("#query_end_" + jsonFieldNames[i].name).datebox('getValue');
            if ($.trim(valStart) == "" && $.trim(valEnd) == "") {
                continue;
            }
            aa += "'entityMap.HHHHHHstart_" + jsonFieldNames[i].name + "':'" + valStart + "',";
            aa += "'entityMap.HHHHHHend_" + jsonFieldNames[i].name + "':'" + valEnd + "',";
        }
        else if (type == "DateTime") {
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
    var field = eval("({" + aa.substr(0, aa.length - 1) + "})");
    return field;
}

function loadData() {
    var url = "common!query.do?actionType=" + $('#actionType').val() + "&random=" + Math.random();
    $.ajax({
        type: "POST",
        url: url,
        data: initQueryDataWithJson(),
        async: false,
        success: function (data) {
            $('#dg').datagrid({loadFilter: pagerFilter}).datagrid('loadData', eval(data));
        }
    });
}

/*function initData() {
 var fieldNames = $('#strEditFields').val();
 var splitFieldNames = fieldNames.split(",");
 alert(splitFieldNames);
 var aa = "";
 for (var i = 0; i < splitFieldNames.length; i++) {
 var val = $("#" + splitFieldNames[i]).val();
 aa += "'entityMap." + splitFieldNames[i] + "':'" + val + "',";
 }
 alert(aa);
 var field = eval("({" + aa.substr(0, aa.length - 1) + "})");
 return field;
 }*/

//分页代码
function pagerFilter(data) {
    if (typeof data.length == 'number' && typeof data.splice == 'function') {    // is array
        data = {
            total: data.length,
            rows: data
        }
    }
    var dg = $(this);
    var opts = dg.datagrid('options');
    var pager = dg.datagrid('getPager');
    pager.pagination({
        onSelectPage: function (pageNum, pageSize) {
            opts.pageNumber = pageNum;
            opts.pageSize = pageSize;
            pager.pagination('refresh', {
                pageNumber: pageNum,
                pageSize: pageSize
            });
            dg.datagrid('loadData', data);
        }
    });
    if (!data.originalRows) {
        data.originalRows = (data.rows);
    }
    var start = (opts.pageNumber - 1) * parseInt(opts.pageSize);
    var end = start + parseInt(opts.pageSize);
    data.rows = (data.originalRows.slice(start, end));
    return data;
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

function dateFormat(date, format) {
    var o = {
        "M+": date.getMonth() + 1, //month
        "d+": date.getDate(),    //day
        "h+": date.getHours(),   //hour
        "m+": date.getMinutes(), //minute
        "s+": date.getSeconds(), //second
        "q+": Math.floor((date.getMonth() + 3) / 3),  //quarter
        "S": date.getMilliseconds() //millisecond
    }
    if (/(y+)/.test(format)) format = format.replace(RegExp.$1,
            (date.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)if (new RegExp("(" + k + ")").test(format))
        format = format.replace(RegExp.$1,
                RegExp.$1.length == 1 ? o[k] :
                        ("00" + o[k]).substr(("" + o[k]).length));
    return format;
}
</script>
</body>
</html>