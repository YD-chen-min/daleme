<%--
  Created by IntelliJ IDEA.
  User: YanDan
  Date: 2020/11/22
  Time: 15:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>今日员工打卡信息</title>
    <link rel="stylesheet" type="text/css" href="/static/jquery-easyui-1.7.0/themes/bootstrap/easyui.css">
    <link rel="stylesheet" type="text/css" href="/static/jquery-easyui-1.7.0/themes/icon.css">
    <script src="/static/jquery-1.9.1.min.js"></script>
    <script src="/static/jquery-easyui-1.7.0/jquery.easyui.min.js"></script>
</head>
<body>
<table align="center">
    <td>日期(格式xxxx-xx-xx)</td>
    <td><input id="date" name="date" type="text" required="required"></td>
    <td>打卡任务</td>
    <td>
        <select id="collectId">
            <option value="">请选择</option>
        </select>
    </td>
    <td><button onclick="search()">查找</button></td>
</table>
<table  id="daySignWorkTable" class="easyui-datagrid" style="width:400px;height:250px">
</table>
<script>
    $(function () {
            $.get('/task/getList',{},function (data) {
                var collectList=data.data;
                var html="";
                for (var i=0;i<collectList.length;i++){
                    html+="<option value='"+collectList[i].collectId+"'>"+collectList[i].collectName+"</option>";
                }
                $('#collectId').append(html);
            },'json')
    });
    function reload() {
        $('#daySignWorkTable').datagrid({
            url: '/sign/page/all',
            queryParams:{
                date:$('#date').val(),
                collectId:$('#collectId').val()
            },
            contentType:'charset=utf-8',
            pagination:true,
            pagePosition: 'top',
            fitColumns:true,
            fit:true,
            striped:true,
            selectOnCheck:false,
            singleSelect:true,
            columns:[[
                {field:'collectName',title:'打卡任务',width:100},
                {field:'collectType',title:'打卡类型',width:100},
                {field:'work',title:'员工',width:100},
                {field: 'departmentName',title: '部门',width: 100},
                {field: 'ifSignIn',title: '是否完成签到',width: 100},
                {field: 'locationName',title: '签到地点',width: 100},
                {field: 'date',title: '签到日期',width: 100},
                {field: 'time',title: '签到时间',width: 100},
                {field: 'info',title: '信息',width: 100},
                {field: 'icon',title: '打卡照片',width: 100}
            ]]
        });
    }
    function search() {
        reload();
    }
</script>
</body>
</html>
