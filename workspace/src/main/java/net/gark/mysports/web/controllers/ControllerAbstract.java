package net.gark.mysports.web.controllers;

import net.gark.mysports.domain.interfaces.*;
import net.gark.mysports.services.interfaces.IServiceGroup;
import net.gark.mysports.services.interfaces.IServiceOrganisation;
import net.gark.mysports.services.interfaces.IServiceUser;
import net.gark.mysports.web.forms.FormGroup;
import net.gark.mysports.web.forms.FormOrganisation;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.ServletContext;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ControllerAbstract {

    protected static final String VIEW_NAME_HOME = "private/home";
    protected static final String VIEW_NAME_LOGIN = "public/login2";
    protected static final String VIEW_NAME_MANAGEMENT_SERVICE = "private/home-management-service";
    protected static final String VIEW_NAME_MANAGEMENT_ORGANISATION = "private/home-management-organisation";
    protected static final String VIEW_NAME_MANAGEMENT_GROUP = "private/home-management-group";
    protected static final String VIEW_NAME_MANAGEMENT_USER = "private/home-management-user";
    protected static final String PROPERTY_NAME_VERSION = "versionString";
    protected static final String PROPERTY_NAME_BUILD_TIME = "buildTime";
    protected static final String pattern = "yyyy-MM-dd HH:mm:ss";
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAbstract.class);
    protected final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    @Autowired
    protected IServiceUser serviceUser;

    protected IUser loggedInUser;

    @Autowired
    protected ServletContext servletContext;

    @Autowired
    private IServiceOrganisation serviceOrganisations;

    @Autowired
    private IServiceGroup serviceGroups;

    protected IUser getLoggedInUser() {
        if (this.loggedInUser != null) {
            return this.loggedInUser;
        }

        IUser user = null;
        try {
            final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null) {
                throw new Exception("Failed to determine loggedIn User!");
            }
            final String email = auth.getName(); // get logged in email
            user = serviceUser.findUserByEmail(email);
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return user;
    }

    /***
     * This is only for testing
     * @param user
     */
    public void setLoggedInUser(final IUser user) {
        this.loggedInUser = user;
    }

    protected Long getLoggedInUserId() {
        final IUser user = this.getLoggedInUser();
        if (user == null) {
            return null;
        }
        return user.getId();
    }

    protected Authentication getAuthentication() {
        try {
            return SecurityContextHolder.getContext().getAuthentication();
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    protected void populateModel(final Map<String, Object> model) {

        final IUser user = this.getLoggedInUser();

        model.put("time", new Date());
        model.put("loggedInUser", this.getLoggedInUser());
        model.put("ratings", ISport.Rating.Simple);
        model.put("status", IGroupMember.Status.Invited);
        model.put("permissions", IGroupMember.Permission.READ);

        // FIXME: we might able to remove the following items - not needed in the JSP
        model.put("formOrganisation", new FormOrganisation());
        model.put("formGroup", new FormGroup());

        final List<IOrganisation> organisations = this.serviceOrganisations.getOrganisationsOwnedByUser(user.getId());
        if (organisations.size() > 0) {
            model.put("organisations", organisations);
        }
        final List<IGroup> groups = this.serviceGroups.readGroupsManagedByUser(user.getId());
        if (groups.size() > 0) {
            model.put("groups", groups);
        }

        try {
            final String path = servletContext.getRealPath("/WEB-INF/version.json");
            final JSONObject jsonObject = (JSONObject) new JSONParser().parse(new FileReader(path));
            Date date = new Date();
            if (jsonObject.containsKey(PROPERTY_NAME_BUILD_TIME)) {
                date = new Date(Long.valueOf(jsonObject.get(PROPERTY_NAME_BUILD_TIME).toString()));
            }

            model.put(PROPERTY_NAME_VERSION, jsonObject.get(PROPERTY_NAME_VERSION));
            model.put(PROPERTY_NAME_BUILD_TIME, simpleDateFormat.format(date));

        } catch (IOException | ParseException e) {
            LOGGER.error("Failed to process version.json file");
        }

        model.put("viewName", "Home");
    }


}
