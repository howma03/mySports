package net.gark.mysports.services;

import net.gark.mysports.services.interfaces.IServiceMail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by mark on 17/02/2017.
 */
@Service(value = "mailServiceJavaMail")
public class ServiceMailImplJavaMail extends ServiceMailAbstract {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceMailImplJavaMail.class);

    private Properties mailServerProperties = new Properties();

    public ServiceMailImplJavaMail() {

    }

    @PostConstruct
    public void init() {
        LOGGER.info("Setting up mail server properties..");

        mailServerProperties.setProperty("mail.smtp.server", env.getProperty("mail.smtp.server"));
        mailServerProperties.setProperty("mail.smtp.port", env.getProperty("mail.smtp.port"));
        mailServerProperties.setProperty("mail.smtp.user", env.getProperty("mail.smtp.user"));
        mailServerProperties.setProperty("mail.smtp.password", env.getProperty("mail.smtp.password"));
        mailServerProperties.setProperty("mail.smtp.auth", env.getProperty("mail.smtp.auth"));
        mailServerProperties.setProperty("mail.smtp.starttls.enable", env.getProperty("mail.smtp.starttls.enable"));

        LOGGER.info("mail.smtp.server=" + env.getProperty("mail.smtp.server"));
        LOGGER.info("mail.smtp.port=" + env.getProperty("mail.smtp.port"));
        LOGGER.info("mail.smtp.auth=" + env.getProperty("mail.smtp.auth"));
        LOGGER.info("mail.smtp.starttls.enable=" + env.getProperty("mail.smtp.starttls.enable"));

        LOGGER.info("Mail server properties have been setup successfully.");
    }

    /**
     * Service implemented using Java Mail
     * The actual transport sender
     *
     * @param from      - String
     * @param to        - String
     * @param inSubject - String
     * @param inBody    - String
     * @throws RuntimeException
     */
    protected void sendEmail(final String from, final String to, String inSubject, String inBody) throws RuntimeException {

        try {
            LOGGER.info(String.format("Sending message - from=[%s], to=[%s]", from, to));

            Session getMailSession;
            MimeMessage generateMailMessage;

            getMailSession = Session.getDefaultInstance(mailServerProperties, null);

            LOGGER.info("Mail Session has been created successfully..");

            generateMailMessage = new MimeMessage(getMailSession);
            generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress("howellmark13@gmail.com"));
            generateMailMessage.setSubject(inSubject);

            generateMailMessage.setContent(inBody, "text/html");

            Transport transport = getMailSession.getTransport("smtp");

            // Enter your correct gmail UserID and Password
            // if you have 2FA enabled then provide App Specific Password
            transport.connect((String) mailServerProperties.get("mail.smtp.server"),
                    (String)  mailServerProperties.get("mail.smtp.user"),
                    (String) mailServerProperties.get("mail.smtp.password"));
            transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
            transport.close();


            LOGGER.info(String.format("Sent message - from=[%s], to=[%s]", from, to));

        } catch (Exception e) {

            LOGGER.error(String.format("Send failed - from=[%s], to=[%s]", from, to), e);

            throw new RuntimeException("Caught a MessagingException, which means that there was a "
                    + "problem sending your message to google smtp server check the "
                    + "stack trace for more information", e);
        }
    }



}