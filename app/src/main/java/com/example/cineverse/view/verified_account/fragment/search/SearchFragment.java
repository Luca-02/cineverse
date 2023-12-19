package com.example.cineverse.view.verified_account.fragment.search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.example.cineverse.databinding.FragmentSearchBinding;
import com.example.cineverse.repository.logged.CustomAdapter;
import com.example.cineverse.repository.logged.OnDeleteItemClickListener;
import com.example.cineverse.repository.logged.SearchRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@link SearchFragment} class representing the search section of the application.
 * This fragment serves as one of the tabs in the BottomNavigationView.
 */
public class SearchFragment extends Fragment implements OnDeleteItemClickListener {

    private FragmentSearchBinding binding;
    private CustomAdapter adapter;

    public SearchFragment() {
        // Required empty public constructor
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
        ListView listView = binding.listview;

        //Popola il menu suggerimenti
        if (getContext() != null) {
            List<String> searchHistory = SearchRepository.getSearchHistory(getContext());
            adapter = new CustomAdapter((ArrayList<String>) searchHistory, getContext());
            adapter.setOnDeleteItemClickListener(this);
            listView.setAdapter(adapter);
        }

        binding.searchView
                .getEditText()
                .setOnEditorActionListener(
                        (v, actionId, event) -> {
                            SearchRepository.addToSearchHistory(getContext(), binding.searchView.getEditText().getText().toString());
                            List<String> searchHistory = SearchRepository.getSearchHistory(getContext());
                            adapter.updateData((ArrayList<String>) searchHistory);
                            adapter.notifyDataSetChanged();
                            return false;
                        });
    }
    @Override
    public void onDeleteItemClick(int position) {
        SearchRepository.removeFromSearchHistory(getContext(), position);

        if (getContext() != null) {
            List<String> searchHistory = SearchRepository.getSearchHistory(getContext());
            adapter.updateData((ArrayList<String>) searchHistory);
            adapter.notifyDataSetChanged();
        }
    }
}