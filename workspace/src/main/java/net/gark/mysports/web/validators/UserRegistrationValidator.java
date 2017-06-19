package net.gark.mysports.web.validators;

import net.gark.mysports.web.forms.FormUserRegistration;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component("registrationValidator")
public class UserRegistrationValidator implements Validator {

    @Override
    public boolean supports(Class<?> klass) {
        return FormUserRegistration.class.isAssignableFrom(klass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        FormUserRegistration registration = (FormUserRegistration) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty.registration.firstName",
                "User's first name must be provided.");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty.registration.lastName",
                "User's last name must be provided.");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.registration.email",
                "An email address must be provided.");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.registration.password",
                "A password must be provided");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "NotEmpty.registration.confirmPassword",
                "Confirm password must be provided.");

        if (!PasswordValidator.validate(registration.getPassword())) {
            errors.rejectValue("password", "TooWeak.registration.password", "The password is not strong enough");
        }

        String firstName = registration.getFirstName();
        if (firstName != null && firstName.length() > 50) {
            errors.rejectValue("firstName", "lengthOfUser.registration.name",
                    "User's first name must not more than 50 characters.");
        }
        String lastName = registration.getLastName();
        if (lastName != null && lastName.length() > 50) {
            errors.rejectValue("lastName", "lengthOfUser.registration.lastName",
                    "User's last name must not more than 50 characters.");
        }

        if (registration.getPassword() != null && registration.getConfirmPassword() != null) {
            if (!(registration.getPassword()).equals(registration.getConfirmPassword())) {
                errors.rejectValue("password", "matchingPassword.registration.password",
                        "The password and confirm password must match.");
            }
        }
    }
}
