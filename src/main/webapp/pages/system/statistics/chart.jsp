<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<%@include file="/component/head.jsp" %>
<body class="easyui-layout" style="border:none;padding: 1px;margin: 1px;">
<div data-options="region:'center'" fit="true" style="border:none;padding: 1px;margin: 1px;">
    <div id="container" style="height: 500px; min-width: 500px"></div>
</div>
<script>
    $(function () {
        $.getJSON('chart!obtainChartData.do?ran=' + Math.random(), function (data) {
            $('#container').highcharts('StockChart', {
                rangeSelector: {
                    selected: 1
                },
                title: {
                    text: '在线用户数统计'
                },
                series: [
                    {
                        name: '在线用户',
                        data: data,
                        tooltip: {
                            valueDecimals: 0
                        }
                    }
                ]
            });
        });
    });
</script>
<script type="text/javascript" src="<%=basePath%>/js/Highcharts-3.0.7/js/highstock.js"></script>
</body>
</html>