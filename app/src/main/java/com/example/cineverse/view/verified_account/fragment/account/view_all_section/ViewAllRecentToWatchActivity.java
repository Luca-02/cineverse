package com.example.cineverse.view.verified_account.fragment.account.view_all_section;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.cineverse.R;
import com.example.cineverse.databinding.ActivityAccountSettingsBinding;
import com.example.cineverse.databinding.ActivityViewAllRecentToWatchBinding;

public class ViewAllRecentToWatchActivity extends AppCompatActivity {
    private ActivityViewAllRecentToWatchBinding binding;

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setActionBar() {
        setSupportActionBar(binding.toolbarRecentToWatchAll);
        setActionBarStyle();
    }

    public void setActionBarStyle(){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.recent_to_watch_title);
        }
    }
}