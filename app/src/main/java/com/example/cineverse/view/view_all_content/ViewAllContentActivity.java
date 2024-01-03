package com.example.cineverse.view.view_all_content;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.cineverse.R;
import com.example.cineverse.data.model.genre.Genre;
import com.example.cineverse.databinding.ActivityViewAllContentBinding;
import com.example.cineverse.handler.BackPressedHandler;

/**
 * The {@link ViewAllContentActivity} is an activity responsible for displaying a list of content items
 * in a specific section, allowing users to navigate and explore the content.
 */
public class ViewAllContentActivity extends AppCompatActivity {

    /**
     * The tag used for the title string ID in the extras bundle.
     */
    public static final String TITLE_STRING_ID_TAG = "TitleStringId";

    /**
     * The tag used for the genre in the extras bundle.
     */
    public static final String GENRE_TAG = "Genre";

    private ActivityViewAllContentBinding binding;
    private NavController navController;

    private int titleStringId;
    private Genre genre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewAllContentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getExtras();
        setActionBar();
        setNavController();
        getWindow().setNavigationBarColor(android.R.attr.colorBackground);
        binding.materialToolbar.setNavigationOnClickListener(
                view -> getOnBackPressedDispatcher().onBackPressed());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ViewAllContentController.getInstance().clearParameters();
    }

    /**
     * Retrieves extra data from the intent bundle, including the title string ID and genre.
     */
    public void getExtras() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            titleStringId = bundle.getInt(TITLE_STRING_ID_TAG);
            genre = bundle.getParcelable(GENRE_TAG);
        }
    }

    /**
     * Sets up the {@link ActionBar} with the provided Toolbar.
     */
    private void setActionBar() {
        setSupportActionBar(binding.materialToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            if (genre != null) {
                actionBar.setTitle(genre.getName());
            } else {
                actionBar.setTitle(titleStringId);
            }
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

}