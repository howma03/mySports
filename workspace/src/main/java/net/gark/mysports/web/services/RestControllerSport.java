package net.gark.mysports.web.services;

import net.gark.mysports.domain.interfaces.IGroup;
import net.gark.mysports.domain.interfaces.ISport;
import net.gark.mysports.services.dto.DtoSport;
import net.gark.mysports.services.dto.DtoSports;
import net.gark.mysports.services.interfaces.IServiceGroup;
import net.gark.mysports.services.interfaces.IServiceSport;
import net.gark.mysports.web.controllers.ControllerAbstract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/sport")
public class RestControllerSport extends ControllerAbstract {

    private final static Logger LOGGER = LoggerFactory.getLogger(RestControllerSport.class);

    private IServiceSport serviceSport;

    private IServiceGroup serviceGroup;

    @Autowired
    public RestControllerSport(final IServiceSport serviceSport,
                               final IServiceGroup serviceGroup) {
        this.serviceSport = serviceSport;
        this.serviceGroup = serviceGroup;
    }

    /**
     * Function to create an sport
     *
     * @param sport
     * @return ResponseEntity<DtoSport>
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<DtoSport> create(@RequestBody final DtoSport sport) {
        LOGGER.debug("Creating sport " + sport.getTitle());
        sport.setOwnerId(this.getLoggedInUserId());
        final DtoSport added = new DtoSport(serviceSport.create(sport));
        //        final URI location = ServletUriComponentsBuilder
        //                .fromCurrentRequest().path("/{id}")
        //                .buildAndExpand(added.getId()).toUri();
        return new ResponseEntity<>(added, HttpStatus.OK);
    }

    private DtoSport convertToDtoSport(final ISport a) {
        final DtoSport dtoSport = new DtoSport(a);
        if (a.getGroupId() != null) {
            final Optional<IGroup> found = serviceGroup.readGroup(a.getGroupId());
            if (found.isPresent()) {
                dtoSport.setGroupName(found.get().getName());
            }
        }
        return dtoSport;
    }

    /**
     * Function to read all sports owned by a user
     *
     * @return ResponseEntity<DtoSports>
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<DtoSports> readAll() {
        final DtoSports list = new DtoSports();
        serviceSport.getAllForUser(this.getLoggedInUser()).forEach(a -> {
            list.add(convertToDtoSport(a));
        });
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Function to read a single sport
     * The sport must be owned by the user
     *
     * @param id
     * @return ResponseEntity<DtoSport>
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DtoSport> read(@PathVariable("id") final Long id) {
        LOGGER.debug("Reading sport with id " + id);
        final ISport found = serviceSport.findOne(id);
        if (found == null) {
            LOGGER.warn("sport with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!this.getLoggedInUser().isAdmin() && found.getOwnerId() != this.getLoggedInUserId()) {
            LOGGER.warn("User attempting to read an sport they do not own");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(convertToDtoSport(found), HttpStatus.OK);
    }

    /**
     * Function to update an sport
     *
     * @param id
     * @param sport
     * @return ResponseEntity<DtoSport>
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<DtoSport> update(@PathVariable("id") final long id,
                                                 @RequestBody final DtoSport sport) {

        LOGGER.debug("Updating sport " + id);

        final ISport found = serviceSport.findOne(id);
        if (found == null) {
            LOGGER.warn("sport with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (found.getOwnerId() != this.getLoggedInUserId()) {
            LOGGER.warn("User attempting to update sport not their own");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        serviceSport.update(sport);
        LOGGER.warn("Updated. sport with id " + id);
        return new ResponseEntity<>(convertToDtoSport(found), HttpStatus.OK);
    }

    /**
     * Function to delete an sport
     *
     * @param id
     * @return ResponseEntity<DtoSport>
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<DtoSport> deleteOne(@PathVariable("id") final long id) {
        LOGGER.debug("Deleting sport with id " + id);
        final ISport found = serviceSport.findOne(id);
        if (found == null) {
            LOGGER.warn("Unable to delete. sport with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (!this.getLoggedInUser().isAdmin() && found.getOwnerId() != this.getLoggedInUserId()) {
            LOGGER.warn("User attempting to delete sport not their own");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        serviceSport.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
