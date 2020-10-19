package com.epam.util.validation;

/**
 * Validation class contains common methods to validate data.
 *
 * @author Solodchenko Nikita
 *
 */
public class FieldValidation {

    private static final String isEmail = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private static final String isLatinOrCyrillicWord = "((?ui)[a-zа-яёїіыєґ]{1,})";
    private static final String isPassword = "((?ui)[a-zа-яёїіыєґ\\d]{4,})";

    /**
     * Validate email.
     *
     * @param email specified email.
     * @return true if the validate success.
     */
    public static boolean isEmail(String email) {

        if (email == null) {
            return false;
        }

        return email.matches(isEmail);
    }

    /**
     * Validate email.
     *
     * @param word specified word.
     * @return true if the validate success.
     */
    public static boolean isLatinOrCyrillicWord(String word) {
        if (word == null) {
            return false;
        }

        return word.matches(isLatinOrCyrillicWord);
    }

    /**
     * Validate password.
     *
     * @param word specified password.
     * @return true if the validate success.
     */
    public static boolean isPassword(String word) {
        if (word == null) {
            return false;
        }

        return word.matches(isPassword);
    }

    /**
     * Validate registration parameters.
     *
     * @param email specified email.
     * @param password specified password.
     * @param firstName specified first name.
     * @param lastName specified last name.
     * @return true if the validate success.
     */
    public static boolean validateRegistrationParameters(String email, String password, String firstName, String lastName) {
        if (email == null || password == null || firstName == null || lastName == null) {
            return false;
        }

        return isEmail(email) && isLatinOrCyrillicWord(firstName) && isLatinOrCyrillicWord(lastName) && isPassword(password);
    }

}
