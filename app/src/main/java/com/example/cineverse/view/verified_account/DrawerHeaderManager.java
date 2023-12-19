package com.example.cineverse.view.verified_account;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.cineverse.R;
import com.example.cineverse.data.model.User;
import com.example.cineverse.databinding.DrawerMenuHeaderLayoutBinding;
import com.example.cineverse.viewmodel.verified_account.VerifiedAccountViewModel;

import java.util.Calendar;

public class DrawerHeaderManager {

    private final DrawerMenuHeaderLayoutBinding binding;

    public DrawerHeaderManager(Context context, VerifiedAccountViewModel viewModel) {
        binding = DrawerMenuHeaderLayoutBinding.inflate(LayoutInflater.from(context));
        User user = viewModel.getCurrentUser();

        Glide.with(context)
                .load(user.getPhotoUrl())
                .placeholder(R.drawable.circular_account_logo)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.circularImageView);
        binding.greetingTextView.setText(getGreeting());
        binding.usernameTextView.setText(user.getUsername());
    }

    public View getHeaderBinding() {
        return binding.getRoot();
    }

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

}
