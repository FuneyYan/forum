<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>主题管理</title>
    <link rel="stylesheet" href="/static/css/font-awesome.min.css">
    <link rel="stylesheet" href="/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="/static/js/sweetAlert/sweetalert.css">
    <style>
        .table td{
            vertical-align: middle;
        }
        .table select{
            width: 150px;
            margin: 0px;

        }

    </style>
</head>
<body>
<%@include file="../include/adminNavBar.jsp" %>
<!--header-bar end-->
<div class="container-fluid" style="margin-top:20px">
    <table class="table">
        <thead>
        <tr>
            <th>名称</th>
            <th>发布人</th>
            <th>发布时间</th>
            <th>回复数量</th>
            <th>最后回复时间</th>
            <th>所属节点</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>

        <c:forEach items="${topicList.items}" var="topic">
            <tr>
                <td><a href="/topicDetail?topicid=${topic.id}" target="_blank">${topic.title}</a></td>
                <td>${topic.user.username}</td>
                <td>${topic.createtime}</td>
                <td>${topic.replynum}</td>
                <td>${topic.lastreplytime}</td>
                <td>
                    <select name="nodeid" class="nodeid">
                        <c:forEach items="${nodeList}" var="node">
                            <option ${node.id==topic.node_id?"selected":""} value="${node.id}">${node.nodename}</option>
                        </c:forEach>

                    </select>
                </td>
                <td>
                    <a href="javascript:;" rel="${topic.id}" class="update">修改</a>
                    <a href="javascript:;" rel="${topic.id}" class="delTopic">删除</a>
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
<script src="/static/js/user/notify.js"></script>

<script>
    $(function () {
        $("#pagination").twbsPagination({
            totalPages:${topicList.totalPage},
            visiblePages: 5,
            first: '首页',
            last: '末页',
            prev: '上一页',
            next: '下一页',
            href: '?p={{number}}'
        });

        $(".delTopic").click(function () {
            var topicid=$(this).attr("rel");
            swal({
                title: "确定要删除该主题?",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "确定",
                closeOnConfirm: false
            },
            function () {
                $.ajax({
                    url:"/admin/topic",
                    type:"post",
                    data:{"topicid":topicid},
                    success:function (data) {
                        if(data.data=='success'){
                            swal("删除成功");
                            window.history.go(0);
                        }else{
                            swal(data);
                        }
                    },
                    error:function () {
                        swal("服务器异常");
                    }
                });

            });
        });
        var nodeid;
        $(".nodeid").change(function () {
           nodeid=$(this).val();
        });

        $(".update").click(function () {
            var topicid=$(this).attr("rel");

           $.ajax({
               url:"/admin/topicUpdate",
               type:"post",
               data:{"topicid":topicid,"nodeid":nodeid},
               success:function (data) {
                   if(data.state='success'){
                       swal({title:"修改完成"},function () {
                          window.history.go(0);
                       });
                   }else{
                       swal(data.data);
                   }
               },
               error:function () {
                   swal("服务器异常");
               }
           })
        });


    });
</script>

</body>
</html>
