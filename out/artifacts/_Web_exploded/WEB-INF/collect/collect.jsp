<%--
  Created by IntelliJ IDEA.
  User: YanDan
  Date: 2020/11/22
  Time: 21:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>收集管理</title>
    <link rel="stylesheet" type="text/css" href="/static/jquery-easyui-1.7.0/themes/bootstrap/easyui.css">
    <link rel="stylesheet" type="text/css" href="/static/jquery-easyui-1.7.0/themes/icon.css">
    <script src="/static/jquery-1.9.1.min.js"></script>
    <script src="/static/jquery-easyui-1.7.0/jquery.easyui.min.js"></script>
</head>
<body>
<table  id="collectInfoTable" class="easyui-datagrid" style="width:400px;height:250px">

</table>
<div id="collectInfoToolBar">
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit-yandan' ,plain:true"  onclick="update()"></a>
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove-yandan', plain:true" onclick="deleteRows()"></a>
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add-yandan', plain:true" onclick="insert()"></a>
</div>
<div id="collectInfoDialog"></div>
<script>
    $(function () {

    });
    function insert() {
        $('#collectInfoDialog').dialog({
            title:'添加收集',
            href:'add.jsp',
            width: 350,
            height:600,
            resizable:true,
            close:true
        });
        $('#collectInfoDialog').dialog('open');
    }
    function update() {
        if($('#collectInfoTable').datagrid('getSelected')==null){
            $.messager.alert('警告','请选择一条数据','warning');
        }else {
            $('#collectInfoDialog').dialog({
                title:'修改员工信息',
                href:'update.jsp',
                width: 300,
                height:200,
                resizable:true,
                close:true
            });
            $('#collectInfoDialog').datagrid('open');
            $.get('/task/get',{name:$('#collectInfoTable').datagrid('getSelected').collectName},function (data) {
                $('#collectInfoUpdateForm').form('load',data.data);
            },'json');
        }
    }
    function deleteRows() {
        if($('#collectInfoTable').datagrid('getChecked').length>0){
            var rows= $('#collectInfoTable').datagrid('getChecked');
            var names='';
            for(var i=0;i<rows.length;i++){
                var collect=rows[i];
                names+=','+collect.collectName;
            }
            names=names.substring(1,ids.length);
            $.get('/task/delete',{
                name:names
            },function (data) {
                $.messager.alert('message',data.msg,'info');
            },'json');
            reload();
        }
    }
    function reload() {
        $('#collectInfoTable').datagrid({
            url: '/task/getPage',
            toolbar:'#collectInfoToolBar',
            contentType:'charset=utf-8',
            pagination:true,
            pagePosition: 'top',
            fitColumns:true,
            fit:true,
            striped:true,
            selectOnCheck:false,
            singleSelect:true,
            columns:[[
                {field:'collectId',title:'序号',width:100},
                {field:'collectName',title:'名称',width:100},
                {field:'work',title:'被收集对象',width:100},
                {field: 'departmentName',title: '被收集部门',width: 100},
                {field: 'ifIcon',title: '是否拍照打卡',width: 100},
                {field: 'info',title: '信息选择',width: 100},
                {field: 'locationName',title: '打卡限制地点',width: 100},
                {field: 'startDate',title: '开始日期',width: 100},
                {field: 'startTime',title: '开始时间',width: 100},
                {field: 'endDate',title: '结束日期',width: 100},
                {field: 'endTime',title: '结束时间',width: 100},
                {field: 'dayTimes',title: '每天重复次数',width: 100}
            ]]
        });
    }
</script>
</body>
</html>
