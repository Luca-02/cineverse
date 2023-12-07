package com.example.cineverse.view.verified_account.fragment.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cineverse.adapter.ContentSectionAdapter;
import com.example.cineverse.data.model.ui.ContentSection;
import com.example.cineverse.databinding.FragmentHomeBinding;
import com.example.cineverse.viewmodel.logged.verified_account.section.home.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@link HomeFragment} class representing the home section of the application.
 * This fragment serves as one of the tabs in the BottomNavigationView.
 */
public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;
    private ContentSectionAdapter sectionAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setViewModel();
        setListener();
        initContentSection();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initContentSection() {
        List<ContentSection> sectionList =
                new ArrayList<>(viewModel.getHomeContentSection());

        sectionAdapter = new ContentSectionAdapter(
                this,
                requireContext(),
                getViewLifecycleOwner(),
                sectionList
        );

        binding.sectionRecyclerView.setLayoutManager(new LinearLayoutManager(
                requireContext(), LinearLayoutManager.VERTICAL, false));
        binding.sectionRecyclerView.setAdapter(sectionAdapter);
        binding.sectionRecyclerView.setAdapter(sectionAdapter);
    }

    /**
     * Sets up the ViewModel for the fragment.
     */
    private void setViewModel() {
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    private void setListener() {
        binding.swipeContainer.setOnRefreshListener(() -> {
            sectionAdapter.refresh();
            binding.swipeContainer.setRefreshing(false);
        });
    }

}