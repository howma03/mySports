package net.gark.mysports.web.forms;

import net.gark.mysports.domain.interfaces.IOrganisation;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.util.Date;

@Deprecated
public class FormOrganisation implements IOrganisation {

    private static final String pattern = "yyyy/MM/dd HH:mm:ss";
    private static final SimpleDateFormat format = new SimpleDateFormat(pattern);

    private Long id;
    private Date created = new Date();
    private String createdString = "";

    @NotEmpty
    @Size(min = 4, max = 100)
    private String name;

    private Long ownerId;
    private Boolean isEnabled = true;

    @NotEmpty
    @Size(min = 4, max = 100)
    private String address = "";

    @Size(min = 4, max = 100)
    private String domain = "";

    public FormOrganisation() {

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
        this.created = created;
        if (created == null) {
            this.createdString = "";
        } else {
            this.createdString = format.format(created);
        }
        return this;
    }

    @Override
    public String getCreatedString() {
        return createdString;
    }

    public Boolean getEnabled() {
        return isEnabled;
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
        return ownerId;
    }

    @Override
    public IOrganisation setOwnerId(final Long ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    @Override
    public IOrganisation setEnabled(final Boolean flag) {
        this.isEnabled = flag;
        return this;
    }

    @Override
    public Boolean isEnabled() {
        return isEnabled;
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

    @Override
    public String toString() {
        return String.format("Name [%s] OwnerId [%s] IsEnabled [%s]", name, ownerId, isEnabled);
    }
}
