package net.gark.mysports.web.validators;

import net.gark.mysports.web.forms.FormUserRegistration;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component("userLoginValidator")
public class UserLoginValidator implements Validator {

    @Override
    public boolean supports(Class<?> klass) {
        return FormUserRegistration.class.isAssignableFrom(klass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.registration.email",
                "An email address must be provided.");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.registration.password",
                "A password must be provided.");
    }
}
