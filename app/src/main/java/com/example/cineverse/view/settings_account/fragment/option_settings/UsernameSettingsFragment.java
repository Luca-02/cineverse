package com.example.cineverse.view.settings_account.fragment.option_settings;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.example.cineverse.R;
import com.example.cineverse.data.model.User;
import com.example.cineverse.databinding.FragmentOptionSettingsBinding;
import com.example.cineverse.databinding.FragmentUsernameSettingsBinding;
import com.example.cineverse.view.settings_account.AccountSettingsActivity;
import com.example.cineverse.viewmodel.verified_account.VerifiedAccountViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class UsernameSettingsFragment extends Fragment {

    private FragmentUsernameSettingsBinding binding;
    private VerifiedAccountViewModel viewModel;
    private String current_usr;
    private ActionBar actionBar;
    private MutableLiveData<String> liveString = new MutableLiveData<>();



    /**
     * Called to create and return the view hierarchy associated with the fragment.
     *
     * @param inflater           The LayoutInflater object that can inflate views in the fragment.
     * @param container          The parent view that the fragment's UI should be attached to.
     * @param savedInstanceState A Bundle object containing the fragment's previously saved state.
     * @return                   The inflated view for the fragment's UI.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUsernameSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    /**
     * Called immediately after onCreateView() and ensures that the view is created.
     *
     * @param view               The created view for the fragment.
     * @param savedInstanceState A Bundle object containing the fragment's previously saved state.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setViewModel();
        setActionBar();
        if (actionBar != null)
        {
            actionBar.setTitle(R.string.account_settings_name);
        }

        binding.changeUsrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomDialog();
            }
        });
    }

    private void setViewModel(){
        viewModel = new ViewModelProvider(this).get(VerifiedAccountViewModel.class);
        viewModel.getUserLiveData().observe(getViewLifecycleOwner(), this::getUserName);
    }

    private void setActionBar(){
        actionBar = ((AccountSettingsActivity)requireActivity()).getSupportActionBar();
    }

    private void getUserName(User user){
        if (user != null) {
            binding.nameUsernameChange.setText(String.format("%s", user.getUsername()));
            current_usr = user.getUsername();
            binding.nameUsernameChange.setHint(null);
        }
    }

    private void showBottomDialog(){

        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheetdialog_layout);

        ImageView close_dialog = dialog.findViewById(R.id.close_dialog_cross);
        TextInputEditText text_edit_dialog = dialog.findViewById(R.id.editText_newUsername);
        MaterialButton button_dialog = dialog.findViewById(R.id.confirm_change_button);
        TextInputLayout edit_layout_dialog = dialog.findViewById(R.id.inputedit_layout);
        MaterialButton button_layout_dialog = dialog.findViewById(R.id.confirm_change_button);

        text_edit_dialog.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                liveString.setValue(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (liveString.getValue().equals(current_usr)) {
                    edit_layout_dialog.setError("Don't use the same username");
                    button_dialog.setEnabled(false);
                }else{
                    edit_layout_dialog.setError(null);
                    button_dialog.setEnabled(true);
                }

            }
        });

        close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        button_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUsrNameDB(liveString.getValue());
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationAccount;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void changeUsrNameDB(String new_username){
        Log.d("new_usr", new_username);
    }


    /**
     * Called when the fragment is no longer in use.
     * This method ensures the ActionBar style is reset and releases the binding.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        ((AccountSettingsActivity)requireActivity()).setActionBarStyle();
        binding = null;
    }

}