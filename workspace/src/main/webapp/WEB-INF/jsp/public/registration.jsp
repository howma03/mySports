<html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<jsp:include page="../header.jsp"/>

<link rel="stylesheet"
      href="<spring:url value="/resources/styles/login.css" />"
      type="text/css" media="all"/>

<spring:url var="urlSignIn" value="/login"/>
<spring:url var="urlAttemptRegistration" value="/registration/simple"
            htmlEscape="true"/>

<body>
<div class="wrap">
    <div class="stacked">

        <h2>My sports</h2>

        <jsp:include page="../forms/registration.jsp"/>

    </div>
</div>
</body>
</html>