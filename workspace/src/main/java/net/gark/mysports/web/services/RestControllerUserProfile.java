package net.gark.mysports.web.services;

import net.gark.mysports.domain.interfaces.IUser;
import net.gark.mysports.services.dto.DtoUserProfile;
import net.gark.mysports.web.controllers.ControllerAbstract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/profile")
public class RestControllerUserProfile extends ControllerAbstract {

    private final static Logger LOGGER = LoggerFactory.getLogger(RestControllerUserProfile.class);

    @Autowired
    public RestControllerUserProfile() {
    }

    //-------------------Read Profile--------------------------------------------------------

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public DtoUserProfile read() {
        LOGGER.debug("Reading User Profile");
        final DtoUserProfile formUserProfile = new DtoUserProfile();
        IUser.COPY(this.getLoggedInUser(), formUserProfile);
        return formUserProfile;
    }

    //------------------- Update Profile --------------------------------------------------------

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestBody final DtoUserProfile form) {
        LOGGER.debug("Updating User Profile");
        final IUser found = this.getLoggedInUser();
        found.setFirstName(form.getFirstName());
        found.setLastName(form.getLastName());
        serviceUser.updateUser(found);
        LOGGER.warn(String.format("Updated User Profile - firstName [%s] lastName [%s]", form.getFirstName(), form.getLastName()));
        return new ResponseEntity<>(form, HttpStatus.OK);
    }
}
