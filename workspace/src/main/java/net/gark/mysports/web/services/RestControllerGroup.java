package net.gark.mysports.web.services;

import net.gark.mysports.domain.interfaces.IGroup;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/group")
public class RestControllerGroup extends ControllerAbstract {

    private final static Logger LOGGER = LoggerFactory.getLogger(RestControllerGroup.class);

    private IServiceGroup serviceGroup;

    private IRepositoryOrganisation repositoryOrganisation;

    @Autowired
    public RestControllerGroup(final IServiceGroup serviceGroup,
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

    /**
     * Function to return all of the groups that the user is a member of
     *
     * @return ResponseEntity<DtoGroups>
     */
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DtoGroups> readAll() {
        final DtoGroups list = new DtoGroups();
        this.serviceGroup.readGroups(gm -> (gm.getUserId().equals(getLoggedInUserId())))
                .stream()
                .forEach(g -> list.add(convertToDtoGroup(g)));
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Function returns a group given an identifier
     *
     * @param id
     * @return ResponseEntity<DtoGroup>
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DtoGroup> read(@PathVariable("id") final long id) {
        if (id == -1) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        LOGGER.debug("Reading Group with id " + id);
        final Optional<IGroup> found = serviceGroup.readGroup(id);
        if (!found.isPresent()) {
            LOGGER.warn("Group not found - id=" + id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        // TODO: limit who can read the group - perhaps on those listed as admins?
        return new ResponseEntity<>(convertToDtoGroup(found.get()), HttpStatus.OK);
    }
}