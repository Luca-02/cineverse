package com.example.cineverse.utils;

import static org.junit.Assert.*;

import com.example.cineverse.data.model.content.section.Movie;
import com.example.cineverse.data.model.content.section.Tv;

import org.junit.Test;

public class ReflectionUtilsTest {

    @Test
    public void getGenericType() {
        Class<?> startClass = A.class;
        Class<?> genericTypeClass = ReflectionUtils.getGenericType(startClass, 1);

        assertNotNull(genericTypeClass);
        System.out.println("Type associated with " + startClass.getSimpleName() +
                ": " + genericTypeClass.getSimpleName());
    }

    private static class A extends B<Movie, Tv> {

    }

    private static class B<T, T1> {
    }

}