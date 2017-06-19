<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<spring:url htmlEscape="true" var="urlServiceBase" value="/api"/>

<html lang="en">

<jsp:include page="../header.jsp"/>

<body>

<jsp:include page="navigation.jsp"/>
<jsp:include page="carousel.jsp"/>

<jsp:include page="../modals/profile.jsp"/>
<jsp:include page="../modals/group.jsp"/>
<jsp:include page="../modals/group-membership.jsp"/>
<jsp:include page="../modals/sport.jsp"/>
<jsp:include page="../modals/user.jsp"/>

<div class="container">

    <div class="row">
        <div class="col-lg-12">
            <jsp:include page="component-management-users.jsp"/>
        </div>
    </div>

    <hr>

    <jsp:include page="../footer.jsp"/>

</div>

</body>

</html>
