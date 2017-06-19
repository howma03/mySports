package net.gark.mysports.web.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern PATTERN;

    static {
        PATTERN = Pattern.compile(EMAIL_PATTERN);
    }

    public EmailValidator() {

    }

    /**
     * Validate hex with regular expression
     *
     * @param hex hex for validation
     * @return true valid hex, false invalid hex
     */
    public static boolean validate(final String hex) {
        synchronized (PATTERN) {
            final Matcher matcher = PATTERN.matcher(hex);
            return matcher.matches();
        }
    }

    public static void main(final String[] args) {
        System.out.println("valid=" + validate("mark.howell1@pxti.com"));
    }

}