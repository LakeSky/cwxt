<%--
  Created by IntelliJ IDEA.
  User: kzh
  Date: 13-11-12
  Time: 下午3:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<div>
    <div style="margin-top: 10px;font-size: 15px;color: brown">常用功能:</div>
    <div style="margin: 10px;">
        <a href="#" onclick="addTab('员工信息', 'employee.do')">员工信息</a>
        <a href="#" onclick="addTab('转存导出', 'employee.do')">转存导出</a>
    </div>
    <br/>
    在线人数${count}
    <p/>

    <div>
        2014-02-24 升级日志:
        <div>1.修复导出数据时第一行为空行出现错误的问题</div>
        <div>2.修复添加修改人员信息导致错误的问题</div>
        <div>3.增加全量字段导出功能</div>
        <div>4.界面简单优化</div>
    </div>
</div>
</body>
</html>