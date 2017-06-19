<html>

<%@ page session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:include page="../header.jsp" />

<link rel="stylesheet"
      href="<spring:url value="/resources/styles/login.css" />"
      type="text/css" media="all" />

<spring:url htmlEscape="true" var="urlLogin" value="/login"  />

<div class="wrap">
    <div class="stacked">

        <h2>Password Reset Failed</h2>

        <form>
            <div class="alert alert-error" id="loginErrors">
                <label>
                    Your attempt to reset the password for (<b>${email}</b>) has failed.
                </label>
                <hr/>
                <div class="alert-text">${errorsEmail.getDefaultMessage()}</div>
                <div class="alert-text">${errorsIdentifier.getDefaultMessage()}</div>
                <hr/>
            </div>


            <hr/>

            <div class="well">
                <label>We have generated another email with details of
                    how to re-attempt the reset.</label>
                <hr/>
                <label>Please check your email - if our email does not
                    arrive shortly please check your SPAM filters.</label>
            </div>

            <hr/>

            <div class="btn-group">
                <button class="btn btn-primary" type="button"
                    onClick="window.location.href='${urlLogin}'">Login</button>
            </div>
        </form>

    </div>
</div>

</html>
