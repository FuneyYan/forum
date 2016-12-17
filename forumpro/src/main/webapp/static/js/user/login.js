$(function () {

    $("#loginBtn").click(function () {
        $("#loginForm").submit();
    });

    $("#loginForm").validate({
        errorElement:"span",
        errorClass:"text-error",
        rules:{
            username:{
                required:true,
            },
            password:{
                required:true,
            }
        },
        messages:{
            username:{
                required:"用户名不能为空",
            },
            password:{
                required:"密码不能为空",
            }
        },
        submitHandler:function () {
           $.ajax({
               url:"/login",
               type:"post",
               data:$("#loginForm").serialize(),
               beforeSend:function () {
                   $("#loginBtn").text("登陆中...").attr("disabled","disabled");
               },
               success:function (data) {
                    if(data.state=='success'){
                        alert("登录成功");
                        window.location.href="/home";
                    }else{
                        alert(data.message);
                    }
               },
               error:function () {
                   alert("server exception");
               },
               complete:function () {
                   $("#loginBtn").text("登陆").removeAttr("disabled");
               }
           })

        }

    });

});
