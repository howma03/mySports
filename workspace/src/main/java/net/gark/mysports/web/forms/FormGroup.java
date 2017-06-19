package net.gark.mysports.web.forms;

import net.gark.mysports.domain.interfaces.IGroup;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.util.Date;

@Deprecated
public class FormGroup implements IGroup {

    private static final String pattern = "yyyy/MM/dd HH:mm:ss";
    private static final SimpleDateFormat format = new SimpleDateFormat(pattern);

    private Long id;
    private Date created = new Date();
    private String createdString = "";

    @NotEmpty
    @Size(min = 4, max = 100)
    private String name; // nick name

    private Long ownerOrganisationId;
    private Boolean isEnabled = true;

    public FormGroup() {

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
    public IGroup setEnabled(final Boolean flag) {
        this.isEnabled = flag;
        return this;
    }

    @Override
    public Boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public Date getCreated() {
        return created;
    }

    @Override
    public FormGroup setCreated(final Date created) {
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

    @Override
    public String toString() {
        return String.format("Name [%s] OwnerOrganisationId [%s] IsEnabled [%s] IsEnabled [%s]", name, ownerOrganisationId, isEnabled);
    }
}
