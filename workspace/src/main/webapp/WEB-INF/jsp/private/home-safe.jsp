<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <%@ page language="java" contentType="text/html; charset=UTF-8"
             pageEncoding="UTF-8" %>

    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

    <title>My sports</title>

    <jsp:include page="../header.jsp"/>

    <!-- Custom CSS -->
    <link href="<spring:url value="/resources/styles/half-slider.css" />" rel="stylesheet">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

    <spring:url htmlEscape="true" var="urlServiceBase" value="/api"/>
    <spring:url htmlEscape="true" var="urlProfile" value="/api/user/profile/"/>
    <spring:url htmlEscape="true" var="urlsport" value="/api/sport/"/>
    <spring:url htmlEscape="true" var="urlReportSummary" value="/api/report/summary"/>
    <spring:url htmlEscape="true" var="urlLogout" value="/logMeOut"/>

</head>

<body>

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
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li>
                    <a href="#">About</a>
                </li>
                <li>
                    <a href="#">Services</a>
                </li>
                <li>
                    <a href="#">Contact</a>
                </li>
                <c:if test="${not empty loggedInUser}">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">${loggedInUser.getEmail()}<b
                                class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li><a tabindex="-1" href="${urlLogout}">Logout</a></li>
                            <li><a tabindex="-1" class="profile-btn-edit" href="#">Profile</a></li>
                        </ul>
                    </li>
                </c:if>
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container -->
</nav>

<!-- Half Page Image Background Carousel Header -->
<header id="myCarousel" class="carousel slide">
    <!-- Indicators -->
    <ol class="carousel-indicators">
        <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
        <li data-target="#myCarousel" data-slide-to="1"></li>
        <li data-target="#myCarousel" data-slide-to="2"></li>
    </ol>

    <!-- Wrapper for Slides -->
    <div class="carousel-inner">
        <div class="item active">
            <!-- Set the first background image using inline CSS below. -->
            <div class="fill" style="background-image:url('<spring:url value="/resources/images/image1.jpg"/>');"></div>
            <div class="carousel-caption">
                <h2>A river cuts through rock, not because of its power, but because of its persistence</h2>
            </div>
        </div>
        <div class="item">
            <!-- Set the second background image using inline CSS below. -->
            <div class="fill" style="background-image:url('<spring:url value="/resources/images/image2.jpg"/>');"></div>
            <div class="carousel-caption">
                <h2>Don't watch the clock: Do what it does. KEEP GOING!</h2>
            </div>
        </div>
        <div class="item">
            <!-- Set the third background image using inline CSS below. -->
            <div class="fill" style="background-image:url('<spring:url value="/resources/images/image3.jpg"/>');"></div>
            <div class="carousel-caption">
                <h2>Difficult roads often lead to beutiful destinations</h2>
            </div>
        </div>
    </div>

    <!-- Controls -->
    <a class="left carousel-control" href="#myCarousel" data-slide="prev">
        <span class="icon-prev"></span>
    </a>
    <a class="right carousel-control" href="#myCarousel" data-slide="next">
        <span class="icon-next"></span>
    </a>

</header>

