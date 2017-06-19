package net.gark.mysports.services.dto;

import net.gark.mysports.domain.interfaces.IUser;
import net.gark.mysports.domain.interfaces.IUtility;

import java.util.Date;

public class DtoUserProfile implements IUser {

    private final Date created = new Date();
    private String createdString = "";
    private Long id;

    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String confirmPassword;

    private String country = "uk";

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public DtoUserProfile setId(final Long id) {
        this.id = id;
        return this;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public DtoUserProfile setFirstName(final String firstName) {
        this.firstName = firstName;
        return this;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public DtoUserProfile setLastName(final String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Override
    public String getVerificationCode() {
        return null;
    }

    @Override
    public DtoUserProfile setVerificationCode(final String verificationCode) {
        return this;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public DtoUserProfile setPassword(final String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public DtoUserProfile setConfirmPassword(final String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    @Override
    public Date getCreated() {
        return new Date(created.getTime());
    }

    @Override
    public DtoUserProfile setCreated(final Date date) {
        if (created == null) {
            this.createdString = "";
        } else {
            this.created.setTime(created.getTime());
            this.createdString = IUtility.format(created);
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
    public DtoUserProfile setEmail(final String email) {
        this.email = email;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public DtoUserProfile setCountry(final String country) {
        this.country = country;
        return this;
    }

    @Override
    public String toString() {
        return String.format("First Name [%s] Last Name [%s] Email [%s]", firstName, lastName, email);
    }
}
