<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>主题页</title>
    <link rel="stylesheet" href="/static/css/font-awesome.min.css">
    <link rel="stylesheet" href="/static/css/bootstrap.min.css">
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
            <c:if test="${not empty sessionScope.curr_user}">
                <ul class="unstyled inline pull-left">
                    <c:choose>

                        <c:when test="${not empty fav}">
                            <li><a href="javascript:;" class="handlerFav">取消收藏</a></li>
                        </c:when>

                        <c:otherwise>
                            <li><a href="javascript:;" class="handlerFav">加入收藏</a></li>
                        </c:otherwise>

                    </c:choose>

                    <c:choose>

                        <c:when test="${not empty thank}">
                            <li><a href="javascript:;" class="handlerThank">取消感谢</a></li>
                        </c:when>

                        <c:otherwise>
                            <li><a href="javascript:;" class="handlerThank">感谢</a></li>
                        </c:otherwise>

                    </c:choose>

                    <c:if test="${sessionScope.curr_user.id==topic.user_id and topic.edit}">
                        <li><a href="/topicEdit?topicid=${topic.id}">编辑</a></li>
                    </c:if>

                </ul>
            </c:if>

            <ul class="unstyled inline pull-right muted">
                <li>${topic.clicknum}次点击</li>
                <li><span id="topicFav">${topic.favnum}</span>人收藏</li>
                <li><span id="topicThank">${topic.thanknum}</span>人感谢</li>
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
                            <a href="javascript:;" title="回复" class="replyLink" rel="${index.count}"><i
                                    class="fa fa-reply"></i></a>&nbsp;
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
<script src="/static/js/user/notify.js"></script>
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
            var count = $(this).attr("rel");
            var html = "<a href=" + '#reply' + count + ">#" + count + "</a>";
            editor.setValue(html + editor.getValue());
            window.location.href = "#reply";
        });

        var action="";
//        加入/取消收藏
        $this=$(".handlerFav");
        $(".handlerFav").click(function () {
            if ($(this).text() == '加入收藏') {
                action="fav";
            } else {//取消收藏
                action="unfav";
            }
            $.ajax({
                url: "/fav",
                type: "post",
                data: {"topicid": ${topic.id}, "userid": ${topic.user.id},"action":action},
                success:function (data) {
                    if(action=="fav"){
                        $this.text("取消收藏");
                    }else{
                        $this.text("加入收藏");
                    }
                    $("#topicFav").text(data.data);
                },
                error:function () {
                    alert("服务器异常");
                },
            });


        });



        var actionThank="";
//        加入/取消收藏
        $thisThank=$(".handlerThank");
        $(".handlerThank").click(function () {
            if ($(this).text() == '感谢') {
                actionThank="unthank";
            } else {//取消收藏
                actionThank="thank";
            }
            $.ajax({
                url: "/thank",
                type: "post",
                data: {"topicid": ${topic.id}, "userid": ${topic.user.id},"action":actionThank},
                success:function (data) {
                    if(actionThank=="unthank"){
                        $thisThank.text("取消感谢");
                    }else{
                        $thisThank.text("感谢");
                    }
                    $("#topicThank").text(data.data);
                },
                error:function () {
                    alert("服务器异常");
                },
            });


        });

    });
</script>

</body>
</html>