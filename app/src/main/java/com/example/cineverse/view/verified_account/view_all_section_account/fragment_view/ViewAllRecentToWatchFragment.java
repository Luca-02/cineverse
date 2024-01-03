package com.example.cineverse.view.verified_account.view_all_section_account.fragment_view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cineverse.R;
import com.example.cineverse.databinding.FragmentAccountBinding;
import com.example.cineverse.databinding.FragmentViewAllRecentToWatchBinding;
import com.google.android.material.button.MaterialButtonToggleGroup;

public class ViewAllRecentToWatchFragment extends Fragment {

    private FragmentViewAllRecentToWatchBinding binding;

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
        binding = FragmentViewAllRecentToWatchBinding.inflate(inflater, container, false);
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
        binding.materialToggleGroup.check(R.id.buttonMovies);
        setTypeOfView();
    }

    /**
     * Called when the fragment is no longer in use.
     * This method ensures the fragment's binding is released to avoid memory leaks.
     */
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
       binding.materialToggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked){
                    if (checkedId == R.id.buttonMovies){
                       binding.constMoviesLayout.setVisibility(View.VISIBLE);
                       binding.constSeriesLayout.setVisibility(View.GONE);
                    }

                    else if (checkedId == R.id.buttonSeries){
                       binding.constSeriesLayout.setVisibility(View.VISIBLE);
                       binding.constMoviesLayout.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

}