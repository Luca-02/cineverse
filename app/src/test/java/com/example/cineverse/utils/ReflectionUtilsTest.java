package com.example.cineverse.utils;

import static org.junit.Assert.*;

import com.example.cineverse.data.model.content.movie.PosterMovie;
import com.example.cineverse.data.model.content.tv.PosterTv;
import com.example.cineverse.viewmodel.logged.verified_account.section.home.section.movie.TopRatedMovieViewModel;

import org.junit.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ReflectionUtilsTest {

    @Test
    public void getGenericType() {
        Class<?> startClass = A.class;
        Class<?> genericTypeClass = ReflectionUtils.getGenericType(startClass, 1);

        assertNotNull(genericTypeClass);
        System.out.println("Type associated with " + startClass.getSimpleName() +
                ": " + genericTypeClass.getSimpleName());
    }

    private static class A extends B<PosterMovie, PosterTv> {

    }

    private static class B<T, T1> {
    }

}