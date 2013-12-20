<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%@ include file="/component/head.jsp" %>
<body style="text-align: center">
<div style="margin:0,auto;text-align: center">
    <div class="easyui-panel" title="登 录" style="width:400px;margin-left: auto;margin-right: auto;">
        <div style="padding:10px 0 10px 60px;">
            <%
                if (session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION") != null &&
                        session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION") != "") {
            %>
            <div style="color:red">
                用户名或密码错误
            </div>
            <%
                }
            %>
            <form id="ff" action="${pageContext.request.contextPath}/j_spring_security_check" method="post">
                <table>
                    <tr>
                        <td>
                            用户：
                        </td>
                        <td>
                            <input type="text" name="j_username" style="width:150px;" value="${sessionScope['SPRING_SECURITY_LAST_USERNAME']}"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            密码：
                        </td>
                        <td>
                            <input type="password" name="j_password" style="width:150px;"/>
                        </td>
                    </tr>
                    <tr>
                        <td>

                        </td>
                        <td>
                            <input type="checkbox" name="_spring_security_remember_me"/>两周之内不必登陆<br/>
                        </td>
                    </tr>
                    <tr>
                        <td>

                        </td>
                        <td>
                            <input type="submit" value="登录">
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
    function login() {
        var url = "${pageContext.request.contextPath}/j_spring_security_check";
        var username = $("input[name='j_username']").val();
        var password = $("input[name='j_password']").val();
        alert(username);
        alert(password);
        $.ajax({
            url: url,
            type: "POST",
            data: "j_username=" + username + "&j_password=" + password,
            success: function (data) {
                alert(data);
                //$("#results").text(data);
            }
        });
    }
    function submitForm() {
        $('#ff').form('submit');
    }
    function clearForm() {
        $('#ff').form('clear');
    }
</script>

</body>
</html>
