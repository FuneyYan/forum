$(function () {

    $("#emailBtn").click(function () {
        $("#emailForm").submit();
    });

    $("#emailForm").validate({
        
        errorElement:"span",
        errorClass:"text-error",
        rules:{
            email:{
                required:true,
                email:true,
                remote:"/validate/email?type=1"
            }
        },
        messages:{
            required:"请填写电子邮件地址",
            email:"电子邮件格式不正确",
            remote:"电子邮件被占用"
        },
        submitHandler:function (form) {
            $.ajax({
                url:"/setting?action=email",
                type:"post",
                data:$(form).serialize(),
                beforeSend:function () {
                    $("#emailBtn").text("保存中...").attr("disabled","disabled");
                },
                success:function (data) {
                    if(data.state=='success'){
                        swal("","邮箱修改成功","success");
                    }
                },
                error:function () {
                    swal("",data.message,"error");
                },
                complete:function () {
                    $("#emailBtn").text("保存").removeAttr("disabled");
                }
            })
        }
    });


    $("#passwordBtn").click(function () {
       $("#passwordForm").submit();
    });

    $("#passwordForm").validate({
        errorClass:"text-error",
        errorElement:"span",
        rules:{
            oldpassword:{
                required:true,
                rangelength:[6,18]
            },
            newpassword:{
                required:true,
                rangelength:[6,18]
            },
            repassword:{
                required:true,
                equalTo:"#newpassword"
            }
        },
        messages:{
            oldpassword:{
                required:"请输入原始密码",
                rangelength:"密码长度6-18个字符"
            },
            newpassword:{
                required:"请输入新密码",
                rangelength:"密码长度6-18个字符"
            },
            repassword:{
                required:"请输入确认密码",
                equalTo:"两次密码不一致"
            }
        },
        submitHandler:function (form) {
            $.ajax({
                url:"/setting?action=password",
                type:"post",
                data:$(form).serialize(),
                beforeSend:function () {
                    $("#passwordBtn").text("保存中...").attr("disabled","disabled");
                },
                success:function (data) {
                    if(data.state=='success'){
                        // alert("修改密码成功,请重新登录");

                            swal({
                                title:"修改密码成功,请重新登陆",
                                type:"success",
                                confirmButtonText:"确定",
                            }, function() {
                                window.location.href="/login";
                            });
                    }else{
                        swal("",data.message,"error");
                    }
                },
                error:function () {
                    alert("服务器异常");
                },
                complete:function () {
                    $("#passwordBtn").text("保存").removeAttr("disabled");
                }
            });
        }
    });


});
