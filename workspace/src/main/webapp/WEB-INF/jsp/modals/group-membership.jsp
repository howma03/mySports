<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div id="modalGroupMembership" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <!-- Modal content-->
        <!-- cna be either a PUT or a POST Operation -->
        <form id="formGroupMembership" method="POST" autocomplete="off">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">Group Membership</h4>
                </div>
                <div class="modal-body">
                    <p>Group user membership details</p>

                    <hr/>

                    <div hidden class="well">
                        <input type="text" style="height: 36px" class="form-control"
                               id="id" name="id" value="" required="true"
                               autocomplete="false"
                               title="Identifier"
                               placeholder="Identifier" disabled="true"/>
                        <span class="help-block"></span>
                    </div>
                    <div hidden>
                        <input type="text" style="height: 36px" class="form-control"
                               id="groupId" name="groupId" value="" required="true"
                               autocomplete="false"
                               title="Group Id"
                               placeholder="Group Identifier" disabled="true"/>
                        <span class="help-block"></span>
                    </div>
                    <div hidden>
                        <input type="text" style="height: 36px" class="form-control"
                               id="userId" name="userId" value=""
                               autocomplete="false"
                               title="User Id"
                               placeholder="User Identifier" disabled="true"/>
                        <span class="help-block"></span>
                    </div>

                    <div>
                        <input type="text" style="height: 36px" class="form-control"
                               id="groupName" name="groupName" value="" required="true"
                               autocomplete="false"
                               title="Group Name"
                               placeholder="Group Name"
                               disabled="true"/>
                        <span class="help-block"></span>
                    </div>
                    <div>
                        <input type="text" style="height: 36px" class="form-control"
                               id="email" name="email" value="" required="true"
                               autocomplete="false"
                               title="Email"
                               placeholder="email address"
                               rows="5"
                               maxWidth="100%"/>
                        <span class="help-block"></span>
                    </div>
                    <div>
                        <input type="text" style="height: 36px" class="form-control"
                               id="firstName" name="firstName" value="" required="true"
                               autocomplete="false"
                               title="First Name"
                               placeholder="First Name"
                               rows="5"
                               maxWidth="100%"/>
                        <span class="help-block"></span>
                    </div>
                    <div>
                        <input type="text" style="height: 36px" class="form-control"
                               id="lastName" name="lastName" value="" required="true"
                               autocomplete="false"
                               title="Last Name"
                               placeholder="Last Name)"
                               rows="5"
                               maxWidth="100%"/>
                        <span class="help-block"></span>
                    </div>
                    <div>
                        <select name="permissions" id="permissions" class="form-control">
                            <c:forEach items="${permissions.values()}" var="option">
                                <option value="${option}">
                                    <c:out value="${option}"></c:out>
                                </option>
                            </c:forEach>
                        </select>
                        <span class="help-block"></span>
                    </div>
                    <div>
                        <select name="status" id="status" class="form-control">
                            <c:forEach items="${status.values()}" var="option">
                                <option value="${option}">
                                    <c:out value="${option.getName()}"></c:out>
                                </option>
                            </c:forEach>
                        </select>
                        <span class="help-block"></span>
                    </div>

                    <hr/>

                    <div id="info-create" class="well">
                        <p class="well">Once you have invited a user they will receive an email and will be able to
                            confirm they wish to join the group</p>

                        <p class="well">They may also decide to decline the invitation - in which case this will be
                            recorded
                            against their group membership record</p>
                    </div>

                    <hr/>

                </div>
                <div class="modal-footer">
                    <input id="btn-submit" type="submit" class="btn btn-primary" value="Add or Update"/>
                    <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel"/>
                </div>
            </div>
        </form>
    </div>
</div>