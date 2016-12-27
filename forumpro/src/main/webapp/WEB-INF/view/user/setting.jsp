<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>用户设置</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/js/uploader/webuploader.css">
    <link rel="stylesheet" href="/static/css/style.css">
    <link rel="stylesheet" href="/static/js/sweetAlert/sweetalert.css">
</head>
<body>
<%@ include file="../include/navbar.jsp"%>
<div class="container">
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-cog"></i> 基本设置</span>
        </div>

        <form action="" class="form-horizontal" id="emailForm">
            <div class="control-group">
                <label class="control-label">账号</label>
                <div class="controls">
                    <input type="text" value="${sessionScope.curr_user.username}" readonly>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">电子邮件</label>
                <div class="controls">
                    <input type="text" name="email" id="email" value="${sessionScope.curr_user.email}">
                </div>
            </div>
            <div class="form-actions">
                <button class="btn btn-primary" id="emailBtn" type="button">保存</button>
            </div>

        </form>

    </div>
    <!--box end-->
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-key"></i> 密码设置</span>
            <span class="pull-right muted" style="font-size: 12px">如果你不打算更改密码，请留空以下区域</span>
        </div>

        <form action="" class="form-horizontal" id="passwordForm">

            <div class="control-group">
                <label class="control-label">旧密码</label>
                <div class="controls">
                    <input type="password" name="oldpassword">
                </div>
            </div>

            <div class="control-group">
                <label class="control-label">密码</label>
                <div class="controls">
                    <input type="password" name="newpassword" id="newpassword">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">重复密码</label>
                <div class="controls">
                    <input type="password" name="repassword">
                </div>
            </div>
            <div class="form-actions">
                <button class="btn btn-primary" type="button" id="passwordBtn">保存</button>
            </div>

        </form>

    </div>
    <!--box end-->

    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-user"></i> 头像设置</span>
        </div>

        <form action="" class="form-horizontal">
            <div class="control-group">
                <label class="control-label">当前头像</label>
                <div class="controls">
                    <img id="avatar_img" src="http://oi1tcffmb.bkt.clouddn.com/${sessionScope.curr_user.avatar}?imageView2/1/w/40/h/40" class="img-circle" alt="">
                </div>
            </div>
            <hr>
            <p style="padding-left: 20px">关于头像的规则</p>
            <ul>
                <li>禁止使用任何低俗或者敏感图片作为头像</li>
                <li>如果你是男的，请不要用女人的照片作为头像，这样可能会对其他会员产生误导</li>
            </ul>

            <div class="form-actions">
                <div id="picker">上传新头像</div>
            </div>


        </form>

    </div>
    <!--box end-->

</div>
<!--container end-->
<script src="/static/js/jquery-1.11.1.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script src="/static/js/uploader/webuploader.min.js"></script>
<script src="/static/js/user/setting.js"></script>
<script src="/static/js/sweetAlert/sweetalert.min.js"></script>
<script src="/static/js/user/notify.js"></script>
<script>



    var uploader=WebUploader.create({
        swf:"/static/js/uploader/Uploader.swf",
        server:"http://up-z1.qiniu.com/",
        pick:"#picker",
        auto : true,
        fileVal:'file',
        formData:{'token':'${token}'},
        accept: {
             title: 'Images',
             extensions: 'gif,jpg,jpeg,bmp,png',
             mimeTypes: 'image/!*'
         }
    });
    
    uploader.on("uploadSuccess",function (file,data) {
        var fileKey=data.key;
       $.post("/setting?action=avatar",{"fileKey":fileKey})
        .done(function (data) {
            if(data.state=='success'){
                swal({
                    title:"上传头像成功",
                    type:"success",
                    confirmButtonText:"确定",
                }, function() {
                    var url="http://oi1tcffmb.bkt.clouddn.com/"+fileKey;
                    $("#avatar_img").attr("src",url+"?imageView2/1/w/40/h/40");
                    $("#navbar_avatar").attr("src",url+"?imageView2/1/w/20/h/20")
                });
            }
        }).error(function () {
           swal({
               title:"头像设置失败",
               type:"error",
               confirmButtonText:"确定",
           });
       });
    });
    
    uploader.on("uploadError",function () {
        swal({
            title:"上传失败,稍后再试",
            type:"error",
            confirmButtonText:"确定",
        });
    });


</script>

</body>
</html>
