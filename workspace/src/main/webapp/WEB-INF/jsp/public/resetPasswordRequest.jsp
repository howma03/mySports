<html>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page session="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:include page="../header.jsp" />

<link rel="stylesheet"
	  href="<spring:url value="/resources/styles/login.css" />"
	  type="text/css" media="all" />

<spring:url var="loginUrl" value="/login" />

<body>
	<div class="wrap">
		<div class="stacked">

			<h2>Reset Password</h2>

			<jsp:include page="../forms/reset-password.jsp" />

		</div>
	</div>
</body>

</html>


