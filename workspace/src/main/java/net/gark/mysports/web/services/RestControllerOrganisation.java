package net.gark.mysports.web.services;

import net.gark.mysports.domain.interfaces.IGroup;
import net.gark.mysports.domain.interfaces.IOrganisation;
import net.gark.mysports.domain.interfaces.IUser;
import net.gark.mysports.domain.repository.IRepositoryOrganisation;
import net.gark.mysports.services.dto.DtoOrganisation;
import net.gark.mysports.services.dto.DtoOrganisations;
import net.gark.mysports.services.interfaces.IServiceGroup;
import net.gark.mysports.services.interfaces.IServiceOrganisation;
import net.gark.mysports.web.controllers.ControllerAbstract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/organisation")
public class RestControllerOrganisation extends ControllerAbstract {

    private final static Logger LOGGER = LoggerFactory.getLogger(RestControllerOrganisation.class);

    private IServiceOrganisation serviceOrganisation;

    private IServiceGroup serviceGroup;

    @Autowired
    private IRepositoryOrganisation repositoryOrganisation;

    @Autowired
    public RestControllerOrganisation(final IServiceOrganisation serviceOrganisation, final IServiceGroup serviceGroup) {
        this.serviceOrganisation = serviceOrganisation;
        this.serviceGroup = serviceGroup;
    }

    private DtoOrganisation convertToDtoOrganisation(final IOrganisation o) {
        final DtoOrganisation dtoOrganisation = new DtoOrganisation(o);
        // Now determine the number of groups the user is a member of

        if (o.getOwnerId() != null) {
            final IUser owner = this.serviceUser.findUser(o.getOwnerId());
            if (owner != null) {
                dtoOrganisation.setOwnerEmail(owner.getEmail());
            }
        }
        final List<IGroup> groups = this.serviceGroup.readGroups(o);
        final Set<IUser> orgUsers = new LinkedHashSet<>();
        groups.stream().forEach(g -> {
            orgUsers.addAll(this.serviceGroup.getMembershipUsersForGroup(g.getId()));
        });
        dtoOrganisation.setCountGroup(Long.valueOf(groups.size()));
        dtoOrganisation.setCountUser(Long.valueOf(orgUsers.size()));

        return dtoOrganisation;
    }

    /**
     * Function to create an Organisation
     * Only a system administrator cna create an Organisation
     *
     * @param organisation
     * @return ResponseEntity<DtoOrganisation>
     */
    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DtoOrganisation> create(@RequestBody final DtoOrganisation organisation) {
        LOGGER.debug("Creating Organisation " + organisation.getName());

        if (!this.getLoggedInUser().isAdmin()) {
            LOGGER.error("Organisations can only be created by system administrators");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(convertToDtoOrganisation(serviceOrganisation.createOrganisation(organisation)), HttpStatus.OK);
    }

    /**
     * Function to read all organisations
     *
     * @return ResponseEntity<DtoOrganisations>
     */
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DtoOrganisations> readAll() {

        // FIXME: enable organisation owners to read their owned organisations

        if (!this.getLoggedInUser().isAdmin()) {
            LOGGER.error("Organisations can only be read by system administrators");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        /**
         * As well as the standard organisation data we want to include statistics for number of users and groups
         * (as well as number of open invitations)
         */
        final List<DtoOrganisation> list = new ArrayList<>();
        this.serviceOrganisation.readAllOrganisations().forEach(o -> list.add(convertToDtoOrganisation(o)));
        return new ResponseEntity<>(new DtoOrganisations(list), HttpStatus.OK);
    }

    /**
     * Return a organisation given an identifier
     *
     * @param id
     * @return ResponseEntity<DtoOrganisation>
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DtoOrganisation> read(@PathVariable("id") final long id) {
        if (!this.getLoggedInUser().isAdmin()) {
            LOGGER.error("Organisations can only be read by system administrators");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        LOGGER.debug("Reading Organisation with id " + id);
        final IOrganisation found = serviceOrganisation.readOrganisation(id);
        if (found == null) {
            LOGGER.warn("Organisation not found - id=" + id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        // TODO: limit who can read the group - perhaps on those listed as admins?
        // TODO: obtain the statistics
        return new ResponseEntity<>(convertToDtoOrganisation(found), HttpStatus.OK);
    }

    /**
     * Function to update a group - only owners (or administrators) should be allowed to update the group
     *
     * @param id
     * @param organisation
     * @return ResponseEntity<DtoOrganisation>
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<DtoOrganisation> update(@PathVariable("id") final long id, @RequestBody final DtoOrganisation organisation) {
        if (!this.getLoggedInUser().isAdmin()) {
            LOGGER.error("Organisations can only be updated by system managers");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        LOGGER.debug("Updating Organisation " + id);
        final IOrganisation found = serviceOrganisation.readOrganisation(id);
        if (found == null) {
            LOGGER.warn("Organisation with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        final IOrganisation updated = serviceOrganisation.updateOrganisation(organisation);
        LOGGER.info("Updated organisation with id " + id);
        return new ResponseEntity<>(convertToDtoOrganisation(updated), HttpStatus.OK);
    }

    //------------------- Delete a Organisation --------------------------------------------------------

    /**
     * Function to delete a group - Only the owner of an Organisation can delete groups
     *
     * @param id
     * @return ResponseEntity<DtoOrganisation>
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<DtoOrganisation> deleteOne(@PathVariable("id") final long id) {
        if (!this.getLoggedInUser().isAdmin()) {
            LOGGER.error("Organisations can only be deleted by system administrators");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        LOGGER.debug("Deleting Organisation with id " + id);
        IOrganisation found = serviceOrganisation.readOrganisation(id);
        if (found == null) {
            LOGGER.warn("Organisation with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        found = serviceOrganisation.deleteOrganisation(id);
        LOGGER.warn("Deleted Organisation with id " + id);
        return new ResponseEntity<>(convertToDtoOrganisation(found), HttpStatus.OK);
    }
}