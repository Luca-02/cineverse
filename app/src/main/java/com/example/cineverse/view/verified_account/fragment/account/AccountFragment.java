package com.example.cineverse.view.verified_account.fragment.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.cineverse.R;
import com.example.cineverse.adapter.account.ScreenSlidePagerAdapter;
import com.example.cineverse.data.model.User;
import com.example.cineverse.databinding.FragmentAccountBinding;
import com.example.cineverse.utils.account.ZoomOutPageTransformer;
import com.example.cineverse.utils.account.account_data.ProfileInfoData;
import com.example.cineverse.view.verified_account.VerifiedAccountActivity;
import com.example.cineverse.viewmodel.verified_account.VerifiedAccountViewModel;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@link AccountFragment} class representing the user account section of the application.
 * This fragment serves as one of the tabs in the BottomNavigationView.
 */
public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;
    private VerifiedAccountViewModel viewModel;
    MaterialButtonToggleGroup materialButtonToggleGroup;

    /*
    Pager View Info User Data
     */
    List<ProfileInfoData> infoList = new ArrayList<>();
    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setElements(view);
        setViewModel();
        setViewPager(view);
        setToolbarAccountEvent();
        viewAllPageSection();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     *
     * @param view
     * Set elements in my Fragment
     */
    private void setElements(View view){
        materialButtonToggleGroup = view.findViewById(R.id.materialToggleGroup);
        materialButtonToggleGroup.check(R.id.buttonMovies);

        materialButtonToggleGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked){
                if (checkedId == R.id.buttonMovies){

                }

                else if (checkedId == R.id.buttonSeries){
                }
            }
        });

        infoList.add(new ProfileInfoData(getString(R.string.to_watch), 0, R.drawable.outline_video_library));
        infoList.add(new ProfileInfoData(getString(R.string.review), 0, R.drawable.outline_question_answer));
    }

    private void setViewPager(View view){
        //Initialize Pager and Adapter
        viewPager = view.findViewById(R.id.pager_profile_info);
        pagerAdapter = new ScreenSlidePagerAdapter(this, infoList);
        viewPager.setPageTransformer(new ZoomOutPageTransformer());
        viewPager.setAdapter(pagerAdapter);
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
    private void setToolbarAccountEvent() {
        binding.materialToolbar.setOnMenuItemClickListener(menuItem -> {
            int itemId = menuItem.getItemId();
            if (itemId == R.id.logoutButton) {
                // do something for item1
                viewModel.logOut();
                return true;
            } else if (itemId == R.id.settingProfile) {
                ((VerifiedAccountActivity) requireActivity()).openSettingsActivity();
                return true;
            } else {
                // if you do nothing, returning false
                return false;
            }
        });
    }

    private void viewAllPageSection(){
        binding.seeAllRecentWatched.setOnClickListener(v ->
                ((VerifiedAccountActivity) requireActivity()).openUserWatchlistActivity());

        binding.seeAllRecentReviews.setOnClickListener(v ->
                ((VerifiedAccountActivity) requireActivity()).openUserReviewsActivity());

    }

    /**
     * Handles the user's authentication status and updates the UI accordingly.
     *
     * @param user The current {@link User} user object representing the logged-in user.
     */
    private void handleUser(User user) {
        if (user != null) {
            binding.userName.setText(String.format("%s", user.getUsername()));
            if (user.getPhotoUrl() != null) {
                Glide.with(this)
                        .load(user.getPhotoUrl())
                        .into(binding.profileImage);
            }
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