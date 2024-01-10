package com.example.cineverse.view.search_result.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cineverse.R;
import com.example.cineverse.databinding.FragmentSearchResultBinding;
import com.example.cineverse.databinding.FragmentViewAllContentBinding;
import com.example.cineverse.view.search_result.SearchResultActivity;
import com.example.cineverse.view.view_all_content.ViewAllContentController;
import com.example.cineverse.viewmodel.search_result.SearchResultViewModel;
import com.example.cineverse.viewmodel.search_result.SearchResultViewModelFactory;
import com.example.cineverse.viewmodel.verified_account.section.home.content.AbstractSectionContentViewModel;
import com.example.cineverse.viewmodel.verified_account.section.home.content.AbstractSectionContentViewModelFactory;

public class SearchResultFragment extends Fragment {

    private FragmentSearchResultBinding binding;
    private SearchResultViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchResultBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setViewModel();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Sets up the ViewModel for the fragment.
     */
    private void setViewModel() {
        String query = ((SearchResultActivity) requireActivity()).getQuery();
        viewModel = new ViewModelProvider(this,
                new SearchResultViewModelFactory(requireActivity().getApplication(), query))
                .get(SearchResultViewModel.class);
    }

}