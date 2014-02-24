<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<%@include file="/component/head.jsp" %>
<body class="easyui-layout" style="border:none;padding: 1px;margin: 1px;">
<div data-options="region:'center'" fit="true" style="border:none;padding: 1px;margin: 1px;">
    <div style="margin:10px; ;padding-left: 5px;">
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
        <div style="color: red">导入格式:</div>
        <div style="color: red">
            <div>1. 第一行必须是标题,没有标题就空一行</div>
            <div>2. 第一列必须是名字</div>
            <%--<div>3.金额那一列导出来后最好手动转换为数字</div>--%>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    function checkName() {

    }
</script>
</html>