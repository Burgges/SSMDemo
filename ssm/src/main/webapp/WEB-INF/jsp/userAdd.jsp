<%--
  Created by IntelliJ IDEA.
  User: hand
  Date: 2017/7/6
  Time: 19:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add user</title>
    <script src="js/jquery.min.js"></script>
    <script src="js/addUser.js"></script>
</head>
<body>
    <div id = "div-upd" style="width: 100%; height: 600px; padding-top: 100px;" align="center">
        <form action="users" method="post">
            <table id = "tab-add" border="1" style="width: 33%; max-height: 600px; " cellspacing="0">
                <tr>
                    <td>User Name</td>
                    <td><input id="userName" name="userName"/></td>
                </tr>
                <tr>
                    <td>User Sex</td>
                    <td><input id="userSex" name="userSex"/></td>
                </tr>
                <tr>
                    <td>User Mail</td>
                    <td><input id="userMail" name="userMail"/></td>
                </tr>
                <tr>
                    <td><button type="submit" id="btn-ok">Ok</button></td>
                    <td><button type="button" id="btn-cancel">Cancel</button></td>
                </tr>
            </table>
        </form>
    </div>
</body>
</html>
