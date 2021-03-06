<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <meta charset="UTF-8">
    <title>找回密码</title>
    <link rel="stylesheet" href="/static/css/font-awesome.min.css">
    <link rel="stylesheet" href="/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="/static/css/style.css">
</head>
<body>
<%@include file="../include/navbar.jsp"%>
<div class="container">
    <div class="box">
        <div class="box-header">
            <span class="title">找回密码</span>
        </div>

        <form action="" class="form-horizontal" id="foundForm">

            <div class="control-group">
                <label class="control-label">选择找回方式</label>
                <div class="controls">
                    <select name="type" id="type">
                        <option value="email">根据电子邮件找回</option>
                        <%--<option value="phone">根据手机号码找回</option>--%>
                    </select>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" id="made">电子邮件</label>
                <div class="controls">
                    <input type="text" name="value" id="value">
                </div>
            </div>
            <div class="form-actions">
                <button class="btn btn-primary" id="foundBtn">提交</button>
            </div>

        </form>



    </div>
    <!--box end-->
</div>
<!--container end-->

<script src="/static/js/jquery-1.11.1.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script src="/static/js/user/foundPassword.js"></script>

</body>
</html>