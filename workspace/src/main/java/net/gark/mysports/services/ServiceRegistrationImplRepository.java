package net.gark.mysports.services;

import net.gark.mysports.domain.entity.User;
import net.gark.mysports.domain.interfaces.IUser;
import net.gark.mysports.services.interfaces.IServiceMail;
import net.gark.mysports.services.interfaces.IServicePasswordGenerator;
import net.gark.mysports.services.interfaces.IServiceRegistration;
import net.gark.mysports.services.interfaces.IServiceUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

@Service("registrationService")
public class ServiceRegistrationImplRepository implements IServiceRegistration {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRegistrationImplRepository.class);

    @Autowired
    private IServiceUser userService;

    @Autowired
    @Qualifier("mailServiceJavaMail")
    private IServiceMail mailService;

    @Autowired
    private IServicePasswordGenerator passwordGenerator;

    public ServiceRegistrationImplRepository() {

    }

    @Override
    public IUser register(final IUser from) throws ExceptionRegistrationFailure {

        final IUser foundUser = userService.findUserByEmail(from.getEmail());
        if (foundUser != null) {
            throw new ExceptionRegistrationFailure("Already Registered ");
        }

        IUser newUser = null;
        try {
            newUser = new User(from);

            newUser = userService.addUser(newUser);
            mailService.sendVerificationEmail(newUser);

        } catch (InvocationTargetException | IllegalAccessException | RuntimeException e) {
            newUser.setEnabled(false);
            userService.updateUser(newUser);
            throw new ExceptionRegistrationFailure("Registration failed", e);
        }
        return newUser;
    }

    @Override
    public boolean completeUserVerification(final String emailAddress, final UUID verificationCode) {
        final IUser user = userService.findUserByEmail(emailAddress);
        if (user == null) {
            LOGGER.warn(String.format("Cannot locate user for verification process", emailAddress));
            return false;
        }

        if (!verificationCode.toString().equals(user.getVerificationCode())) {
            LOGGER.warn(String.format("Provided verification code [%s] does not match user's [%s] code [%s]",
                    verificationCode, user.getEmail(), user.getVerificationCode()));
            return false;
        }

        user.setVerified(true);
        user.setEnabled(true);

        userService.updateUser(user);
        return true;
    }

    @Override
    public boolean resetPassword(final String emailAddress) {
        final IUser user = this.userService.findUserByEmail(emailAddress);

        if (user == null) {
            LOGGER.warn("Cannot reset password; user not found - " + emailAddress);
            return false;
        }

        /**
         * We now need to reset the password - we need to generate a strong password.
         */

        user.setPassword(passwordGenerator.generate());

        userService.updateUser(user);
        mailService.sendPasswordResetEmail(user);

        return true;
    }

    @Override
    public boolean requestVerificationCode(final String emailAddress) {
        final IUser user = this.userService.findUserByEmail(emailAddress);

        if (user == null) {
            LOGGER.warn("Cannot send verification code reminder; user not found - " + emailAddress);
            return false;
        }

        mailService.sendVerificationEmail(user);

        return true;
    }
}
