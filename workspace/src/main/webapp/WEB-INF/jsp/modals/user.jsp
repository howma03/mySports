<div id="modalUser" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <!-- Modal content-->
        <form id="formUser" modelAttribute="formUserUser" method="POST" autocomplete="off">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">User Details</h4>
                </div>
                <div class="modal-body">
                    <p>Please update user details here</p>

                    <hr/>

                    <div>
                        <input type="text" style="height: 36px" class="form-control"
                               id="id" name="id" value="" required="true"
                               autocomplete="false"
                               title="Identifier"
                               placeholder="Identifier"
                               disabled="true"/>
                        <span class="help-block"></span>
                    </div>

                    <div>
                        <input type="text" style="height: 36px" class="form-control"
                               id="firstName" name="firstName" value="" required="true"
                               autocomplete="false"
                               title="First Name"
                               placeholder="First Name"/>
                        <span class="help-block"></span>
                    </div>
                    <div>
                        <input type="text" style="height: 36px" class="form-control"
                               id="lastName" name="lastName" value="" required="true"
                               autocomplete="false"
                               title="Last Name"
                               placeholder="Last Name"/>
                        <span class="help-block"></span>
                    </div>

                    <div>
                        <input type="text" style="height: 36px" class="form-control"
                               id="email" name="email" value="" required="true"
                               autocomplete="false"
                               title="Email"
                               placeholder="Email"/>
                        <span class="help-block"></span>
                    </div>

                    <div>
                        <input type="text" style="height: 36px" class="form-control"
                               id="verificationCode" name="verificationCode" value=""
                               autocomplete="false"
                               title="Verification Code"
                               placeholder="Verification Code"
                               disabled="true"/>
                        <span class="help-block"></span>
                    </div>

                    <div>
                        <input type="text" style="height: 36px" class="form-control"
                               id="password" name="password" value=""
                               autocomplete="false"
                               title="Password"
                               placeholder="Password"/>
                        <span class="help-block"></span>
                    </div>
                </div>

                <div class="modal-footer">
                    <input type="submit" class="btn btn-submit btn-primary" value="Save"/>
                    <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel"/>
                </div>
            </div>
        </form>
    </div>

</div>
