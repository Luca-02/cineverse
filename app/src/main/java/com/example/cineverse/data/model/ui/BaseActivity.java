package com.example.cineverse.data.model.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cineverse.data.source.theme.ThemeModeLocalDataSource;

public class BaseActivity extends AppCompatActivity {

    private ThemeModeLocalDataSource themeModeLocalDataSource;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        themeModeLocalDataSource = new ThemeModeLocalDataSource(getBaseContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        setThemeMode();
    }

    private void setThemeMode() {
        themeModeLocalDataSource = new ThemeModeLocalDataSource(getBaseContext());
        String themeMode = themeModeLocalDataSource.getThemeMode();
        themeModeLocalDataSource.applyTheme(getDelegate(), themeMode);
    }

}
