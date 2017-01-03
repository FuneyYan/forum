<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>邮箱激活失败</title>
    <link rel="stylesheet" href="/static/css/font-awesome.min.css">
    <link rel="stylesheet" href="/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="/static/css/style.css">
</head>
<body>
<%@ include file="../include/navbar.jsp"%>
<div class="container">
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-sign-in"></i> 系统提示</span>
        </div>
        <div class="box-padding">
            <h4 style="font-size: 18px">激活失败：${requestScope.message}</h4>
        </div>


    </div>
    <!--box end-->
</div>
<!--container end-->
</body>
</html>