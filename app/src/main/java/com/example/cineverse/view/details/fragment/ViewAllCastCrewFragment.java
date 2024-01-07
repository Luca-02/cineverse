package com.example.cineverse.view.details.fragment;

import android.animation.LayoutTransition;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cineverse.adapter.details.CastAdapter;
import com.example.cineverse.adapter.details.CrewDepartmentAdapter;
import com.example.cineverse.data.model.details.Credits;
import com.example.cineverse.databinding.FragmentViewAllCastCrewBinding;

public class ViewAllCastCrewFragment extends Fragment {

    public static final String CREDITS_TAG = "Credits";

    private FragmentViewAllCastCrewBinding binding;
    private Credits credits;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentViewAllCastCrewBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getExtras();
        binding.animatedLinearLayout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        setListener();
        if (credits != null) {
            setCastSection();
            setCrewSection();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Retrieves extra data from the intent bundle, including the title string ID and genre.
     */
    private void getExtras() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            credits = bundle.getParcelable(CREDITS_TAG);
        }
    }

    private void setListener() {
        binding.expandCastButton.setOnClickListener(v -> {
            if (binding.castRecyclerView.getVisibility() == View.VISIBLE) {
                binding.castRecyclerView.setVisibility(View.GONE);
            } else {
                binding.castRecyclerView.setVisibility(View.VISIBLE);
            }
        });

        binding.expandCrewButton.setOnClickListener(v -> {
            if (binding.crewDepartmentRecyclerView.getVisibility() == View.VISIBLE) {
                binding.crewDepartmentRecyclerView.setVisibility(View.GONE);
            } else {
                binding.crewDepartmentRecyclerView.setVisibility(View.VISIBLE);
            }
        });

        binding.materialToolbar.setNavigationOnClickListener(v ->
                requireActivity().getOnBackPressedDispatcher().onBackPressed());
    }

    private void setCastSection() {
        CastAdapter castAdapter = new CastAdapter(requireContext(), credits.getCast(), false);
        binding.castRecyclerView.setLayoutManager(new LinearLayoutManager(
                requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.castRecyclerView.setAdapter(castAdapter);
    }

    private void setCrewSection() {
        CrewDepartmentAdapter crewDepartmentAdapter =
                new CrewDepartmentAdapter(requireContext(), credits.getCrewMap());
        binding.crewDepartmentRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.crewDepartmentRecyclerView.setAdapter(crewDepartmentAdapter);
    }

}