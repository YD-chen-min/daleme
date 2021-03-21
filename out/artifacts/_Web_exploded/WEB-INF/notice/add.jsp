<%--
  Created by IntelliJ IDEA.
  User: YanDan
  Date: 2020/11/22
  Time: 21:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>添加公告</title>
    <link rel="stylesheet" type="text/css" href="/static/jquery-easyui-1.7.0/themes/bootstrap/easyui.css">
    <link rel="stylesheet" type="text/css" href="/static/jquery-easyui-1.7.0/themes/icon.css">
    <script src="/static/jquery-1.9.1.min.js"></script>
    <script src="/static/jquery-easyui-1.7.0/jquery.easyui.min.js"></script>
</head>
<body>
<form id="noticeAddForm" method="post">
    <div  style="padding: 20px 0px 0px 50px">
        <label for="title">标题:</label>
        <input class="easyui-validatebox" type="text" name="title" id="title" data-options="required:true" />
        <label for="departmentId">部门</label>
        <select id="departmentId" class="easyui-combobox" name="departmentId" style="width: 200px;">
            <option value="">请选择</option>
        </select>
        <label for="type">公告类型</label>
        <select id="type" class="easyui-combobox" name="type" style="width: 200px;" >
            <option value="普通公告">普通公告</option>
            <option value="紧急公告">紧急公告</option>
        </select>
        <label for="content">内容:</label>
        <input class="easyui-validatebox" type="text" name="content" id="content" data-options="required:true" />
    </div>
    <div align="center"><button>添加</button></div>
</form>
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
        $('#noticeAddForm').form({
            url:'/notice/add',
            onSubmit:function (param) {
                param.date=new Date().getDate();
            },
            success:function (data) {
                data=JSON.parse(data);
                $.messager.alert('信息',data.msg,'info');
                reload();
            }
        });
    });
</script>
</body>
</html>
