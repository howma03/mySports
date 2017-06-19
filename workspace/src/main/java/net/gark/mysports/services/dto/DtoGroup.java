package net.gark.mysports.services.dto;

import net.gark.mysports.domain.interfaces.IGroup;
import net.gark.mysports.domain.interfaces.IUtility;

import java.util.Date;

public class DtoGroup implements IGroup {

    private final Date created = new Date();
    protected boolean isEnabled = false;
    private Long id;
    private String createdString = "";
    private String name;
    private Long ownerOrganisationId;
    private Long countMembers = 0l;

    public DtoGroup() {

    }

    public DtoGroup(final IGroup from) {
        IGroup.COPY(from, this);
    }

    @Override
    public Long getId() {
        return id;
    }

    public DtoGroup setId(final Long id) {
        this.id = id;
        return this;
    }

    @Override
    public Date getCreated() {
        return created;
    }

    @Override
    public IGroup setCreated(final Date created) {
        this.created.setTime(created.getTime());
        if (created == null) {
            this.createdString = "";
        } else {
            this.createdString = IUtility.format(created);
        }
        return this;
    }

    @Override
    public String getCreatedString() {
        return createdString;
    }

    @Override
    public IGroup setEnabled(final Boolean isEnabled) {
        this.isEnabled = isEnabled;
        return this;
    }

    @Override
    public Boolean isEnabled() {
        return isEnabled == true;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public DtoGroup setName(final String name) {
        this.name = name;
        return this;
    }

    @Override
    public Long getOwnerOrganisationId() {
        return ownerOrganisationId;
    }

    @Override
    public DtoGroup setOwnerOrganisationId(final Long ownerOrganisationId) {
        this.ownerOrganisationId = ownerOrganisationId;
        return this;
    }


    public Long getCountMembers() {
        return countMembers;
    }

    public DtoGroup setCountMembers(final Long countMembers) {
        this.countMembers = countMembers;
        return this;
    }

    @Override
    public String toString() {
        return String.format("ID [%d] Group Name [%s] Owner Organisation Id [%d]", id, name, ownerOrganisationId);
    }
}
