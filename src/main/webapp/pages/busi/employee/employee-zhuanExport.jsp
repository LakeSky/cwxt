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
                        <s:submit method="exportZhunCunData" onclick="return checkName();" value="导出"/>
                    </td>
                </tr>
            </table>
        </s:form>
    </div>
    <div style="padding-left: 5px;">
        <div>导入格式</div>
        <div>
            第一行必须是标题,没有标题就空一行
            第一列必须是名字就可以了
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    function checkName() {
        $.ajax({
            type: "POST",
            url: "register!confirmYzm.do?random=" + Math.random(),
            data: {"user.phone": phone, "user.yzm": yzm},
            async: false,
            success: function (data) {
                var yzmResult = eval('(' + data + ')');
                alert(yzmResult["retCode"]);
                if (yzmResult["retCode"] == "ok") {
                    confirmYzm = true;
                }
            }
        });
    }
</script>
</html>