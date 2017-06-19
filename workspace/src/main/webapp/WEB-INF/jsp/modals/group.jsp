<div id="modalGroup" class="modal fade" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->

        <form id="formGroup" method="POST" autocomplete="off" action="">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">Group</h4>
                </div>
                <div class="modal-body">

                    <hr/>

                    <div hidden>
                        <input type="text" style="height: 36px" class="form-control"
                               id="id" name="id" value="" required="true"
                               autocomplete="false"
                               title="Identifier"
                               placeholder="Identifier"
                               disabled="true"/>
                        <span class="help-block"></span>
                    </div>

                    <div>
                        <select name="ownerOrganisationId" id="ownerOrganisationId" class="form-control" title="Owner">
                            <option>
                            </option>
                        </select>
                        <span class="help-block"></span>
                    </div>

                    <div>
                        <input type="text" style="height: 36px" class="form-control"
                               id="name" name="name" value="" required="true"
                               autocomplete="false"
                               title="Name"
                               placeholder="Name"/>
                        <span class="help-block"></span>
                    </div>

                    <div>
                        <select name="enabled" id="enabled" class="form-control" title="Enabled">
                            <option value="0">Disabled</option>
                            <option value="1">Enabled</option>
                        </select>
                        <span class="help-block"></span>
                    </div>

                    <hr/>

                </div>
                <div class="modal-footer">
                    <input type="submit" class="btn btn-primary" value="Save"/>
                    <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel"/>
                </div>
            </div>
        </form>
    </div>
</div>