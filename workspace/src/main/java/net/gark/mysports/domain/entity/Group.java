package net.gark.mysports.domain.entity;

import net.gark.mysports.domain.interfaces.IGroup;
import net.gark.mysports.domain.interfaces.IUtility;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "grouping")
public class Group implements IGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date created = new Date();

    @Column(nullable = false)
    private boolean enabled = true;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long ownerOrganisationId;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Override
    public Boolean isEnabled() {
        return enabled;
    }

    @Override
    public IGroup setEnabled(final Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public IGroup setName(final String name) {
        this.name = name;
        return this;
    }

    @Override
    public Long getOwnerOrganisationId() {
        return ownerOrganisationId;
    }

    @Override
    public IGroup setOwnerOrganisationId(final Long ownerOrganisationId) {
        this.ownerOrganisationId = ownerOrganisationId;
        return this;
    }

    @Override
    public Date getCreated() {
        return new Date(created.getTime());
    }

    @Override
    public IGroup setCreated(final Date created) {
        this.created = new Date(created.getTime());
        return this;
    }

    @Override
    public String getCreatedString() {
        return IUtility.format(created);
    }

    @Override
    public String toString() {
        return String.format("ID [%d] Group Name [%s] Owner Organisation Id [%d]", id, name, ownerOrganisationId);
    }
}
