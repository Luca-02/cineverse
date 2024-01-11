package com.example.cineverse.view.search_result;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.cineverse.R;
import com.example.cineverse.databinding.ActivitySearchResultBinding;

public class SearchResultActivity extends AppCompatActivity {

    public static final String QUERY_TAG = "query";

    private ActivitySearchResultBinding binding;
    private NavController navController;
    private String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        binding = ActivitySearchResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getExtras();
        setActionBar();
        setNavController();
        binding.materialToolbar.setNavigationOnClickListener(
                view -> getOnBackPressedDispatcher().onBackPressed());
    }

    /**
     * Retrieves extra data from the intent bundle, including the title string ID and genre.
     */
    public void getExtras() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            query = bundle.getString(QUERY_TAG);
        }
    }

    /**
     * Sets up the {@link ActionBar} with the provided Toolbar.
     */
    private void setActionBar() {
        setSupportActionBar(binding.materialToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            if (query != null) {
                actionBar.setTitle(query);
            } else {
                actionBar.setTitle(null);
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

    public NavController getNavController() {
        return navController;
    }

    public String getQuery() {
        return query;
    }

}