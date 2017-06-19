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

<body>
	<div class="wrap">
		<div class="stacked">

			<H2>Concurrent Sessions Exceeded</H2>

			<p>You have exceeded the number of concurrent sessions allowed</p>

			<p>You have another session in another browser</p>

			<p>This session has been invalidated</p>

		</div>
	</div>
</body>

</html>


