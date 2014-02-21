<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<%@include file="/component/head.jsp" %>
<body class="easyui-layout" style="border:none;padding: 1px;margin: 1px;">
<div data-options="region:'center'" fit="true" style="border:none;padding: 1px;margin: 1px;">
    <s:form action="excel-config.do">
        <s:label>缓存文件保存地址</s:label>
        <br/>
        <s:textfield name="tmpPath"/>
        <br/>
        <s:submit name="保存" method="changeTmpPath"/>
    </s:form>
</div>
</body>
</html>