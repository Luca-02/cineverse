package com.example.cineverse.view.verified_account.fragment.search;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cineverse.R;
import com.example.cineverse.adapter.search.KeywordAdapter;
import com.example.cineverse.adapter.search.SearchHistoryAdapter;
import com.example.cineverse.data.model.search.QueryHistory;
import com.example.cineverse.databinding.FragmentSearchBinding;
import com.example.cineverse.utils.constant.GlobalConstant;
import com.example.cineverse.view.verified_account.VerifiedAccountActivity;
import com.example.cineverse.view.verified_account.fragment.DashboardFragment;
import com.example.cineverse.viewmodel.verified_account.section.search.SearchViewModel;
import com.google.android.material.search.SearchView;

import java.util.ArrayList;
import java.util.Locale;

/**
 * The {@link SearchFragment} class representing the search section of the application.
 * This fragment serves as one of the tabs in the BottomNavigationView.
 */

public class SearchFragment extends Fragment
        implements SearchHistoryAdapter.OnSearchQueryListener {

    private FragmentSearchBinding binding;
    private SearchViewModel viewModel;
    private SearchHistoryAdapter searchHistoryAdapter;
    private SearchView searchView;
    private RecyclerView keywordRecyclerView;
    private KeywordAdapter keywordAdapter;

    private final ActivityResultLauncher<Intent> speechInputLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        ArrayList<String> resultArrayList =
                                data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                        if (resultArrayList != null) {
                            handleSpeechToText(resultArrayList.get(0));
                        }
                    }
                }
            }
    );

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        DashboardFragment dashboardFragment = (DashboardFragment) requireParentFragment()
                .requireParentFragment();
        searchView = dashboardFragment.getSearchView();
        keywordRecyclerView = dashboardFragment.getKeywordRecyclerView();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setViewModel();
        initHistorySection();
        initKeywordSection();
        setListener();
        searchView.setupWithSearchBar(binding.searchBar);
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
        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        viewModel.getSearchHistoryLiveData().observe(getViewLifecycleOwner(), searchHistory ->
                searchHistoryAdapter.setData(searchHistory));
        viewModel.getKeywordLiveData().observe(getViewLifecycleOwner(), keywords -> {
            keywordAdapter.setData(keywords);
        });
        viewModel.getFailureLiveData().observe(getViewLifecycleOwner(), failure -> {

        });
    }

    private void initHistorySection() {
        searchHistoryAdapter = new SearchHistoryAdapter(new ArrayList<>(), this);
        binding.historyRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.historyRecyclerView.setAdapter(searchHistoryAdapter);
        binding.historyRecyclerView.setHasFixedSize(true);
        viewModel.loadSearchHistory();
    }

    private void initKeywordSection() {
        keywordAdapter = new KeywordAdapter(new ArrayList<>(), this);
        keywordRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        keywordRecyclerView.setAdapter(keywordAdapter);
        keywordRecyclerView.setHasFixedSize(true);
    }

    /**
     * Sets up click listeners for UI elements in the fragment.
     */
    private void setListener() {
        searchView.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.fetchKeyword(String.valueOf(s));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        searchView.getEditText().setOnEditorActionListener(
                (v, actionId, event) -> {
                    QueryHistory queryHistory = viewModel.addToSearchHistory(
                            searchView.getEditText().getText().toString());
                    searchHistoryAdapter.addQuery(queryHistory);
                    searchView.hide();
                    openSearchResult(queryHistory.getQuery());
                    return false;
                });

        binding.searchBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.microphone) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speak_to_text));

                try {
                    speechInputLauncher.launch(intent);
                }
                catch (Exception e) {
                    Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT)
                            .show();
                }
            }
            return false;
        });

        binding.searchBar.setNavigationOnClickListener(v ->
                ((VerifiedAccountActivity) requireActivity()).openDrawer());
    }

    private void handleSpeechToText(String query) {
        binding.searchBar.setText(query);
    }

    private void openSearchResult(String query) {
        ((VerifiedAccountActivity) requireActivity()).openResultSearchActivity(query);
    }

    @Override
    public void onDeleteClick(QueryHistory queryHistory) {
        viewModel.removeFromSearchHistory(queryHistory);
    }

    @Override
    public void onDataChange(int dataSize) {
        if (dataSize > 0) {
            binding.noItemSearchHistoryLayout.setVisibility(View.GONE);
        } else {
            binding.noItemSearchHistoryLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onQueryClick(String query) {
        openSearchResult(query);
    }

}
