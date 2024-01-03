package com.example.cineverse.view.verified_account.view_all_section_account;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.cineverse.R;
import com.example.cineverse.databinding.ActivityViewAllRecentToWatchBinding;

public class ViewAllRecentToWatchActivity extends AppCompatActivity {
    private ActivityViewAllRecentToWatchBinding binding;

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState A Bundle object containing the activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewAllRecentToWatchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setActionBar();
        getWindow().setNavigationBarColor(android.R.attr.colorBackground);
        binding.toolbarRecentToWatchAll.setNavigationOnClickListener(
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
        setSupportActionBar(binding.toolbarRecentToWatchAll);
        setActionBarStyle();
    }

    /**
     * Sets the style for the ActionBar, including the title.
     */
    public void setActionBarStyle(){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.recent_to_watch_title);
        }
    }
}