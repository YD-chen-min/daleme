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
    <title>打卡地址管理</title>
    <link rel="stylesheet" type="text/css" href="/static/jquery-easyui-1.7.0/themes/bootstrap/easyui.css">
    <link rel="stylesheet" type="text/css" href="/static/jquery-easyui-1.7.0/themes/icon.css">
    <script src="/static/jquery-1.9.1.min.js"></script>
    <script src="/static/jquery-easyui-1.7.0/jquery.easyui.min.js"></script>
</head>
<body>
<table  id="locationTable" class="easyui-datagrid" style="width:400px;height:250px">
</table>
<div id="locationToolBar">
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit-yandan' ,plain:true"  onclick="update()"></a>
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove-yandan', plain:true" onclick="deleteRows()"></a>
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add-yandan', plain:true" onclick="insert()"></a>
</div>
<div id="locationDialog"></div>
<script>
    $(function () {
        reload();
    });
    function reload() {
        $('#locationTable').datagrid({
            url: '/location/getPage',
            toolbar:'#locationToolBar',
            contentType:'charset=utf-8',
            pagination:true,
            pagePosition: 'top',
            fitColumns:true,
            fit:true,
            striped:true,
            selectOnCheck:false,
            singleSelect:true,
            columns:[[
                {field:'locationId',title:'序号',width:100},
                {field:'locationName',title:'方案名称',width:100},
                {field:'locationInfo',title:'地址详细信息',width:100},
            ]]
        });
    }
    function insert() {
        $('#locationDialog').dialog({
            title:'添加位置信息',
            href:'add.jsp',
            width: 300,
            height:200,
            resizable:true,
            close:true
        });
        $('#locationDialog').dialog('open');
    }
    function update() {
        if($('#locationTable').datagrid('getSelected')==null){
            $.messager.alert('警告','请选择一条数据','warning');
        }else {
            $('#locationDialog').dialog({
                title:'修改位置信息',
                href:'update.jsp',
                width: 300,
                height:200,
                resizable:true,
                close:true
            });
            $('#locationDialog').datagrid('open');
            $.get('/location/get',{name:$('#locationTable').datagrid('getSelected').locationName},function (data) {
                $('#locationUpdateForm').form('load',data.data);
            },'json');
        }
    }
    function deleteRows() {
        if($('#locationTable').datagrid('getChecked').length>0){
            var rows= $('#locationTable').datagrid('getChecked');
            var names='';
            for(var i=0;i<rows.length;i++){
                var location=rows[i];
                names+=','+location.locationName;
            }
            names=names.substring(1,ids.length);
            $.get('/location/delete',{
                name:names
            },function (data) {
                $.messager.alert('message',data.msg,'info');
            },'json');
            reload();
        }
    }
</script>
</body>
</html>
