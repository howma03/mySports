package net.gark.mysports.web.controllers.secure.management;

import net.gark.mysports.web.controllers.ControllerAbstract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class ControllerManagementUser extends ControllerAbstract {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerManagementUser.class);

    @RequestMapping("/management/user")
    public String get(final Map<String, Object> model) {

        LOGGER.debug("User loggedIn - user=" + this.getLoggedInUser().getEmail());

        populateModel(model);

        model.put("message", String.format("Welcome to the management-user page"));
        model.put("navigation", "Management");

        return VIEW_NAME_MANAGEMENT_USER;
    }
}