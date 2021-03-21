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
    <title>公告管理</title>
    <link rel="stylesheet" type="text/css" href="/static/jquery-easyui-1.7.0/themes/bootstrap/easyui.css">
    <link rel="stylesheet" type="text/css" href="/static/jquery-easyui-1.7.0/themes/icon.css">
    <script src="/static/jquery-1.9.1.min.js"></script>
    <script src="/static/jquery-easyui-1.7.0/jquery.easyui.min.js"></script>
</head>
<body>
<table  id="noticeTable" class="easyui-datagrid" style="width:400px;height:250px">
</table>
<div id="noticeToolBar">
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove-yandan', plain:true" onclick="deleteRows()"></a>
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add-yandan', plain:true" onclick="insert()"></a>
</div>
<div id="noticeDialog"></div>
<script>
    $(function () {
        reload();
    });
    function reload() {
        $('#noticeTable').datagrid({
            url: '/notice/getPage',
            toolbar:'#noticeToolBar',
            contentType:'charset=utf-8',
            pagination:true,
            pagePosition: 'top',
            fitColumns:true,
            fit:true,
            striped:true,
            selectOnCheck:false,
            singleSelect:true,
            columns:[[
                {field:'noticeId',title:'序号',width:100},
                {field:'title',title:'标题',width:100},
                {field:'departmentName',title:'发布部门',width:100},
                {field: 'type',title: '公告类型',width: 100},
                {field: 'date',title: '发布日期',width: 100},
                {field: 'noticeContents',title: '内容',width: 100},

            ]]
        });
    }
    function deleteRows() {
        if($('#noticeTable').datagrid('getChecked').length>0){
            var rows= $('#noticeTable').datagrid('getChecked');
            var names='';
            for(var i=0;i<rows.length;i++){
                var notice=rows[i];
                names+=','+notice.title;
            }
            names=names.substring(1,ids.length);
            $.get('/user/delete',{
                name:names
            },function (data) {
                $.messager.alert('message',data.msg,'info');
            },'json');
            reload();
        }
    }
    function insert() {
        $('#noticeDialog').dialog({
            title:'添加公告信息',
            href:'add.jsp',
            width: 300,
            height:200,
            resizable:true,
            close:true
        });
        $('#noticeDialog').dialog('open');
    }
</script>
</body>
</html>
