<%--
  Created by IntelliJ IDEA.
  User: hand
  Date: 2017/7/6
  Time: 16:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"
            +request.getServerName()+":"+request.getServerPort()
            +path+"/";
%>
<html>
<head>
    <META http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="x-ua-compatible" content="IE=edge" >
    <BASE href="<%=basePath%>">
    <title>User List</title>
    <link href="css/jsgrid.min.css" rel="stylesheet">
    <link href="css/jsgrid-theme.min.css" rel="stylesheet">
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <script src="js/jquery.min.js"></script>
    <script src="js/jsgrid.min.js"></script>
    <script src="js/index.js"></script>
</head>
<body>

<%--<div id = "div-list" style="width: 100%; height: 600px; padding-top: 100px;" align="center">
    <table id = "tab-add" border="0" style="width: 50%; max-height: 600px; " cellspacing="0">
        <tr><td style="text-align: right"><button id="btn-add" onclick="addFun()">Add</button></td></tr>
    </table>
    <table id = "tab-list" border="1" style="width: 50%; max-height: 600px; " cellspacing="0">
    </table>

</div>--%>

<div class="page-body col-xs-10" id="main-body" style="width:100%; height: 600px; margin-top: 50px;">
    <ul style="line-height: 36px;list-style: none;border-bottom: 1px solid #eee; width: 80%; margin: 4px auto;">
        <li>
            <input style="margin-bottom: 20px; height: 35px; border-radius: 2px; border: 1px #e9e9e9 solid; text-indent: 5px;" id="searchContent">
            <button style="margin-left: 20px;margin-bottom: 1px" type="button" class="btn btn-info" id="searchBtn">Search</button>
            <span style="margin-left: 68%; ">${sessionScope.userName}</span>
            <a style="float: right; color:red;" href="logout"> 注销</a>
        </li>
    </ul>
    <div id="userList" style="margin: 0px auto;"></div>
    <div style="clear: both"></div>
</div>

</body>
</html>
