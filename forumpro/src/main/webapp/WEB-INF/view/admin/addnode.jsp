<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>添加新节点</title>
    <link rel="stylesheet" href="/static/css/font-awesome.min.css">
    <link rel="stylesheet" href="/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="/static/js/sweetAlert/sweetalert.css">
</head>
<body>
<%@ include file="../include/adminNavBar.jsp"%>
<!--header-bar end-->
<div class="container-fluid" style="margin-top:20px">
    <form action="" id="addNodeForm">
        <legend>添加新节点</legend>
        <label>节点名称</label>
        <input type="text" name="nodename" id="nodename">
        <div class="form-actions">
            <button class="btn btn-primary" type="button" id="addNodeBtn">保存</button>
        </div>
    </form>
</div>
<!--container end-->
<script src="/static/js/jquery-1.11.1.js"></script>
<script src="/static/js/sweetAlert/sweetalert.min.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script>
    $(function () {
        var nodename="";
       $("#addNodeBtn").click(function () {
           nodename=$("#nodename");
           $("#addNodeForm").submit();
       }) ;
        $("#addNodeForm").validate({
            errorElement:"span",
            errorClass:"text-error",
            rules:{
                nodename:{
                    required:true,
                    remote:"/admin/nodeValidate"
                }
            },
            messages:{
                nodename:{
                    required:"字节名不能为空",
                    remote:"字节名已经存在"
                }
            },
            submitHandler:function () {
                $.ajax({
                   url:"/admin/addNode",
                    type:"post",
                    data:$("#addNodeForm").serialize(),
                    beforeSend:function () {
                        $("#addNodeBtn").text("添加中...").attr("disabled","disabled");
                    },
                    success:function () {
                        $("#nodename").val("");
                        swal("添加节点成功");
                    },
                    error:function () {
                        swal("服务器异常");
                    },
                    complete:function () {
                        $("#addNodeBtn").text("添加节点").removeAttr("disabled");
                    }
                })
            }
        });
    });
</script>
</body>
</html>
