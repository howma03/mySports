package net.gark.mysports.web.services;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/group/membership")
public class RestControllerGroupMember extends ControllerAbstract {

    private final static Logger LOGGER = LoggerFactory.getLogger(RestControllerGroupMember.class);

    private IRepositoryUser repositoryUser;

    private IRepositoryGroup repositoryGroup;

    private IServiceGroup serviceGroup;

    @Autowired
    public RestControllerGroupMember(final IRepositoryUser repositoryUser,
                                     final IRepositoryGroup repositoryGroup,
                                     final IServiceGroup serviceGroup) {
        this.repositoryUser = repositoryUser;
        this.repositoryGroup = repositoryGroup;
        this.serviceGroup = serviceGroup;
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

    private DtoGroupMembers convertToDtoGroupMembers(final List<IGroupMember> list) {
        final DtoGroupMembers out = new DtoGroupMembers();
        list.forEach(gm -> {
                    out.add(convertToDtoGroupMember(gm));
                }
        );
        return out;
    }

    /***
     * Function returns a list of the group memberships for a user
     *
     * @param userId
     * @return ResponseEntity<DtoGroups>
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DtoGroupMembers> readAllGroupMembershipsForUser(@PathVariable("id") long userId) {
        if (userId == -1) {
            return new ResponseEntity<>(new DtoGroupMembers(), HttpStatus.OK);
        }
        if (!this.getLoggedInUser().isAdmin()) {
            userId = this.getLoggedInUserId();
        }
        return new ResponseEntity<>(convertToDtoGroupMembers(this.serviceGroup.getGroupMembershipsForUser(userId)), HttpStatus.OK);
    }
}