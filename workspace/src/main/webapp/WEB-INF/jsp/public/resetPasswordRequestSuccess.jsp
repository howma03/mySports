<html>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
		 pageEncoding="ISO-8859-1"%>
<%@ page session="false"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:include page="../header.jsp" />

<link rel="stylesheet"
	  href="<spring:url value="/resources/styles/login.css" />"
	  type="text/css" media="all" />

<spring:url htmlEscape="true" var="urlLogin" value="/login"  />
<spring:url htmlEscape="true" var="urlRegistration" value="/registration"  />

<div class="wrap">
	<div class="stacked">

		<h2>Password Reset Successful</h2>

		<form>
			<div class="well alert-success">
				We have sent details of how to change your password to your email address
				(<b>${form.email}</b>)
			</div>

			<div class="well">Please your refer to it - if our email does
				not arrive shortly please check your SPAM filters.</div>

			<div class="btn-group">
				<button id="submit" type="submit" class="btn btn-primary signin-btn"
						onClick="window.location.href='${urlLogin}'" >Login</button>
				<button id="register" type="button" class="register-btn btn btn-success" type="button"
						onClick="window.location.href='${urlRegistration}'" >Register</button>
			</div>

		</form>
	</div>
</div>

</html>
