<%--
  Created by IntelliJ IDEA.
  User: YanDan
  Date: 2020/11/22
  Time: 21:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改打卡限制地点</title>
    <link rel="stylesheet" type="text/css" href="/static/jquery-easyui-1.7.0/themes/bootstrap/easyui.css">
    <link rel="stylesheet" type="text/css" href="/static/jquery-easyui-1.7.0/themes/icon.css">
    <script src="/static/jquery-1.9.1.min.js"></script>
    <script src="/static/jquery-easyui-1.7.0/jquery.easyui.min.js"></script>
</head>
<body>
<button onclick="openBMap()">打开地图</button>
<button onclick="autoIn()">自动填入地址详细</button>
<form id="locationUpdateForm" method="post">
    <div  style="padding: 20px 0px 0px 50px">
        <label for="locationId">方案序号:</label>
        <input class="easyui-validatebox" type="text" name="locationId" id="locationId" data-options="required:true;"readonly="readonly" />
        <label for="locationName">方案名称:</label>
        <input class="easyui-validatebox" type="text" name="locationName" id="locationName" data-options="required:true" />
        <label for="locationInfo">详细信息</label>
        <input class="easyui-validatebox" type="text" name="locationInfo" id="locationInfo" data-options="required:true" />
    </div>
    <div align="center"><button>添加</button></div>
</form>
<script>
    $(function () {
        $('#locationUpdateForm').form({
            url:'/location/modify',
            success:function (data) {
                data=JSON.parse(data);
                $.messager.alert('信息',data.msg,'info');
                reload();
            }
        });
    });
    function openBMap() {
        $('#locationDialog').dialog({
            title:'选择地图',
            href:'chose.jsp',
            width: 500,
            height:500,
            resizable:true,
            close:true
        });
        $('#locationDialog').dialog('open');
    }
    function autoIn() {
        var indexStr=sessionStorage.getItem("index");
        $('#locationInfo').val(indexStr);
    }
</script>
</body>
</html>
