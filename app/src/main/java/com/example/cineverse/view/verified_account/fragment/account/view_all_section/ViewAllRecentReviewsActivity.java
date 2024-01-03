package com.example.cineverse.view.verified_account.fragment.account.view_all_section;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.cineverse.R;
import com.example.cineverse.databinding.ActivityAccountSettingsBinding;
import com.example.cineverse.databinding.ActivityViewAllRecentReviewsBinding;

public class ViewAllRecentReviewsActivity extends AppCompatActivity {
    private ActivityViewAllRecentReviewsBinding binding;

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setActionBar() {
        setSupportActionBar(binding.toolbarReviewsAll);
        setActionBarStyle();
    }

    public void setActionBarStyle(){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.recent_reviews_title);
        }
    }
}