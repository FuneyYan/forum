
$(function () {

    function getParameterByName(name, url) {
        if (!url) {
            url = window.location.href;
        }
        name = name.replace(/[\[\]]/g, "\\$&");
        var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
            results = regex.exec(url);
        if (!results) return null;
        if (!results[2]) return '';
        return decodeURIComponent(results[2].replace(/\+/g, " "));
    }

    $("#loginBtn").click(function () {
        $("#loginForm").submit();
    });

    $("#loginForm").validate({
        errorElement:"span",
        errorClass:"text-error",
        rules:{
            adminname:{
                required:true,
            },
            password:{
                required:true,
            }
        },
        messages:{
            adminname:{
                required:"用户名不能为空",
            },
            password:{
                required:"密码不能为空",
            }
        },
        submitHandler:function () {
            $.ajax({
                url:"/admin/login",
                type:"post",
                data:$("#loginForm").serialize(),
                beforeSend:function () {
                    $("#loginBtn").text("登陆中...").attr("disabled","disabled");
                },
                success:function (data) {
                    if(data.state=='success'){
                        swal({
                            title:"登陆成功",
                            type:"success",
                            confirmButtonText:"确定",
                        }, function() {
                            var url=getParameterByName("redirect");
                            if(url){
                                var hash=location.hash;
                                window.location.href=url+hash;
                            }else{
                                window.location.href="/admin/home";
                            }
                        });


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
