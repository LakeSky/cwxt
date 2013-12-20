<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<%@include file="/component/head.jsp" %>
<body class="easyui-layout" style="border:none;padding: 1px;margin: 1px;">
<div data-options="region:'center'" fit="true" style="border:none;padding: 1px;margin: 1px;">
    <s:form id="form" action="role" method="post">
        <s:hidden name="entity.id"/>
        <table id="editTable">
            <tr>
                <td>
                    角色编码
                </td>
                <td>
                    <s:textfield name="entity.name"/>
                </td>
            </tr>
            <tr>
                <td>
                    角色名称
                </td>
                <td>
                    <s:textfield name="entity.zh_name"/>
                </td>
            </tr>
            <tr>
                <td>
                </td>
                <td>
                    <input type="button" value="提交" onclick="submitForm();">
                </td>
            </tr>
        </table>
    </s:form>
</div>

<script type="text/javascript">
    function submitForm() {
        var form = $("#form");
        form.attr("action", "role!save.do");
        form.submit();
        alert("保存成功");
        window.parent.closePopForm('editFrame');
    }

</script>
</body>
</html>