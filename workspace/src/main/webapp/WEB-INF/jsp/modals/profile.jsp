<div id="modalProfile" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <!-- Modal content-->
        <form id="formProfile" modelAttribute="formUserProfile" method="POST" autocomplete="off">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">Your Profile</h4>
                </div>
                <div class="modal-body">
                    <p>Please update your profile details</p>

                    <hr/>

                    <div>
                        <input
                                type="text" style="height: 36px" class="form-control"
                                id="firstName" name="firstName" path="firstName" value="" required="true"
                                autocomplete="false"
                                title="First Name"
                                placeholder="First Name"/>
                        <span class="help-block"></span>
                    </div>
                    <div>
                        <input
                                type="text" style="height: 36px" class="form-control"
                                id="lastName" name="lastName" path="lastName" value="" required="true"
                                autocomplete="false"
                                title="Last Name"
                                placeholder="Last Name"/>
                        <span class="help-block"></span>
                    </div>

                </div>
                <div class="modal-footer">
                    <input type="submit" class="btn btn-primary" value="Save"/>
                    <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel"/>
                </div>
            </div>
        </form>
    </div>

    <script type="text/javascript" language="javascript" class="init">
        $(document).ready(function () {

            $('.btn-edit-profile').click(function () {
                // obtain user profile details from the server
                MYSPORTSAPP.readProfile(
                    function (data) {
                        $('#formProfile').trigger("reset"); // Clear data from the form
                        $('#formProfile #firstName').val(data.firstName);
                        $('#formProfile #lastName').val(data.lastName);
                        $('#modalProfile').modal('show');
                    },
                    function () {
                        MYSPORTSAPP.defaultFailureFn("Update profile (read operation) failed");
                    }
                );
            });
            $('#formProfile').submit(function (event) {
                event.preventDefault();
                var elements = this.elements;
                var profile = {
                    firstName: elements.firstName.value,
                    lastName: elements.lastName.value
                };
                MYSPORTSAPP.updateProfile(profile,
                    MYSPORTSAPP.defaultSuccessFn,
                    function () {
                        MYSPORTSAPP.defaultFailureFn("Update profile (save operation) failed");
                    }
                );
            });
        });

    </script>

</div>
