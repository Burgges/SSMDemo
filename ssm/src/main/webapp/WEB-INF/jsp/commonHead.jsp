<%--
  Created by IntelliJ IDEA.
  User: hand
  Date: 2017/7/18
  Time: 18:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<div id="head-div">
    <ul id="menu-ul">
        <%--<li id="homePage-li">Home Page</li>
        <li id="users-li">Users</li>
        <li id="userRole-li">Roles</li>
        <li id="lookup-li">Lookups</li>
        <li id="logs-li">Logs</li>--%>

        <li style="float: right;" id="logOut-li">Log Out</li>
        <li style="float: right;" id="loginUser-li">${sessionScope.userName}</li>
    </ul>
</div>