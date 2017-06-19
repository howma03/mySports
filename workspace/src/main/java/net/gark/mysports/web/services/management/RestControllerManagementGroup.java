package net.gark.mysports.web.services.management;

import net.gark.mysports.domain.interfaces.IGroup;
import net.gark.mysports.domain.interfaces.IOrganisation;
import net.gark.mysports.domain.interfaces.IUser;
import net.gark.mysports.domain.repository.IRepositoryOrganisation;
import net.gark.mysports.services.dto.DtoGroup;
import net.gark.mysports.services.dto.DtoGroups;
import net.gark.mysports.services.interfaces.IServiceGroup;
import net.gark.mysports.web.controllers.ControllerAbstract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * When managing groups the user is important
 * <p>
 * We have potential users
 * <p>
 * 1. System Managers (or System admins - they can do all operations)
 * 2. Organisation Owners - they can add groups to organisations they own or disable groups they own (via organisations they own)
 * 3. Group Managers - they can invite or reject users from the groups they manager
 */
@RestController
@RequestMapping("/api/management/group")
public class RestControllerManagementGroup extends ControllerAbstract {

    private final static Logger LOGGER = LoggerFactory.getLogger(RestControllerManagementGroup.class);

    private IServiceGroup serviceGroup;

    private IRepositoryOrganisation repositoryOrganisation;

    @Autowired
    public RestControllerManagementGroup(final IServiceGroup serviceGroup,
                                         final IRepositoryOrganisation repositoryOrganisation) {
        this.serviceGroup = serviceGroup;
        this.repositoryOrganisation = repositoryOrganisation;
    }

    private DtoGroup convertToDtoGroup(final IGroup g) {
        final DtoGroup dtoGroup = new DtoGroup(g);
        final List<IUser> users = serviceGroup.getMembershipUsersForGroup(g.getId());
        dtoGroup.setCountMembers(Long.valueOf(users.size()));
        return dtoGroup;
    }

    private DtoGroups convertToGroups(final List<IGroup> groups) {
        final DtoGroups list = new DtoGroups();
        groups.stream().forEach(g -> list.add(convertToDtoGroup(g)));
        return list;
    }

