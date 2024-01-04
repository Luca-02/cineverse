package com.example.cineverse.view.details;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.cineverse.R;
import com.example.cineverse.databinding.ActivityContentDetailsBinding;
import com.example.cineverse.view.network_error.NetworkErrorActivity;

public class ContentDetailsActivity extends AppCompatActivity {

    public static final String CONTENT_TYPE_STRING_TAG = "ContentTypeString";
    public static final String CONTENT_ID_TAG = "ContentId";

    private NavController navController;
    private String contentType;
    private int contentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        ActivityContentDetailsBinding binding = ActivityContentDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getExtras();
        setNavController();
    }

    /**
     * Retrieves extra data from the intent bundle, including the title string ID and genre.
     */
    public void getExtras() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            contentType = bundle.getString(CONTENT_TYPE_STRING_TAG);
            contentId = bundle.getInt(CONTENT_ID_TAG);
        }
    }

    /**
     * Sets up the {@link NavController} for navigating between destinations.
     * This method finds the {@link NavHostFragment} and initializes the {@link NavController}.
     */
    private void setNavController() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        }
    }

    public String getContentType() {
        return contentType;
    }

    public int getContentId() {
        return contentId;
    }

    /**
     * Opens the network error activity ({@link NetworkErrorActivity}).
     */
    public void openNetworkErrorActivity() {
        if (navController != null) {
            navController.navigate(R.id.action_global_networkErrorActivity);
        }
    }

}