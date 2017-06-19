<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div>

    <div>

        <br/>

        <div>

            <div class="btn-group">
                <button class="btn-refresh-groups btn btn-info">Refresh</button>
            </div>

            <hr/>

            <table id="table-groups" class="display table table-bordered">
                <thead>
                <tr>
                    <th>Id</th>
                    <th>Created</th>
                    <th>Enabled</th>
                    <th>Name</th>
                    <th>Owner Organisation</th>
                    <th>Members</th>
                </tr>
                </thead>
                <tbody>

                </tbody>
            </table>

        </div>

        <!-- if an group is selected then we want to show the users for that group  -->

        <hr/>

        <h3>Group's Members</h3>

        <hr/>

        <div class="btn-group">
            <button class="btn-edit-group-user btn btn-warning" disabled="true">Edit</button>
            <button class="btn-create-group-user btn btn-primary" disabled="true">Add</button>
            <button class="btn-delete-group-user btn btn-danger" disabled="true">Delete</button>
            <button class="btn-refresh-group-users btn btn-info">Refresh</button>
        </div>

        <hr/>

        <table id="table-group-user-memberships" style="width:100%;" class="display table table-bordered">
            <thead>
            <tr>
                <th>Identifier</th>
                <th>Created</th>
                <th>Enabled</th>
                <th>User Name</th>
                <th>Group Name</th>
                <th>Permissions</th>
                <th>Status</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>

    </div>

    <script type="text/javascript" language="javascript" class="init">

        $(document).ready(function () {

            var tableGroups = $('#table-groups').DataTable({
                "ajax": MYSPORTSAPP.groupsUrl(),
                rowId: 'id',
                columns: [
                    {data: "id"},
                    {data: "createdString"},
                    {data: "enabled"},
                    {data: "name"},
                    {data: "ownerOrganisationId"},
                    {data: "countMembers"}
                ],
                "order": [[0, "desc"]]
            });

            var tableGroupUsers = $('#table-group-user-memberships').DataTable({
                "ajax": MYSPORTSAPP.groupMembershipsForGroupManagementUrl() + "-1",
                rowId: 'id',
                columns: [
                    {data: "id"},
                    {data: "createdString"},
                    {data: "enabled"},
                    {data: "userName"},
                    {data: "groupName"},
                    {data: "permissions"},
                    {data: "status"}
                ],
                "order": [[0, "desc"]]
            });

            $('#table-groups tbody').on('click', 'tr', function () {

                $('.btn-edit-group-user').attr('disabled', 'true');
                $('.btn-create-group-user').attr('disabled', 'true');
                $('.btn-delete-group-user').attr('disabled', 'true');
                $('.btn-refresh-group-user').attr('disabled', 'true');

                tableGroupUsers.clear().draw();

                if ($(this).hasClass('selected')) {
                    $(this).removeClass('selected');
                }
                else {
                    tableGroups.$('tr.selected').removeClass('selected');
                    $(this).addClass('selected');

                    var data = tableGroups.row(this).data();
                    if (data) {
                        var groupId = parseInt(data.id);

                        $('.btn-create-group-user').removeAttr('disabled');
                        $('.btn-create-group-user').attr("data-group-name", data.name);
                        $('.btn-create-group-user').attr("data-group-identifier", groupId);
                        $('.btn-refresh-group-user').removeAttr('disabled');

                        // Now we reload the groups table with the selected organisation
                        tableGroupUsers.ajax.url(MYSPORTSAPP.groupMembershipsForGroupManagementUrl() + groupId).load();
                    }
                }
            });

            var refresh = function () {
                tableGroups.ajax.reload();
            };
            setInterval(function () {
                refresh();
            }, 60000);

            $('.btn-refresh-groups').click(function () {
                refresh();
            });


            $('#table-group-user-memberships tbody').on('click', 'tr', function () {

                $('.btn-edit-group-user').attr('disabled', 'disabled');
                $('.btn-delete-group-user').attr('disabled', 'disabled');

                if ($(this).hasClass('selected')) {
                    $(this).removeClass('selected');
                }
                else {
                    tableGroupUsers.$('tr.selected').removeClass('selected');
                    $(this).addClass('selected');

                    var data = tableGroupUsers.row(this).data();
                    if (data) {
                        var membershipId = data.id;
                        if (!membershipId) {
                            membershipId = 0;
                        }
                        $('.btn-edit-group-user').attr("data-identifier", membershipId);
                        $('.btn-edit-group-user').removeAttr('disabled');
                        $('.btn-delete-group-user').attr("data-identifier", membershipId);
                        $('.btn-delete-group-user').removeAttr('disabled');
                    }
                }
            });

            refresh();


            var refreshGroupUsers = function () {
                tableGroupUsers.ajax.reload();
            };

            $('.btn-create-group-user').click(function () {
                var groupId = $(this).attr('data-group-identifier');
                var groupName = $(this).attr('data-group-name');

                wireUpFormFnc();

                $('#formGroupMembership #btn-submit').attr('value', 'Add');
                $('#formGroupMembership #info-create').removeAttr('hidden');
                $('#formGroupMembership').trigger("reset"); // Clear data from the form

                $('#formGroupMembership #groupId').val(groupId);
                $('#formGroupMembership #groupName').val(groupName);

                $('#formGroupMembership #email').removeAttr('disabled');
                $('#formGroupMembership #firstName').removeAttr('disabled');
                $('#formGroupMembership #lastName').removeAttr('disabled');
                $('#formGroupMembership #status').attr('disabled', 'true');

                $('#modalGroupMembership').modal('show');
            });
            $('.btn-refresh-group-users').click(function () {
                tableGroupUsers.ajax.reload();
            });

            $('.btn-edit-group-user').click(function () {
                var id = parseInt($(this).attr("data-identifier"));
                if (id) {
                    wireUpFormFnc();

                    $('#formGroupMembership #btn-submit').attr('value', 'Update');
                    $('#formGroupMembership #info-create').attr('hidden', "true");
                    MYSPORTSAPP.readGroupMemberManagement(id,
                        function (data) {
                            $('#formGroupMembership #email').attr('disabled', 'true');
                            $('#formGroupMembership #firstName').attr('disabled', 'true');
                            $('#formGroupMembership #lastName').attr('disabled', 'true');
                            $('#formGroupMembership #status').removeAttr('disabled');

                            $('#formGroupMembership').trigger("reset"); // Clear data from the form
                            $('#formGroupMembership #id').val(data.id);
                            $('#formGroupMembership #groupName').val(data.groupName);
                            $('#formGroupMembership #groupId').val(data.groupId);
                            $('#formGroupMembership #userId').val(data.userId);
                            $('#formGroupMembership #email').val(data.email);
                            $('#formGroupMembership #firstName').val(data.firstName);
                            $('#formGroupMembership #lastName').val(data.lastName);
                            $('#formGroupMembership #permissions').val(data.permissions);
                            $('#formGroupMembership #status').val(data.status);
                            $('#modalGroupMembership').modal('show');
                        },
                        function () {
                            MYSPORTSAPP.defaultFailureFn("Update Group Membership(read operation) failed");
                        }
                    );
                }
            });

            var wireUpFormFnc = function () {
                $('#formGroupMembership').submit(function (event) {
                    event.preventDefault();
                    var elements = this.elements;
                    var membership = {
                        id: elements.id.value,
                        userId: elements.userId.value,
                        groupName: elements.groupName.value,
                        groupId: elements.groupId.value,
                        email: elements.email.value,
                        firstName: elements.firstName.value,
                        lastName: elements.lastName.value,
                        permissions: elements.permissions.value,
                        status: elements.status.value
                    };
                    if (!membership.id) {
                        MYSPORTSAPP.createGroupMemberManagement(membership,
                            function () {
                                MYSPORTSAPP.defaultSuccessFn();
                                refreshGroupUsers();
                            },
                            function () {
                                MYSPORTSAPP.defaultFailureFn("Create Group Membership (save operation) failed");
                            }
                        );
                    } else {
                        MYSPORTSAPP.updateGroupMemberManagement(membership,
                            function () {
                                MYSPORTSAPP.defaultSuccessFn();
                                refreshGroupUsers();
                            },
                            function () {
                                MYSPORTSAPP.defaultFailureFn("Update Group Membership (save operation) failed");
                            }
                        );
                    }
                });
            }
        });

    </script>

</div>
