<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>管理员登录</title>
    <link rel="stylesheet" href="/static/css/font-awesome.min.css">
    <link rel="stylesheet" href="/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="/static/css/style.css">
    <link rel="stylesheet" href="/static/js/sweetAlert/sweetalert.css">
</head>
<body>
<div class="header-bar">
    <div class="container">
        <a href="/home" class="brand">
            <i class="fa fa-reddit-alien"></i>
        </a>
    </div>
</div>
<!--header-bar end-->
<div class="container">
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-sign-in"></i> 管理员登录</span>
        </div>

        <form action="" class="form-horizontal" id="loginForm">

            <c:if test="${not empty requestScope.message}">
                <div class="alert alert-success">
                        ${requestScope.message}
                </div>
            </c:if>

            <div class="control-group">
                <label class="control-label">账号</label>
                <div class="controls">
                    <input type="text" name="adminname">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">密码</label>
                <div class="controls">
                    <input type="password" name="password">
                </div>
            </div>

            <div class="form-actions">
                <button class="btn btn-primary" id="loginBtn">登录</button>
            </div>

        </form>

    </div>
    <!--box end-->
</div>
<!--container end-->

<script src="/static/js/jquery-1.11.1.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script src="/static/js/sweetAlert/sweetalert.min.js"></script>
<script src="/static/js/admin/login.js"></script>

</body>
</html>

