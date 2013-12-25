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
    <s:hidden name="multiNames"/>
    <div id="tb" style="padding-top:5px;padding-left:20px;height: 10px;">
        <div>
            <a href="#" onclick="exportExcel();" class="easyui-linkbutton" iconCls="icon-redo" plain="true">导出</a>
            <s:form action="employee" method="post" enctype="multipart/form-data">
                <table>
                    <tr>
                        <td>
                            <a href="#" class="easyui-linkbutton" plain="true">Excel查询:</a>
                        </td>
                        <td>
                            <s:file id="queryExcel" name="queryExcel"></s:file>
                        </td>
                        <td>
                            <s:submit method="initMultiNames" value="查询"/>
                        </td>
                    </tr>
                </table>
            </s:form>
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
</div>
<script>
    $(function () {
        loadData();
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

    function exportExcel() {

    }

    function importExcel() {

    }

    function loadData() {
        var url = "employee!multQuery.do?random=" + Math.random();
        $.ajax({
            type: "POST",
            url: url,
            async: false,
            data: {"multiNames": $("#multiNames").val()},
            success: function (data) {
                $('#dg').datagrid({loadFilter: pagerFilter}).datagrid('loadData', eval(data));
            }
        });
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


</script>
</body>
</html>