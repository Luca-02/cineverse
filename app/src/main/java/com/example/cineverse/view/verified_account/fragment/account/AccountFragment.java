package com.example.cineverse.view.verified_account.fragment.account;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.cineverse.R;
import com.example.cineverse.data.model.User;
import com.example.cineverse.databinding.FragmentAccountBinding;
import com.example.cineverse.utils.account.SizeModifierAccount;
import com.example.cineverse.view.verified_account.VerifiedAccountActivity;
import com.example.cineverse.utils.account.adapter.ScreenSlidePagerAdapter;
import com.example.cineverse.utils.account.ZoomOutPageTransformer;
import com.example.cineverse.utils.account.account_data.ProfileInfoData;
import com.example.cineverse.viewmodel.verified_account.VerifiedAccountViewModel;
import com.google.android.material.appbar.AppBarLayout;
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
    private ImageView profile_account_image;
    private TextView userName;
    private AppBarLayout appBarLayout;
    private SizeModifierAccount sizemodifier;
    private int initialImageSizePx; // Initial size of profile picture in pixels
    private int initialTextSizePx; // Initial text size of username in pixels
    MaterialButtonToggleGroup materialButtonToggleGroup;
    ConstraintLayout consMoviesLayout, consSeriesLayout;

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
        //binding.toolbar.inflateMenu(R.menu.account_page_section);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setElements(view);
        setViewModel();
        setViewPager(view);
        setAnimation();
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
        profile_account_image = view.findViewById(R.id.profile_image);
        appBarLayout = view.findViewById(R.id.account_appBarLayout);
        userName = view.findViewById(R.id.userName);
        materialButtonToggleGroup = view.findViewById(R.id.materialToggleGroup);
        consMoviesLayout = view.findViewById(R.id.constMoviesLayout);
        consSeriesLayout = view.findViewById(R.id.constSeriesLayout);
        materialButtonToggleGroup.check(R.id.buttonMovies);

        sizemodifier = new SizeModifierAccount();
        materialButtonToggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked){
                    if (checkedId == R.id.buttonMovies){
                        consMoviesLayout.setVisibility(View.VISIBLE);
                        consSeriesLayout.setVisibility(View.GONE);
                    }

                    else if (checkedId == R.id.buttonSeries){
                        consSeriesLayout.setVisibility(View.VISIBLE);
                        consMoviesLayout.setVisibility(View.GONE);
                    }
                }
            }
        });

        infoList.add(new ProfileInfoData("Film of the Year", 0, R.drawable.star_account_24));
        infoList.add(new ProfileInfoData("Total Movie", 0, R.drawable.tv_series_account_24px));
        infoList.add(new ProfileInfoData("Likes", 0, R.drawable.favorite_account_24px));
        infoList.add(new ProfileInfoData("Reviews", 0, R.drawable.reviews_account_24px));

    }

    private void setViewPager(View view){
        //Initialize Pager and Adapter
        viewPager = view.findViewById(R.id.pager_profile_info);
        pagerAdapter = new ScreenSlidePagerAdapter(this, infoList);
        viewPager.setPageTransformer(new ZoomOutPageTransformer());
        viewPager.setAdapter(pagerAdapter);
    }

    private void setAnimation(){
        // Get initial sizes of profileImage and userName from XML
        initialImageSizePx = profile_account_image.getLayoutParams().width; // Assuming width and height are the same
        initialTextSizePx = (int) userName.getTextSize(); // Initial text size in pixels
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isColorChanged = false;
            int scrollRange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                // Calculate the percentage of collapse/expansion (-1f to 1f)
                float collapsePercent = Math.abs(verticalOffset / (float) appBarLayout.getTotalScrollRange());

                // Calculate new sizes based on the collapse percentage
                int newSize = sizemodifier.calculateNewSize(collapsePercent,
                        initialImageSizePx, sizemodifier.dpToPx(getContext(),40));
                int newTextSize = sizemodifier.calculateNewTextSize(collapsePercent,
                        initialTextSizePx, sizemodifier.dpToPx(getContext(),18));
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    // Collapsed
                    if (!isColorChanged) {
                        binding.profileConstraintLayout.setBackgroundColor(Color.TRANSPARENT);
                        userName.setTextColor(getContext().getResources().getColor(R.color.white));
                        isColorChanged = true;
                    }
                } else {
                    // Expanded or in-between
                    if (isColorChanged) {
                        binding.profileConstraintLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.md_theme_dark_inverseOnSurface));
                        isColorChanged = false;
                    }
                }
                updateSize(newSize, newTextSize);
            }
        });
    }

    /**
     *
     * @param newSize
     * @param newTextSize
     *
     * Update size of profile elements while scrolling
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
    private void setToolbarAccountEvent() {
        binding.toolbarAccount.setOnMenuItemClickListener(menuItem -> {
            int itemId = menuItem.getItemId();
            if (itemId == R.id.logoutButton) {
                // do something for item1
                viewModel.logOut();
                return true;
            } else if (itemId == R.id.settingProfile) {
                ((VerifiedAccountActivity) getContext()).openAccountSettingsActivity();
                return true;
            } else {
                // if you do nothing, returning false
                return false;
            }
        });
    }

    private void viewAllPageSection(){
        binding.seeAllRecentWatched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((VerifiedAccountActivity) getContext()).openViewAllRecentToWatchActivity();
            }
        });

        binding.seeAllRecentReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((VerifiedAccountActivity) getContext()).openViewAllRecentReviewsActivity();
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
            binding.userName.setText(String.format("%s", user.getUsername()));
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