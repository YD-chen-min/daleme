<%--
  Created by IntelliJ IDEA.
  User: YanDan
  Date: 2020/11/22
  Time: 21:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改收集</title>
</head>
<body>
<form id="collectInfoUpdateForm" method="post">
    <div  style="padding: 20px 0px 0px 50px">
        <label for="name">收集名称:</label>
        <input class="easyui-validatebox" type="text" name="name" id="name" data-options="required:true" />
        <label for="locationId">打卡限制地点</label>
        <select id="locationId" class="easyui-combobox" name="departmentId" style="width: 200px;">
            <option value="">无限制</option>
        </select>
        <label for="workId">指定被收集员工(编号)</label>
        <input class="easyui-validatebox" type="text" name="workId" id="workId" data-options="required:false" />
        <label for="departmentId">指定被收集部门</label>
        <select id="departmentId" class="easyui-combobox" name="departmentId" style="width: 200px;">
            <option value="">无限制</option>
        </select>
        <label for="startDate">开始日期</label>
        <input class="easyui-validatebox" type="text" name="startDate" id="startDate" data-options="required:true" />
        <label for="startTime">开始时间</label>
        <input class="easyui-validatebox" type="text" name="startTime" id="startTime" data-options="required:true" />
        <label for="endDate">截止日期</label>
        <input class="easyui-validatebox" type="text" name="endDate" id="endDate" data-options="required:true" />
        <label for="endTime">截止时间</label>
        <input class="easyui-validatebox" type="text" name="endTime" id="endTime" data-options="required:true" />
        <label for="ifIcon">是否为拍照打卡</label>
        <input  id="ifIcon" class="easyui-radiobutton" name="ifIcon" value="0" label="否"  labelPosition="after" >
        <input class="easyui-radiobutton" name="ifIcon" value="1" label="是" labelPosition="after" >
        <label for="dayTimes">每天收集次数:</label>
        <input class="easyui-validatebox" type="number" name="dayTimes" id="dayTimes" data-options="required:true" />
        <label for="health">信息选择:</label>
        <input class="easyui-validatebox" type="text" name="health" id="health" data-options="required:true" />
    </div>
    <div align="center"><button>添加</button></div>
</form>
<script>
    $(function () {
        $.get('/location/getList',{},function (data) {
            var list=data.data;
            var html="";
            for (var i=0;i<list.length;i++){
                html+="<option value='"+list[i].locationId+"'>"+list[i].locationName+"</option>";
            }
            $('#locationId').append(html);
        },'json');
        $.get('/department/getlist',{},function (data) {
            var departmentList=data.data;
            var html="";
            for (var i=0;i<departmentList.length;i++){
                html+="<option value='"+departmentList[i].id+"'>"+departmentList[i].name+"</option>";
            }
            $('#departmentId').append(html);
        },'json');
        $('#collectInfoUpdateForm').form({
            url:'/task/modify',
            queryParams:{oldName:<%= request.getParameter("oldName")%>},
            success:function (data) {
                data=JSON.parse(data);
                $.messager.alert('信息',data.msg,'info');
            }
        });
    });
</script>
</body>
</html>
