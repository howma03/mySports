package net.gark.mysports.services;

import net.gark.mysports.domain.entity.Organisation;
import net.gark.mysports.domain.interfaces.IOrganisation;
import net.gark.mysports.domain.repository.IRepositoryOrganisation;
import net.gark.mysports.services.interfaces.IServiceOrganisation;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by mark on 29/05/2017.
 */
@Service
public class ServiceOrganisationImplRepository implements IServiceOrganisation {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceOrganisationImplRepository.class);

    /**
     * CRUD operations for Organisations
     */

    @Autowired
    private IRepositoryOrganisation repositoryOrganisation;

    @Override
    public IOrganisation createOrganisation(final IOrganisation organisation) {
        LOGGER.info("Creating an Organization - " + organisation);
        final Organisation newOrganisation = new Organisation();
        IOrganisation.COPY(organisation, newOrganisation);
        return repositoryOrganisation.save(newOrganisation);
    }

    @Override
    public IOrganisation readOrganisation(final Long id) {
        LOGGER.info("Reading an Organization - " + id);
        return repositoryOrganisation.findOne(id);
    }

    @Override
    public List<IOrganisation> getOrganisationsOwnedByUser(final Long userId) {
        return repositoryOrganisation.findByOwnerId(userId).stream().map(IOrganisation.class::cast).collect(Collectors.toList());
    }

    @Override
    public List<IOrganisation> readAllOrganisations() {
        final List<IOrganisation> list = new ArrayList<>();
        CollectionUtils.addAll(list, repositoryOrganisation.findAll().iterator());
        return list;
    }

    @Override
    public IOrganisation updateOrganisation(final IOrganisation organisation) {
        LOGGER.info("Updating an Organization - " + organisation);
        final IOrganisation found = this.repositoryOrganisation.findOne(organisation.getId());
        if (found == null) {
            LOGGER.error("Failed to find organisation - " + organisation);
            return null;
        }
        // Ensure created time remains unchanged
        organisation.setCreated(found.getCreated());
        IOrganisation.COPY(organisation, found);
        return repositoryOrganisation.save((Organisation) found);
    }

    @Override
    public IOrganisation deleteOrganisation(final Long id) {
        LOGGER.info("Deleting an Organization - " + id);
        final IOrganisation found = this.repositoryOrganisation.findOne(id);
        if (found == null) {
            LOGGER.error("Failed to find organisation - id=" + id);
            return null;
        }
        found.setEnabled(false);
        // TODO: Need to consider the operations when we delete an organization
        // TODO: perhaps we simply disable it?
        return this.repositoryOrganisation.save((Organisation) found);
    }
}
