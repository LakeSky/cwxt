<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<html>
<head>
    <title>增删改查范例</title>
    <meta HTTP-EQUIV="pragma" CONTENT="no-cache">
    <meta HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
    <meta HTTP-EQUIV="expires" CONTENT="0">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/js/jquery-easyui-1.3.2/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/js/jquery-easyui-1.3.2/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/js/jquery-easyui-1.3.2/demo/demo.css">
    <script type="text/javascript" src="<%=basePath%>/js/jquery-easyui-1.3.2/jquery-1.8.0.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/jquery-easyui-1.3.2/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/jquery-easyui-1.3.2/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript">

    </script>
</head>
<body>
<div style="margin:10px 0;"></div>

<div id="tb" style="padding:5px;height:auto">
    <div style="margin-bottom:5px">
        <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="javascript:$('#dg').editagrid('addRow')"></a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true"></a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true"></a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-cut" plain="true"></a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true"></a>
    </div>
    <div>
        Date From: <input class="easyui-datebox" style="width:80px">
        To: <input class="easyui-datebox" style="width:80px">
        Language:
        <select class="easyui-combobox" panelHeight="auto" style="width:100px">
            <option value="java">Java</option>
            <option value="c">C</option>
            <option value="basic">Basic</option>
            <option value="perl">Perl</option>
            <option value="python">Python</option>
        </select>
        <a href="#" class="easyui-linkbutton" iconCls="icon-search">Search</a>
    </div>
</div>
<%=request.getAttribute("message")%>
<table id="dg" class="easyui-datagrid" title="联系人增删改查范例" data-options="
				rownumbers:true,
				singleSelect:true,
				autoRowHeight:false,
				pagination:true,
				pageSize:10,
				<%--fit:true,--%>
                toolbar:'#tb'">
    <thead>
    <tr>
        <th data-options="field:'ck',checkbox:true"></th>
        <th field="name" width="80">name</th>
        <th field="age" width="100">age</th>
        <th field="phone" width="80">phone</th>
    </tr>
    </thead>

</table>
<script>
    function getDataFromServer() {
        var htmlobj = $.ajax({url: "phone-book!generatePhoneBookList.do", async: false});
        var rows = eval(htmlobj.responseText);
        return rows;
    }
    function getDataUseGetMethod() {
        var htmlobj = $.ajax({url: "phone-book!generatePhoneBookList.do", async: true});
        var rows = eval(htmlobj.responseText);
        alert(rows);
        return rows;
    }
    $(function () {
        $.get("phone-book!queryPhoneBookList.do?random=" + Math.random(),
                function (data, status) {
//                    alert(data);
                    $('#dg').datagrid({loadFilter: pagerFilter}).datagrid('loadData', eval(data));
                    addCustomButton();
                }
        );
    });
    function addCustomButton() {
        var pager = $('#dg').datagrid('getPager');	// get the pager of datagrid
        pager.pagination({
            buttons: [
                {
                    iconCls: 'icon-search',
                    handler: function () {
                        alert('search');
                    }
                },
                {
                    iconCls: 'icon-add',
                    handler: function () {
                        alert('add');
                    }
                },
                {
                    iconCls: 'icon-edit',
                    handler: function () {
                        alert('edit');
                    }
                }
            ]
        });
    }

    //参考代码
    $(document).ready(function () {
        /*$("#b01").click(function () {
         var htmlobj = $.ajax({
         url: "phone-book!generatePhoneBookList.do",
         success: function (result, state, res) {
         alert(result);
         alert(state)
         alert(res.responseText);
         }
         });
         $("#myDiv").html(htmlobj.responseText);
         });
         */
        /*$(window).resize(function () {
         //            alert($('#dg').parent().width());
         //            alert(document.body.clientHeight);
         var datagrid = $('#dg');
         alert(datagrid.parent().css('width'));

         $('#dg').datagrid('resize', {
         width: datagrid.parent().width(),
         height: 'auto'
         });
         */
        /*$('#dg').datagrid('resize', {
         width: function () {
         return parent.body.width();// document.body.clientWidth;
         },
         height: function () {
         return parent.body.height();// document.body.clientHeight;
         }
         });*/
        /*
         });*/
    });

    //分页代码
    // 注意：(分页代码放在head里面导致数据无法显示，这个可能是因为tab加载的时候只加载了body里的内容，head里面的内容不会加载,为了单页调试的时候方便，我们仍然写好了head)
    function pagerFilter(data) {
        if (typeof data.length == 'number' && typeof data.splice == 'function') {    // is array
            data = {
                total: data.length,
                rows: data
            }
        }
        var dg = $(this);
        var opts = dg.datagrid('options');
        var pager = dg.datagrid('getPager');
        pager.pagination({
            onSelectPage: function (pageNum, pageSize) {
                opts.pageNumber = pageNum;
                opts.pageSize = pageSize;
                pager.pagination('refresh', {
                    pageNumber: pageNum,
                    pageSize: pageSize
                });
                dg.datagrid('loadData', data);
            }
        });
        if (!data.originalRows) {
            data.originalRows = (data.rows);
        }
        var start = (opts.pageNumber - 1) * parseInt(opts.pageSize);
        var end = start + parseInt(opts.pageSize);
        data.rows = (data.originalRows.slice(start, end));
        return data;
    }

</script>

</body>
</html>

