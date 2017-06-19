<!DOCTYPE html>

<html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<jsp:include page="../header.jsp"/>

<link rel="stylesheet" type="text/css" media="all" href="<spring:url value="/resources/styles/login2.css" />"/>

<body>

<nav class="navbar navbar-default navbar-inverse" role="navigation">
    <div class="container-fluid">

        <jsp:include page="../branding-navbar-header.jsp"/>

        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">

            <ul class="nav navbar-nav navbar-right">
                <li><p class="navbar-text">Would you like an account?</p></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><b>Register</b> <span
                            class="caret"></span></a>
                    <ul id="login-dp" class="dropdown-menu">
                        <li>
                            <div class="row">
                                <div class="col-md-12">
                                    Register using
                                    <div class="social-buttons">
                                        <a href="#" class="btn btn-fb"><i class="fa fa-facebook"></i> Facebook</a>
                                        <a href="#" class="btn btn-tw"><i class="fa fa-twitter"></i> Twitter</a>
                                    </div>
                                    or
                                    <jsp:include page="../forms/registration.jsp"/>
                                </div>

                                <hr/>

                                <div class="bottom text-center">
                                    Already have an account then <a href="${urlLogin}"><b>Login</b></a>
                                </div>

                            </div>
                        </li>
                    </ul>
                </li>
            </ul>

            <script>
                $('#login-dp').show();
            </script>
        </div>
    </div>
</nav>
</body>
</html>


