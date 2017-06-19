<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:url var="urlLogin" value="/login"/>
<spring:url var="urlPasswordChange" value="/password/change/submit"/>

<form:form id="main" method="POST" action="${urlPasswordChange}"
           commandName="form">

    <div class="well alert-success">Please choose a new password for
        your account.
    </div>

    <hr/>

    <label class="control-label" for="email">Email Address</label>

    <form:input class="form-control" id="email" path="email"
                placeholder="Email" style="height: 36px"/>

    <div hidden="true">
        <label class="control-label" for="email">Old Password</label>

        <form:input class="form-control" id="password-old"
                    path="oldPassword" placeholder="Old Password"
                    data-content="Please provide your old password - passwords should be at least 8 characters long and contain at least 1 number and 1 capital letter. The confirmation password field will be enabled once you provide a valid password here."
                    rel="popover" data-original-title="Password" style="height: 36px"/>
    </div>

    <label class="control-label" for="password">Password</label>

    <form:password class="form-control" id="password"
                   path="password" placeholder="Password"
                   data-content="Please provide your new password - passwords should be at least 8 characters long and contain at least 1 number and 1 capital letter. The confirmation password field will be enabled once you provide a valid password here."
                   rel="popover" data-original-title="Password" style="height: 36px"/>


    <label class="control-label" for="confirmPassword">Confirm Password</label>

    <form:password class="form-control" id="password-confirm"
                   path="confirmPassword" placeholder="Confirm Password"
                   disabled="disabled"
                   data-content="Please confirm your new password - passwords should be at least 8 characters long and contain at least 1 number and 1 capital letter. The confirmation password field will be enabled once you provide a valid password here."
                   rel="popover" data-original-title="Confirm Password" style="height: 36px"/>

    <div id="errors" hidden="true" class="errors alert alert-error">
        <hr/>
        <div id="errors-name" class="alert-text"></div>
        <div id="errors-email" class="alert-text"></div>
        <div id="errors-password-old" class="alert-text"></div>
        <div id="errors-password" class="alert-text"></div>
        <div id="errors-password-confirm" class="alert-text"></div>
    </div>
    <div>
        <c:if test="${errors != null}">
            <c:if test="${errors.getFieldError('failure') != null}">
                <div class="serverErrors alert alert-error" id="loginErrors">
                    <div class="alert-text">${errors.getFieldError("failure").getDefaultMessage()}</div>
                </div>
            </c:if>
        </c:if>
    </div>

    <c:if test="${success != null}">
        <hr/>
        <div id="success" class="alert alert-success">${success}</div>
    </c:if>

    <c:if test="${errors != null}">
        <hr/>
        <c:if test="${errors.getFieldError('email') != null}">
            <div class="serverErrors alert alert-error" id="loginErrors">
                <div class="alert-text">${errors.getFieldError("email").getDefaultMessage()}</div>
            </div>
        </c:if>
        <c:if test="${errors.getFieldError('oldPassword') != null}">
            <div class="serverErrors alert alert-error" id="loginErrors">
                <div class="alert-text">${errors.getFieldError("oldPassword").getDefaultMessage()}</div>
            </div>
        </c:if>
        <c:if test="${errors.getFieldError('password') != null}">
            <div class="serverErrors alert alert-error" id="loginErrors">
                <div class="alert-text">${errors.getFieldError("password").getDefaultMessage()}</div>
            </div>
        </c:if>
        <c:if test="${errors.getFieldError('confirmPassword') != null}">
            <div class="serverErrors alert alert-error" id="loginErrors">
                <div class="alert-text">${errors.getFieldError("confirmPassword").getDefaultMessage()}</div>
            </div>
        </c:if>
    </c:if>

    <hr/>

    <div class="btn-group">
        <c:choose>
            <c:when test="${success != null}">
                <button class="btn btn-primary" type="button"
                        onClick="window.location.href='${urlLogin}'">Login
                </button>
            </c:when>
            <c:otherwise>
                <button id="submit" class="btn btn-primary" type="submit" value="Set Password">Set Password</button>
                <button class="btn btn-danger" type="button" onClick="window.location.href='${urlLogin}'">Cancel
                </button>
            </c:otherwise>
        </c:choose>
    </div>
</form:form>

