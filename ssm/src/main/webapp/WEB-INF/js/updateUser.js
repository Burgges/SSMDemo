/**
 * Created by huiyu.chen on 2017/7/6.
 */

$(document).ready(function() {

    var userId = GetQueryString("userId");

    $("#form-upd").attr("action","users/"+userId);

    echoUserInfo(userId);

    $("#btn-cancel").click(function () {
        window.location.href = "index";
    });
});

/**
 * 回显用户信息
 * @param userId
 */
function echoUserInfo(userId) {
    $.ajax({
        url : userId+"/users",
        type : "GET",
        async : true,
        cache : false,
        success : function (response) {
            console.log(response);
            $("#userName").val(response.userName);
            $("#userSex").val(response.userSex);
            $("#userMail").val(response.userMail);
        }
    });
}

/**
 * get param in url
 * @param name
 * @returns {null}
 */
function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)
        return decodeURI(r[2]);
    return null;
}