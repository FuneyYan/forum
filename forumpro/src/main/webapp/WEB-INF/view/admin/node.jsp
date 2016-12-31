<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>节点管理</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/js/sweetAlert/sweetalert.css">
    <style>
        .mt20 {
            margin-top: 20px;
        }
    </style>
</head>
<body>
<%@include file="../include/adminNavBar.jsp"%>
<!--header-bar end-->
<div class="container-fluid mt20">
    <a href="newnode.html" class="btn btn-success">添加新节点</a>
    <table class="table">
        <thead>
        <tr>
            <th>节点名称</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>

        <c:forEach items="${nodeList}" var="node">

            <tr>
                <td>${node.nodename}</td>
                <td>
                    <a href="/admin/nodeUpdate?nodeid=${node.id}">修改</a>
                    <a href="javascript:;" rel="${node.id}" class="delNode">删除</a>
                </td>
            </tr>

        </c:forEach>

        </tbody>
    </table>
</div>

<script src="/static/js/jquery-1.11.1.js"></script>
<script src="/static/js/sweetAlert/sweetalert.min.js"></script>
<script>
    $(".delNode").click(function () {
        var nodeid=$(this).attr("rel");
        $(function () {
            console.log(nodeid);
            swal({title: "确定要删除该节点?",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "确定",
                closeOnConfirm: false
            },function () {
                $.ajax({
                    url:"/admin/node",
                    type:"post",
                    data:{"nodeid":nodeid},
                    success:function (data) {
                        if(data.state=='success'){
                            swal({title:"删除节点成功!"},function () {
                                window.history.go(0);
                            });
                        }else{
                            swal(data.data);
                        }
                    },
                    error:function () {
                        swal('服务器异常');
                    }
                });
            });
        });
    });
</script>
</body>
</html>