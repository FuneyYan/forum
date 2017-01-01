<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>用户管理</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/js/sweetAlert/sweetalert.css">
</head>
<body>
<%@ include file="../include/adminNavBar.jsp"%>
<!--header-bar end-->
<div class="container-fluid" style="margin-top:20px">
    <table class="table">
        <thead>
        <tr>
            <th>账号</th>
            <th>注册时间</th>
            <th>最后登录时间</th>
            <th>最后登录IP</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>

        <c:forEach items="${userList.items}" var="uservo">
            <tr>
                <td>${uservo.username}</td>
                <td>${uservo.createtime}</td>
                <td>${uservo.logintime}</td>
                <td>${uservo.ip}</td>
                <td>
                    <a href="javascript:;" class="update" onClick="update(${uservo.id},${uservo.state})"
                       rel="${uservo.state},${uservo.id}">${uservo.state == '1'?'禁用':'恢复'}</a>
                </td>
            </tr>
        </c:forEach>


        </tbody>
    </table>
    <div class="pagination pagination-mini pagination-centered">
        <ul id="pagination" style="margin-bottom:20px;"></ul>
    </div>

</div>
<!--container end-->
<script src="/static/js/jquery-1.11.1.js"></script>
<script src="/static/js/sweetAlert/sweetalert.min.js"></script>
<script src="/static/js/jquery.twbsPagination.min.js"></script>
<script>
    $(function () {
        $("#pagination").twbsPagination({
            totalPages:${userList.totalPage},
            visiblePages: 5,
            first: '首页',
            last: '末页',
            prev: '上一页',
            next: '下一页',
            href: '?p={{number}}'
        });


    });

    function update(userid,userstate) {
        $.post("/admin/user",{"userid":userid,"userstate":userstate},function (data) {
            if(data.state='success'){
                swal({title:"修改成功"},function () {
                    window.history.go(0);
                });
            }else{
                swal(data.data);
            }
        });

    }


</script>
</body>
</html>
