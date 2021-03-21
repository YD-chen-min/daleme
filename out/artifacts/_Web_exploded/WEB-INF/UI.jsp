
<%@ page import="com.yandan.demo.dataobject.AdminInfo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>打了吗后台管理系统</title>
    <link rel="stylesheet" type="text/css" href="/static/jquery-easyui-1.7.0/themes/bootstrap/easyui.css">
    <link rel="stylesheet" type="text/css" href="/static/jquery-easyui-1.7.0/themes/icon.css">
    <script src="/static/jquery-1.9.1.min.js"></script>
    <script src="/static/jquery-easyui-1.7.0/jquery.easyui.min.js"></script>
</head>
<body class="easyui-layout">
<%
   AdminInfo adminInfo=(AdminInfo) request.getSession().getAttribute("adminInfo");
%>
<div data-options="region:'north',title:'North Title',split:true" style="height:100px;">
    <h1>打了吗后台管理系统</h1>
</div>
<div data-options="region:'west',title:'导航栏',split:true" style="width:100px;">
    <ul id="mytree" class="easyui-tree">
        <li>
            <span onclick="openTab('员工信息管理','workInfo/workInfo.jsp')">员工信息管理</span>
        </li>
        <li>
            <span>打卡信息</span>
            <ul>
                <li>
                    <span onclick="openTab('今日员工打卡信息','sign/daySignWork.jsp')">今日员工打卡信息</span>
                </li>
                <li>
                    <span onclick="openTab('部门打卡次数统计','sign/signTimesDepartment.jsp')">部门打卡次数统计</span>
                </li>
                <li>
                    <span onclick="openTab('员工打卡次数统计','sign/signTimesWork.jsp')">员工打卡次数统计</span>
                </li>
            </ul>
        </li>
        <li>
            <span onclick="openTab('公告管理','notice/notice.jsp')">公告管理</span>
        </li>
        <li>
            <span onclick="openTab('打卡地址管理','location/location.jsp')">打卡地址管理</span>
        </li>
        <li>
            <span onclick="openTab('收集管理','collect/collect.jsp')">收集管理</span>
        </li>
    </ul>
</div>
<div data-options="region:'center',title:'center title'" style="padding:5px;background:#eee;">
    <div id="tabs" class="easyui-tabs" style="width:500px;height:250px;" data-options="fit:true">
    </div>
</div>
</body>
<script>
    function openTab(name,path) {
        if($('#tabs').tabs('exists',name)){
            $('#tabs').tabs('select',name);
            var tab=$('#tabs').tabs('getTable',name);
            tab.panel('refresh');
        }else{
            $('#tabs').tabs('add',{
                title:name,
                select:true,
                closeable:true,
                href: path
            });
            $('#tabs').tabs('select',name);
            var tab=$('#tabs').tabs('getTable',name);
            tab.panel('refresh');
        }

    }
</script>
</html>
