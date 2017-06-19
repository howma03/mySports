package net.gark.mysports.web.forms;

import net.gark.mysports.domain.interfaces.IUser;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormUserRegistration implements IUser {

    private static final String pattern = "yyyy/MM/dd HH:mm:ss";
    private static final SimpleDateFormat format = new SimpleDateFormat(pattern);

    private Date created = new Date();
    private String createdString = "";

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Size(min = 4, max = 100)
    private String name; // nick name

    @NotEmpty
    @Size(min = 4, max = 100)
    private String firstName;

    @NotEmpty
    @Size(min = 4, max = 100)
    private String lastName;

    @NotEmpty
    @Size(min = 4, max = 20)
    private String password;

    @NotEmpty
    private String confirmPassword;

    private String country = "uk";

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public IUser setFirstName(final String firstName) {
        this.firstName = firstName;
        return this;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public FormUserRegistration setLastName(final String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Override
    public String getVerificationCode() {
        return null;
    }

    @Override
    public FormUserRegistration setVerificationCode(final String verificationCode) {
        return this;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public FormUserRegistration setPassword(final String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public FormUserRegistration setConfirmPassword(final String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    @Override
    public Date getCreated() {
        return created;
    }

    @Override
    public FormUserRegistration setCreated(final Date date) {
        this.created = created;
        if (created == null) {
            this.createdString = "";
        } else {
            this.createdString = format.format(created);
        }
        return this;
    }

    @Override
    public String getCreatedString() {
        return createdString;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public FormUserRegistration setEmail(final String email) {
        this.email = email;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public FormUserRegistration setCountry(final String country) {
        this.country = country;
        return this;
    }

    @Override
    public String toString() {
        return String.format("First Name [%s] Last Name [%s] Email [%s]", firstName, lastName, email);
    }
}
