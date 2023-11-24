package com.example.cineverse.utils.validator;

import static org.junit.Assert.*;

import org.junit.Test;

public class UsernameValidatorTest {

    @Test
    public void isValid() {
        assertTrue(UsernameValidator.getInstance().isValid("test"));
        assertTrue(UsernameValidator.getInstance().isValid("milanesiluca2002"));
        assertTrue(UsernameValidator.getInstance().isValid("milanesi.luca2002"));
    }

}