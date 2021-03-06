<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>发布新主题</title>
    <link rel="stylesheet" href="/static/css/font-awesome.min.css">
    <link rel="stylesheet" href="/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="/static/css/style.css">
    <link rel="stylesheet" href="/static/js/editer/styles/simditor.css">
    <link rel="stylesheet" href="/static/js/sweetAlert/sweetalert.css">
</head>
<body>
<%@ include file="../include/navbar.jsp"%>
<!--header-bar end-->
<div class="container">
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-plus"></i> 发布新主题</span>
        </div>

        <form action="" style="padding: 20px" id="topicForm">
            <label class="control-label">主题标题</label>
            <input type="text" name="title" style="width: 100%;box-sizing: border-box;height: 30px" placeholder="请输入主题标题，如果标题能够表达完整内容，则正文可以为空">
            <label class="control-label">正文</label>
            <textarea name="content" id="editor"></textarea>

            <select name="nodeid" id="nodeid" style="margin-top:15px;">
                <c:forEach items="${requestScope.listnode}" var="node">
                    <option value="${node.id}">${node.nodename}</option>
                </c:forEach>
            </select>

        </form>
        <div class="form-actions" style="text-align: right">
            <button class="btn btn-primary" id="sendBtn">发布主题</button>
        </div>


    </div>
    <!--box end-->
</div>
<!--container end-->
<script src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
<script src="/static/js/editer/scripts/module.min.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script src="/static/js/editer/scripts/hotkeys.min.js"></script>
<script src="/static/js/editer/scripts/uploader.min.js"></script>
<script src="/static/js/editer/scripts/simditor.min.js"></script>
<script src="/static/js/sweetAlert/sweetalert.min.js"></script>
<script src="/static/js/user/notify.js"></script>

<script>
    $(function () {
        var editor = new Simditor({
            textarea: $('#editor'),
            upload:{
                url:"http://up-z1.qiniu.com",
                params:{"token":"${token}"},
                fileKey:'file'
            }
        });

        $("#sendBtn").click(function () {
            $("#topicForm").submit();
        });

        $("#topicForm").validate({
           errorElement:"span",
            errorClass:"text-error",
            rules:{
                title:{
                    required:true,
                },
                nodeid:{
                    required:true,
                }
            },
            messages:{
                title:{
                    required:"请输入标题",
                },
                nodeid:{
                    required:"请选择节点",
                }
            },
            submitHandler:function (form) {
                $.ajax({
                    url:"/addTopic",
                    type:"post",
                    data:$(form).serialize(),
                    beforeSend:function () {
                        $("#sendBtn").text("发布中").attr("disabled","disabled");
                    },
                    success:function (data) {
                        if(data.state=='success'){
                            swal({
                                title:"添加成功",
                                type:"success",
                                confirmButtonText:"确定",
                            },function () {
                                window.location.href="/topicDetail?topicid="+data.data.id;
                            });
                        }else{
                            swal({
                                title:"添加主题异常",
                                type:"error",
                                confirmButtonText:"确定",
                            });
                        }
                    },
                    error:function () {
                        swal({
                            title:"服务器异常",
                            type:"error",
                            confirmButtonText:"确定",
                        });
                    },
                    complete:function () {
                        $("#sendBtn").text("发布主题").removeAttr("disabled");
                    }
                });
            }
        });

    });
</script>

</body>
</html>
