<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<%@include file="/component/head.jsp" %>
<body class="easyui-layout" style="border:none;padding: 1px;margin: 1px;">
<div data-options="region:'center'" fit="true" style="border:none;padding: 1px;margin: 1px;">
    <div style="padding-left: 5px;">
        <s:form action="employee" method="post" enctype="multipart/form-data">
            <table>
                <tr>
                    <td>
                        <a href="#" class="easyui-linkbutton" plain="true">Excel:</a>
                    </td>
                    <td>
                        <s:file id="zhuanCunExcel" name="zhuanCunExcel"></s:file>
                    </td>
                    <td>
                        <s:submit method="exportZhunCunData" value="导出"/>
                    </td>
                </tr>
            </table>
        </s:form>
    </div>
    <div style="padding-left: 5px;">
        <div>导入格式</div>
        <table style="background-color: salmon">
            <tr style="border: 1px;">
                第一行必须是标题,没有标题就空一行
                第一列必须是名字就可以了
            </tr>
        </table>
    </div>
</div>
</body>
</html>