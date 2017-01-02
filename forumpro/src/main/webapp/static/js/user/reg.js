$(function () {
   $("#regBtn").click(function () {
       $("#regForm").submit();
   });

    $("#regForm").validate({
        errorElement:"span",
        errorClass:"text-error",
        rules:{
            username:{
                required:true,
                minlength:3,
                remote:"validate/user"
            },
            password:{
                required:true,
                rangelength:[6,18]
            },
            repassword:{
                required:true,
                equalTo:"#password"
            },
            email:{
                required:true,
                email:true,
                remote:"validate/email"
            },
            phone:{
                required:true,
                rangelength:[11,11],
                digits:true
            }
        },
        messages:{
            username:{
                required:"请填写用户名",
                minlength:"用户名长度3位以上",
                remote:"用户名被占用"
            },
            password:{
                required:"请填写密码",
                rangelength:"密码长度6-18"
            },
            repassword:{
                required:"请填写确认密码",
                equalTo:"密码不一致"
            },
            email:{
                required:"请填写邮箱地址",
                email:"邮箱格式不正确",
                remote:"邮件被占用"
            },
            phone:{
                required:"请填写手机号",
                rangelength:"手机号格式不正确",
                digits:"手机号格式不正确"
            }
        },
        submitHandler:function () {
            $.ajax({
                url:"/reg",
                type:"post",
                data:$("#regForm").serialize(),
                beforeSend:function () {
                    $("#regBtn").text("注册中...").attr("disabled","disabled");
                },
                success:function (data) {
                    if(data.state=='success'){
                        swal({title:"注册成功，请前往邮箱激活账号"},function () {
                            window.location.href="/login";
                        });
                    }
                },
                error:function () {
                    swal("服务器异常");
                },
                complete:function () {
                    $("#regBtn").text("注册").removeAttr("disabled");
                }
            });
        }
    });

});
