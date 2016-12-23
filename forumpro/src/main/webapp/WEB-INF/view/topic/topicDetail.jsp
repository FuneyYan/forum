<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>主题页</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
    <link rel="stylesheet" href="/static/js/editer/styles/simditor.css">
    <style>
        body {
            background-image: url(/static/img/bg.jpg);
        }

        .simditor .simditor-body {
            min-height: 100px;
        }
    </style>
</head>
<body>
<%@include file="../include/navbar.jsp" %>
<!--header-bar end-->
<div class="container">
    <div class="box">
        <ul class="breadcrumb" style="background-color: #fff;margin-bottom: 0px;">
            <li><a href="/home">首页</a> <span class="divider">/</span></li>
            <li class="active">${topic.node.nodename}</li>
        </ul>
        <div class="topic-head">
            <img class="img-rounded avatar" src="${topic.user.avatar}?imageView2/1/w/60/h/60" alt="">
            <h3 class="title">${topic.title}</h3>
            <p class="topic-msg muted"><a href="">${topic.user.username}</a> · <span id="topicTime">${topic.createtime}</span></p>
        </div>
        <div class="topic-body">
            ${topic.content}
        </div>
        <div class="topic-toolbar">
            <ul class="unstyled inline pull-left">
                <li><a href="">加入收藏</a></li>
                <li><a href="">感谢</a></li>
                <li><a href=""></a></li>
            </ul>
            <ul class="unstyled inline pull-right muted">
                <li>${topic.clicknum}次点击</li>
                <li>${topic.favnum}人收藏</li>
                <li>${topic.thanknum}人感谢</li>
            </ul>
        </div>
    </div>
    <!--box end-->

    <div class="box" style="margin-top:20px;">

        <div class="talk-item muted" style="font-size: 12px">
            ${fn:length(replyList)}个回复 | 直到 <span id="lastReplyTime">${topic.lastreplytime}</span>
        </div>

        <c:forEach items="${replyList}" var="reply" varStatus="index">

            <div class="talk-item">
                <table class="talk-table">
                    <tr>
                        <a name="reply${index.count}"></a>
                        <td width="50">
                            <img class="avatar"
                                 src="${reply.user.avatar}?imageView2/1/w/40/h/40"
                                 alt="">
                        </td>
                        <td width="auto">
                            <a href="" style="font-size: 12px">${reply.user.username}</a> <span style="font-size: 12px"
                                                                                class="reply">${reply.createtime}</span>
                            <br>
                            <p style="font-size: 14px">${reply.content}</p>
                        </td>
                        <td width="70" align="right" style="font-size: 12px">
                            <a href="javascript:;" title="回复" class="replyLink" rel="${index.count}"><i class="fa fa-reply"></i></a>&nbsp;
                            <span class="badge">${index.count}</span>
                        </td>
                    </tr>
                </table>
            </div>

        </c:forEach>


    </div>

    <div class="box" style="margin:20px 0px;">

        <c:choose>
            <c:when test="${not empty sessionScope.curr_user}">
                <a name="reply"></a>
                <div class="talk-item muted" style="font-size: 12px"><i class="fa fa-plus"></i> 添加一条新回复</div>
                <form action="/addReply" method="post" style="padding: 15px;margin-bottom:0px;" id="replyForm">
                    <input type="hidden" value="${topic.id}" name="topicid">
                    <textarea name="content" id="editor"></textarea>
                </form>
                <div class="talk-item muted" style="text-align: right;font-size: 12px">
                    <span class="pull-left">请尽量让自己的回复能够对别人有帮助回复</span>
                    <button class="btn btn-primary" id="replyBtn">发布</button>
                </div>
            </c:when>
            <c:otherwise>
                <div class="talk-item">请 <a href="/login?redirect=topicDetail?topicid=${topic.id}#reply">登陆</a> 后再回复
                </div>
            </c:otherwise>
        </c:choose>


    </div>

</div>
<!--container end-->
<script src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
<script src="/static/js/editer/scripts/module.min.js"></script>
<script src="/static/js/editer/scripts/hotkeys.min.js"></script>
<script src="/static/js/editer/scripts/uploader.min.js"></script>
<script src="/static/js/editer/scripts/simditor.min.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script src="//cdn.bootcss.com/moment.js/2.10.6/moment.min.js"></script>
<script src="//cdn.bootcss.com/moment.js/2.10.6/locale/zh-cn.js"></script>
<script>
    $(function () {
        var editor = new Simditor({
            textarea: $('#editor'),
            toolbar: false
            //optional options


        });

        $("#replyBtn").click(function () {
            $("#replyForm").submit();
        });

        $("#replyForm").validate({
            errorElement: "span",
            errorClass: "text-error",
            rules: {
                content: {
                    required: true
                }
            },
            messages: {
                content: {
                    required: "评论不能为空"
                }
            },
//            submitHandler: function (form) {
//                $.ajax({
//                    url: "/addReply",
//                    type: "post",
//                    data: $(form).serialize(),
//                    beforeSend:function () {
//                        $("#replyBtn").text("发布中...").attr("disabled","disabled");
//                    },
//                    success:function (data) {
//
//                    },
////                    error:function () {
////                        alert("服务器异常");
////                    },
//                    complete:function () {
//                        $("#editor").text("");
//                        $("#replyBtn").text("发布").removeAttr("disabled");
//                    }
//            });
//            }
        });

//        主题添加时间(topictime)

        $("#topicTime").text(moment($("#topicTime").text()).fromNow());
        $("#lastReplyTime").text(moment($("#lastReplyTime").text()).format("YYYY年MM月DD日 HH:mm:ss"));

//        回复用户的评论
        $(".replyLink").click(function () {
            var count=$(this).attr("rel");
            var html="<a href="+'#reply'+count+">#"+count+"</a>";
            editor.setValue(html+editor.getValue());
           window.location.href="#reply";
        });

    });
</script>

</body>
</html>