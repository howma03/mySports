package net.gark.mysports.web.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {
    private static Pattern PATTERN;

    private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])" + ".{6,20})";

    static {
        PATTERN = Pattern.compile(PASSWORD_PATTERN);
    }

    public PasswordValidator() {
    }

    /**
     * Validate password with regular expression
     *
     * @param password password for validation
     * @return true valid password, false invalid password
     */
    public static boolean validate(final String password) {
        synchronized (PATTERN) {
            Matcher matcher = PATTERN.matcher(password);
            return matcher.matches();
        }
    }

    public static void main(String[] args) {
        System.out.println("valid=" + validate("S0uthern"));
    }
}
