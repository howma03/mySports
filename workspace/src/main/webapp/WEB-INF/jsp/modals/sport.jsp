<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div id="modalsport" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <!-- Modal content-->
        <form id="formsport" modelAttribute="formsport" method="POST" autocomplete="off">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">sport</h4>
                </div>
                <div class="modal-body">
                    <p>Please provide details of your sport here</p>

                    <hr/>

                    <div>
                        <input type="text" style="height: 36px" class="form-control"
                               id="id" name="Identifier" value="" required="false"
                               autocomplete="false"
                               title="sport Identifier"
                               placeholder="Identifier"
                               disabled="true"/>
                        <span class="help-block"></span>
                    </div>

                    <div>
                        <input type="text" style="height: 36px" class="form-control"
                               id="title" name="title" value="" required="true"
                               autocomplete="false"
                               title="sport Title"
                               placeholder="Title"/>
                        <span class="help-block"></span>
                    </div>
                    <div>
                        <textarea type="text" style="height: 36px" class="form-control"
                                  id="details" name="details" value="" required="true"
                                  autocomplete="false" maxlength="65535"
                                  title="sport Details"
                                  placeholder="Details"
                                  rows="5"
                                  maxWidth="100%"></textarea>
                        <span class="help-block"></span>
                    </div>
                    <div>
                        <select name="rating" id="rating" class="form-control">
                            <c:forEach items="${ratings.values()}" var="option">
                                <option value="${option}">
                                    <c:out value="${option.getName()}"></c:out>
                                </option>
                            </c:forEach>
                        </select>
                        <span class="help-block"></span>
                    </div>
                    <div>
                        <select name="groupId" id="groupId" class="form-control">
                            <option>
                            </option>
                        </select>
                        <span class="help-block"></span>
                    </div>

                    <hr/>

                    <p class="well">Once added you will will be able to modify it for 24 hours before it becomes
                        permanent.</p>

                    <hr/>

                </div>
                <div class="modal-footer">
                    <input type="submit" class="btn-submit btn btn-primary" value="Add"/>
                    <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel"/>
                </div>
            </div>
        </form>
    </div>

</div>
