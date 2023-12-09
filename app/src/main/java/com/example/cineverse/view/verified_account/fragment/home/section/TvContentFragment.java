package com.example.cineverse.view.verified_account.fragment.home.section;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cineverse.adapter.ContentSectionAdapter;
import com.example.cineverse.data.model.content.poster.AbstractPoster;
import com.example.cineverse.data.model.ui.ContentSection;
import com.example.cineverse.databinding.FragmentTvContentBinding;
import com.example.cineverse.viewmodel.logged.verified_account.section.home.AbstractSectionViewModel;
import com.example.cineverse.viewmodel.logged.verified_account.section.home.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class TvContentFragment extends Fragment
        implements ContentSectionAdapter.OnClickListener {

    private FragmentTvContentBinding binding;
    private HomeViewModel viewModel;
    private ContentSectionAdapter sectionAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTvContentBinding.inflate(inflater, container, false);
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
                new ArrayList<>(viewModel.getTvContentSection());

        sectionAdapter = new ContentSectionAdapter(
                this,
                requireContext(),
                getViewLifecycleOwner(),
                sectionList,
                this
        );

        binding.sectionRecyclerView.setLayoutManager(new LinearLayoutManager(
                requireContext(), LinearLayoutManager.VERTICAL, false));
        binding.sectionRecyclerView.setAdapter(sectionAdapter);
        binding.sectionRecyclerView.setHasFixedSize(true);
    }

    /**
     * Sets up the ViewModel for the fragment.
     */
    private void setViewModel() {
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    /**
     * Sets up click listeners for UI elements in the fragment.
     */
    private void setListener() {
        binding.swipeContainer.setOnRefreshListener(() -> {
            sectionAdapter.refresh();
            binding.swipeContainer.setRefreshing(false);
        });
    }

    @Override
    public void onViewAllClick(@IdRes int sectionTitleStringId, Class<? extends AbstractSectionViewModel<? extends AbstractPoster>> viewModelClass) {

    }

}