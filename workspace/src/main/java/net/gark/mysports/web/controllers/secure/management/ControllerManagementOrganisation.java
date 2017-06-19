package net.gark.mysports.web.controllers.secure.management;

import net.gark.mysports.domain.interfaces.IOrganisation;
import net.gark.mysports.domain.interfaces.IUser;
import net.gark.mysports.services.interfaces.IServiceOrganisation;
import net.gark.mysports.web.controllers.ControllerAbstract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
public class ControllerManagementOrganisation extends ControllerAbstract {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerManagementOrganisation.class);

    @Autowired
    private IServiceOrganisation serviceOrganisations;

    @RequestMapping("/management/organisation")
    public String get(final Map<String, Object> model) {

        final IUser user = this.getLoggedInUser();
        LOGGER.debug("User loggedIn - user=" + user.getEmail());

        populateModel(model);

        final List<IOrganisation> organisations;
        if (user.isAdmin()) {
            organisations = this.serviceOrganisations.readAllOrganisations();
        } else {
            organisations = this.serviceOrganisations.getOrganisationsOwnedByUser(user.getId());
        }

        if (organisations.size() > 0) {
            model.put("organisations", organisations);
        }

        model.put("message", String.format("Welcome to the management-organisation page"));
        model.put("navigation", "Organisations");

        return VIEW_NAME_MANAGEMENT_ORGANISATION;
    }
}