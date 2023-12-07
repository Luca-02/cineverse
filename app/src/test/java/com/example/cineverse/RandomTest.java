package com.example.cineverse;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Locale;

public class RandomTest {

    @Test
    public void regionTest() {
        Locale locale = Locale.getDefault();
        String countryCode = locale.getCountry();
        System.out.println(countryCode);
        assertNotNull(countryCode);
    }

}
