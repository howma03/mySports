<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div id="component-sports">

    <jsp:include page="../modals/sport.jsp"/>

    <br/>

    <div class="well">
        <p>You have added <b id="lastWeekCount">?</b> sports in the last 7 days</p>
        <p>You are a member of <b id="groupsCount">?</b> group(s)</p>
    </div>

    <hr/>

    <ul class="nav nav-tabs">
        <li class="active"><a data-toggle="tab" href="#homeAll">Your sports</a></li>
        <li><a data-toggle="tab" href="#groups">Your Groups</a></li>
        <li><a data-toggle="tab" href="#groupMemberships">Your Group Memberships</a></li>
    </ul>

    <div class="tab-content">

        <div id="homeAll" class="tab-pane fade in active">

            <h3>Your sports</h3>

            <hr/>

            <div class="btn-group">
                <button class="btn-edit-sport btn btn-warning">Edit</button>
                <button class="btn-create-sport btn btn-primary">Add</button>
                <button class="btn-refresh-sports btn btn-info">Refresh</button>
            </div>

            <hr/>

            <table id="table-sports" class="display compact table table-bordered" cellspacing="0">
                <thead>
                <tr>
                    <th></th>
                    <th>Identifier</th>
                    <th>Created</th>
                    <th>Days Old</th>
                    <th>Title</th>
                    <th>Rating</th>
                </tr>
                </thead>
            </table>
        </div>

        <div id="groups" class="tab-pane fade in">

            <h3>Your summary</h3>

            <hr/>

            <table id="table-sport-groups" style="width:100%;" class="display table table-bordered">
                <thead>
                <tr>
                    <th rowspan="2">Group</th>
                    <th rowspan="2">Members</th>
                    <th colspan="3">Week</th>
                    <th colspan="3">Overall</th>
                </tr>
                <tr>
                    <th>Count</th>
                    <th>Score</th>
                    <th>Rank</th>
                    <th>Count</th>
                    <th>Score</th>
                    <th>Rank</th>
                </tr>
                </thead>
            </table>
        </div>

        <div id="groupMemberships" class="tab-pane fade in">

            <h3>Your Group Memberships</h3>

            <hr/>

            <table id="table-user-memberships" style="width:100%;" class="display table table-bordered">
                <thead>
                <tr>
                    <th>Identifier</th>
                    <th>Created</th>
                    <th>Enabled</th>
                    <th>User Name</th>
                    <th>Group Name</th>
                    <th>Permissions</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>

    </div>

    <div id="errorsportFixed" class="modal fade">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title"><b>sport is Permanent</b></h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="panel panel-danger">
                        <div class="panel-body">
                            <p>You cannot edit that sport.</p>
                            <p>It is more than 7 days old and is therefore read
                                only</p>
                        </div>
                        <div class="panel-footer">
                            <p>Contact one of your Group managers if you need additional help</p>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>

    <script type="text/javascript" language="javascript" class="init">

        $(document).ready(function () {

            var tableGroups = undefined;

            var table = $('#table-sports').DataTable({
                "ajax": MYSPORTSAPP.sportsUrl(),
                rowId: 'id',
                columnDefs: [
                    {width: 50, targets: 0},
                    {width: 50, targets: 0},
                    {width: 100, targets: 0},
                    {width: 30, targets: 0}
                ],
                fixedColumns: true,
                columns: [
                    {
                        "className": 'details-control',
                        "orderable": false,
                        "data": null,
                        "defaultContent": '',
                        width: 50
                    },
                    {
                        data: "id",
                        width: 50
                    },
                    {
                        data: "createdString",
                        width: 100
                    },
                    {
                        data: "ageInDays",
                        width: 20
                    },
                    {
                        data: "title",
                        width: 400
                    },
                    {
                        data: "rating",
                        width: 100
                    }
                ],
                "order": [[0, "desc"]]
            });

            function format(d) {
                // `d` is the original data object for the row
                return '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">' +
                    '<tr>' +
                    '<td>Details:</td>' +
                    '<td>' + d.details + '</td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td>Group Name:</td>' +
                    '<td>' + d.groupName + '</td>' +
                    '</tr>' +
                    '</table>';
            };

            $('#table-sports tbody').on('click', 'tr', function () {
                $('.btn-edit-sport').attr('disabled', 'disabled');
                if ($(this).hasClass('selected')) {
                    $(this).removeClass('selected');
                    var tr = $(this).closest('tr');
                    var row = table.row(tr);
                    row.child.hide();
                    tr.removeClass('shown');
                }
                else {
                    // Prevent selection if the sport is more than 7 days hours old
                    var tr = $(this).closest('tr');
                    var row = table.row(tr);
                    if (row.child.isShown()) {
                        // This row is already open - close it
                        row.child.hide();
                        tr.removeClass('shown');
                    }
                    else {
                        // Open this row
                        if (row.data()) {
                            row.child(format(row.data())).show();
                            tr.addClass('shown');
                        }
                    }

                    var data = table.row(this).data();
                    if (data) {
                        if (data.ageInDays && data.ageInDays > 7) {
                            return;
                        }
                        table.$('tr.selected').removeClass('selected');
                        $(this).addClass('selected');
                        $('.btn-edit-sport').removeAttr('disabled');
                        $('.btn-edit-sport').attr("data-identifier", data.id);
                    }
                }
            });

            var getGroups = function () {
                MYSPORTSAPP.readGroups(
                    function (json) {
                        $('#groupId').get(0).options.length = 0;
                        $.each(json.data, function (i, item) {
                            $('#groupId').get(0).options[$('#groupId').get(0).options.length] =
                                new Option(item.name, item.id);
                        });
                    },
                    function () {
                        MYSPORTSAPP.defaultFailureFn("Group (read operation) failed");
                    }
                );
            };

            var refresh = function () {
                table.ajax.reload(function (data) {
                    // $('#myInput').val(json.lastInput);
                }, false);
                tableGroups.ajax.reload(function (data) {
                    // $('#myInput').val(json.lastInput);
                    $('#lastWeekCount').text(data.overallSummary.countInLast7Days);
                    $('#groupsCount').text(data.groupRankings.length);
                }, false);
            };
            setInterval(function () {
                refresh();
            }, 60000);

            $('.btn-create-sport').click(function () {
                getGroups();
                $('#formsport .btn-submit').attr('value', 'Add');
                $('#formsport').trigger("reset"); // Clear data from the form
                $('#modalsport').modal('show');
            });
            $('.btn-refresh-sports').click(function () {
                refresh();
            });

            var subtractDays = function (dateObj, numDays) {
                dateObj.setDate(dateObj.getDate() - numDays);
                return dateObj;
            };

            $('.btn-edit-sport').click(function () {
                getGroups();
                var id = $(this).attr("data-identifier");
                if (id) {
                    $('#formsport .btn-submit').attr('value', 'Save');
                    MYSPORTSAPP.readsport(id,
                        function (data) {
                            var lastWeek = subtractDays(new Date(), 7);
                            // if younger than 7 days then allow edit
                            if (data.created < lastWeek.getTime()) {
                                $('#errorsportFixed').modal('show');
                                return;
                            }

                            $('#formsport').trigger("reset"); // Clear data from the form
                            $('#formsport #id').val(data.id);
                            $('#formsport #createdString').val(data.createdString);
                            $('#formsport #title').val(data.title);
                            $('#formsport #details').val(data.details);
                            $('#formsport #rating').val(data.rating);
                            $('#formsport #groupId').val(data.groupId);
                            $('#modalsport').modal('show');
                        },
                        function () {
                            MYSPORTSAPP.defaultFailureFn("Update service organisation (read operation) failed");
                        }
                    );
                }
            });

            $('#formsport').submit(function (event) {
                event.preventDefault();
                var elements = this.elements;
                var sport = {
                    id: elements.id.value,
                    title: elements.title.value,
                    details: elements.details.value,
                    groupId: elements.groupId.value,
                    rating: elements.rating.value
                };
                if (!sport.id) {
                    MYSPORTSAPP.createsport(sport,
                        function () {
                            MYSPORTSAPP.defaultSuccessFn();
                            refresh();
                        },
                        function () {
                            MYSPORTSAPP.defaultFailureFn("Create sport (save operation) failed");
                        }
                    );
                } else {
                    MYSPORTSAPP.updatesport(sport,
                        function () {
                            MYSPORTSAPP.defaultSuccessFn();
                            refresh();
                        },
                        function () {
                            MYSPORTSAPP.defaultFailureFn("Update sport (save operation) failed");
                        }
                    );
                }
            });

            var tableGroups = $('#table-sport-groups').DataTable({
                "ajax": {
                    "url": MYSPORTSAPP.summaryUrl(),
                    "dataSrc": "groupRankings"
                },
                rowId: 'id',
                columns: [
                    {data: "groupName"},
                    {data: "membersInGroup"},
                    {data: "countInLast7Days"},
                    {data: "totalInLast7Days"}, // last week Score
                    {data: "rankInLast7Days"}, // last week Rank
                    {data: "count"},
                    {data: "total"},
                    {data: "rank"}
                ],
                "order": [[7, "desc"]]
            });

            refresh();
        });

    </script>

</div>