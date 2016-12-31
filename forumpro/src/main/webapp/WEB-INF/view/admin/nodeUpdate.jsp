<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>编辑节点</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/js/sweetAlert/sweetalert.css">
</head>
<body>
<%@include file="../include/adminNavBar.jsp"%>
<!--header-bar end-->
<div class="container-fluid" style="margin-top:20px">
    <form action="" id="updateNodeForm">
        <legend>编辑节点</legend>
        <label>节点名称</label>
        <input type="text" id="nodename" name="nodename" value="${requestScope.node.nodename}">
        <input type="hidden" id="nodeid" name="nodeid" value="${node.id}">
        <div class="form-actions">
            <button class="btn btn-primary" id="updateNodeBtn" type="button">保存</button>
        </div>
    </form>
</div>
<!--container end-->
<script src="/static/js/jquery-1.11.1.js"></script>
<script src="/static/js/sweetAlert/sweetalert.min.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script>
    $(function () {
       $("#nodename").focus();
        $("#updateNodeBtn").click(function () {
           $("#updateNodeForm").submit();
        });
        $("#updateNodeForm").validate({
           errorElement:"span",
            errorClass:"text-error",
            rules:{
                nodename:{
                    required:true,
                    remote:"/admin/nodeValidate?nodeid=${node.id}"
                }
            },
            messages:{
                nodename:{
                    required:"请输入节点名",
                    remote:"节点名已存在"
                }
            },
            submitHandler:function () {
                $.ajax({
                    url:"/admin/nodeUpdate",
                    type:"post",
                    data:$("#updateNodeForm").serialize(),
                    submitHandler:function () {
                        $("#updateNodeBtn").text("保存中...").attr("disabled","disabled");
                    },
                    success:function (data) {
                        if(data.state=='success'){
                            swal({title:"修改节点成功"},function () {
                                window.location.href="/admin/node?_=2";
                            });
                        }else{
                            swal(data.data);
                        }
                    },
                    error:function () {
                        swal("服务器异常");
                    },
                    complete:function () {
                        $("#updateNodeBtn").text("保存").removeAttr("disabled");
                    }
                });
            }
        });
    });
</script>
</body>
</html>

