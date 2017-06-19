<html lang="en">

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<jsp:include page="../header.jsp"/>

<link rel="stylesheet"
      href="<spring:url value="/resources/styles/login.css" />"
      type="text/css" media="all"/>

<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.1/angular.min.js"></script>

<body>
<h2>My-sports</h2>

<jsp:include page="../menu.jsp"/>

<br/>

<%--<div ng-app="">--%>
<%--<label>Name:</label>--%>
<%--<input type="text" ng-model="yourName" placeholder="Enter a name here">--%>
<%--<hr>--%>
<%--<h1>Hello {{yourName}}!</h1>--%>
<%--</div>--%>

<br/>

<div class="well">

    <h3>These are your sports</h3>

    <table data-toggle="table">
        <thead>
        <tr>
            <th>Identifier</th>
            <th>Date</th>
            <th>Title</th>
            <th>Description</th>
            <th>Difficulty</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>1</td>
            <td>2017-01-01</td>
            <td>Completed Angular training</td>
            <td>Completed the online Angualr training course and completed the training examiniation</td>
            <td>***</td>
        </tr>
        <tr>
            <td>2</td>
            <td>2017-01-01</td>
            <td>Completed Angular training</td>
            <td>Completed the online Angualr training course and completed the training examiniation</td>
            <td>***</td>
        </tr>
        <tr>
            <td>3</td>
            <td>2017-01-01</td>
            <td>Completed Angular training</td>
            <td>Completed the online Angualr training course and completed the training examiniation</td>
            <td>***</td>
        </tr>
        </tbody>
    </table>
</div>

<hr/>

</body>
</html>

