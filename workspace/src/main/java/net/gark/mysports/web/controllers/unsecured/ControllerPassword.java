package net.gark.mysports.web.controllers.unsecured;


import net.gark.mysports.domain.interfaces.IUser;
import net.gark.mysports.services.interfaces.IServiceRegistration;
import net.gark.mysports.services.interfaces.IServiceUser;
import net.gark.mysports.web.controllers.ControllerAbstract;
import net.gark.mysports.web.forms.FormUserChangePassword;
import net.gark.mysports.web.forms.FormUserResetPassword;
import org.apache.http.auth.InvalidCredentialsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/password")
public class ControllerPassword extends ControllerAbstract {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerPassword.class);

    private static final String RESET_PASSWORD_REQUEST_VIEW = "public/resetPasswordRequest";
    private static final String RESET_PASSWORD_REQUEST_SUCCESS_VIEW = "public/resetPasswordRequestSuccess";
    private static final String CHANGE_PASSWORD_VIEW = "public/changePassword";
    private static final String CHANGE_PASSWORD_REJECT_VIEW = "public/changePasswordReject";

    @Autowired
    private IServiceUser userService;

    @Autowired
    private IServiceRegistration registrationService;

    @Autowired
    private HttpServletRequest request;

    @SuppressWarnings({"rawtypes", "unchecked"})
    @RequestMapping(value = "/reset", method = RequestMethod.GET)
    public String resetPassword(Map model) {
        LOGGER.warn("ResetPassword requested");
        FormUserResetPassword form = new FormUserResetPassword();
        model.put("form", form);
        return RESET_PASSWORD_REQUEST_VIEW;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    public String resetPassword(@Valid FormUserResetPassword form, BindingResult result, Map model) {
        String email = form.getEmail();

        LOGGER.info("Resetting password ... email=" + email);

        if (result.getFieldError("email") != null) {
            LOGGER.warn("Validation errors seen in email address - results=" + result);

            model.put("form", form);
            model.put("errors", result);

            return RESET_PASSWORD_REQUEST_VIEW;
        }

        /*
         * Send an email containing the user's password
         */
        if (!registrationService.resetPassword(email)) {
            FieldError fieldError = new FieldError("email", "email", email, false,
                    new String[]{"errors.email.unknown"}, null, "The provided email address cannot be found");
            result.addError(fieldError);

            model.put("form", form);
            model.put("errors", result);

            return RESET_PASSWORD_REQUEST_VIEW;
        }

        model.put("form", form);

        return RESET_PASSWORD_REQUEST_SUCCESS_VIEW;
    }

    /***
     * A User has arrived here having received a 'reset password' email.
     *
     * If the id is correct then we must send them to the change password view - with the old-password already populated
     * but hidden.
     *
     * @param email
     * @param id
     * @param model
     * @return string - view
     * @throws ServletRequestBindingException
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping(value = "/reset/submit", method = RequestMethod.GET)
    public ModelAndView passwordResetSubmit(@RequestParam(value = "email", required = true) String email,
                                            @RequestParam(value = "id", required = true) String id, Map model) {

        IUser user = userService.findUserByEmail(email);

        if (user == null) {
            LOGGER.error("Cannot reset password; user cannot be found! - " + email);

            FieldError fieldError = new FieldError("email", "email", email, false,
                    new String[]{"errors.email.unknown"}, null, "The email cannot be found");

            model.put("email", email);
            model.put("errorsEmail", fieldError);

            /**
             * The view will inform the user of the failure and state that a new password reset email has been
             * generated.
             *
             * TODO: Change the JSP to look for and display any errors
             */

            return new ModelAndView(CHANGE_PASSWORD_REJECT_VIEW, model);
        }

        try {

            if (!user.getPassword().equals(id)) {
                throw new InvalidCredentialsException();
            }

            /**
             * Otherwise we need to send the user to the Password Change view.
             *
             * The OldPassword field will be pre-populated with the old password.
             */

            FormUserChangePassword form = new FormUserChangePassword();
            form.setEmail(email);
            form.setOldPassword(id);

            model.put("form", form);

            System.out.println("Constructed 'Change Password' form - " + form);

            return new ModelAndView(CHANGE_PASSWORD_VIEW, model);

        } catch (InvalidCredentialsException e) {
            LOGGER.error("Wrong Password - email=" + email);
        }

        LOGGER.warn("User authentication has failed - " + email);

        FieldError fieldError = new FieldError("identifier", "identifier", id, false,
                new String[]{"errors.identifier.unknown"}, null, "Wrong identifier provided");

        model.put("email", email);
        model.put("errorsIdentifier", fieldError);

        this.registrationService.resetPassword(email);

        /**
         * The view will inform the user of the failure and state that a new password reset email has been generated.
         *
         * TODO: Change the JSP to look for and display any errors
         */
        return new ModelAndView(CHANGE_PASSWORD_REJECT_VIEW, model);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @RequestMapping(value = "/change", method = RequestMethod.GET)
    public String changePassword(Map model) {
        LOGGER.warn("ResetPassword requested");
        FormUserResetPassword form = new FormUserResetPassword();
        model.put("form", form);
        return CHANGE_PASSWORD_VIEW;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @RequestMapping(value = "/change/submit", method = RequestMethod.POST)
    public String changePasswordSubmit(@Valid FormUserChangePassword form, BindingResult result, Map model) {

        String email = form.getEmail();

        IUser user = userService.findUserByEmail(email);

        LOGGER.info("Received request to change password for email - " + email + ", form=" + form);

        if (user == null) {
            LOGGER.error("Cannot find user! - " + email);

            FieldError fieldError = new FieldError("email", "email", email, false,
                    new String[]{"errors.email.unknown"}, null, "Cannot find user with given email address");
            result.addError(fieldError);

            model.put("form", form);
            model.put("errors", result);

            return CHANGE_PASSWORD_VIEW;
        }

        if (!user.getEmail().equalsIgnoreCase(email)) {

            LOGGER.error("Only owner may set the password for your their account! - " + email);

            FieldError fieldError = new FieldError("email", "email", email, false,
                    new String[]{"errors.email.unknown"}, null,
                    "You can only set the password for your own account!");
            result.addError(fieldError);

            model.put("form", form);
            model.put("errors", result);

            return CHANGE_PASSWORD_VIEW;
        }

        String oldPassword = form.getOldPassword();
        String newPassword = form.getPassword();
        String newConfirmPassword = form.getConfirmPassword();

        try {
            if (!oldPassword.equals(user.getPassword())) {
                LOGGER.error("Previous password not provided! - " + oldPassword);

                FieldError fieldError = new FieldError("oldPassword", "oldPassword", email, false,
                        new String[]{"errors.oldPassword.invalid"}, null, "Old password does not match");
                result.addError(fieldError);

            } else if (newPassword == null || newPassword.length() < 8) {
                LOGGER.error("Password is too weak! - " + newPassword);

                FieldError fieldError = new FieldError("password", "password", email, false,
                        new String[]{"errors.password.invalid"}, null, "Password is not valid");
                result.addError(fieldError);

            } else if (!newPassword.equals(newConfirmPassword)) {
                LOGGER.error("Confirm Password does not match password! - " + newConfirmPassword);

                FieldError fieldError = new FieldError("confirmPassword", "confirmPassword", email, false,
                        new String[]{"errors.confirmPassword.noMatch"}, null, "Password and Confirmed do not match");
                result.addError(fieldError);

            } else {

                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

                user.setPassword(encoder.encode(newPassword));

                this.userService.updateUser(user);

                LOGGER.info("Successfully updated user password - " + form);

                form.setOldPassword(null);
                form.setPassword(null);
                form.setConfirmPassword(null);

                model.put("form", form);
                model.put("success", "Password has been changed successfully");

                return CHANGE_PASSWORD_VIEW;
            }

        } catch (Exception ex) {
            FieldError fieldError = new FieldError("failure", "failure", "", false,
                    new String[]{"errors.changepassword.failure"}, null, "Failed to update user password!");
            result.addError(fieldError);

            LOGGER.error("Failed to updated user password - " + email, ex);
        }

        model.put("form", form);
        model.put("errors", result);

        return CHANGE_PASSWORD_VIEW;
    }
}
