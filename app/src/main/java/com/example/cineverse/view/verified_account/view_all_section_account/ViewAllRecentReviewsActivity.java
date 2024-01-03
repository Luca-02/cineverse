package com.example.cineverse.view.verified_account.view_all_section_account;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.cineverse.R;
import com.example.cineverse.databinding.ActivityViewAllRecentReviewsBinding;

public class ViewAllRecentReviewsActivity extends AppCompatActivity {
    private ActivityViewAllRecentReviewsBinding binding;

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState A Bundle object containing the activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewAllRecentReviewsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setActionBar();
        getWindow().setNavigationBarColor(android.R.attr.colorBackground);
        binding.toolbarReviewsAll.setNavigationOnClickListener(
                view -> getOnBackPressedDispatcher().onBackPressed());
    }

    /**
     * Called when the activity is about to be destroyed.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    /**
     * Sets the ActionBar for the activity and its style.
     */
    private void setActionBar() {
        setSupportActionBar(binding.toolbarReviewsAll);
        setActionBarStyle();
    }

    /**
     * Sets the style for the ActionBar, including the title.
     */
    public void setActionBarStyle(){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.recent_reviews_title);
        }
    }
}