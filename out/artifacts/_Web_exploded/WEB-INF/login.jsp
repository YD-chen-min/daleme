<%--
  Created by IntelliJ IDEA.
  User: YanDan
  Date: 2020/5/8
  Time: 10:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>登录</title>
    <link type="text/css" href="../static/css/style.css" rel="stylesheet" />
    <link rel="stylesheet" type="/text/css" href="../static/jquery-easyui-1.7.0/themes/bootstrap/easyui.css">
    <link rel="stylesheet" type="text/css" href="../static/jquery-easyui-1.7.0/themes/icon.css">
    <script src="/static/jquery-1.9.1.min.js"></script>
    <script src="/static/jquery-easyui-1.7.0/jquery.easyui.min.js"></script>
</head>
<body>
<div class="form-wrap">
    <form id="userForm" action="<%=request.getContextPath()%>/web/login" method="post" >
        <div class="input-wrap">
            <input type="text" id="id" name="id">
            <label for="id">账号</label>
        </div>
        <div class="input-wrap">
            <input type="password" id="password" name="password">
            <label for="password">登录密码</label>
        </div>
        <div class="input-wrap">
            <button >登录</button>
        </div>
    </form>

</div>


<script>

    $(document).ready(function(){
        $('.form-wrap').finput();
    });

</script>
</body>
</html>
