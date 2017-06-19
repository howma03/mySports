<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:url htmlEscape="true" var="urlLogin" value="/login"/>
<spring:url htmlEscape="true" var="urlLogout" value="/login?logout"/>
<spring:url htmlEscape="true" var="urlRegistration" value="/registration"/>
<spring:url htmlEscape="true" var="urlPasswordReset" value="/password/reset"/>

<form:form id="myLoginForm" method="POST" autocomplete="off" action="${urlLogin}"
           always-use-default-target="true">

    <b>If you are a registered user then please login</b>

    <hr/>

    <div class="form-group">

        <label class="control-label">Email Address</label>

        <input
                type="text" style="height: 36px" class="form-control"
                id="username" name="username" value="" required="true"
                autocomplete="false"
                title="Please enter you email address"
                placeholder="example@gmail.com"> <span class="help-block"></span>

        <label class="control-label">Password</label>

        <input
                type="password" style="height: 36px" class="form-control"
                autocomplete="false"
                id="password" name="password" value="" required="true"
                title="Please enter your password"> <span class="help-block"></span>
    </div>

    <c:if test="${errors != null}">
        <div class="alert alert-error" id="loginErrors">
            <c:if test="${errors.getFieldError('email') != null}">
                <div class="alert-text">${errors.getFieldError("email").getDefaultMessage()}</div>
            </c:if>
            <c:if test="${errors.getFieldError('password') != null}">
                <div class="alert-text">${errors.getFieldError("password").getDefaultMessage()}</div>
            </c:if>
            <c:if test="${errors.getFieldError('invalidCredentials') != null}">
                <div class="alert-text">${errors.getFieldError("invalidCredentials").getDefaultMessage()}</div>
            </c:if>
            <div class="alert-text">${errors}</div>
        </div>

    </c:if>

    <c:if test="${not empty param}">
        <div class="alert alert-error" id="loginErrors">
            <div class="alert-text">Login failed - user not known or
                password invalid
            </div>
        </div>
    </c:if>

    <button type="submit" class="btn btn-primary">login</button>

    <hr/>

    <p>If you have forgotten your password then reset it.</p>

    <button class="btn btn-danger" type="button"
            onClick="window.location.href='${urlPasswordReset}'">Reset Password
    </button>

    <hr/>

    <p>If you do not yet have an account then you may register.</p>

    <button class="register-btn btn btn-success" type="button"
            onClick="window.location.href='${urlRegistration}'">Register for Free
    </button>

    <hr/>

    <div>
        <p class="bg-primary">
        <div class="checkbox" disabled="true">
            <label> <input type="checkbox" name="remember-me" id="remember-me" disabled="true"> Remember me
            </label> <label class="help-block">(if this is a private computer)</label>
        </div>
    </div>


</form:form>

<script>
    $("#j_username").focus();

    $("#mySignInForm").validate({
        rules: {
            field: {
                required: true,
                email: true
            }
        }
    });
</script>

