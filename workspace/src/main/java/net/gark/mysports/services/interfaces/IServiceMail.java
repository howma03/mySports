package net.gark.mysports.services.interfaces;

import net.gark.mysports.domain.interfaces.IUser;

public interface IServiceMail {
    /***
     * Send a simple email
     *
     * @param from
     * @param to
     * @param subject
     * @param body
     */
    void sendMail(String from, String to, String subject, String body);

    /**
     * Send a verification email to the user - the user will need to verify that they have received the email by
     * visiting the web site and providing their verification code.
     *
     * @param user
     */
    void sendVerificationEmail(final IUser user);

    /**
     * This is an email services that will generate a standard cloud email containing the password for the user.
     *
     * @param user
     */
    void sendPasswordResetEmail(final IUser user);
}
