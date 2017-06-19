<div id="modalOrganisation" class="modal fade" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->

        <form id="formOrganisation" method="POST" autocomplete="off">
            <div class="modal-content">

                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">Organisation</h4>
                </div>

                <div class="modal-body">

                    <hr/>

                    <div>
                        <input type="text" style="height: 36px" class="form-control"
                               id="id" name="id" value=""
                               autocomplete="false"
                               title="Identifier"
                               placeholder="Identifier"
                               disabled="true"/>
                        <span class="help-block"></span>
                    </div>
                    <div>
                        <input type="text" style="height: 36px" class="form-control"
                               id="createdString" name="createdString" value="" required="false"
                               autocomplete="false"
                               title="Created"
                               placeholder="Created"
                               disabled="true"/>
                        <span class="help-block"></span>
                    </div>
                    <div>
                        <input type="text" style="height: 36px" class="form-control"
                               id="name" name="name" path="name" value="" required="true"
                               autocomplete="false"
                               title="Name"
                               placeholder="Name"/>
                        <span class="help-block"></span>
                    </div>
                    <div>
                        <input type="text" style="height: 36px" class="form-control"
                               id="address" name="address" path="address" value="" required="true"
                               autocomplete="false"
                               title="Address"
                               placeholder="Address"/>
                        <span class="help-block"></span>
                    </div>
                    <div>
                        <input type="text" style="height: 36px" class="form-control"
                               id="domain" name="domain" path="domain" value="" required="true"
                               autocomplete="false"
                               title="Domain"
                               placeholder="Domain"/>
                        <span class="help-block"></span>
                    </div>

                    <div>
                        <select name="enabled" id="enabled" class="form-control" title="Enabled">
                            <option value="0">Disabled</option>
                            <option value="1">Enabled</option>
                        </select>
                        <span class="help-block"></span>
                    </div>

                    <div>
                        <select name="ownerId" id="ownerId" class="form-control" title="Owner">
                            <option>
                            </option>
                        </select>
                        <span class="help-block"></span>
                    </div>

                    <hr/>

                </div>
                <div class="modal-footer">
                    <input type="submit" class="btn btn-submit btn-primary" value="Save"/>
                    <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel"/>
                </div>
            </div>
        </form>
    </div>

</div>