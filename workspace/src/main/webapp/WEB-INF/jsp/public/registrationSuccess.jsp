<html>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:include page="../header.jsp" />

<link rel="stylesheet"
	  href="<spring:url value="/resources/styles/login.css" />"
	  type="text/css" media="all" />

<spring:url var="attemptloginUrl" value="/login" htmlEscape="true" />

<body>
	<div class="wrap">
		<div class="stacked">
			<h2>Registration Successful</h2>

			<div class="well alert-success">Congratulations, you have registered successfully.</div>

			<div class="well">We have sent you an email message so that we can verify
				your identity.</div>

			<div class="well">Once you receive this email please use the link it
				contains to confirm your identity.</div>

			<div class="well">
				Then you will be able to use your email address (<b>${userRegistrationForm.email}</b>)
				and your password to sign-in to our site.
			</div>			
		</div>
	</div>
</body>
</html>