<!-- Page Content -->
<div class="container">

    <div class="row">
        <div class="col-lg-12">

            <br/>

            <ul class="nav nav-tabs">

                <c:if test="${loggedInUser.isAdmin()}">
                    <li><a data-toggle="tab" href="#systemadmin">Service Management</a></li>
                </c:if>

                <c:if test="${not empty ownedOrganisations}">
                    <li><a data-toggle="tab" href="#organisationManagement">Organisation Management</a></li>
                </c:if>

                <c:if test="${not empty groupAdmin}">
                    <li><a data-toggle="tab" href="#groupManagement">Group Management</a></li>
                </c:if>

                <li class="active"><a data-toggle="tab" href="#home">Your sports</a></li>

            </ul>

            <div class="tab-content">

                <c:if test="${loggedInUser.isAdmin()}">
                    <div id="systemadmin" class="tab-pane fade in">

                        <hr/>

                        <div class="btg-group">
                            <button type="button" class="organisation-btn-add btn btn-info btn-lg">Add an Organisation
                            </button>
                        </div>

                        <hr/>

                        <p>The following organisations are registered.</p>

                        <hr/>

                        <table id="organisationsTable" class="display table table-bordered">
                            <thead>
                            <tr>
                                <th>Id</th>
                                <th>Enabled</th>
                                <th>Name</th>
                                <th>Owner</th>
                                <th>Groups</th>
                                <th>Users</th>
                                <th>Operations</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="organisation" items="${adminOrganisations}" varStatus="counter">
                                <tr>
                                    <td>${organisation.getId()}</td>
                                    <td>${organisation.isEnabled()}</td>
                                    <td>${organisation.getName()}</td>
                                    <td>${organisation.getOwnerId()}</td>
                                    <td>??</td>
                                    <td>??</td>
                                    <td>
                                        <div class="btn-group">
                                            <button class="organisation-btn-edit btn btn-warning"
                                                    data-identifier="${organisation.getId()}">Edit
                                            </button>
                                            <button class="organisation-btn-delete btn btn-danger"
                                                    data-identifier="${organisation.getId()}">Delete
                                            </button>
                                            <button class="organisation-btn-managers btn btn-primary"
                                                    data-identifier="${organisation.getId()}">Managers
                                            </button>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:if>

                <c:if test="${not empty ownedOrganisations}">

                    <div id="organisationManagement" class="tab-pane fade in">

                        <br/>

                        <p>You own and manage the following organisations.</p>

                        <br/>

                        <table id="ownedOrganisationsTable" class="display table table-bordered">
                            <thead>
                            <tr>
                                <th>Id</th>
                                <th>Enabled</th>
                                <th>Name</th>
                                <th>Owner</th>
                                <th>Groups</th>
                                <th>Users</th>
                                <th>Operations</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="organisation" items="${ownedOrganisations}" varStatus="counter">
                                <tr>
                                    <td>${organisation.getId()}</td>
                                    <td>${organisation.isEnabled()}</td>
                                    <td>${organisation.getName()}</td>
                                    <td>${organisation.getOwnerId()}</td>
                                    <td>??</td>
                                    <td>??</td>
                                    <td>
                                        <div class="btn-group">
                                            <button class="organisation-btn-edit btn btn-warning"
                                                    data-identifier="${organisation.getId()}">Edit
                                            </button>
                                            <button class="organisation-btn-delete btn btn-danger"
                                                    data-identifier="${organisation.getId()}">Delete
                                            </button>
                                            <button class="group-btn-add btn btn-primary"
                                                    data-identifier="${organisation.getId()}">Add Group
                                            </button>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:if>

                <c:if test="${not empty groupAdmin}">

                    <div id="groupManagement" class="tab-pane fade in">

                        <br/>

                        <p>You are manager for the following groups.</p>

                        <br/>

                        <ul class="nav nav-tabs">

                            <li class="active"><a data-toggle="tab" href="#groupManagementAll">All Groups</a></li>

                            <c:forEach var="group" items="${groupAdmin}" varStatus="counter">
                                <li><a data-toggle="tab" href="#groupManagement${group.getId()}">${group.getName()}</a>
                                </li>
                            </c:forEach>

                        </ul>

                        <div class="tab-content">

                            <div id="groupManagementAll" class="active tab-pane fade in">

                                <br/>

                                <table id="groupsTable" class="display table table-bordered">
                                    <thead>
                                    <tr>
                                        <th>Id</th>
                                        <th>Enabled</th>
                                        <th>Name</th>
                                        <th>Owner Organisation</th>
                                        <th>Members</th>
                                        <th>Operations</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="group" items="${groupAdmin}" varStatus="counter">
                                        <tr>
                                            <td>${group.getId()}</td>
                                            <td>${group.isEnabled()}</td>
                                            <td>${group.getName()}</td>
                                            <td>${group.getOwnerOrganisationId()}</td>
                                            <td>??</td>
                                            <td>
                                                <div class="btn-group">
                                                    <button class="group-btn-edit btn btn-warning"
                                                            data-identifier="${group.getId()}">Edit
                                                    </button>
                                                    <button class="group-btn-add-member btn btn-primary"
                                                            data-identifier="${group.getId()}">Add Member
                                                    </button>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>

                            <c:forEach var="group" items="${groupAdmin}" varStatus="counter">

                                <div id="groupManagement${group.getId()}" class="tab-pane fade in">

                                    <br/>

                                    <p>You can review sports and invite, suspend or remove members here</p>

                                    <br/>

                                    <div class="well">
                                        <p>There are <b>?</b> members of this group</p>
                                        <p>You have <b>?</b> sports ready for review</p>
                                    </div>

                                    <ul class="nav nav-tabs">

                                        <li class="active"><a data-toggle="tab"
                                                              href="#groupsports${group.getId()}">Group
                                            sports</a></li>

                                        <li><a data-toggle="tab" href="#groupMembers${group.getId()}">Group Members</a>
                                        </li>

                                    </ul>

                                    <div class="tab-content">

                                        <div id="groupsports${group.getId()}" class="active tab-pane fade in">

                                            <br/>

                                            <p>You can manage group sports here</p>

                                            <br/>

                                        </div>

                                        <div id="groupMembers${group.getId()}" class="tab-pane fade in">

                                            <br/>

                                            <p>These are the members of the group - you can add, remove or modify their
                                                permissions</p>

                                            <br/>

                                            <table id="groupMembersTable${group.getId()}"
                                                   class="display table table-bordered">
                                                <thead>
                                                <tr>
                                                    <th>User Id</th>
                                                    <th>Name</th>
                                                    <th>Email</th>
                                                    <th>Permissions</th>
                                                    <th>Operations</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <c:forEach var="member" items="${formGroupMembership.getMembers()}"
                                                           varStatus="counter">
                                                    <tr>
                                                        <td>${member.getUserId()}</td>
                                                        <td>${member.getName()}</td>
                                                        <td>${member.getEmail()}</td>
                                                        <td>${member.getPermissions()}</td>
                                                        <td>
                                                            <div class="btn-group">
                                                                <button class="member-btn-remove btn btn-warning"
                                                                        data-identifier="${member.getId()}">Remove
                                                                </button>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </c:if>

                <div id="home" class="tab-pane fade in active">
                    <br/>

                    <div class="well">
                        <table>
                            <tr>
                                <td col-width="50%">
                                    <p>You have added <b id="lastWeekCount">?</b> sports in the last 7 days</p>
                                    <p>You are a member of <b id="groupsCount">?</b> group(s)</p>
                                </td>
                                <td col-width="50%">
                                    <button style="margin-left: 200px;" id="recordsport" type="button"
                                            class="btn btn-info btn-lg">Record an sport
                                    </button>
                                </td>
                            </tr>
                        </table>
                    </div>

                    <ul class="nav nav-tabs">

                        <li class="active"><a data-toggle="tab" href="#homeAll">All</a></li>

                        <c:if test="${not empty groupMemberships}">
                            <c:forEach var="group" items="${groupMemberships}" varStatus="counter">
                                <li><a data-toggle="tab" href="#group${group.getId()}">${group.getName()}</a></li>
                            </c:forEach>
                        </c:if>
                    </ul>

                    <div class="tab-content">

                        <div id="homeAll" class="tab-pane fade in active">
                            <br/>

                            <table id="sportsTable" class="display table table-bordered">
                                <thead>
                                <tr>
                                    <th>Created</th>
                                    <th>Title</th>
                                    <th>Details</th>
                                    <th>Rating</th>
                                    <th>Group</th>
                                </tr>
                                </thead>
                            </table>
                        </div>

                        <c:if test="${not empty groupMemberships}">
                            <c:forEach var="group" items="${groupMemberships}" varStatus="counter">
                                <div id="group${group.getId()}" class="tab-pane fade">
                                    <br/>
                                    <h3>Your sports in ${group.getName()}</h3>

                                    <div class="well">
                                        <p>You have added <b id="lastWeekCount${group.getId()}">?</b> sports in
                                            the last 7 days</p>
                                        <p>You are ranked <b id="rank${group.getId()}">?</b> out of the <b
                                                id="membersOfGroup${group.getId()}">?</b> members of your group</p>
                                    </div>

                                    <table id="sportsTable${group.getId()}" class="display table table-bordered">
                                        <thead>
                                        <tr>
                                            <th>Created</th>
                                            <th>Title</th>
                                            <th>Details</th>
                                            <th>Rating</th>
                                            <th>Group</th>
                                        </tr>
                                        </thead>
                                    </table>
                                </div>
                            </c:forEach>
                        </c:if>
                    </div>
                </div>

            </div>
        </div>
    </div>

    <hr>

    <!-- Modals -->

    <div>

        <c:if test="${loggedInUser.isAdmin() || not empty ownedOrganisations}">

            <div id="myModalOrganisation" class="modal fade" role="dialog">
                <div class="modal-dialog">

                    <!-- Modal content-->

                    <form:form id="myFormOrganisation" modelAttribute="formOrganisation" method="POST"
                               autocomplete="off">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                                <h4 class="modal-title">Organisation</h4>
                            </div>
                            <div class="modal-body">

                                <hr/>

                                <div>
                                    <form:input
                                            type="text" style="height: 36px" class="form-control"
                                            id="id" name="id" path="id" value="" required="true"
                                            autocomplete="false"
                                            title="Identifier"
                                            placeholder="Identifier"
                                            disabled="true"/>
                                    <span class="help-block"></span>
                                </div>
                                <div>
                                    <form:input
                                            type="text" style="height: 36px" class="form-control"
                                            id="name" name="name" path="name" value="" required="true"
                                            autocomplete="false"
                                            title="Name"
                                            placeholder="Name"/>
                                    <span class="help-block"></span>
                                </div>
                                <div>
                                    <form:input
                                            type="text" style="height: 36px" class="form-control"
                                            id="address" name="address" path="address" value="" required="true"
                                            autocomplete="false"
                                            title="Address"
                                            placeholder="Address"/>
                                    <span class="help-block"></span>
                                </div>
                                <div>
                                    <form:input
                                            type="text" style="height: 36px" class="form-control"
                                            id="domain" name="domain" path="domain" value="" required="true"
                                            autocomplete="false"
                                            title="Domain"
                                            placeholder="Domain"/>
                                    <span class="help-block"></span>
                                </div>
                                <div>
                                    <form:input
                                            type="text" style="height: 36px" class="form-control"
                                            id="ownerId" name="ownerId" path="ownerId" value="" required="true"
                                            autocomplete="false"
                                            title="Owner Id"
                                            placeholder="Owner Id"/>
                                    <span class="help-block"></span>
                                </div>

                                <hr/>

                            </div>
                            <div class="modal-footer">
                                <input type="submit" class="btn btn-primary" value="Save"/>
                                <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel"/>
                            </div>
                        </div>
                    </form:form>
                </div>
            </div>

        </c:if>

        <c:if test="${not empty ownedOrganisations}">

            <div id="myModalGroup" class="modal fade" role="dialog">

                <div class="modal-dialog">

                    <!-- Modal content-->

                    <form:form id="myFormGroup" modelAttribute="formGroup" method="POST" autocomplete="off" action="">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                                <h4 class="modal-title">Group</h4>
                            </div>
                            <div class="modal-body">

                                <hr/>

                                <div>
                                    <form:input
                                            type="text" style="height: 36px" class="form-control"
                                            id="ownerOrganisationId" name="ownerOrganisationId"
                                            path="ownerOrganisationId" value="" required="true"
                                            autocomplete="false"
                                            title="Owner Organisation Identifier"
                                            placeholder="Owner Organisation Identifier"
                                            disabled="true"/>
                                    <span class="help-block"></span>
                                </div>
                                <div>
                                    <form:input
                                            type="text" style="height: 36px" class="form-control"
                                            id="id" name="id" path="id" value="" required="true"
                                            autocomplete="false"
                                            title="Identifier"
                                            placeholder="Identifier"
                                            disabled="true"/>
                                    <span class="help-block"></span>
                                </div>
                                <div>
                                    <form:input
                                            type="text" style="height: 36px" class="form-control"
                                            id="name" name="name" path="name" value="" required="true"
                                            autocomplete="false"
                                            title="Name"
                                            placeholder="Name"/>
                                    <span class="help-block"></span>
                                </div>

                                <hr/>

                            </div>
                            <div class="modal-footer">
                                <input type="submit" class="btn btn-primary" value="Save"/>
                                <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel"/>
                            </div>
                        </div>
                    </form:form>
                </div>
            </div>

        </c:if>

        <div id="myModalGroupMembership" class="modal fade" role="dialog">
            <div class="modal-dialog">
                <!-- Modal content-->
                <form:form id="myFormGroupMembership" modelAttribute="formGroupMembership" method="POST"
                           autocomplete="off">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">Group Members</h4>
                        </div>
                        <div class="modal-body">
                            <p>Members of the group can be managed here</p>

                            <hr/>

                            <div>
                                <form:input
                                        type="text" style="height: 36px" class="form-control"
                                        id="groupId" name="groupId" path="groupId" value="" required="true"
                                        autocomplete="false"
                                        title="Group Id"
                                        placeholder="Title" disabled="true"/>
                                <span class="help-block"></span>
                            </div>
                            <div>
                                <form:input
                                        type="text" style="height: 36px" class="form-control"
                                        id="groupName" name="groupName" path="groupName" value="" required="true"
                                        autocomplete="false"
                                        title="Group Name"
                                        placeholder="Group Name"
                                        disabled="true"/>
                                <span class="help-block"></span>
                            </div>
                            <div>
                                <form:textarea
                                        type="text" style="height: 36px" class="form-control"
                                        id="invitedUsers" name="invitedUsers" path="invitedUsers" value=""
                                        required="true"
                                        autocomplete="false"
                                        title="Invited users"
                                        placeholder="Invited Users (email addresses)"
                                        rows="5"
                                        maxWidth="100%" disabled="true"/>
                                <span class="help-block"></span>
                            </div>

                            <hr/>

                            <p class="well">Once you have invited new users they will receive an email and will be able
                                to join the group</p>

                            <hr/>

                        </div>
                        <div class="modal-footer">
                            <input type="submit" class="btn btn-primary" value="Send Invite"/>
                            <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel"/>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>

        <div id="myModalProfile" class="modal fade" role="dialog">
            <div class="modal-dialog">
                <!-- Modal content-->
                <form:form id="myFormProfile" modelAttribute="formUserProfile" method="POST" autocomplete="off"
                           action="${urlProfile}">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">Your Profile</h4>
                        </div>
                        <div class="modal-body">
                            <p>Please update your profile here</p>

                            <hr/>

                            <div>
                                <form:input
                                        type="text" style="height: 36px" class="form-control"
                                        id="firstName" name="firstName" path="firstName" value="" required="true"
                                        autocomplete="false"
                                        title="First Name"
                                        placeholder="First Name"/>
                                <span class="help-block"></span>
                            </div>
                            <div>
                                <form:input
                                        type="text" style="height: 36px" class="form-control"
                                        id="lastName" name="lastName" path="lastName" value="" required="true"
                                        autocomplete="false"
                                        title="Last Name"
                                        placeholder="Last Name"/>
                                <span class="help-block"></span>
                            </div>

                        </div>
                        <div class="modal-footer">
                            <input type="submit" class="btn btn-primary" value="Save"/>
                            <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel"/>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>

        <div id="myModalsport" class="modal fade" role="dialog">
            <div class="modal-dialog">
                <!-- Modal content-->
                <form:form id="myFormsport" modelAttribute="formsport" method="POST" autocomplete="off"
                           action="${urlsport}">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">sport</h4>
                        </div>
                        <div class="modal-body">
                            <p>Please provide details of your sport here</p>

                            <hr/>

                            <div>
                                <form:input
                                        type="text" style="height: 36px" class="form-control"
                                        id="title" name="title" path="title" value="" required="true"
                                        autocomplete="false"
                                        title="sport Title"
                                        placeholder="Title"/>
                                <span class="help-block"></span>
                            </div>
                            <div>
                                <form:textarea
                                        type="text" style="height: 36px" class="form-control"
                                        id="details" name="details" path="details" value="" required="true"
                                        autocomplete="false"
                                        title="sport Description"
                                        placeholder="Details"
                                        rows="5"
                                        maxWidth="100%"/>
                                <span class="help-block"></span>
                            </div>
                            <div>
                                <span class="help-block"></span>
                                <form:select id="rating" name="rating" path="rating" class="form-control">
                                    <form:options items="${ratings}"></form:options>
                                </form:select>
                            </div>
                            <c:if test="${not empty groupMemberships}">
                                <div>
                                    <span class="help-block"></span>
                                    <form:select id="groupId" name="groupId" path="groupId" class="form-control">
                                        <form:options items="${groupMemberships}"></form:options>
                                    </form:select>
                                </div>
                            </c:if>

                            <hr/>

                            <p class="well">Once added you will will be able to modify it for 24 hours before it becomes
                                perminent.</p>

                            <hr/>

                        </div>
                        <div class="modal-footer">
                            <input type="submit" class="btn btn-primary" value="Record"/>
                            <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel"/>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>

    </div>

    <!-- Footer -->

    <footer>
        <div class="row">
            <div class="col-lg-12">
                <p>Copyright &copy; My sports (2017) - version: 2017-05-30 23:30</p>
            </div>
        </div>
        <!-- /.row -->
    </footer>

