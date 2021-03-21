<%--
  Created by IntelliJ IDEA.
  User: YanDan
  Date: 2020/11/22
  Time: 15:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改用户基本数据</title>
    <link rel="stylesheet" type="text/css" href="/static/jquery-easyui-1.7.0/themes/bootstrap/easyui.css">
    <link rel="stylesheet" type="text/css" href="/static/jquery-easyui-1.7.0/themes/icon.css">
    <script src="/static/jquery-1.9.1.min.js"></script>
    <script src="/static/jquery-easyui-1.7.0/jquery.easyui.min.js"></script>
</head>
<body>
<form id="workUpdateForm" method="post">
    <div  style="padding: 20px 0px 0px 50px">
        <label for="name">姓名:</label>
        <input class="easyui-validatebox" type="text" name="name" id="name" data-options="required:true" readonly="readonly"/>
        <label for="departmentId">部门</label>
        <select id="departmentId" class="easyui-combobox" name="departmentId"  style="width: 200px;">
            <option value="" selected="selected">不变</option>
        </select>
        <label for="password">权限</label>
        <input  id="password"  class="easyui-radiobutton" name="password" value="0" label="不变"  labelPosition="after" >
        <input class="easyui-radiobutton" name="password" value="1" label="重置" labelPosition="after" >
    </div>
    <div align="center"><button>修改</button></div>
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
        $('#workUpdateForm').form({
            url:'/user/update',
            onSubmit:function (param) {
                if ($('#password').val()==1||$('#password').val()=="1"){
                    param.password="888888";
                }
            },
            success:function (data) {
                data=JSON.parse(data);
                $.messager.alert('信息',data.msg,'info');
            }
        });
    });
</script>
</body>
</html>
