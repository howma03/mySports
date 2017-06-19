<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:url var="urlLogin" value="/login"/>
<spring:url var="urlRegister" value="/registration/simple"
            htmlEscape="true"/>

<form:form id="myRegistrationForm" method="POST" action="${urlRegister}"
           commandName="userRegistrationForm">

    <div class="form-group">

        <div class="well">
            <label>Provide your details</label>
        </div>

        <label for="email" class="control-label">Email Address</label>
        <form:input class="form-control" id="email" path="email" type="email"
                    style="height: 36px" placeholder="example@gmail.com"
                    autocomplete="false" required="true"/>

        <label for="firstName" class="control-label">First Name</label>
        <form:input class="form-control input text-input" id="firstName"
                    path="firstName" style="height: 36px" placeholder="First Name"
                    autocomplete="false" required="true"/>

        <label for="lastName" class="control-label">Last Name</label>
        <form:input class="form-control input text-input" id="lastName"
                    path="lastName" style="height: 36px" placeholder="Last Name"
                    autocomplete="false" required="true"/>

        <label for="password" class="control-label">Password</label>
        <form:password class="form-control input text-input" id="password"
                       path="password" style="height: 36px" placeholder="Password"
                       autocomplete="false" required="true"/>

        <label for="confirmPassword" class="control-label">Confirm
            Password</label>
        <form:password class="form-control input text-input"
                       id="password-confirm" autocomplete="false" path="confirmPassword"
                       style="height: 36px" placeholder="Confirm Password"
                       disabled="disabled" required="true"/>
    </div>

    <c:if test="${errors != null}">
        <div class="serverErrors alert alert-error">
            <c:if test="${errors.getFieldError('email') != null}">
                <div class="alert-text">${errors.getFieldError("email").getDefaultMessage()}</div>
            </c:if>
            <c:if test="${errors.getFieldError('lastName') != null}">
                <div class="alert-text">${errors.getFieldError("lastName").getDefaultMessage()}</div>
            </c:if>
            <c:if test="${errors.getFieldError('firstName') != null}">
                <div class="alert-text">${errors.getFieldError("firstName").getDefaultMessage()}</div>
            </c:if>
            <c:if test="${errors.getFieldError('password') != null}">
                <div class="alert-text">${errors.getFieldError("password").getDefaultMessage()}</div>
            </c:if>
            <c:if test="${errors.getFieldError('confirmPassword') != null}">
                <div class="alert-text">${errors.getFieldError("confirmPassword").getDefaultMessage()}</div>
            </c:if>
            <c:if test="${errors.getFieldError('failure') != null}">
                <div class="alert-text">${errors.getFieldError("failure").getDefaultMessage()}</div>
            </c:if>
        </div>
    </c:if>

    <hr/>

    <div class="btn-group">
        <button id="submit" class="btn btn-primary" type="submit">Register</button>
        <button class="btn btn-warning" type="button"
                onClick="window.location.href='${urlLogin}'">Cancel
        </button>
    </div>
</form:form>

<script>
    $("#myRegistrationForm").validate({
        rules: {
            field: {
                required: true,
                email: true
            }
        }
    });
</script>