    /***
     * Function to create a group
     * Only admins or the owner of an Organisation can create groups
     * @param group
     * @return ResponseEntity<DtoGroup>
     */
    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DtoGroup> create(@RequestBody final DtoGroup group) {
        LOGGER.debug("Creating Group " + group.getName());

        final IUser user = this.getLoggedInUser();

        final Long organisationId = group.getOwnerOrganisationId();
        if (organisationId == null) {
            LOGGER.error("Organisation not provided!");
            return null;
        }

        final IOrganisation organisation = repositoryOrganisation.findOne(organisationId);
        if (organisation == null) {
            LOGGER.error("Organisation not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (!user.isAdmin()) {
            if (!organisation.getOwnerId().equals(user.getId())) {
                LOGGER.error("Organisation not owned by logged-in user!");
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }

        return new ResponseEntity<>(convertToDtoGroup(serviceGroup.createGroup(organisation, group)), HttpStatus.OK);
    }

    /**
     * Function to return all of the groups that the user either owns or manages
     * (System Managers will see all groups)
     *
     * @return ResponseEntity<DtoGroups>
     */
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DtoGroups> readAll() {
        final DtoGroups list = new DtoGroups();

        if (this.getLoggedInUser().isAdmin()) {
            list.addAll(convertToGroups(this.serviceGroup.readAllGroups()));
        } else {
            list.addAll(readAllManagedByUser().getBody());
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Function to return all of the groups managed by the user
     *
     * @return ResponseEntity<DtoGroups>
     */
    @RequestMapping(value = "/managed", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DtoGroups> readAllManagedByUser() {
        final DtoGroups list = new DtoGroups();
        list.addAll(convertToGroups(this.serviceGroup.readGroupsManagedByUser(getLoggedInUserId())));
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /***
     * Function returns a list of the groups belonging to an organisation
     * (Only the admin user or the organisation owner can perform this operation)
     *
     * @param id
     * @return ResponseEntity<DtoGroups>
     */
    @RequestMapping(value = "/organisation/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DtoGroups> readAllOrganisation(@PathVariable("id") final long id) {
        if (id == -1) {
            // ignore -1 operations
            return new ResponseEntity<>(new DtoGroups(), HttpStatus.OK);
        }

        final IUser user = this.getLoggedInUser();
        final IOrganisation organisation = this.repositoryOrganisation.findOne(id);

        if (!user.isAdmin()) {
            if (!organisation.getOwnerId().equals(user.getId())) {
                LOGGER.error("Non-admin user trying to read groups for organisation they do not own!");
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }
        final DtoGroups list = new DtoGroups();
        list.addAll(convertToGroups(this.serviceGroup.readGroups(organisation)));
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Function returns a group given an identifier
     * Only the admin, the group owner or a manager of the group can perform this operation
     *
     * @param id
     * @return ResponseEntity<DtoGroup>
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DtoGroup> read(@PathVariable("id") final long id) {
        LOGGER.debug("Reading Group with id " + id);
        final IUser user = this.getLoggedInUser();

        final Optional<IGroup> found = serviceGroup.readGroup(id);
        if (!found.isPresent()) {
            LOGGER.warn("Group not found - id=" + id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        final IGroup group = found.get();
        final IOrganisation organisation = this.repositoryOrganisation.findOne(group.getId());

        if (!user.isAdmin()) {
            final List<IUser> allowed = this.serviceGroup.readGroupAdmins(group.getId());
            allowed.add(this.serviceUser.findUser(organisation.getOwnerId()));
            if (!allowed.contains(user)) {
                LOGGER.error("Non-admin user trying to read group for organisation they do not own or group they do not manage!");
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }

        // limit who can read the group - only admins, group owner or manager
        return new ResponseEntity<>(convertToDtoGroup(found.get()), HttpStatus.OK);
    }

    /**
     * Function to update a group - only admins or owners can perform this operation
     *
     * @param id
     * @param group
     * @return ResponseEntity<IGroup>
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<DtoGroup> update(@PathVariable("id") final long id, @RequestBody final DtoGroup group) {
        LOGGER.debug("Updating Group " + id);
        final IUser user = this.getLoggedInUser();

        final Optional<IGroup> found = serviceGroup.readGroup(id);
        if (!found.isPresent()) {
            LOGGER.warn("Group with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        final IOrganisation organisation = serviceGroup.getGroupOwner(id);

        if (!user.isAdmin()) {
            final List<IUser> allowed = new ArrayList<>();
            allowed.add(this.serviceUser.findUser(organisation.getOwnerId()));
            if (!allowed.contains(user)) {
                LOGGER.error("Non-admin user trying to update a group for organisation they do not own!");
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }

        final IGroup updatedGroup = serviceGroup.updateGroup(group);
        LOGGER.warn("Updated. Group with id " + id);
        return new ResponseEntity<>(convertToDtoGroup(updatedGroup), HttpStatus.OK);
    }

    /**
     * Function to delete a group - Only the admins or the owner of an organisation can delete groups
     *
     * @param id
     * @return ResponseEntity<?>
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<DtoGroup> deleteOne(@PathVariable("id") final long id) {
        LOGGER.debug("Deleting Group with id " + id);
        final IUser user = this.getLoggedInUser();

        final Optional<IGroup> found = serviceGroup.readGroup(id);
        if (!found.isPresent()) {
            LOGGER.warn("Group with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        final IGroup foundGroup = found.get();

        final IOrganisation organisation = serviceGroup.getGroupOwner(id);

        if (!user.isAdmin()) {
            final List<IUser> allowed = new ArrayList<>();
            allowed.add(this.serviceUser.findUser(organisation.getOwnerId()));
            if (!allowed.contains(user)) {
                LOGGER.error("Non-admin user trying to delete a group for organisation they do not own!");
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }

        serviceGroup.deleteGroup(foundGroup.getId());
        LOGGER.warn("Deleted. Group with id " + id);
        return new ResponseEntity<>(convertToDtoGroup(serviceGroup.readGroup(id).get()), HttpStatus.OK);
    }
}