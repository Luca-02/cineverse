package com.example.cineverse.view.details;

import static com.example.cineverse.view.details.ContentDetailsActivity.CONTENT_ID_TAG;
import static com.example.cineverse.view.details.ContentDetailsActivity.CONTENT_TYPE_STRING_TAG;

import android.content.Context;
import android.os.Bundle;

import androidx.navigation.NavController;

import com.example.cineverse.R;
import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.content.section.Movie;
import com.example.cineverse.data.model.content.section.Tv;
import com.example.cineverse.utils.NetworkUtils;
import com.example.cineverse.utils.mapper.ContentTypeMappingManager;

public class ContentDetailsActivityOpener {

    public static void openContentDetailsActivity(Context context, NavController navController, AbstractContent content) {
        if (navController != null) {
            if (!NetworkUtils.isNetworkAvailable(context)) {
                navController.navigate(R.id.action_global_networkErrorActivity);
            } else {
                if (content.getClass().isAssignableFrom(Movie.class) || content.getClass().isAssignableFrom(Tv.class)) {
                    Bundle bundle = new Bundle();
                    bundle.putString(CONTENT_TYPE_STRING_TAG, ContentTypeMappingManager.getContentType(content.getClass()));
                    bundle.putInt(CONTENT_ID_TAG, content.getId());
                    navController.navigate(R.id.action_global_contentDetailsActivity, bundle);
                }
            }
        }
    }
    
}
