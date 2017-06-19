package net.gark.mysports.web.services.management;

import net.gark.mysports.domain.interfaces.IUser;
import net.gark.mysports.services.dto.DtoUser;
import net.gark.mysports.services.dto.DtoUsers;
import net.gark.mysports.services.interfaces.IServiceGroup;
import net.gark.mysports.services.interfaces.IServiceUser;
import net.gark.mysports.web.controllers.ControllerAbstract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/management/user")
public class RestControllerManagementUser extends ControllerAbstract {

    private final static Logger LOGGER = LoggerFactory.getLogger(RestControllerManagementUser.class);

    private IServiceUser serviceUser;

    private IServiceGroup serviceGroup;

    @Autowired
    public RestControllerManagementUser(final IServiceUser serviceUser, final IServiceGroup serviceGroup) {
        this.serviceUser = serviceUser;
        this.serviceGroup = serviceGroup;
    }

    private DtoUser getDtoUser(final IUser u) {
        final DtoUser dtoUser = new DtoUser(u);
        // Now determine the number of groups the user is a member of
        dtoUser.setCountGroup(Long.valueOf(serviceGroup.getMembershipGroupsForUser(u.getId()).size()));
        return dtoUser;
    }

    /**
     * Function to create a user
     * Only a system administrator can create an User
     *
     * @param user
     * @return DtoUser
     */
    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DtoUser> create(@RequestBody final DtoUser user) {
        LOGGER.debug("Creating User " + user.getEmail());

        if (!this.getLoggedInUser().isAdmin()) {
            LOGGER.error("Users can only be created by system administrators");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(getDtoUser(serviceUser.addUser(user)), HttpStatus.OK);
    }

    /**
     * Function to read all users
     *
     * @return ResponseEntity<DtoUsers>
     */
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DtoUsers> readAll() {
        if (!this.getLoggedInUser().isAdmin()) {
            LOGGER.error("Users can only be read by system administrators");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        /**
         * As well as the standard user data we want to include statistics for number of users and groups
         * (as well as number of open invitations)
         */
        final DtoUsers list = new DtoUsers();
        this.serviceUser.getAll().forEach(u -> {
            list.add(getDtoUser(u));
        });
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Function to read a user given an identifier
     *
     * @param id
     * @return ResponseEntity<DtoUser>
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DtoUser> read(@PathVariable("id") final long id) {
        if (!this.getLoggedInUser().isAdmin()) {
            LOGGER.error("Users can only be read by system administrators");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        LOGGER.debug("Reading User with id " + id);
        final IUser found = serviceUser.findUser(id);
        if (found == null) {
            LOGGER.warn("User not found - id=" + id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(getDtoUser(found), HttpStatus.OK);
    }

    /**
     * Function to update a user - only admins can update users
     *
     * @param id
     * @param user
     * @return ResponseEntity<DtoUser>
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<DtoUser> update(@PathVariable("id") final long id, @RequestBody final DtoUser user) {

        //TODO: Ensure security handles this
        if (!this.getLoggedInUser().isAdmin()) {
            LOGGER.error("Users can only be updated by system administrators");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        LOGGER.debug("Updating User " + id);

        IUser found = serviceUser.findUser(id);
        if (found == null) {
            LOGGER.warn("User with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        found = serviceUser.updateUser(user);
        LOGGER.warn("Updated user with id " + id);
        return new ResponseEntity<>(getDtoUser(found), HttpStatus.OK);
    }

    /**
     * Function to delete a user -
     * Only the administrators can perform this operation
     *
     * @param id
     * @return ResponseEntity<DtoUser>
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<DtoUser> deleteOne(@PathVariable("id") final long id) {
        if (!this.getLoggedInUser().isAdmin()) {
            LOGGER.error("Users can only be deleted by system administrators");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        LOGGER.debug("Deleting User with id " + id);

        final IUser found = serviceUser.findUser(id);
        if (found == null) {
            LOGGER.warn("User with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        serviceUser.deleteUser(found.getId());
        return new ResponseEntity<>(getDtoUser(found), HttpStatus.OK);
    }
}