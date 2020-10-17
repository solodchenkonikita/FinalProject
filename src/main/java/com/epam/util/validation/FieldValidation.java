package com.epam.util.validation;

public class FieldValidation {

    private static final String isEmail = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private static final String isLatinOrCyrillicWord = "((?ui)[a-zа-яёїіыєґ]{1,})";
    private static final String isPassword = "((?ui)[a-zа-яёїіыєґ\\d]{4,})";

    public static boolean isEmail(String email) {

        if (email == null) {
            return false;
        }

        return email.matches(isEmail);
    }

    public static boolean isLatinOrCyrillicWord(String word) {
        if (word == null) {
            return false;
        }

        return word.matches(isLatinOrCyrillicWord);
    }

    public static boolean isPassword(String word) {
        if (word == null) {
            return false;
        }

        return word.matches(isPassword);
    }

    public static boolean validateRegistrationParameters(String email, String password, String firstName, String lastName) {
        if (email == null || password == null || firstName == null || lastName == null) {
            return false;
        }

        return isEmail(email) && isLatinOrCyrillicWord(firstName) && isLatinOrCyrillicWord(lastName) && isPassword(password);
    }

}
