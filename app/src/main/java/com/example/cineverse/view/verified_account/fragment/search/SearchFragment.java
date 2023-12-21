package com.example.cineverse.view.verified_account.fragment.search;

        import android.os.Bundle;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Adapter;
        import android.widget.ListView;
        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.fragment.app.Fragment;
        import androidx.lifecycle.ViewModelProvider;

        import com.example.cineverse.R;
        import com.example.cineverse.databinding.FragmentSearchBinding;
        import com.example.cineverse.repository.logged.search.CustomAdapter;
        import com.example.cineverse.repository.logged.search.OnDeleteItemClickListener;
        import com.example.cineverse.viewmodel.logged.search.SearchViewModel;

        import java.util.ArrayList;
        import java.util.List;

/**
 * The {@link SearchFragment} class representing the search section of the application.
 * This fragment serves as one of the tabs in the BottomNavigationView.
 */

public class SearchFragment extends Fragment implements OnDeleteItemClickListener {

    private FragmentSearchBinding binding;
    private CustomAdapter adapter;
    private SearchViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);
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

        // Popola il menu suggerimenti
        adapter = new CustomAdapter((List<String>) viewModel.getSearchHistoryLiveData().getValue(), getContext());
        adapter.setOnDeleteItemClickListener(this);
        listView.setAdapter(adapter);

        viewModel.getSearchHistoryLiveData().observe(getViewLifecycleOwner(), searchHistory -> {
            adapter.updateData((List<String>) searchHistory);
            adapter.notifyDataSetChanged();
            Log.d("TAG", "UI2");
        });

        binding.searchView
                .getEditText()
                .setOnEditorActionListener(
                        (v, actionId, event) -> {
                            viewModel.addToSearchHistory(binding.searchView.getEditText().getText().toString());
                            return false;
                        });
    }

    @Override
    public void onDeleteItemClick(int position) {
        viewModel.removeFromSearchHistory(position);
    }
}
