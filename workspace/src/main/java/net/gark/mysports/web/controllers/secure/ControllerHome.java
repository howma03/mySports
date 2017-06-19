package net.gark.mysports.web.controllers.secure;

import net.gark.mysports.domain.entity.Sport;
import net.gark.mysports.domain.interfaces.IUser;
import net.gark.mysports.web.controllers.ControllerAbstract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Map;

@Controller
public class ControllerHome extends ControllerAbstract {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerHome.class);

    @RequestMapping("/home")
    public String home(final Map<String, Object> model) {

        final IUser user = this.getLoggedInUser();
        LOGGER.debug("User loggedIn - user=" + user.getEmail());

        super.populateModel(model);
        model.put("message", String.format("Welcome %s to the home page", user.getFirstName()));
        model.put("sports", new ArrayList<Sport>());
        model.put("navigation", "Home");

        return VIEW_NAME_HOME;
    }
}