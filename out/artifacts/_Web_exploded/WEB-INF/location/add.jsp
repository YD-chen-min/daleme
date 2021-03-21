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
    <title>添加打卡限制地点</title>
</head>
<body>
<button onclick="openBMap()">打开地图</button>
<button onclick="autoIn()">自动填入地址详细</button>
<form id="locationAddForm" method="post">
    <div  style="padding: 20px 0px 0px 50px">
        <label for="locationName">方案名称:</label>
        <input class="easyui-validatebox" type="text" name="locationName" id="locationName" data-options="required:true" />
        <label for="locationInfo">详细信息</label>
        <input class="easyui-validatebox" type="text" name="locationInfo" id="locationInfo" data-options="required:true" />
    </div>
    <div align="center"><button>添加</button></div>
</form>
<div id="mapDialog"></div>
<script>
    $(function () {
        $('#locationAddForm').form({
            url:'/location/add',
            success:function (data) {
                data=JSON.parse(data);
                $.messager.alert('信息',data.msg,'info');
                reload();
            }
        });
    });
    function openBMap() {
        $('#mapDialog').dialog({
            title:'选择地图',
            href:'chose.jsp',
            width: 500,
            height:500,
            resizable:true,
            close:true
        });
        $('#mapDialog').dialog('open');
    }
    function autoIn() {
        var indexStr=sessionStorage.getItem("index");
        $('#locationInfo').val(indexStr);
    }
</script>
</body>
</html>
