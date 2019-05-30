package utils.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Jan
 */
public class PasswordValidator implements Validator {
    private static final int PASSWORD_MIN_LENGTH = 8;
    private static final String SPECIAL_CHARACTER = "[^a-zA-Z0-9]";

    @Override
    public boolean isValidate(String password) {
        return checkLength(password) 
                && checkSpecialCharacter(password)
                && checkUpperAndLowercase(password);
    }

    /**
     * A method to check whether a password is at least length 8
     *
     * @param password - the String to check
     * @return true if length is greater than or equal to 8 and false otherwise
     */
    public boolean checkLength(String password) {
        return password.length() >= PASSWORD_MIN_LENGTH;
    }

    /**
     * A method to check whether a password has at least 1 special character
     *
     * @param password - the String to check
     * @return true if the string contains at least 1 special character and
     * false otherwise
     */
    public boolean checkSpecialCharacter(String password) {
        Pattern pattern = Pattern.compile(SPECIAL_CHARACTER);
        Matcher match = pattern.matcher(password);
        return match.find();
    }

    /**
     * A method to check whether a password has at least 1 special character
     *
     * @param password - the String to check
     * @return true if the string contains at least 1 special character and
     * false otherwise
     */
    public boolean checkUpperAndLowercase(String password) {
        return checkUppercase(password) && checkLowercase(password);
    }

    /**
     * A method to check whether a password has at least 1 uppercase
     *
     * @param password - the String to check
     * @return true if the string contains at least 1 uppercase and false
     * otherwise
     */
    private boolean checkUppercase(String password) {
        for (int i = 0; i < password.length(); i++) {
            if (Character.isUpperCase(password.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * A method to check whether a password has at least 1 lowercase
     *
     * @param password - the String to check
     * @return true if the string contains at least 1 lowercase and false otherwise
     */
    private boolean checkLowercase(String password) {
        for (int i = 0; i < password.length(); i++) {
            if (!Character.isUpperCase(password.charAt(i))) {
                return true;
            }
        }
        return false;
    }
}
