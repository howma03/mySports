package net.gark.mysports.web.controllers.secure.management;

import net.gark.mysports.domain.interfaces.IOrganisation;
import net.gark.mysports.domain.repository.IRepositorySport;
import net.gark.mysports.services.interfaces.IServiceGroup;
import net.gark.mysports.services.interfaces.IServiceOrganisation;
import net.gark.mysports.web.controllers.ControllerAbstract;
import net.gark.mysports.web.services.RestControllerSport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
public class ControllerManagementService extends ControllerAbstract {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerManagementService.class);

    @Autowired
    private IRepositorySport repositorysport;

    @Autowired
    private RestControllerSport servicesports;

    @Autowired
    private IServiceGroup serviceGroups;

    @Autowired
    private IServiceOrganisation serviceOrganisations;

    @RequestMapping("/management/service")
    public String get(final Map<String, Object> model) {

        LOGGER.debug("User loggedIn - user=" + this.getLoggedInUser().getEmail());

        populateModel(model);

        final List<IOrganisation> organisations = this.serviceOrganisations.readAllOrganisations();
        if (organisations.size() > 0) {
            model.put("organisations", organisations);
        }
        model.put("message", String.format("Welcome to the management-services page"));
        model.put("navigation", "Management");

        return VIEW_NAME_MANAGEMENT_SERVICE;
    }
}