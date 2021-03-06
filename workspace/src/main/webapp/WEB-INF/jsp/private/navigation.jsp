<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<spring:url htmlEscape="true" var="urlLogin" value="/login"/>
<spring:url htmlEscape="true" var="urlLogout" value="/logMeOut"/>
<spring:url htmlEscape="true" var="urlHome" value="/home"/>
<spring:url htmlEscape="true" var="urlManagementService" value="/management/service"/>
<spring:url htmlEscape="true" var="urlManagementOrganisation" value="/management/organisation"/>
<spring:url htmlEscape="true" var="urlManagementGroup" value="/management/group"/>
<spring:url htmlEscape="true" var="urlManagementUser" value="/management/user"/>

<div id="navigation">

    <!-- Navigation -->
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse"
                        data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">My sports</a>
            </div>

            <div id="navbar" class="navbar-collapse collapse">
                <ul class="nav navbar-nav">
                    <li class="${navigation.equals('Home') ? 'active' : ''}"><a href="${urlHome}">Home</a></li>

                    <c:if test="${not empty organisations}">
                        <li class="${navigation.equalsIgnoreCase('organisations') ? 'active' : ''}">
                            <a href="${urlManagementOrganisation}"
                            >Organisations</a>
                        </li>
                    </c:if>
                    <c:if test="${not empty groups}">
                        <li class="${navigation.equalsIgnoreCase('groups') ? 'active' : ''}">
                            <a href="${urlManagementGroup}"
                            >Groups</a>
                        </li>
                    </c:if>

                    <li><a href="#" disabled="true">About</a></li>
                    <li><a href="#" disabled="true">Contact</a></li>

                    <li class="dropdown ${navigation.equals('Management') ? 'active' : ''}">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                           aria-expanded="false">Management <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="${urlManagementService}">Service</a></li>
                            <li><a href="${urlManagementOrganisation}">Organisations</a></li>
                            <li role="separator" class="divider"></li>
                            <!-- <li class="dropdown-header">Nav header</li> -->
                            <li><a href="${urlManagementGroup}">Groups</a></li>
                            <li><a href="${urlManagementUser}">Users</a></li>
                        </ul>
                    </li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                           aria-expanded="false">${loggedInUser.getEmail()}<b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li><a class="btn-edit-profile" href="#">Profile</a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="${urlLogout}">Logout</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</div>
