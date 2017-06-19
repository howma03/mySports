package net.gark.mysports.web.forms;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class FormUserResetPassword {
    @NotEmpty
    @Email
    private String email;

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return String.format("Email [%s]", email);
    }
}
