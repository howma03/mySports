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
			<h2>Verification Failure</h2>

			<div class="well alert-error">
				The verification process has failed - we have generated another
				verification email and have sent this to your email address (<b>${userRegistrationForm.email}</b>).
			</div>

			<div class="well">
				Please retry the verification operation; if this fails then please
				contact our support staff (<b>support@docSigner.com)</b>.
			</div>
		</div>
	</div>
</body>
</html>
