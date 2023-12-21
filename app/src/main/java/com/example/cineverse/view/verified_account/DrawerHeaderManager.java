package com.example.cineverse.view.verified_account;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.cineverse.R;
import com.example.cineverse.data.model.User;
import com.example.cineverse.databinding.DrawerMenuHeaderLayoutBinding;

import java.util.Calendar;

/**
 * The {@link DrawerHeaderManager} class is responsible for managing the header layout of the navigation drawer.
 * It provides methods to set up and update the UI components such as greeting text, user photo, and username.
 */
public class DrawerHeaderManager {

    private final Context context;
    private final DrawerMenuHeaderLayoutBinding binding;

    /**
     * Constructs a DrawerHeaderManager.
     *
     * @param context The context in which the header will be managed.
     */
    public DrawerHeaderManager(Context context) {
        this.context = context;
        binding = DrawerMenuHeaderLayoutBinding.inflate(LayoutInflater.from(context));
        binding.greetingTextView.setText(getGreeting());
    }


    public View getHeaderBinding() {
        return binding.getRoot();
    }

    /**
     * Determines the appropriate greeting based on the current time of day.
     *
     * @return The resource ID of the greeting string.
     */
    public int getGreeting() {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);

        if (hour >= 5 && hour < 12) {
            return R.string.good_morning;
        } else if (hour >= 12 && hour < 18) {
            return R.string.good_afternoon;
        } else {
            return R.string.good_evening;
        }
    }

    /**
     * Sets the UI components of the drawer header based on the provided user information.
     *
     * @param user The user whose information will be displayed in the header.
     */
    public void setDrawerUserUi(User user) {
        Glide.with(context)
                .load(user.getPhotoUrl())
                .placeholder(R.drawable.circular_account_logo)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.circularImageView);
        binding.usernameTextView.setText(user.getUsername());
    }

}
