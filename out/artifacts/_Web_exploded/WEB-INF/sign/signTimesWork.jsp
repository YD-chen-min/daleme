<%--
  Created by IntelliJ IDEA.
  User: YanDan
  Date: 2020/11/22
  Time: 15:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>员工打卡次数统计</title>
    <link rel="stylesheet" type="text/css" href="/static/jquery-easyui-1.7.0/themes/bootstrap/easyui.css">
    <link rel="stylesheet" type="text/css" href="/static/jquery-easyui-1.7.0/themes/icon.css">
    <script src="/static/jquery-1.9.1.min.js"></script>
    <script src="/static/jquery-easyui-1.7.0/jquery.easyui.min.js"></script>
</head>
<body>
<table align="center">
    <td>部门</td>
    <td>
        <select id="departmentId">
            <option value="">请选择</option>
        </select>
    </td>
    <td><button onclick="search()">查找</button></td>
</table>
<table  id="signTimesWorkTable" class="easyui-datagrid" style="width:400px;height:250px">
</table>
<script>
    $(function () {
        $.get('/department/getlist',{},function (data) {
            var departmentList=data.data;
            var html="";
            for (var i=0;i<departmentList.length;i++){
                html+="<option value='"+departmentList[i].id+"'>"+departmentList[i].name+"</option>";
            }
            $('#departmentId').append(html);
        },'json');
        $('#signTimesWorkTable').datagrid({
            url: '/counts/page/all',
            contentType:'charset=utf-8',
            pagination:true,
            pagePosition: 'top',
            fitColumns:true,
            fit:true,
            striped:true,
            selectOnCheck:false,
            singleSelect:true,
            columns:[[
                {field:'workName',title:'员工',width:100},
                {field:'departmentName',title:'部门',width:100},
                {field:'signInTimes',title:'累计签到次数',width:100},
                {field:'noSignTimes',title:'累计缺勤次数',width:100},
                {field: 'lateTimes',title: '累计迟到次数',width: 100},
            ]]
        });
    });
    function search() {
        $('#signTimesWorkTable').datagrid({
            url: '/counts/page/all',
            queryParams:{departmentId:$('#departmentId').val()},
            contentType:'charset=utf-8',
            pagination:true,
            pagePosition: 'top',
            fitColumns:true,
            fit:true,
            striped:true,
            selectOnCheck:false,
            singleSelect:true,
            columns:[[
                {field:'workName',title:'员工',width:100},
                {field:'departmentName',title:'部门',width:100},
                {field:'signInTimes',title:'累计签到次数',width:100},
                {field:'noSignTimes',title:'累计缺勤次数',width:100},
                {field: 'lateTimes',title: '累计迟到次数',width: 100},
            ]]
        });
    }
</script>
</body>
</html>
