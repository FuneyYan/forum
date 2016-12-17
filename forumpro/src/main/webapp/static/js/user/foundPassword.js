$(function () {

    $("#type").change(function () {
       var type=$(this).val();
        if(type=='email'){
            $("#made").text("电子邮件");
        }else{
            $("#made").text("手机号码");
        }
    });

   $("#foundBtn").click(function () {
        $("#foundForm").submit();
   });
    $("#foundForm").validate({
        errorElement:"span",
        errorClass:"text-error",
        rules:{
            value:{
                required:true,
                email:true
            }
        },
        messages:{
            value:{
                required:"请填写邮箱地址",
                email:"邮箱格式不正确"
            }
        },
        submitHandler:function () {
            $.ajax({
                url:"/foundPassword",
                type:"post",
                data:$("#foundForm").serialize(),
                beforeSend:function () {
                    $("#foundBtn").text("提交中...").attr("disabled","disabled");
                },
                success:function (data) {
                    if(data.state=='success'){
                        alert("发送邮箱成功,请前往邮箱进行下一步操作");
                        // window.location.href="/foundPassword/newPassword";
                    }else{
                        alert(data.message);
                    }
                },
                error:function () {
                    alert("Server Exception");
                },
                complete:function () {
                    $("#foundBtn").text("提交").removeAttr("disabled");
                }
            });
        }
    });
});
