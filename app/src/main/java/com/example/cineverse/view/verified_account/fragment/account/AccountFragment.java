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

import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.cineverse.R;
import com.example.cineverse.data.model.account_model.MovieModel;
import com.example.cineverse.data.model.User;
import com.example.cineverse.databinding.FragmentAccountBinding;
import com.example.cineverse.utils.utils_account.JSONParserUtil;
import com.example.cineverse.utils.utils_account.adapter.RVItem_AccountAdapter;
import com.example.cineverse.view.verified_account.VerifiedAccountActivity;
import com.example.cineverse.utils.utils_account.AbstractSizeUpdate;
import com.example.cineverse.utils.utils_account.adapter.ScreenSlidePagerAdapter;
import com.example.cineverse.utils.utils_account.ZoomOutPageTransformer;
import com.example.cineverse.utils.utils_account.account_data.ProfileInfoData;
import com.example.cineverse.viewmodel.verified_account.VerifiedAccountViewModel;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    CollapsingToolbarLayout collapsingToolbarLayout;
    ConstraintLayout profile_ConstraintLayout, consMoviesLayout, consSeriesLayout;

    /*
    Pager View Info User Data
     */
    List<ProfileInfoData> infoList = new ArrayList<>();
    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;

    /*
    Display Movie RecycleView
     */
    RecyclerView rVRecentWatched;
    RVItem_AccountAdapter rvItemAdapter;
    List<MovieModel> newList;

    /*
    Constant
     */
    private static final int ITEMS_MV_TV_TO_DISPLAY = 10;

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
        setInfoData(view);
        setAnimation();
        setListeners();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        toolbar.setVisibility(View.VISIBLE);
        binding = null;
    }

    /**
     * Set Fragment Elements
     */
    public void setElements(View view){
        profile_account_image = view.findViewById(R.id.profile_image);
        appBarLayout = view.findViewById(R.id.account_appBarLayout);
        userName = view.findViewById(R.id.userName);

        consMoviesLayout = view.findViewById(R.id.constMoviesLayout);
        consSeriesLayout = view.findViewById(R.id.constSeriesLayout);

        consMoviesLayout.setVisibility(View.VISIBLE);
        consSeriesLayout.setVisibility(View.VISIBLE);
        size_updater = new AbstractSizeUpdate(){};
        collapsingToolbarLayout = view.findViewById(R.id.collapsingToolbarLayout);
        profile_ConstraintLayout = view.findViewById(R.id.profileConstraintLayout);

        infoList.add(new ProfileInfoData("Film of the Year", 0, R.drawable.star_account_24));
        infoList.add(new ProfileInfoData("Total Movie", 0, R.drawable.tv_series_account_24px));
        infoList.add(new ProfileInfoData("Likes", 0, R.drawable.favorite_account_24px));
        infoList.add(new ProfileInfoData("Reviews", 0, R.drawable.reviews_account_24px));

        rVRecentWatched = view.findViewById(R.id.recyclerViewRecentWatched);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(requireContext(),
                        LinearLayoutManager.HORIZONTAL, false);

        newList = getMovieWithGson();
        rvItemAdapter = new RVItem_AccountAdapter(getContext(), newList, ITEMS_MV_TV_TO_DISPLAY);
        rVRecentWatched.setLayoutManager(layoutManager);
        rVRecentWatched.setAdapter(rvItemAdapter);

    }

    public void setInfoData(View view){
        //Initialize Pager and Adapter
        viewPager = view.findViewById(R.id.pager_profile_info);
        pagerAdapter = new ScreenSlidePagerAdapter(this, infoList);
        viewPager.setPageTransformer(new ZoomOutPageTransformer());
        viewPager.setAdapter(pagerAdapter);
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

        // Add an offset listener to the AppBarLayout
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isColorChanged = false;
            int scrollRange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                // Calculate the percentage of collapse/expansion (-1f to 1f)
                float collapsePercent = Math.abs(verticalOffset / (float) appBarLayout.getTotalScrollRange());

                // Calculate new sizes based on the collapse percentage
                int newSize = size_updater.calculateNewSize(collapsePercent,
                        initialImageSizePx, size_updater.dpToPx(getContext(),40)); // Initial and final size in pixels
                int newTextSize = size_updater.calculateNewTextSize(collapsePercent,
                        initialTextSizePx, size_updater.dpToPx(getContext(),18)); // Initial and final text size in pixels


                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    // Collapsed
                    if (!isColorChanged) {
                        profile_ConstraintLayout.setBackgroundColor(Color.TRANSPARENT); // Set transparent background
                        userName.setTextColor(getResources().getColor(R.color.white));
                        isColorChanged = true;
                    }
                } else {
                    // Expanded or in-between
                    if (isColorChanged) {
                        profile_ConstraintLayout.setBackgroundColor(Color.parseColor("#80C0C0C0")); // Set your original color
                        isColorChanged = false;
                    }
                }
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
            binding.userName.setText(String.format("%s", user.getUsername()));

            binding.textViewFavouriteFilms.setText(String.format("%s" + "'s\n" +
                    getResources().getString(R.string.fav_films), user.getUsername()));

            binding.textViewFavouriteSeries.setText(String.format("%s" + "'s\n" +
                    getResources().getString(R.string.fav_series), user.getUsername()));

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

    /**
     * Returns the list of News using Gson.
     * @return The list of News.
     */
    private List<MovieModel> getMovieWithGson(){
        JSONParserUtil jsonParserUtil = new JSONParserUtil(requireActivity().getApplication());

        try {
            return jsonParserUtil.parseJSONFileWithGson("movieapi-test.json").getmData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}