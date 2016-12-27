$(function () {
   var islogin=$("#isLogin").text();
    var loadNotify=function () {
        $.post("/notify",function (data) {
            if(data.state='success' && data.data>0){
                $("#unreadCount").text(data.data);
            }
        });
    };

    if(islogin=='1'){
        setInterval(loadNotify,10*1000);
    }

    loadNotify();

});
