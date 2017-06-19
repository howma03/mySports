package net.gark.mysports.services;

import net.gark.mysports.domain.interfaces.IUser;
import net.gark.mysports.services.interfaces.IServiceMail;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by mark on 17/02/2017.
 */
@Configuration
public abstract class ServiceMailAbstract implements IServiceMail {

    protected static final String VAR_NAME_COMPANY = "company";
    protected static final String VAR_NAME_PRODUCT = "product";
    protected static final String VAR_NAME_USER = "user";
    protected static final String VAR_NAME_WEB_ADDRESS = "webAddress";
    protected static final String VAR_NAME_SUPPORT_EMAIL = "supportEmail";
    protected static final String VAR_NAME_RESET_PASSWORD_ID = "id";
    protected static final String COMPANY = "GARK Ltd";
    protected static final String PRODUCT = "My sports";
    protected static final String SENDER_EMAIL_ADDRESS = "myAcheivementsWeb@gmail.com";
    protected static final String SUBJECT_SUCCESSFUL_REGISTRATION = "Registration successful";
    protected static final String SUBJECT_PASSWORD_RESET = "Password Reset";
    protected static final String TEMPLATE_LOCATION_FOR_EMAIL_VERIFICATION = "registration-verification.vm";
    protected static final String TEMPLATE_LOCATION_FOR_PASSWORD_RESET = "registration-reset-password.vm";
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceMailAbstract.class);
    protected String emailAddressForSender = SENDER_EMAIL_ADDRESS;

    @Autowired
    protected Environment env;

    @Autowired
    protected ServiceVelocityEngine velocityEngine;

    protected String alertMessage = "";

    public ServiceMailAbstract() {
    }

    @PostConstruct
    public void init() {
    }

    public String getEmailAddressForSender() {
        return emailAddressForSender;
    }

    public void setEmailAddressForSender(final String emailAddressForSender) {
        this.emailAddressForSender = emailAddressForSender;
    }

    public String getAlertMessage() {
        return alertMessage;
    }

    public void setAlertMessage(final String alertMessage) {
        this.alertMessage = alertMessage;
    }

    public void setVelocityEngine(final ServiceVelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    private void sendEmail(final IUser user, final String inSubject, final String inTemplate, final Map<String, Object> values) {
        final Properties props = new Properties();
        props.setProperty("resource.loader", "class");
        props.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

        final VelocityEngine engine = new VelocityEngine(props);
        final VelocityContext context = new VelocityContext();
        engine.init();

        for (final String key : values.keySet()) {
            LOGGER.debug(String.format("\t -- %s=%s", key, values.get(key)));
            context.put(key, values.get(key));
        }

        final StringWriter writer = new StringWriter();
        final Template template = engine.getTemplate("templates/" + inTemplate);
        template.merge(context, writer);
        final String inBody = writer.toString();

        sendEmail(emailAddressForSender, user.getEmail(), inSubject, inBody);
    }

    private void sendEmail(final IUser user, final String inSubject, final String inBody) {
        sendEmail(emailAddressForSender, user.getEmail(), inSubject, inBody);
    }

    protected abstract void sendEmail(final String from, final String to, String inSubject, String inBody) throws RuntimeException;


    protected Map<String, Object> getValues(final IUser user) {
        final Map<String, Object> values = new HashMap<>();
        values.put(VAR_NAME_COMPANY, COMPANY);
        values.put(VAR_NAME_PRODUCT, PRODUCT);
        values.put(VAR_NAME_USER, user);
        values.put(VAR_NAME_WEB_ADDRESS, this.env.getProperty("webAddress"));
        values.put(VAR_NAME_SUPPORT_EMAIL, emailAddressForSender);
        return values;
    }

    @Override
    public void sendMail(final String from, final String to, final String inSubject, final String inBody) {
        try {
            sendEmail(from, to, inSubject, inBody);
        } catch (final RuntimeException e) {
            LOGGER.error("sendMail failed", e);
        }
    }

    @Override
    public void sendVerificationEmail(final IUser user) {
        try {
            final String inSubject = COMPANY + " " + PRODUCT + " - " + SUBJECT_SUCCESSFUL_REGISTRATION;

            sendEmail(user, inSubject, TEMPLATE_LOCATION_FOR_EMAIL_VERIFICATION, getValues(user));

        } catch (final RuntimeException e) {
            LOGGER.error("sendVerificationEmail failed - user=" + user);
            throw new RuntimeException("sendVerificationEmail failed - user=" + user, e);
        }
    }

    @Override
    public void sendPasswordResetEmail(final IUser user) {

        try {
            LOGGER.debug("Reset Password");

            final Map<String, Object> values = getValues(user);

            values.put(VAR_NAME_RESET_PASSWORD_ID, user.getPassword());

            final String inSubject = COMPANY + " " + PRODUCT + " - " + SUBJECT_PASSWORD_RESET;

            sendEmail(user, inSubject, TEMPLATE_LOCATION_FOR_PASSWORD_RESET, values);

        } catch (final RuntimeException e) {
            LOGGER.error("sendPasswordResetEmail failed - user=" + user);
            throw new RuntimeException("sendPasswordResetEmail failed - user=" + user, e);
        }
    }
}
