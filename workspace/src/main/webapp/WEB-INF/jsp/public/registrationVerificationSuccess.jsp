<html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
		 pageEncoding="ISO-8859-1"%>
<%@ page session="false"%>

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
			<div>
				<h2>Verification Success</h2>

				<div class="well alert-success">Congratulations, you have
					verified your identity.</div>				

				<div class="well">
					In future please sign-in to our site using your email address (<b>${userRegistrationForm.email}</b>)
					and your password.
				</div>

				<div class="well">
					Please use this button to go to the login page
					<button id="goButton" class="btn btn-primary" type="button"
						onClick="window.location.href='${attemptloginUrl}'">Login</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>

