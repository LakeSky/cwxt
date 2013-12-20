<%@ page language="java" pageEncoding="UTF-8" %>
<html>
<%@ include file="component/head.jsp" %>
<body class="easyui-layout">
<div data-options="region:'north'" style="height:50px">
    <%--<c:url value="/j_spring_security_logout" var="logoutUrl"/>--%>
    <a href="${pageContext.request.contextPath}/j_spring_security_logout">Log Out</a>
</div>
<div data-options="region:'south',split:true" style="height:50px;"></div>
<div data-options="region:'west',split:true" title="菜单" style="width:180px;">
    <ul class="easyui-tree"></ul>
</div>
<div data-options="region:'center'">
    <div id="tabs" class="easyui-tabs" data-options="fit:true,border:false,plain:true">
        <div title="首页" data-options="href:'home.do'" style="padding:10px"></div>
    </div>
</div>
</body>
</html>
<script type="text/javascript">

    $(document).ready(function () {
        $('.easyui-tree').tree({
            url: 'resource!queryMenuList.do?r=' + Math.random(),
            animate: true,
            onClick: function (node) {
                addTab($.trim(node.text), node.attributes['url']);
            }
        });
        intervalConnect();
    });

    function addTab(title, href, icon) {
        var tt = $('#tabs');
        if (tt.tabs('exists', title)) {//如果tab已经存在,则选中并刷新该tab
            tt.tabs('select', title);
            refreshTab({tabTitle: title, url: href});
        } else {
            if (href) {
                var content = '<iframe scrolling="no" frameborder="0"  src="' + href
                        + '" style="width:100%;height:100%;"></iframe>';
            } else {
//                var content = '未实现';
                return;
            }
            tt.tabs('add', {
                title: title,
                closable: true,
                content: content
            });
        }
    }
    /**
     *example: {tabTitle:'tabTitle',url:'refreshUrl'}
     *如果tabTitle为空，则默认刷新当前选中的tab
     *如果url为空，则默认以原来的url进行reload
     */
    function refreshTab(cfg) {
        var refresh_tab = cfg.tabTitle ? $('#tabs').tabs('getTab', cfg.tabTitle) : $('#tabs').tabs('getSelected');
        if (refresh_tab && refresh_tab.find('iframe').length > 0) {
            var _refresh_ifram = refresh_tab.find('iframe')[0];
            var refresh_url = cfg.url ? cfg.url : _refresh_ifram.src;
            //_refresh_ifram.src = refresh_url;
            _refresh_ifram.contentWindow.location.href = refresh_url;
        }
    }

    //如果用户没有关闭该页面就，则定时发送请求，维护连接
    function intervalConnect() {
        $.get("component/connect.html?ran=" + Math.random());
        setTimeout('intervalConnect()', 1000 * 5 * 60);
    }

</script>