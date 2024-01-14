package com.example.cineverse.view.settings.fragment.section;

import static com.example.cineverse.theme.ThemeModeController.DARK_MODE;
import static com.example.cineverse.theme.ThemeModeController.LIGHT_MODE;
import static com.example.cineverse.theme.ThemeModeController.SYSTEM_DEFAULT_MODE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.cineverse.R;
import com.example.cineverse.theme.ThemeModeController;
import com.example.cineverse.databinding.FragmentThemeSettingsBinding;
import com.example.cineverse.view.settings.SettingsActivity;

public class ThemeSettingsFragment extends Fragment {

    private FragmentThemeSettingsBinding binding;
    private ThemeModeController themeModeController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentThemeSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        themeModeController = ((SettingsActivity) requireActivity()).getThemeModeController();
        setActionBar();
        setContentUi();
        setListener();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((SettingsActivity)requireActivity()).setActionBarStyle();
        binding = null;
    }

    private void setActionBar(){
        ActionBar actionBar = ((SettingsActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.theme);
        }
    }

    private void setContentUi() {
        String themeMode = themeModeController.getThemeMode();
        if (themeMode == null) {
            binding.radioGroup.check(R.id.systemRadio);
        } else {
            switch (themeMode) {
                case LIGHT_MODE: {
                    binding.radioGroup.check(R.id.lightRadio);
                    break;
                }
                case DARK_MODE: {
                    binding.radioGroup.check(R.id.darkRadio);
                    break;
                }
                case SYSTEM_DEFAULT_MODE: {
                    binding.radioGroup.check(R.id.systemRadio);
                    break;
                }
            }
        }
    }

    private void setListener() {
        binding.radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            AppCompatDelegate delegate = ((SettingsActivity) requireActivity()).getDelegate();
            if (checkedId == R.id.lightRadio) {
                themeModeController.applyTheme(delegate, LIGHT_MODE);
            } else if (checkedId == R.id.darkRadio) {
                themeModeController.applyTheme(delegate, DARK_MODE);
            } else if (checkedId == R.id.systemRadio) {
                themeModeController.applyTheme(delegate, SYSTEM_DEFAULT_MODE);
            }
        });
    }

}