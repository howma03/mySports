package net.gark.mysports.domain.interfaces;

import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * Created by mark on 17/02/2017.
 */
public interface IGroup {

    static IGroup COPY(final IGroup from, final IGroup to) {
        if (from == null) {
            return null;
        }
        if (to == null) {
            return null;
        }
        BeanUtils.copyProperties(from, to);
        if (from.isEnabled() != null) {
            to.setEnabled(from.isEnabled());
        }
        return to;
    }

    default Long getId() {
        return null;
    }

    String getName();

    IGroup setName(String name);

    Long getOwnerOrganisationId();

    IGroup setOwnerOrganisationId(Long ownerId);

    IGroup setEnabled(final Boolean flag);

    Boolean isEnabled();

    Date getCreated();

    IGroup setCreated(Date created);

    String getCreatedString();
}
