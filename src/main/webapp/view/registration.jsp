<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Create an account</title>

    <%--TODO Why don't work--%>
    <%--<link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">--%>
    <%--<link href="${contextPath}/resources/css/common.css" rel="stylesheet">--%>

    <style>
        <%@include file='../resources/css/bootstrap.min.css' %>
        <%@include file='../resources/css/common.css' %>
    </style>
    <%--<script src="../resources/js/bootstrap.min.js" type="text/javascript"></script>--%>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>
<%@include file="header.jsp" %>
<div class="container">
    <form:form method="POST" modelAttribute="userForm" class="form-signin">
        <h2 class="form-signin-heading"><spring:message code="Create.account"/></h2>
        <spring:bind path="username">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <spring:message code="User.name" var="Username" />
                <form:input type="text" path="username" class="form-control"
                            placeholder="${Username}" autofocus="true"></form:input>
                <form:errors path="username"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="password">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <spring:message code="Password" var="Password" />
                <form:input type="password" path="password" class="form-control" placeholder="${Password}"></form:input>
                <form:errors path="password"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="passwordConfirm">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <spring:message code="Password.confirm" var="PasswordConfirm" />
                <form:input type="password" path="passwordConfirm" class="form-control"
                            placeholder="${PasswordConfirm}"></form:input>
                <form:errors path="passwordConfirm"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="email">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <spring:message code="Email" var="Email" />
                <form:input type="text" path="email" class="form-control" placeholder="${Email}"
                            autofocus="true"></form:input>
                <form:errors path="email"></form:errors>
            </div>
        </spring:bind>

        <button class="btn btn-lg btn-primary btn-block" type="submit"><spring:message code="Submit"/></button>
    </form:form>

</div>
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
<%--<script src="../resources/js/jquery-1.11.3.min.js" type="text/javascript"></script>--%>
</body>
</html>
