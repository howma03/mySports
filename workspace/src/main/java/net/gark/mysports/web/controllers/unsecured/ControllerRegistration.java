package net.gark.mysports.web.controllers.unsecured;

import net.gark.mysports.domain.interfaces.IUser;
import net.gark.mysports.services.interfaces.IServiceRegistration;
import net.gark.mysports.web.controllers.ControllerAbstract;
import net.gark.mysports.web.forms.FormUserRegistration;
import net.gark.mysports.web.validators.UserRegistrationValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.UUID;

/**
 * Handles requests for the application home page.
 */
@Controller
public class ControllerRegistration extends ControllerAbstract {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerRegistration.class);

    private static final String REGISTRATION_FORM_VIEW = "public/registration2";
    private static final String REGISTRATION_FORM_SUCCESS_VIEW = "public/registrationSuccess";
    private static final String VERIFICATION_FORM_SUCCESS_VIEW = "public/registrationVerificationSuccess";
    private static final String VERIFICATION_FORM_FAILURE_VIEW = "public/registrationVerificationFailure";

    private UserRegistrationValidator registrationValidator;

    private IServiceRegistration registrationService;

    @Autowired
    public ControllerRegistration(final UserRegistrationValidator registrationValidator,
                                  final IServiceRegistration registrationService) {
        this.registrationService = registrationService;
        this.registrationValidator = registrationValidator;
    }

    // Display the form on the get request
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String showRegistration(final Map model) {

        final FormUserRegistration form = new FormUserRegistration();

        model.put("userRegistrationForm", form);

        LOGGER.debug("Registration started - form=" + form);

        return REGISTRATION_FORM_VIEW;
    }

    @RequestMapping(value = "/registration/simple", method = RequestMethod.POST)
    public String processRegistration(final FormUserRegistration form, final BindingResult result, final Map model) {

        registrationValidator.validate(form, result);

        if (result.hasErrors()) {
            LOGGER.warn("Validation errors seen - form=" + form);

            model.put("userRegistrationForm", form);
            model.put("errors", result);

            return REGISTRATION_FORM_VIEW;
        }

        model.put("userRegistrationForm", form);

        /*
         * Call the user registration services - if successful then return the user to the success view
         */

        try {
            registrationService.register(form);

            return REGISTRATION_FORM_SUCCESS_VIEW;

        } catch (final IServiceRegistration.ExceptionRegistrationFailure ex) {

            LOGGER.warn("Registration failed - from=" + form + ", ex=" + ex.getMessage());

            final FieldError fieldError = new FieldError("failure", "failure", null, false,
                    new String[]{"errors.registrationException"}, null, ex.getMessage());
            result.addError(fieldError);
        }

        model.put("errors", result);

        return REGISTRATION_FORM_VIEW;
    }

    @RequestMapping(value = "/registration/verification", method = RequestMethod.GET)
    public String verification(@RequestParam(value = "email") final String emailAddress,
                               @RequestParam(value = "id", required = true) final String uuidString, final Map model) {

        final FormUserRegistration userRegistrationForm = new FormUserRegistration();
        userRegistrationForm.setEmail(emailAddress);

        model.put("userRegistrationForm", userRegistrationForm);

        final UUID verificationCode = UUID.fromString(uuidString);

        if (!registrationService.completeUserVerification(emailAddress, verificationCode)) {

            LOGGER.warn("User verification failed - " + emailAddress);

            this.registrationService.requestVerificationCode(emailAddress);

            return VERIFICATION_FORM_FAILURE_VIEW;
        }

        final IUser user = serviceUser.findUserByEmail(emailAddress);
        if (user != null) {
            userRegistrationForm.setFirstName(user.getFirstName());
            userRegistrationForm.setLastName(user.getLastName());
        }

        return VERIFICATION_FORM_SUCCESS_VIEW;
    }
}