</div>

<!-- /.container -->

</body>

<!-- Script to Activate the Carousel -->
<script type="text/javascript" language="javascript" class="init">
    $('.carousel').carousel({
        interval: 30000 //changes the speed
    });

    $(document).ready(function () {

        MYSPORTSAPP.init("${urlServiceBase}", "hello", "${urlLogin}");

        var successFn = function () {
            debugger;
            $('.modal.in').modal('hide');
        };
        var failureFn = function (message) {
            debugger;
            alert(message);
        };

        $('.profile-btn-edit').click(function () {
            // obtain user profile details from the server
            debugger;
            MYSPORTSAPP.readProfile(
                function (data) {
                    $('#myFormProfile').trigger("reset"); // Clear data from the form
                    $('#firstName').val(data.firstName);
                    $('#lastName').val(data.lastName);
                    $('#myModalProfile').modal('show');
                },
                function () {
                    failureFn("Update profile (read operation) failed");
                }
            );
        });
        $('#myFormProfile').submit(function (event) {
            event.preventDefault();
            var elements = this.elements;
            var profile = {
                firstName: elements.firstName.value,
                lastName: elements.lastName.value
            };
            MYSPORTSAPP.updateProfile(profile,
                successFn,
                function () {
                    failureFn("Update profile (save operation) failed");
                }
            );
        });

        <!-- sport functions -->

        $('#recordsport').click(function () {
            $('#myFormsport').trigger("reset"); // Clear data from the form
            $('#myModalsport').modal('show');
        });
        $('#myFormsport').submit(function (event) {
            event.preventDefault();
            var elements = this.elements;
            var sport = {
                title: elements.title.value,
                details: elements.details.value,
                rating: elements.rating.value,
                groupId: elements.groupId ? elements.groupId.value : null
            };
            MYSPORTSAPP.createsport(sport,
                function () {
                    successFn();
                    refresh();
                },
                function () {
                    failureFn("Add sport (save operation) failed");
                }
            );
        });

        <!-- Organisation functions -->

        $('#organisationsTable').DataTable();
        $('#ownedOrganisationsTable').DataTable();
        $('.organisation-btn-create').click(function () {
            $('#myFormOrganisation').trigger("reset"); // Clear data from the form
            $('#myModalOrganisation').modal('show');
        });
        $('.organisation-btn-edit').click(function () {
            var id = $(this).attr("data-identifier");
            MYSPORTSAPP.readOrganisation(id,
                function (data) {
                    $('#myFormOrganisation').trigger("reset"); // Clear data from the form
                    $('#myFormOrganisation #id').val(data.id);
                    $('#myFormOrganisation #name').val(data.name);
                    $('#myFormOrganisation #address').val(data.address);
                    $('#myFormOrganisation #domain').val(data.domain);
                    $('#myFormOrganisation #ownerId').val(data.ownerId);
                    $('#myModalOrganisation').modal('show');
                },
                function () {
                    failureFn("Update organisation (read operation) failed");
                }
            );
        });
        $('#myFormOrganisation').submit(function (event) {
            event.preventDefault();
            var elements = this.elements;
            var organisation = {
                id: elements.id.value,
                name: elements.name.value,
                address: elements.address.value,
                domain: elements.domain.value,
                ownerId: elements.ownerId.value
            };
            if (organisation.id) {
                MYSPORTSAPP.updateOrganisation(organisation,
                    successFn,
                    failureFn("Update organisation (save operation) failed")
                );
            } else {
                MYSPORTSAPP.createOrganisation(organisation,
                    successFn,
                    function () {
                        failureFn("Add organisation (save operation) failed");
                    }
                );
            }
        });

        <!-- Group functions -->

        $('#groupsTable').DataTable();
        $('#groupMembersTable').DataTable();
        $('.group-btn-create').click(function () {
            var ownerOrganisationId = $(this).attr("data-identifier");
            $('#myFormGroup').trigger("reset"); // Clear data from the form
            $('#ownerOrganisationId').val(ownerOrganisationId);
            $('#myModalGroup').modal('show');
        });
        $('.group-btn-edit').click(function () {
            var id = $(this).attr("data-identifier");
            MYSPORTSAPP.readGroup(id,
                function (data) {
                    $('#myFormGroup').trigger("reset"); // Clear data from the form
                    $('#myFormGroup #id').val(data.id);
                    $('#myFormGroup #ownerOrganisationId').val(data.ownerOrganisationId);
                    $('#myFormGroup #name').val(data.name);
                    $('#myModalGroup').modal('show');
                },
                function () {
                    failureFn("Update group (read operation) failed");
                }
            );
        });
        $('.group-btn-members').click(function () {
            // TODO: obtain group details from the server
            $('#myFormGroupMembership').trigger("reset"); // Clear data from the form
            $('#myModalGroupMembership').modal('show');
        });
        $('#myFormGroup').submit(function (event) {
            event.preventDefault();
            var elements = this.elements;
            var group = {
                id: elements.id.value,
                ownerOrganisationId: elements.ownerOrganisationId.value,
                name: elements.name.value
            };
            if (!group.id) {
                MYSPORTSAPP.createGroup(group,
                    successFn,
                    function () {
                        failureFn("Create group (save operation) failed");
                    }
                );
            } else {
                MYSPORTSAPP.updateGroup(group,
                    successFn,
                    function () {
                        failureFn("Update group (save operation) failed");
                    }
                );
            }
        });

        var table = $('#sportsTable').DataTable({
            "ajax": "${urlsport}",
            columns: [
                {data: "createdString"},
                {data: "title"},
                {data: "details"},
                {data: "rating"},
                {data: "groupId"}
            ],
            "order": [[0, "desc"]]
        });

        var refresh = function () {
            MYSPORTSAPP.readSummary(
                // We will need to process each group set of summaries
                function (data) {
                    $('#lastWeekCount').text(data.overallSummary.countInLast7Days);
                    $('#groupsCount').text(data.groupRankings.length);

                    // for each group update counts and ranks
                    for (var i = 0, len = data.groupRankings.length; i < len; i++) {
                        var group = data.groupRankings[i];
                        $('#lastWeekCount' + group.groupId).text(group.countInLast7Days);
                        $('#membersOfGroup' + group.groupId).text(group.membersInGroup);
                        $('#rank' + group.groupId).text(group.rank);
                    }
                },
                function () {
                    failureFn("Summary (read operation) failed");
                }
            );
            table.ajax.reload();
        };
        setInterval(function () {
            refresh();
        }, 60000);

        refresh();
    });

</script>

</html>
