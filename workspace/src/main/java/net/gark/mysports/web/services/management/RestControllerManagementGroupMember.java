package net.gark.mysports.web.services.management;

import net.gark.mysports.domain.interfaces.IGroup;
import net.gark.mysports.domain.interfaces.IGroupMember;
import net.gark.mysports.domain.interfaces.IUser;
import net.gark.mysports.domain.repository.IRepositoryGroup;
import net.gark.mysports.domain.repository.IRepositoryUser;
import net.gark.mysports.services.dto.DtoGroupMember;
import net.gark.mysports.services.dto.DtoGroupMembers;
import net.gark.mysports.services.interfaces.IServiceGroup;
import net.gark.mysports.web.controllers.ControllerAbstract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/management/group/membership")
public class RestControllerManagementGroupMember extends ControllerAbstract {

    private final static Logger LOGGER = LoggerFactory.getLogger(RestControllerManagementGroupMember.class);

    private IRepositoryUser repositoryUser;

    private IRepositoryGroup repositoryGroup;

    private IServiceGroup serviceGroup;

    @Autowired
    public RestControllerManagementGroupMember(final IRepositoryUser repositoryUser,
                                               final IRepositoryGroup repositoryGroup,
                                               final IServiceGroup serviceGroup) {
        this.repositoryUser = repositoryUser;
        this.repositoryGroup = repositoryGroup;
        this.serviceGroup = serviceGroup;
    }

    /***
     * Function to create a group member
     * Only admins or Group Managers can create Group Memberships
     * @param groupMember
     * @return ResponseEntity<DtoGroupMember>
     */
    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DtoGroupMember> createGroupMember(@RequestBody final DtoGroupMember groupMember) {
        LOGGER.debug("Creating Group Membership " + groupMember);
        final IGroupMember added = serviceGroup.createGroupMember(groupMember);
        return new ResponseEntity<>(convertToDtoGroupMember(added), HttpStatus.OK);
    }

    /**
     * Function construct the DtoGroupMember container
     *
     * @param gm
     * @return DtoGroupMember
     */
    private DtoGroupMember convertToDtoGroupMember(final IGroupMember gm) {
        final DtoGroupMember dtoGroupMember = new DtoGroupMember(gm);
        final IUser u = repositoryUser.findOne(gm.getUserId());
        if (u != null) {
            dtoGroupMember.setEmail(u.getEmail());
            dtoGroupMember.setUserName(u.getFirstName() + " " + u.getLastName());
            dtoGroupMember.setFirstName(u.getFirstName());
            dtoGroupMember.setLastName(u.getLastName());
        }
        final IGroup g = repositoryGroup.findOne(gm.getGroupId());
        if (g != null) {
            dtoGroupMember.setGroupName(g.getName());
        }
        return dtoGroupMember;
    }

    private DtoGroupMembers convertToDotGroupMembers(final List<IGroupMember> list) {
        final DtoGroupMembers out = new DtoGroupMembers();
        list.forEach(gm -> {
                    out.add(convertToDtoGroupMember(gm));
                }
        );
        return out;
    }

    /***
     * Function returns a list of the group memberships for a user
     * @param userId
     * @return ResponseEntity<DtoGroups>
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DtoGroupMembers> readAllGroupMembershipsForUser(@PathVariable("id") long userId) {
        if (userId == -1) {
            new ResponseEntity<>(new DtoGroupMembers(), HttpStatus.OK);
        }
        if (!this.getLoggedInUser().isAdmin()) {
            userId = this.getLoggedInUserId();
        }
        return new ResponseEntity<>(convertToDotGroupMembers(this.serviceGroup.getGroupMembershipsForUser(userId)), HttpStatus.OK);
    }

    /***
     * Function returns a list of the groups memberships for a group
     * @param groupId
     * @return ResponseEntity<DtoGroups>
     */
    @RequestMapping(value = "/group/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DtoGroupMembers> readAllGroupMembershipsForGroup(@PathVariable("id") final long groupId) {
        if (groupId == -1) {
            new ResponseEntity<>(new DtoGroupMembers(), HttpStatus.OK);
        }
        if (!this.getLoggedInUser().isAdmin()) {
            LOGGER.error("Non-admin user trying to read groups!");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(convertToDotGroupMembers(this.serviceGroup.getGroupMembershipsForGroup(groupId)), HttpStatus.OK);
    }


    /***
     * Function returns a list of the groups memberships for a group
     * @param groupMembershipId
     * @return ResponseEntity<DtoGroups>
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DtoGroupMember> readGroupMembership(@PathVariable("id") final long groupMembershipId) {
        if (groupMembershipId == -1) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(convertToDtoGroupMember(this.serviceGroup.getMembership(groupMembershipId)), HttpStatus.OK);
    }

    /**
     * Function to update a group - only owners (or administrators) should be allowed to update the group
     *
     * @param id
     * @param groupMember
     * @return ResponseEntity<DtoGroupMember>
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<DtoGroupMember> updateGroupMembership(@PathVariable("id") final long id, @RequestBody final DtoGroupMember groupMember) {
        LOGGER.debug("Updating Group Membership " + groupMember);

        final IGroupMember found = serviceGroup.getMembership(id);
        if (found == null) {
            LOGGER.warn("Group Member with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        final IGroupMember updatedGroupMember = serviceGroup.updateGroupMember(groupMember);
        LOGGER.warn("Updated. Group Member with id " + id);
        return new ResponseEntity<>(convertToDtoGroupMember(updatedGroupMember), HttpStatus.OK);
    }
}