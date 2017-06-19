package net.gark.mysports.services.dto;

import java.util.List;

/**
 * Created by mark on 26/05/2017.
 */
public class DtoOrganisations extends DtoList<DtoOrganisation> {

    public DtoOrganisations(final List<DtoOrganisation> from) {
        data.addAll(from);
    }

    public DtoOrganisations() {

    }
}