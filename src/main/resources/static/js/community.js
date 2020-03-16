function setCookie(cname,cvalue,exhours){
    var d = new Date();
    d.setTime(d.getTime()+(exhours*60*60*1000));
    var expires = "expires="+d.toUTCString();
    document.cookie = cname+"="+cvalue+"; "+expires+"; path=/";
}

//登录
function login_post() {
    var email = $("#inputEmail").val();
    var password = $("#inputPassword").val();
    var remember = $("#remember-me").prop("checked");

    if (!email || !password) {
        alert("邮箱地址和密码不允许为空！");
        return false;
    }
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/login",
        data: JSON.stringify({
            "inputEmail": email,
            "inputPassword": hex_md5(password)
        }),
        success: function (response) {
            if (response.code == 200) {
                if(remember){
                    setCookie("tokenMaxAge", (24*30).toString(), 24*30);
                    setCookie("token", response.data.token, 24*30);
                } else {
                    setCookie("tokenMaxAge", (0.5).toString(), 0.5);
                    setCookie("token", response.data.token, 0.5);
                }
                window.location.href="/";
            } else {
                alert(response.code + ":" + response.message);
            }
        },
        dataType: "json"
    });
}