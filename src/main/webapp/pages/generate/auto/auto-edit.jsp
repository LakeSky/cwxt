<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<%@include file="/component/head.jsp" %>
<body class="easyui-layout" style="border:none;padding: 1px;margin: 1px;">
<div data-options="region:'center'" fit="true" style="border:none;padding: 1px;margin: 1px;">
    <div id="dd" class="easyui-dialog" title="My Dialog" style="width:400px;height:200px;"
         data-options="width:400,height:400,resizable:true,modal:true,closed:true,cache:false">
        <form id="form" action="common" method="post">
            <table id="editTable">
                <s:iterator value="editFields" id="field" status="st">
                    <tr>
                        <td>
                                ${field.zh_name}
                            <s:if test="#field.notNull">
                                <span style="color: red;padding: 0;margin: 0">*</span>
                            </s:if>
                        </td>
                        <td>
                            <s:if test="#field.map.size>0">
                                <s:select id="%{#field.name}" list="#field.map"/>
                            </s:if>
                            <s:elseif test="#field.type=='Date'">
                                <s:textfield id="%{#field.name}" cssClass="easyui-datebox"/>
                            </s:elseif>
                            <s:elseif test="#field.type=='DateTime'">
                                <s:textfield id="%{#field.name}" cssClass="easyui-datetimebox"/>
                            </s:elseif>
                            <s:elseif test="#field.length>=500">
                                <s:textarea id="%{#field.name}" cssStyle="width: 200px;"/>
                            </s:elseif>
                            <s:else>
                                <s:if test="#field.notNull">
                                    <s:textfield id="%{#field.name}" cssClass="easyui-validatebox"
                                                 data-options="required:true"/>
                                </s:if>
                                <s:else>
                                    <s:textfield id="%{#field.name}"/>
                                </s:else>
                            </s:else>
                        </td>
                    </tr>
                </s:iterator>
            </table>
        </form>
    </div>
</div>

</body>
</html>