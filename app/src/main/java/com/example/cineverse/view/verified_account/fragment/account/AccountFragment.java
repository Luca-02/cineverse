package com.example.cineverse.view.verified_account.fragment.account;

import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.cineverse.R;
import com.example.cineverse.data.model.user.User;
import com.example.cineverse.databinding.FragmentAccountBinding;
import com.example.cineverse.view.verified_account.VerifiedAccountActivity;
import com.example.cineverse.view.verified_account.fragment.account.utils.AbstractSizeUpdate;
import com.example.cineverse.viewmodel.logged.verified_account.VerifiedAccountViewModel;
import com.google.android.material.appbar.AppBarLayout;

/**
 * The {@link AccountFragment} class representing the user account section of the application.
 * This fragment serves as one of the tabs in the BottomNavigationView.
 */
public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;
    private VerifiedAccountViewModel viewModel;

    private Toolbar toolbar;
    private ImageView profile_account_image;
    private TextView userName;
    private AppBarLayout appBarLayout;
    private int initialImageSizePx; // Initial size of profile picture in pixels
    private int initialTextSizePx; // Initial text size of username in pixels
    private AbstractSizeUpdate size_updater;


    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        //binding.toolbar.inflateMenu(R.menu.account_page_section);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setElements(view);
        setViewModel();
        setActionBar();
        setAnimation();
        setListeners();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        toolbar.setVisibility(View.VISIBLE);
        binding = null;
    }

    /*
    Menu Methods
     */

    /**
     * Set Fragment Elements
     */
    public void setElements(View view){
        profile_account_image = view.findViewById(R.id.profile_image);
        appBarLayout = view.findViewById(R.id.account_appBarLayout);
        userName = view.findViewById(R.id.userEmail);
        size_updater = new AbstractSizeUpdate(){};
    }

    /**
     * Set my Own Action Bar for the Fragment
     */
    private void setActionBar(){
        toolbar = getActivity().findViewById(R.id.materialToolbar);
        if (toolbar != null) {
            toolbar.setVisibility(View.GONE);
        }
    }

    public void setAnimation(){

        // Get initial sizes of profileImage and userName from XML
        initialImageSizePx = profile_account_image.getLayoutParams().width; // Assuming width and height are the same
        initialTextSizePx = (int) userName.getTextSize(); // Initial text size in pixels

        Log.d("IMAGE", String.valueOf(initialImageSizePx));
        Log.d("TEXT", String.valueOf(initialTextSizePx));
        // Add an offset listener to the AppBarLayout
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                // Calculate the percentage of collapse/expansion (-1f to 1f)
                float collapsePercent = Math.abs(verticalOffset / (float) appBarLayout.getTotalScrollRange());

                // Calculate new sizes based on the collapse percentage
                int newSize = size_updater.calculateNewSize(collapsePercent, initialImageSizePx, size_updater.dpToPx(getContext(),40)); // Initial and final size in pixels
                int newTextSize = size_updater.calculateNewTextSize(collapsePercent, initialTextSizePx, size_updater.dpToPx(getContext(),18)); // Initial and final text size in pixels

                // Update the size of profileImage and userName
                updateSize(newSize, newTextSize);
            }
        });
    }

    /*
    Update Size of Elments profile image account and Username
     */
    private void updateSize(int newSize, int newTextSize) {
        // Update the size of profileImage and userName
        profile_account_image.getLayoutParams().width = newSize;
        profile_account_image.getLayoutParams().height = newSize;
        profile_account_image.requestLayout();

        userName.setTextSize(TypedValue.COMPLEX_UNIT_PX, newTextSize);
    }

    /**
     * Sets up the ViewModel for the fragment.
     */
    private void setViewModel() {
        viewModel = new ViewModelProvider(this).get(VerifiedAccountViewModel.class);
        viewModel.getUserLiveData().observe(getViewLifecycleOwner(), this::handleUser);
        viewModel.getLoggedOutLiveData().observe(getViewLifecycleOwner(), this::handleLoggedOut);
    }

    /**
     * Sets up click listeners for UI elements in the fragment.
     */
    private void setListeners() {
        binding.toolbar.setOnMenuItemClickListener(menuItem -> {
            int itemId = menuItem.getItemId();
            if (itemId == R.id.logoutButton) {
                // do something for item1
                viewModel.logOut();
                return true;
            } else {
                // if you do nothing, returning false
                return false;
            }
        });
    }

    /**
     * Handles the user's authentication status and updates the UI accordingly.
     *
     * @param user The current {@link User} user object representing the logged-in user.
     */
    private void handleUser(User user) {
        if (user != null) {
            binding.userEmail.setText(String.format("%s", user.getUsername()));
            if (user.getPhotoUrl() != null) {
                Glide.with(this)
                        .load(user.getPhotoUrl())
                        .into(profile_account_image);
            }
        } else {
            viewModel.logOut();
        }
    }

    /**
     * Handles the event when the user is logged out.
     *
     * @param loggedOut A boolean indicating whether the user has been successfully logged out.
     */
    private void handleLoggedOut(Boolean loggedOut) {
        if (loggedOut) {
            ((VerifiedAccountActivity) requireActivity()).openAuthActivity();
        }
    }

}