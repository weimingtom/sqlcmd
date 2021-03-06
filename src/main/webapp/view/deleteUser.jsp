<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>SQLCmd</title>
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
</head>
<body>
<%@include file="header.jsp" %>
<div class="container">
    <h2><spring:message code="Delete.user"/></h2><br>
    <form action="deleteuser" method="post" class="form-horizontal">
        <input type="hidden" name = "username" value="${user.username}">
        <div class="form-group">
            <label for="name" class="col-sm-2 control-label"><spring:message code="Name"/></label>
            <div class="col-sm-10">
                <input class="form-control" id="username" name="username" type="text" value="${user.username}" disabled>
            </div>
        </div>
        <div class="form-group">
            <label for="password" class="col-sm-2 control-label"><spring:message code="Password"/></label>
            <div class="col-sm-10">
                <input class="form-control" id="password" name="password" type="text" value="${user.password}" disabled>
            </div>
        </div>
        <div class="form-group">
            <label for="email" class="col-sm-2 control-label"><spring:message code="Email"/></label>
            <div class="col-sm-10">
                <input class="form-control" id="email" name="email" type="text" value="${user.email}" disabled>
            </div>
        </div>
        <div class="form-group">
            <div class="checkbox col-sm-offset-2 col-sm-10">
                <label><input id="enabled" name="enabled" type="checkbox" value="${user.enabled}" disabled>
                    <spring:message code="Enabled"/></label>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button id="btn_delete_user" class="btn btn-default"><spring:message code="Delete"/></button>
            </div>
        </div>
        <a href="users"><spring:message code="Back.to.users"/></a>
        <%@include file="footer.jsp" %>
    </form>
</div>
</body>
</html>