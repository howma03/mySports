package net.gark.mysports.services;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Configuration
@Service(value = "mailServiceAws")
@PropertySource("classpath:default-application.properties")
public class ServiceMailImplAws extends ServiceMailAbstract {

    protected static final String DEFAULT_EMAIL_ENDPOINT = "email-smtp.us-west-2.amazonaws.com";
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceMailImplAws.class);
    @Autowired
    private Environment env;

    /**
     * Amazon specific properties
     */
    private AWSCredentials credentials;
    private AmazonSimpleEmailService ses;
    private String endpoint;

    public ServiceMailImplAws() {
        this(DEFAULT_EMAIL_ENDPOINT);
    }

    public ServiceMailImplAws(final String endpoint) {
        this.endpoint = endpoint;
    }

    public static void main(final String[] args) {
        final ServiceMailImplAws s = new ServiceMailImplAws("email.eu-west-1.amazonaws.com");
        s.sendMail("howellmark13@gmail.com", "howellmark13@gmail.com", "Test", "Test");
        s.sendMail("myPasswordSafe@gmail.com", "howellmark13@gmail.com", "Test", "Test");
        s.sendMail("myPasswordSafe@gmail.com", "thestoat@gmail.com", "Test", "Test");
        s.sendMail("myPasswordSafe@gmail.com", "myPasswordSafe@gmail.com", "Test", "Test");
    }

    /***
     * Service implemented using AWS
     */

    @Override
    @PostConstruct
    public void init() {
        try {
            credentials = new AWSCredentials() {
                @Override
                public String getAWSAccessKeyId() {
                    return env.getProperty("accessKey");
                }

                @Override
                public String getAWSSecretKey() {
                    return env.getProperty("secretKey");
                }
            };

            ses = new AmazonSimpleEmailServiceClient(credentials);
            ses.setEndpoint(endpoint);
            // ses.setRegion(Region.getRegion(Regions.US_WEST_2));
            ses.setRegion(Region.getRegion(Regions.EU_WEST_1));

            LOGGER.info("AWS end-point=" + endpoint);
            LOGGER.info("AWS email user=" + credentials.getAWSAccessKeyId());

        } catch (final Exception e) {
            LOGGER.error("Exception occurred in CTOR", e);
        }
    }

    /**
     * The actual transport sender
     *
     * @param from
     * @param to
     * @param inSubject
     * @param inBody
     * @throws RuntimeException
     */
    @Override
    protected void sendEmail(final String from, final String to, final String inSubject, final String inBody) throws RuntimeException {

        try {
            LOGGER.info(String.format("Sending message - from=[%s], to=[%s]", from, to));

            final Destination destination = new Destination().withToAddresses(new String[]{to});

            final Content subject = new Content().withData(inSubject);
            final Content htmlBody = new Content().withData(inBody);
            final Body body = new Body().withHtml(htmlBody);
            final Message message = new Message().withSubject(subject).withBody(body);


            final SendEmailRequest sendRequest = new SendEmailRequest().withSource(from).withDestination(destination)
                    .withMessage(message);

            ses.sendEmail(sendRequest);

            LOGGER.info(String.format("Sent message - from=[%s], to=[%s]", from, to));

        } catch (final Exception e) {

            LOGGER.error(String.format("Send Failed message - from=[%s], to=[%s]", from, to), e);

            throw new RuntimeException("Caught a MessagingException, which means that there was a "
                    + "problem sending your message to Amazon's E-mail Service check the "
                    + "stack trace for more information", e);
        }
    }
}