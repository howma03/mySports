package net.gark.mysports.web.controllers.secure.management;

import net.gark.mysports.web.controllers.ControllerAbstract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class ControllerManagementGroup extends ControllerAbstract {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerManagementGroup.class);

    @RequestMapping("/management/group")
    public String get(final Map<String, Object> model) {

        LOGGER.debug("User loggedIn - user=" + this.getLoggedInUser().getEmail());

        populateModel(model);

        model.put("message", String.format("Welcome to the management-group page"));
        model.put("navigation", "Groups");

        return VIEW_NAME_MANAGEMENT_GROUP;
    }
}