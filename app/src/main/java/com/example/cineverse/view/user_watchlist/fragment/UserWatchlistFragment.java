package com.example.cineverse.view.user_watchlist.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cineverse.R;
import com.example.cineverse.adapter.content.ContentViewAllAdapter;
import com.example.cineverse.adapter.content.OnContentClickListener;
import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.databinding.FragmentUserWatchlistBinding;
import com.example.cineverse.utils.mapper.ContentTypeMappingManager;
import com.example.cineverse.view.details.ContentDetailsActivityOpener;
import com.example.cineverse.view.user_watchlist.UserWatchlistActivity;
import com.example.cineverse.viewmodel.watchlist.WatchlistViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class UserWatchlistFragment extends Fragment
        implements OnContentClickListener {

    private static final String CHECKED_ITEM_KEY = "watchlistCheckedItem";

    private FragmentUserWatchlistBinding binding;
    private WatchlistViewModel watchlistViewModel;
    private ContentViewAllAdapter watchlistAdapter;
    private int checkedItem;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserWatchlistBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkedItem = (savedInstanceState != null) ?
                savedInstanceState.getInt(CHECKED_ITEM_KEY, 0) : 0;
        setActionBarMenu();
        setViewModel();
        setContentUi();
        setListener();
        binding.materialToggleGroup.check(R.id.buttonMovies);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CHECKED_ITEM_KEY, checkedItem);
    }

    /**
     * Sets up the ActionBarMenu of the activity related to the fragment.
     */
    private void setActionBarMenu() {
        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.sort_content_menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.sort) {
                    openSortDialog();
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * Sets up the ViewModel for the fragment.
     */
    private void setViewModel() {
        watchlistViewModel = new ViewModelProvider(this).get(WatchlistViewModel.class);
        watchlistViewModel.getUserMovieWatchlistLiveData().observe(getViewLifecycleOwner(), abstractContents -> {
            if (binding.materialToggleGroup.getCheckedButtonId() == R.id.buttonMovies) {
                fetchWatchlistData(
                        watchlistViewModel.getUserMovieWatchlistLiveData().getValue(),
                        ContentTypeMappingManager.ContentType.MOVIE.getType()
                );
            }
        });
        watchlistViewModel.getUserTvWatchlistLiveData().observe(getViewLifecycleOwner(), abstractContents -> {
            if (binding.materialToggleGroup.getCheckedButtonId() == R.id.buttonSeries) {
                fetchWatchlistData(
                        watchlistViewModel.getUserTvWatchlistLiveData().getValue(),
                        ContentTypeMappingManager.ContentType.TV.getType()
                );
            }
        });
        watchlistViewModel.getNetworkErrorLiveData().observe(getViewLifecycleOwner(), this::handleNetworkError);
    }

    private void setContentUi() {
        watchlistAdapter = new ContentViewAllAdapter(requireContext(), new ArrayList<>(), this);
        binding.recyclerViewWatchlist.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewWatchlist.setAdapter(watchlistAdapter);
    }

    private void setListener() {
        binding.swipeContainer.setOnRefreshListener(() -> {
            binding.swipeContainer.setRefreshing(false);
            updateAllData();
        });
        binding.materialToggleGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked){
                handleCheckedButton(checkedId);
            }
        });
    }

    private void handleCheckedButton(@IdRes int checkedId) {
        if (checkedId == R.id.buttonMovies) {
            fetchWatchlistData(
                    watchlistViewModel.getUserMovieWatchlistLiveData().getValue(),
                    ContentTypeMappingManager.ContentType.MOVIE.getType()
            );
        } else if (checkedId == R.id.buttonSeries) {
            fetchWatchlistData(
                    watchlistViewModel.getUserTvWatchlistLiveData().getValue(),
                    ContentTypeMappingManager.ContentType.TV.getType()
            );
        }
    }

    private void updateAllData() {
        watchlistViewModel.getUserMovieWatchlistLiveData().postValue(null);
        watchlistViewModel.getUserTvWatchlistLiveData().postValue(null);
    }

    private void fetchWatchlistData(List<AbstractContent> userDataList, String type) {
        if (userDataList == null) {
            watchlistViewModel.getUserContentWatchlist(type);
        } else {
            setWatchlistAdapterData(userDataList);
        }
    }

    private void setWatchlistAdapterData(List<AbstractContent> userDataList) {
        if (userDataList.size() > 0) {
            watchlistAdapter.setData(userDataList);
            watchlistAdapter.sortContentList(checkedItem);
            binding.emptyContentLayout.getRoot().setVisibility(View.GONE);
        } else {
            binding.emptyContentLayout.getRoot().setVisibility(View.VISIBLE);
            watchlistAdapter.clearData();
        }
    }

    private void handleNetworkError(Boolean bool) {
        if (bool != null && bool) {
            ((UserWatchlistActivity) requireActivity()).openNetworkErrorActivity();
            watchlistViewModel.getNetworkErrorLiveData().setValue(null);
        }
    }

    private void openSortDialog() {
        String[] sortingArrayString = AbstractContent.getSortingArrayString(requireContext());
        AtomicInteger selectedItem = new AtomicInteger(checkedItem);
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.sort)
                .setPositiveButton(getString(R.string.confirm), (dialog, which) -> {
                    checkedItem = selectedItem.get();
                    watchlistAdapter.sortContentList(checkedItem);
                })
                .setSingleChoiceItems(sortingArrayString, checkedItem, (dialog, which) -> {
                    selectedItem.set(which);
                })
                .show();
    }

    @Override
    public void onContentClick(AbstractContent content) {
        ContentDetailsActivityOpener.openContentDetailsActivity(
                requireContext(),
                ((UserWatchlistActivity) requireActivity()).getNavController(),
                content
        );
    }

}