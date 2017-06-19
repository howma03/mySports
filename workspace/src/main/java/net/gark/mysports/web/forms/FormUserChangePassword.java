package net.gark.mysports.web.forms;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

@Deprecated
public class FormUserChangePassword {
    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Size(min = 6, max = 20)
    private String oldPassword;

    @NotEmpty
    @Size(min = 6, max = 20)
    private String password;

    @NotEmpty
    @Size(min = 6, max = 20)
    private String confirmPassword;

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    @Override
    public String toString() {
        return String
                .format("Email [%s] Old [%s] New [%s] Confirm [%s]", email, oldPassword, password, confirmPassword);
    }
}
