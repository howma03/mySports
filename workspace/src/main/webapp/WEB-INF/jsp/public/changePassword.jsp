<html>

<%@ page session="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:include page="../header.jsp" />

<link rel="stylesheet"
	  href="<spring:url value="/resources/styles/login.css" />"
	  type="text/css" media="all" />

<spring:url htmlEscape="true" var="urlLogin" value="/login" />
<spring:url htmlEscape="true" var="urlSetPassword" value="/password/change/submit"  />

<div class="wrap">
	<div class="stacked">

		<h2>Change Password</h2>

		<jsp:include page="../forms/change-password.jsp"/>

	</div>
</div>

</html>
