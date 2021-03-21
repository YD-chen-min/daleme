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
    <title>员工信息管理</title>
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
<table  id="workInfoTable" class="easyui-datagrid" style="width:400px;height:250px">

</table>
<div id="workInfoToolBar">
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit-yandan' ,plain:true"  onclick="update()"></a>
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove-yandan', plain:true" onclick="deleteRows()"></a>
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add-yandan', plain:true" onclick="insert()"></a>
</div>
<div id="workInfoDialog"></div>
</body>
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
        $('#workInfoTable').datagrid({
            url: '/user/getPage',
            toolbar:'#workInfoToolBar',
            contentType:'charset=utf-8',
            pagination:true,
            pagePosition: 'top',
            fitColumns:true,
            fit:true,
            striped:true,
            selectOnCheck:false,
            singleSelect:true,
            columns:[[
                {field:'workId',title:'工号',width:100},
                {field:'name',title:'姓名',width:100},
                {field:'departmentName',title:'部门',width:100},
                {field: 'phoneNumber',title: '手机',width: 100}
            ]]
        });
    });
    function reload() {
        $('#workInfoTable').datagrid({
            url: '/user/getPage',
            queryParams:{departmentId:$('#departmentId').val()},
            toolbar:'#workInfoToolBar',
            contentType:'charset=utf-8',
            pagination:true,
            pagePosition: 'top',
            fitColumns:true,
            fit:true,
            striped:true,
            selectOnCheck:false,
            singleSelect:true,
            columns:[[
                {field:'workId',title:'工号',width:100},
                {field:'name',title:'姓名',width:100},
                {field:'departmentName',title:'部门',width:100},
                {field: 'phoneNumber',title: '手机',width: 100}
            ]]
        });
    }
    function search() {
        reload();
    }
    function insert() {
        $('#workInfoDialog').dialog({
            title:'添加员工信息',
            href:'add.jsp',
            width: 300,
            height:200,
            resizable:true,
            close:true
        });
        $('#workInfoDialog').dialog('open');
    }
    function update() {
        if($('#workInfoTable').datagrid('getSelected')==null){
            $.messager.alert('警告','请选择一条数据','warning');
        }else {
            $('#workInfoDialog').dialog({
                title:'修改员工信息',
                href:'update.jsp',
                width: 300,
                height:200,
                resizable:true,
                close:true
            });
            $('#workInfoDialog').datagrid('open');
            $.get('/user/get',{departmentId:$('#workInfoTable').datagrid('getSelected').workId},function (data) {
                    $('#workUpdateForm').form('load',data.data);
            },'json');
        }
    }
    function deleteRows() {
        if($('#workInfoTable').datagrid('getChecked').length>0){
            var rows= $('#workInfoTable').datagrid('getChecked');
            var ids='';
            for(var i=0;i<rows.length;i++){
                var workInfo=rows[i];
                ids+=','+workInfo.workId;
            }
            ids=ids.substring(1,ids.length);
            $.get('/user/delete',{
                workIds:ids
            },function (data) {
                $.messager.alert('message',data.msg,'info');
            },'json');
            reload();
        }
    }
</script>
</html>
