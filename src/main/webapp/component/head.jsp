<%@ page language="java" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<head>
    <title>企业管理系统</title>
    <meta HTTP-EQUIV="pragma" CONTENT="no-cache">
    <meta HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
    <meta HTTP-EQUIV="expires" CONTENT="0">

    <link rel="stylesheet" type="text/css" href="<%=basePath%>/js/jquery-easyui-1.3.2/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/js/jquery-easyui-1.3.2/themes/icon.css">
    <script type="text/javascript" src="<%=basePath%>/js/jquery-easyui-1.3.2/jquery-1.8.0.js"></script>
    <%--<script type="text/javascript" src="<%=basePath%>/js/jquery-easyui-1.3.2/jquery.min.js"></script>--%>
    <script type="text/javascript" src="<%=basePath%>/js/jquery-easyui-1.3.2/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/jquery-easyui-1.3.2/plugins/jquery.tree.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/jquery-easyui-1.3.2/locale/easyui-lang-zh_CN.js"></script>
</head>