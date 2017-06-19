<!DOCTYPE html>
<html lang="en">

    <%@ page language="java" contentType="text/html; charset=UTF-8"
             pageEncoding="UTF-8"%>

    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

    <jsp:include page="../header.jsp" />

    <script type="text/javascript" language="javascript" class="init">
        $(document).ready(function() {
            MYSPORTSAPP.init("${urlServiceBase}", "hello", "${urlLogin}");
        });
    </script>

    <body>

        <jsp:include page="navigation.jsp" />

        <jsp:include page="carousel.jsp" />

        <jsp:include page="../modals/profile.jsp" />
        <jsp:include page="../modals/organisation.jsp" />
        <jsp:include page="../modals/group.jsp" />
        <jsp:include page="../modals/group-membership.jsp" />
        <jsp:include page="../modals/sport.jsp" />

        <!-- Page Content -->

        <div class="container">

            <div class="row">
                <div class="col-lg-12">

                    <br/>

                    <ul class="nav nav-tabs">

                        <c:if test="${loggedInUser.isAdmin()}">
                            <li><a class="active" data-toggle="tab" href="#service-management">Service Management</a></li>
                        </c:if>

                        <c:if test="${not empty ownedOrganisations}">
                            <li><a data-toggle="tab" href="#organisation-management">Organisation Management</a></li>
                        </c:if>

                        <c:if test="${not empty groupAdmin}">
                            <li><a data-toggle="tab" href="#group-management">Group Management</a></li>
                        </c:if>

                     </ul>

                    <div class="tab-content">

                        <c:if test="${loggedInUser.isAdmin()}">
                            <div id="service-management" class="active tab-pane fade in">

                                <jsp:include page="component-management-organisations.jsp"/>

                            </div>
                        </c:if>

                        <c:if test="${not empty ownedOrganisations}">
                            <div id="organisation-management" class="tab-pane fade in">

                            </div>
                        </c:if>

                        <c:if test="${not empty groupAdmin}">
                            <div id="group-management" class="tab-pane fade in">


                            </div>
                        </c:if>

                     </div>
                </div>
            </div>

            <hr>

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


</html>
