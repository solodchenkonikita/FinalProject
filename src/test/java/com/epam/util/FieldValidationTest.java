package com.epam.util;

import com.epam.util.validation.FieldValidation;
import org.junit.Test;

import static org.junit.Assert.*;

public class FieldValidationTest {

    @Test
    public void isEmailTest() {
        boolean actual = FieldValidation.isEmail("test@gmail.com");
        assertTrue(actual);

        actual = FieldValidation.isEmail("test@gmail");
        assertFalse(actual);

        actual = FieldValidation.isEmail(null);
        assertFalse(actual);
    }

    @Test
    public void isLatinOrCyrillicWordTest() {
        boolean actual = FieldValidation.isLatinOrCyrillicWord("test");
        assertTrue(actual);

        actual = FieldValidation.isLatinOrCyrillicWord("тест");
        assertTrue(actual);

        actual = FieldValidation.isLatinOrCyrillicWord("test1");
        assertFalse(actual);

        actual = FieldValidation.isLatinOrCyrillicWord(null);
        assertFalse(actual);
    }

    @Test
    public void isPasswordTest() {
        boolean actual = FieldValidation.isPassword("test123");
        assertTrue(actual);

        actual = FieldValidation.isPassword("test-123");
        assertFalse(actual);

        actual = FieldValidation.isPassword(null);
        assertFalse(actual);
    }

    @Test
    public void validateRegistrationParametersTest() {
        boolean actual = FieldValidation.validateRegistrationParameters("test@gmail.com", "test123" , "test", "test");
        assertTrue(actual);

        actual = FieldValidation.validateRegistrationParameters("test@gmail.com", "test-123" , "test", "test");
        assertFalse(actual);

        actual = FieldValidation.validateRegistrationParameters("test@gmail.com", "test123" , null, "test");
        assertFalse(actual);
    }
}
