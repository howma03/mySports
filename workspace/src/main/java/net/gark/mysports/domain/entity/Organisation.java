package net.gark.mysports.domain.entity;

import net.gark.mysports.domain.interfaces.IOrganisation;
import net.gark.mysports.domain.interfaces.IUtility;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "organisation")
public class Organisation implements IOrganisation {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private final Date created = new Date();
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long ownerId; // userId

    @Column(nullable = false)
    private boolean enabled = true;

    @Column(nullable = false)
    private String address = "";

    @Column(nullable = true)
    private String domain = "";

    public Organisation() {

    }

    public Organisation(final Long ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Override
    public Date getCreated() {
        return created;
    }

    @Override
    public IOrganisation setCreated(final Date created) {
        if (created != null) {
            this.created.setTime(created.getTime());
        }
        return this;
    }

    @Override
    public String getCreatedString() {
        return IUtility.format(created);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public IOrganisation setName(final String name) {
        this.name = name;
        return this;
    }

    @Override
    public Long getOwnerId() {
        return this.ownerId;
    }

    @Override
    public IOrganisation setOwnerId(final Long ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    @Override
    public Boolean isEnabled() {
        return enabled;
    }

    @Override
    public IOrganisation setEnabled(final Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public IOrganisation setAddress(final String address) {
        this.address = address;
        return this;
    }

    @Override
    public String getDomain() {
        return domain;
    }

    @Override
    public IOrganisation setDomain(final String domain) {
        this.domain = domain;
        return this;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return String.format("Id [%d] Organisation [%s] Owner Id [%d]", id, name, ownerId);
    }
}
