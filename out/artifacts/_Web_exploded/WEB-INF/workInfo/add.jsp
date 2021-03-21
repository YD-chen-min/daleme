<%--
  Created by IntelliJ IDEA.
  User: YanDan
  Date: 2020/11/22
  Time: 15:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>添加员工</title>
    <link rel="stylesheet" type="text/css" href="/static/jquery-easyui-1.7.0/themes/bootstrap/easyui.css">
    <link rel="stylesheet" type="text/css" href="/static/jquery-easyui-1.7.0/themes/icon.css">
    <script src="/static/jquery-1.9.1.min.js"></script>
    <script src="/static/jquery-easyui-1.7.0/jquery.easyui.min.js"></script>
</head>
<body>
<form id="workAddForm" method="post">
    <div  style="padding: 20px 0px 0px 50px">
        <label for="name">姓名:</label>
        <input class="easyui-validatebox" type="text" name="name" id="name" data-options="required:true" />
        <label for="departmentId">部门</label>
        <select id="departmentId" class="easyui-combobox" name="departmentId" style="width: 200px;">
            <option value="">请选择</option>
        </select>
        <label for="roleId">权限</label>
        <input  id="roleId" class="easyui-radiobutton" name="roleId" value="0" label="普通员工"  labelPosition="after" >
        <input class="easyui-radiobutton" name="roleId" value="1" label="管理员" labelPosition="after" >
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
        $('#workAddForm').form({
            url:'/user/add',
            onSubmit:function (param) {
                param.password="888888";
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
