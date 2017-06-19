<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:url var="urlLogin" value="/login"/>
<spring:url var="urlPasswordReset" value="/password/reset"/>

<form:form id="myPasswordResetForm" method="POST" action="${urlPasswordReset}"
           commandName="form">

    <div class="field" class="form-control">

        <label>Please provide your email address - we
            will send a email with password reset instructions to you.</label>

        <hr/>

        <label class="control-label">Email Address</label>

        <form:input class="form-control" type="text" id="email" path="email"
                    name="email" style="height: 36px" placeholder="example@gmail.com"
                    autocomplete="false"/>
    </div>

    <c:if test="${errors != null}">

        <hr/>

        <div class="serverErrors alert alert-error">
            <c:if test="${errors.getFieldError('email') != null}">
                <div class="alert-text">${errors.getFieldError("email").getDefaultMessage()}</div>
            </c:if>
            <c:if test="${errors.getFieldError('id') != null}">
                <div class="alert-text">${errors.getFieldError("id").getDefaultMessage()}</div>
            </c:if>
        </div>
    </c:if>

    <hr/>

    <div class="btn-group">
        <button id="submit" type="submit" class="btn btn-primary">Reset</button>
        <button class="btn btn-warning" type="button"
                onClick="window.location.href='${urlLogin}'">Cancel
        </button>
    </div>

</form:form>