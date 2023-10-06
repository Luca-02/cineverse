package com.example.cineverse;

import static org.junit.Assert.*;

import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Test {

    private static final String ACCESS_KEY_AUTH = "bca628430df3c43389a71d8109d9be94";
    private static final String ACCESS_TOKEN_AUTH = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiY2E2Mjg0MzBkZjNjNDMzODlhNzFkODEwOWQ5YmU5NCIsInN1YiI6IjY1MWFlY2I1OWQ1OTJjMDEwMmMwYjcxMSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.YjbEjNLSKh9SejXvH4BCs4o0eDMYYIK_nws6Fqcc5HY";

    @org.junit.Test
    public void addition_isCorrect() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/movie/top_rated?language=en-US&page=1")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer " + ACCESS_TOKEN_AUTH)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.body() != null)
                System.out.print(response.body().string());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertTrue(true);
    }

}