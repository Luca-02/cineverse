package com.example.cineverse.view.settings.fragment.section;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import com.example.cineverse.R;
import com.example.cineverse.databinding.FragmentLanguageSettingsBinding;
import com.example.cineverse.theme.LanguageController;
import com.example.cineverse.view.settings.SettingsActivity;
import com.google.android.material.radiobutton.MaterialRadioButton;

import java.util.Locale;

public class LanguageSettingsFragment extends Fragment {

    private FragmentLanguageSettingsBinding binding;
    private LanguageController languageController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLanguageSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        languageController = ((SettingsActivity) requireActivity()).getLanguageController();
        setActionBar();
        initRadioGroup();
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
            actionBar.setTitle(R.string.language);
        }
    }

    private void initRadioGroup() {
        String[] languageCodes = languageController.getLanguageCodes();
        RadioGroup radioGroup = binding.radioGroup;

        for (String languageCode : languageCodes) {
            MaterialRadioButton radioButton = new MaterialRadioButton(requireContext());
            radioButton.setText(getLanguageDisplayName(languageCode));
            radioGroup.addView(radioButton);
        }

        int systemLanguageIndex = findLanguageIndex(languageCodes, languageController.getLanguage());
        if (systemLanguageIndex >= 0) {
            radioGroup.check(radioGroup.getChildAt(systemLanguageIndex).getId());
        }

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            int index = radioGroup.indexOfChild(radioGroup.findViewById(checkedId));
            if (index >= 0 && index < languageCodes.length) {
                String selectedLanguage = languageCodes[index];
                languageController.setLanguage(requireActivity(), selectedLanguage);
            }
        });
    }

    private String getLanguageDisplayName(String languageCode) {
        if ("default".equals(languageCode)) {
            return getString(R.string.system_default);
        } else {
            Locale locale = new Locale(languageCode);
            return locale.getDisplayName(locale);
        }
    }

    private int findLanguageIndex(String[] languageCodes, String languageCode) {
        for (int i = 0; i < languageCodes.length; i++) {
            if (languageCodes[i].equals(languageCode)) {
                return i;
            }
        }
        return 0;
    }

}