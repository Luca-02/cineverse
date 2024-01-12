package com.example.cineverse.view.user_watchlist.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cineverse.R;
import com.example.cineverse.databinding.FragmentUserWatchlistBinding;

public class UserWatchlistFragment extends Fragment {

    private FragmentUserWatchlistBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserWatchlistBinding.inflate(inflater, container, false);
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
    private void setTypeOfView(){
       binding.materialToggleGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
           if (isChecked){
               if (checkedId == R.id.buttonMovies) {

               }

               else if (checkedId == R.id.buttonSeries) {

               }
           }
       });
    }

}