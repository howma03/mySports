<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div>

    <div>

        <br/>

        <div>

            <div class="btn-group">
                <button class="btn-edit-organisation btn btn-warning">Edit</button>
                <button class="btn-create-organisation btn btn-primary">Add</button>
                <button class="btn-refresh-organisations btn btn-info">Refresh</button>
            </div>

            <hr/>

            <table id="table-organisations" class="display table table-bordered">
                <thead>
                <tr>
                    <th>Id</th>
                    <th>Created</th>
                    <th>Enabled</th>
                    <th>Name</th>
                    <th>Address</th>
                    <th>Domain</th>
                    <th>Owner</th>
                    <th>Groups</th>
                    <th>Users</th>
                </tr>
                </thead>
                <tbody>

                </tbody>
            </table>

        </div>

        <hr/>

        <h3>Organisation's Groups</h3>

        <hr/>

        <div class="btn-group">
            <button class="btn-edit-organisation-group btn btn-warning" disabled="true">Edit</button>
            <button class="btn-create-organisation-group btn btn-primary" disabled="true">Add</button>
            <button class="btn-delete-organisation-group btn btn-danger" disabled="true">Delete</button>
            <button class="btn-refresh-organisation-groups btn btn-info" disabled="true">Refresh</button>
        </div>

        <hr/>

        <table id="table-organisation-groups" style="width:100%;" class="display table table-bordered">
            <thead>
            <tr>
                <th>Id</th>
                <th>Created</th>
                <th>Enabled</th>
                <th>Name</th>
                <th>Members</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>

    </div>

    <script type="text/javascript" language="javascript" class="init">

        $(document).ready(function () {

            var watcher = {
                callbacks: [],
                data: {
                    organisationId: undefined,
                    organisationName: undefined,
                    groupId: undefined,
                    groupName: undefined
                },
                register: function (callback) {
                    this.callbacks.push(callback);
                },
                notifyAll: function () {

                }
            };

            watcher.register(function (data) {
                if (data.organisationId || data.organisationId === 0) {
                    $('.btn-edit-organisation').removeAttr('disabled');
                    $('.btn-edit-organisation').attr("data-identifier", data.organisationId);
                    $('.btn-create-organisation-group').removeAttr('disabled');
                } else {
                    $('.btn-edit-organisation').attr('disabled', "true");
                    $('.btn-edit-organisation').removeAttr("data-identifier");
                    $('.btn-create-organisation-group').attr('disabled', 'true');
                }
            });

            var setOrganisationSelection = function (orgId) {
                if (orgId || orgId === 0) {
                    $('.btn-edit-organisation').removeAttr('disabled');
                    $('.btn-edit-organisation').attr("data-identifier", orgId);
                    $('.btn-create-organisation-group').removeAttr('disabled');
                    $('.btn-create-organisation-group').attr("data-identifier", orgId);
                    $('.btn-refresh-organisation-groups').removeAttr("disabled");
                } else {
                    $('.btn-edit-organisation').attr('disabled', 'true');
                    $('.btn-edit-organisation').removeAttr("data-identifier");
                    $('.btn-create-organisation-group').attr('disabled', 'true');
                    $('.btn-create-organisation-group').removeAttr("data-identifier");
                    $('.btn-refresh-organisation-groups').attr("disabled", "true");
                }
            };

            var tableOrganisations = $('#table-organisations').DataTable({
                "ajax": MYSPORTSAPP.organisationUrl(),
                rowId: 'id',
                columns: [
                    {data: "id"},
                    {data: "createdString"},
                    {data: "enabled"},
                    {data: "name"},
                    {data: "address"},
                    {data: "domain"},
                    {data: "ownerEmail"},
                    {data: "countGroup"},
                    {data: "countUser"},
                ],
                "order": [[0, "desc"]]
            });

            var tableOrganisationGroups = $('#table-organisation-groups').DataTable({
                "ajax": MYSPORTSAPP.groupsForOrganisationManagementUrl() + "-1",
                rowId: 'id',
                columns: [
                    {data: "id"},
                    {data: "createdString"},
                    {data: "enabled"},
                    {data: "name"},
                    {data: "countMembers"}
                ],
                "order": [[0, "desc"]]
            });

            $('#table-organisations tbody').on('click', 'tr', function () {
                setOrganisationSelection(null);
                setOrganisationGroupSelection(null);
                tableOrganisationGroups.clear().draw();

                if ($(this).hasClass('selected')) {
                    $(this).removeClass('selected');
                    watcher.data.organisationId = undefined;
                    watcher.data.organisationName = undefined;
                    watcher.notifyAll();
                }
                else {
                    tableOrganisations.$('tr.selected').removeClass('selected');

                    var data = tableOrganisations.row(this).data();
                    if (data) {
                        $(this).addClass('selected');

                        var orgId = parseInt(data.id);
                        watcher.data.organisationId = orgId;
                        watcher.data.organisationName = data.name;
                        watcher.notifyAll();

                        setOrganisationSelection(orgId, data.name);

                        // Now we reload the groups table with the selected organisation
                        tableOrganisationGroups.ajax.url(MYSPORTSAPP.groupsForOrganisationManagementUrl() + orgId).load();
                    }
                }
            });
            var refresh = function () {
                tableOrganisations.ajax.reload();
                setOrganisationSelection(null);
            };
            setInterval(function () {
                tableOrganisations.ajax.reload();
            }, 60000);

            var getOrganisations = function (selectedOrganisationId) {
                $('#formGroup #ownerOrganisationId').get(0).options.length = 0;
                MYSPORTSAPP.readOrganisations(
                    function (json) {
                        $.each(json.data, function (i, item) {
                            var option = {
                                value: item.id,
                                text: item.name,
                            };
                            if (selectedOrganisationId == item.id) {
                                option.selected = "selected";
                            }
                            $('#formGroup #ownerOrganisationId').append($('<option>', option));
                        });
                    },
                    function () {
                        MYSPORTSAPP.defaultFailureFn("Update organisations (read operation) failed");
                    }
                );
            };
            var getOwners = function (selectedOwnerId) {
                $('#formOrganisation #ownerId').get(0).options.length = 0;
                MYSPORTSAPP.readUsersManagement(
                    function (json) {
                        $.each(json.data, function (i, item) {
                            var option = {
                                value: item.id,
                                text: item.email,
                            };
                            if (selectedOwnerId === item.id) {
                                option.selected = "selected";
                            }
                            $('#formOrganisation #ownerId').append($('<option>', option));
                        });
                    },
                    function () {
                        MYSPORTSAPP.defaultFailureFn("Update owners (read operation) failed");
                    }
                );
            };

            $('.btn-create-organisation').click(function () {
                // Obtain a list of the users so that owner can be selected from the combo box
                getOwners();
                $('#formOrganisation .btn-submit').attr('value', 'Add');
                $('#formOrganisation').trigger("reset"); // Clear data from the form
                $('#formOrganisation #id').hide();
                $('#formOrganisation #createdString').hide();
                $('#modalOrganisation').modal('show');
            });
            $('.btn-edit-organisation').click(function () {
                var id = $(this).attr("data-identifier");
                if (id) {
                    $('#formOrganisation .btn-submit').attr('value', 'Save');
                    $('#formOrganisation #id').show();
                    $('#formOrganisation #createdString').show();
                    // Obtain a list of the users so that owner can be selected from the combo box
                    MYSPORTSAPP.readOrganisation(id,
                        function (data) {
                            $('#formOrganisation').trigger("reset"); // Clear data from the form
                            $('#formOrganisation #id').val(data.id);
                            $('#formOrganisation #createdString').val(data.createdString);
                            $('#formOrganisation #name').val(data.name);
                            $('#formOrganisation #address').val(data.address);
                            $('#formOrganisation #domain').val(data.domain);
                            $('#formOrganisation #ownerId').val(data.ownerId);
                            $('#formOrganisation #enabled').val(data.enabled ? 1 : 0);
                            getOwners(data.ownerId);
                            $('#modalOrganisation').modal('show');
                        },
                        function () {
                            MYSPORTSAPP.defaultFailureFn("Update service organisation (read operation) failed");
                        }
                    );
                }
            });
            $('.btn-refresh-organisations').click(function () {
                refresh();
            });

            $('#formOrganisation').submit(function (event) {
                event.preventDefault();
                var elements = this.elements;
                var organisation = {
                    id: elements.id.value,
                    name: elements.name.value,
                    address: elements.address.value,
                    domain: elements.domain.value,
                    ownerId: elements.ownerId.value,
                    enabled: elements.enabled.value === 0 ? "false" : "true"
                };
                if (!organisation.id) {
                    MYSPORTSAPP.createOrganisation(organisation,
                        function () {
                            MYSPORTSAPP.defaultSuccessFn()
                            refresh();
                        },
                        function () {
                            MYSPORTSAPP.defaultFailureFn("Create organisation (save operation) failed");
                        }
                    );
                } else {
                    MYSPORTSAPP.updateOrganisation(organisation,
                        function () {
                            MYSPORTSAPP.defaultSuccessFn()
                            refresh();
                        },
                        function () {
                            MYSPORTSAPP.defaultFailureFn("Update organisation (save operation) failed");
                        }
                    );
                }
            });

            var setOrganisationGroupSelection = function (groupId) {
                if (groupId || groupId === 0) {
                    $('.btn-edit-organisation-group').removeAttr('disabled');
                    $('.btn-edit-organisation-group').attr("data-identifier", groupId);
                    $('.btn-delete-organisation-group').removeAttr('disabled');
                    $('.btn-delete-organisation-group').attr("data-identifier", groupId);
                } else {
                    $('.btn-edit-organisation-group').attr('disabled', 'true');
                    $('.btn-edit-organisation-group').removeAttr("data-identifier");
                    $('.btn-delete-organisation-group').attr('disabled', 'true');
                    $('.btn-delete-organisation-group').removeAttr("data-identifier");
                }
            };

            $('#table-organisation-groups tbody').on('click', 'tr', function () {
                setOrganisationGroupSelection(null);
                if ($(this).hasClass('selected')) {
                    $(this).removeClass('selected');
                }
                else {
                    tableOrganisationGroups.$('tr.selected').removeClass('selected');
                    var data = tableOrganisationGroups.row(this).data();
                    if (data) {
                        var groupId = parseInt(data.id);
                        $(this).addClass('selected');
                        setOrganisationGroupSelection(groupId);
                    }
                }
            });

            refresh();

            var refreshOrganisationGroups = function () {
                tableOrganisationGroups.ajax.reload();
                setOrganisationGroupSelection(null);
            };

            $('.btn-edit-organisation-group').click(function () {
                var id = $(this).attr("data-identifier");
                if (id) {
                    MYSPORTSAPP.readGroupManagement(id,
                        function (data) {
                            $('#formGroup').trigger("reset"); // Clear data from the form
                            $('#formGroup #id').val(data.id);
                            $('#formGroup #name').val(data.name);
                            $('#formGroup #enabled').val(data.enabled ? 1 : 0);
                            $('#modalGroup').modal('show');
                            getOrganisations(data.ownerOrganisationId);
                        },
                        function () {
                            MYSPORTSAPP.defaultFailureFn("Update service group (read operation) failed");
                        }
                    );
                }
            });
            $('.btn-create-organisation-group').click(function () {
                getOrganisations($(this).attr("data-identifier"));
                $('#formGroup').trigger("reset"); // Clear data from the form
                $('#modalGroup').modal('show');
            });
            $('.btn-refresh-organisation-groups').click(function () {
                refreshOrganisationGroups();
            });

            $('#formGroup').submit(function (event) {
                event.preventDefault();
                var elements = this.elements;
                debugger;
                var group = {
                    id: elements.id.value,
                    enabled: elements.enabled.value === 0 ? "false" : "true",
                    ownerOrganisationId: elements.ownerOrganisationId.value,
                    name: elements.name.value
                };
                if (!group.id) {
                    MYSPORTSAPP.createGroupManagement(group,
                        function () {
                            MYSPORTSAPP.defaultSuccessFn();
                            refresh();
                            refreshOrganisationGroups();
                        },
                        function () {
                            MYSPORTSAPP.defaultFailureFn("Create Group Membership (save operation) failed");
                        }
                    );
                } else {
                    MYSPORTSAPP.updateGroupManagement(group,
                        function () {
                            MYSPORTSAPP.defaultSuccessFn();
                            refresh();
                            refreshOrganisationGroups();
                        },
                        function () {
                            MYSPORTSAPP.defaultFailureFn("Update Group Membership (save operation) failed");
                        }
                    );
                }
            });
        });

    </script>

</div>
