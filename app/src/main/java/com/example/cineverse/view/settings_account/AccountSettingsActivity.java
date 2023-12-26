package com.example.cineverse.view.settings_account;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.cineverse.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

public class AccountSettingsActivity extends AppCompatActivity {

    private MaterialButton materialButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        materialButton = findViewById(R.id.buttonLogOutSettings);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}