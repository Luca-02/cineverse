package com.example.cineverse.utils.utils_account;

import android.app.Application;

import com.example.cineverse.data.model.account_model.MovieApiResponse;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JSONParserUtil {

    private final Application application;

    public JSONParserUtil(Application application) {
        this.application = application;
    }

    /**
     * Returns a list of News from a JSON file parsed using Gson.
     * Doc can be read here: <a href="https://github.com/google/gson">...</a>
     * @param fileName The JSON file to be parsed.
     * @return The NewsApiResponse object associated with the JSON file content.
     * @throws IOException
     */
    public MovieApiResponse parseJSONFileWithGson(String fileName) throws IOException {
        InputStream inputStream = application.getAssets().open(fileName);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        return new Gson().fromJson(bufferedReader, MovieApiResponse.class);
    }

}
