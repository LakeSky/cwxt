<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<%@include file="/component/head.jsp" %>
<body class="easyui-layout" style="border:none;padding: 1px;margin: 1px;">
<div data-options="region:'center'" fit="true" style="border:none;padding: 1px;margin: 1px;">
    <div id="tb" style="padding-top:5px;padding-left:20px;height:auto;">
        <div style="margin-bottom:5px">
            <a href="#" onclick="add()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
            <a href="#" onclick="edit();" class="easyui-linkbutton" iconCls="icon-edit" plain="true">编辑</a>
            <a href="#" onclick="del();" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
            <a href="#" onclick="editRoleResource();" class="easyui-linkbutton" iconCls="icon-edit" plain="true">编辑角色菜单</a>
            <%--<a href="#" onclick="exportExcel();" class="easyui-linkbutton" iconCls="icon-redo" plain="true">导出</a>--%>
            <a href="#" onclick="test();" class="easyui-linkbutton" iconCls="icon-ok" plain="true">测试</a>
        </div>
        <div style="padding-left: 5px;">
            角色名称:<s:textfield id="roleName" cssStyle="vertical-align:middle"/>
            <a href="#" onclick="loadData()" class="easyui-linkbutton" iconCls="icon-search">查询</a>
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
            <th field="id" hidden="true"></th>
            <th field="name" width="100">角色编码</th>
            <th field="zh_name" width="100">角色名称</th>
        </tr>
        </thead>
    </table>
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

}

function add() {
    $("#editTable input").val("");
    $("<div id='editFrame'></div>").
            append('<iframe  frameborder="0" src="role!edit.do"' +
                    ' style="width: 100%;height: 100%;"></iframe>')
            .dialog({
                width: 500,
                height: 500,
                modal: true,
                cache: false,
                title: '添加'
            });
}

function edit() {
    var rows = $("#dg").datagrid("getSelections");
    if (rows.length != 1) {
        alert("请选中一行数据");
        return;
    }
    $("<div id='editFrame'></div>").
            append('<iframe frameborder="0" src="role!edit.do?entity.id=' + rows[0].id +
                    '" style="width: 100%;height: 100%;"></iframe>')
            .dialog({
                width: 500,
                height: 500,
                modal: true,
                title: '编辑'
            });
}

function editByDoubleClick(row) {
    $("<div id='editFrame'></div>").
            append('<iframe frameborder="0" src="role!edit.do?entity.id=' + row.id +
                    '" style="width: 100%;height: 100%;"></iframe>')
            .dialog({
                width: 500,
                height: 500,
                modal: true,
                title: '编辑'
            });
}

function editRoleResource() {
    var rows = $("#dg").datagrid("getSelections");
    if (rows.length != 1) {
        alert("请选中一行数据");
        return;
    }
    $("<div id='treeMenuFrame'></div>").
            append('<iframe id="loginForm" frameborder="0" src="resource!editMenuTree.do?role.id=' +
                    rows[0].id + '&role.zh_name=' + rows[0].zh_name + '" style="width: 100%;height: 100%;"></iframe>')
            .dialog({
                width: 500,
                height: 500,
                modal: true,
                cache: false,
                title: '角色菜单管理'
            });
    /*$("#treeMenuFrame").
     append('<iframe id="loginForm" frameborder="0" src="resource!editMenuTree.do?role.id=' +
     rows[0].id + '&role.zh_name=' + rows[0].zh_name + '" style="width: 100%;height: 100%;"></iframe>')
     .dialog({
     width: 500,
     height: 500,
     modal: true,
     cache: false,
     title: '角色菜单管理'
     });*/
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
                ids = ids + row["id"] + ",";
            }
            $.ajax({
                type: "POST",
                url: "role!del.do?random=" + Math.random(),
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
    var url = "role!queryRoles.do?random=" + Math.random();
    $.ajax({
        type: "POST",
        url: url,
        async: true,
        success: function (data) {
            $('#dg').datagrid({loadFilter: pagerFilter}).datagrid('loadData', eval(data));
        }
    });
}

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

function closePopForm(frameId) {
    var frame = $("#" + frameId);
    frame.dialog("close");
    loadData();
    frame.remove();
}
</script>
</body>
</html>