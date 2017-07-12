/**
 * Created by huiyu.chen on 2017/7/6.
 *
 */
$(document).ready(function() {
/*    $("#btn-ok").click(function () {
        var userName = $("#user-name").val();
        var userSex = $("#user-sex").val();
        var userMail = $("#user-mail").val();
        var param = {
            userName: userName,
            userSex: userSex,
            userMail: userMail
        };

        $.ajax({
            url: "users",
            type: "POST",
            data: param,
            async: true,
            cache: false,
            success: function (response) {
                console.log(response);
                if ("success" == response) {
                    window.location.href = "index";
                } else {
                    console.log("add error");
                    alert("add error");
                }
            }
        });
    });*/

    $("#btn-cancel").click(function () {
        window.location.href = "index";
    });
});