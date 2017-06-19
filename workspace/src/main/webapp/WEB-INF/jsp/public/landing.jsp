<html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:include page="../header.jsp" />

<link rel="stylesheet"
      href="<spring:url value="/resources/styles/login.css" />"
      type="text/css" media="all" />

<spring:url htmlEscape="true" var="urlLogin" value="/login"  />
<spring:url htmlEscape="true" var="urlLogout" value="/login?logout" 	 />
<spring:url htmlEscape="true" var="urlRegistration" value="/registration"  />
<spring:url htmlEscape="true" var="urlPasswordReset" value="/password/reset" />

<body>
<div class="wrap">
    <div class="stacked">

        <h2>My sports</h2>

        <form>

            <h3>Landing Page</h3>

            <br/>

            <br/>

            <br/>

            <br/>

            <br/>

            <br/>

            <br/>

            <br/>

            <hr/>

            <div class="btn-group">
                <button class="btn btn-primary" type="button"
                       onClick="window.location.href='${urlLogin}'">Login</button>
                <button class="btn btn-danger" type="button"
                        onClick="window.location.href='${urlPasswordReset}'">Reset Password</button>
                <button class="register-btn btn btn-success" type="button"
                        onClick="window.location.href='${urlRegistration}'">Register for Free</button>
            </div>

        </form>
    </div>
</div>
</body>
</html>


