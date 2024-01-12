package com.example.cineverse.view.user_reviews.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cineverse.R;
import com.example.cineverse.databinding.FragmentUserReviewBinding;
import com.google.android.material.button.MaterialButtonToggleGroup;


public class UserReviewFragment extends Fragment {

    private FragmentUserReviewBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserReviewBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.materialToggleGroup.check(R.id.buttonMovies);
        setTypeOfView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    /**
     * Sets the type of view based on the selected button
     * in the MaterialButtonToggleGroup.
     */
    private void setTypeOfView() {
        binding.materialToggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked){
                    if (checkedId == R.id.buttonMovies){
                    }

                    else if (checkedId == R.id.buttonSeries){
                    }
                }
            }
        });
    }

}