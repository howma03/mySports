package net.gark.mysports.web.controllers.unsecured;

import net.gark.mysports.web.controllers.ControllerAbstract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by mark on 11/02/2017.
 */
@Controller
public class ControllerLogin extends ControllerAbstract {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerLogin.class);

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@RequestParam(value = "error", required = false) final String error,
                        @RequestParam(value = "logout", required = false) final String logout) {
        LOGGER.warn("Login requested");

        return VIEW_NAME_LOGIN;
    }

    @RequestMapping(value = "/logMeOut", method = RequestMethod.GET)
    public String logout(final HttpServletRequest request, final HttpServletResponse response, final Model model) {
        LOGGER.warn("Logout requested");
        final org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
    }
}
