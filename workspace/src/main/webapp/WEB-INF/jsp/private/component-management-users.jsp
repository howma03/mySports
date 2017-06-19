<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div>

    <br/>

    <div>

        <div class="btn-group">
            <button class="btn-edit-user btn btn-warning" disabled="true">Edit</button>
            <button class="btn-create-user btn btn-primary">Add</button>
            <button class="btn-delete-user btn btn-danger" disabled="true">Delete</button>
            <button class="btn-refresh-users btn btn-info">Refresh</button>
        </div>

        <hr/>

        <table id="table-users" class="display table table-bordered">
            <thead>
            <tr>
                <th>Id</th>
                <th>Created</th>
                <th>Enabled</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Email</th>
                <th>Groups</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>

    </div>

    <!-- if an user is selected then we want to show the groups for that user  -->

    <hr/>

    <h3>User's Group Membership</h3>

    <hr/>

    <div class="btn-group">
        <button class="btn-edit-user-group-membership btn btn-warning" disabled="true">Edit</button>
        <button class="btn-create-user-group-membership btn btn-primary" disabled="true">Add</button>
        <button class="btn-delete-user-group-membership btn btn-danger" disabled="true">Delete</button>
        <button class="btn-refresh-user-group-membership btn btn-info">Refresh</button>
    </div>

    <hr/>

    <table id="table-user-group-memberships" style="width:100%;" class="display table table-bordered">
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

        var tableUsers = $('#table-users').DataTable({
            "ajax": MYSPORTSAPP.userManagementUrl(),
            rowId: 'id',
            columns: [
                {data: "id"},
                {data: "createdString"},
                {data: "enabled"},
                {data: "firstName"},
                {data: "lastName"},
                {data: "email"},
                {data: "countGroup"}
            ],
            "order": [[0, "desc"]]
        });

        var tableUserMemberships = $('#table-user-group-memberships').DataTable({
            "ajax": MYSPORTSAPP.groupMembershipsForUserManagementUrl() + "-1",
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

        $('#table-users tbody').on('click', 'tr', function () {

            $('.btn-edit-user').attr('disabled', 'true');
            $('.btn-edit-user').removeAttr("data-identifier");
            $('.btn-edit-user-group-membership').attr('disabled', 'true');
            $('.btn-create-user-group-membership').attr('disabled', 'true');
            $('.btn-delete-user-group-membership').attr('disabled', 'true');
            $('.btn-refresh-user-group-membership').attr('disabled', 'true');

            tableUserMemberships.clear().draw();

            if ($(this).hasClass('selected')) {
                $(this).removeClass('selected');
            }
            else {
                tableUsers.$('tr.selected').removeClass('selected');
                $(this).addClass('selected');

                var data = tableUsers.row(this).data();
                if (data) {
                    var userId = data.id;
                    if (!userId) {
                        userId = 0;
                    }
                    $('.btn-edit-user').removeAttr('disabled');
                    $('.btn-edit-user').attr("data-identifier", userId);

                    $('.btn-create-user-group-membership').removeAttr('disabled');
                    $('.btn-create-user-group-membership').attr("data-group-name", data.name);
                    $('.btn-create-user-group-membership').attr("data-group-identifier", userId);
                    $('.btn-refresh-user-group-membership').removeAttr('disabled');

                    // Now we reload the groups table with the selected user
                    tableUserMemberships.ajax.url(MYSPORTSAPP.groupMembershipsForUserManagementUrl() + userId).load();
                }
            }
        });
        var refresh = function () {
            tableUsers.ajax.reload();
        };
        setInterval(function () {
            refresh();
        }, 60000);

        $('.btn-create-user').click(function () {
            $('#formUser .btn-submit').attr('value', 'Add');
            $('#formUser').trigger("reset"); // Clear data from the form
            $('#modalUser').modal('show');
        });
        $('.btn-refresh-users').click(function () {
            refresh();
        });
        $('.btn-edit-user').click(function () {
            var id = $(this).attr("data-identifier");
            if (id) {
                $('#formUser .btn-submit').attr('value', 'Save');
                MYSPORTSAPP.readUserManagement(id,
                    function (data) {
                        $('#formUser').trigger("reset"); // Clear data from the form
                        $('#formUser #id').val(data.id);
                        $('#formUser #email').val(data.email);
                        $('#formUser #firstName').val(data.firstName);
                        $('#formUser #lastName').val(data.lastName);
                        $('#formUser #password').val(data.password);
                        $('#formUser #verificationCode').val(data.verificationCode);
                        $('#modalUser').modal('show');
                    },
                    function () {
                        MYSPORTSAPP.defaultFailureFn("Update service user (read operation) failed");
                    }
                );
            }
        });

        $('#formUser').submit(function (event) {
            event.preventDefault();
            var elements = this.elements;
            var user = {
                id: elements.id.value,
                email: elements.email.value,
                firstName: elements.firstName.value,
                lastName: elements.lastName.value,
                password: elements.password.value,
                verificationCode: elements.verificationCode.value
            };
            if (!user.id) {
                MYSPORTSAPP.createUserManagement(user,
                    function () {
                        MYSPORTSAPP.defaultSuccessFn();
                        refresh();
                    },
                    function () {
                        MYSPORTSAPP.defaultFailureFn("Create User (save operation) failed");
                    }
                );
            } else {
                MYSPORTSAPP.updateUser(user,
                    function () {
                        MYSPORTSAPP.defaultSuccessFn();
                        refresh();
                    },
                    function () {
                        MYSPORTSAPP.defaultFailureFn("Update User (save operation) failed");
                    }
                );
            }
        });


        $('#table-user-group-memberships tbody').on('click', 'tr', function () {
            $('.btn-edit-user-group-membership').attr('disabled', 'disabled');
            $('.btn-delete-user-group-membership').attr('disabled', 'disabled');

            if ($(this).hasClass('selected')) {
                $(this).removeClass('selected');
            }
            else {
                tableUserMemberships.$('tr.selected').removeClass('selected');
                var data = tableUserMemberships.row(this).data();
                if (data) {
                    var membershipId = data.id;
                    if (!membershipId) {
                        membershipId = 0;
                    }
                    $(this).addClass('selected');
                    $('.btn-edit-user-group-membership').attr("data-identifier", membershipId);
                    $('.btn-edit-user-group-membership').removeAttr('disabled');
                    $('.btn-delete-user-group-membership').attr("data-identifier", membershipId);
                    $('.btn-delete-user-group-membership').removeAttr('disabled');
                }
            }
        });

        refresh();

        var refreshUsersMemberships = function () {
            tableUserMemberships.ajax.reload();
        };

        $('.btn-create-user-group-membership').click(function () {
            var groupId = $(this).attr('data-group-identifier');
            var groupName = $(this).attr('data-group-name');

            wireUpFormFnc();

            $('#formGroupMembership #btn-submit').attr('value', 'Add');
            $('#formGroupMembership').trigger("reset"); // Clear data from the form

            $('#formGroupMembership #groupId').val(groupId);
            $('#formGroupMembership #groupName').val(groupName);

            $('#formGroupMembership #email').removeAttr('disabled');
            $('#formGroupMembership #firstName').removeAttr('disabled');
            $('#formGroupMembership #lastName').removeAttr('disabled');
            $('#formGroupMembership #status').attr('disabled', 'true');

            $('#modalGroupMembership').modal('show');
        });
        $('.btn-refresh-user-group-membership').click(function () {
            tableUserMemberships.ajax.reload();
        });

        $('.btn-edit-user-group-membership').click(function () {
            var id = $(this).attr("data-identifier");
            if (!id) {
                id = 0;
            }
            if (id) {
                wireUpFormFnc();

                $('#formGroupMembership #btn-submit').attr('value', 'Update');
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
                            refreshUsersMemberships();
                        },
                        function () {
                            MYSPORTSAPP.defaultFailureFn("Create Group Membership (save operation) failed");
                        }
                    );
                } else {
                    MYSPORTSAPP.updateGroupMemberManagement(membership,
                        function () {
                            MYSPORTSAPP.defaultSuccessFn();
                            refreshUsersMemberships();
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

