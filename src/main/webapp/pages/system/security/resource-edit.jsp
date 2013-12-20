<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<%@include file="/component/head.jsp" %>
<body class="easyui-layout" style="border:none;padding: 1px;margin: 1px;width: 100%;height: 100%;">
<div data-options="region:'north'" style="height:50px;width: 100%;">
    <input type="hidden" id="roleId">
    <table>
        <tr>
            <td>
                <s:hidden name="role.id" id="role_id"/>
                ${role.zh_name}
            </td>
        </tr>
    </table>
</div>
<div data-options="region:'center'" style="width: 100%;height: 100%;">
    <ul id="menuTree" class="easyui-tree"></ul>
    <input type="button" value="提交" onclick="saveMenu()">
</div>

<script type="text/javascript">
    var ids = "";
    $(document).ready(function () {
        loadTree();
    });

    function loadTree() {
        $('.easyui-tree').tree({
            url: 'resource!queryMenuTree.do?role.id=' + $("#role_id").val(),
            animate: true,
            checkbox: true,
            dnd: true,
            lines: true,
            cache: false,
            onClick: function (node) {
                //addTab($.trim(node.text), node.attributes['url']);
            },
            onCheck: function (node, checked) {

            },
            onLoadSuccess: function (node, data) {
                var nodesChecked = $('#menuTree').tree('getChecked');
                //var nodes = $('#menuTree').tree('getChecked', ['checked', 'indeterminate']);
                var nodesIndeterminate = $('#menuTree').tree('getChecked', 'indeterminate');
                var nodes = nodesChecked.concat(nodesIndeterminate);
                //alert(nodes.length);
                ids = obtainIds(nodes);
            }
        });
    }

    function saveMenu() {
        //var newNodes = $('.easyui-tree').tree('getChecked', ['checked', 'indeterminate']);
        var nodesChecked = $('#menuTree').tree('getChecked', 'checked');
        //var nodes = $('#menuTree').tree('getChecked', ['checked', 'indeterminate']);
        var nodesIndeterminate = $('#menuTree').tree('getChecked', 'indeterminate');
        var newNodes = nodesChecked.concat(nodesIndeterminate);
        //alert(newNodes.length);
        //var newIds = obtainIds(newNodes);
        var addIds = "";
        var delIds = ids;
        for (var i = 0; i < newNodes.length; i++) {
            if (ids.indexOf("," + newNodes[i].id + ",") == -1) {
                addIds = addIds + newNodes[i].id + ",";
            }
            else {
                delIds = delIds.replace("," + newNodes[i].id + ",", ",");
            }
        }
        //alert(ids);
        if (addIds == "" && delIds == "") {
            window.parent.closePopForm('treeMenuFrame');
        }
        var url = "resource!updateMenuTree.do?random=" + Math.random();
        $.ajax({
            type: "POST",
            url: url,
            async: false,
            data: {"addIds": addIds, "delIds": delIds, "role.id": $("#role_id").val()}
        });
        window.parent.closePopForm('treeMenuFrame');
    }

    function obtainIds(nodes) {
        var s = ',';
        var ids = [];
        for (var i = 0; i < nodes.length; i++) {
            s += nodes[i].id + ',';
            ids.push(nodes[i].id);
        }
        return s;
    }
</script>
</body>
</html>