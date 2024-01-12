package com.example.cineverse.view.user_watchlist;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;

import com.example.cineverse.R;
import com.example.cineverse.data.model.ui.BaseActivity;
import com.example.cineverse.databinding.ActivityUserWatchlistBinding;

public class UserWatchlistActivity extends BaseActivity {

    private ActivityUserWatchlistBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserWatchlistBinding.inflate(getLayoutInflater());
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