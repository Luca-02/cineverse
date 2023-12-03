package com.example.cineverse.adapter;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cineverse.data.model.content.AbstractPoster;
import com.example.cineverse.data.model.ui.HomeContentSection;
import com.example.cineverse.databinding.HomeContentSectionBinding;
import com.example.cineverse.viewmodel.logged.verified_account.section.home.AbstractSectionViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeContentSectionAdapter
        extends RecyclerView.Adapter<HomeContentSectionAdapter.MyViewHolder> {

    private final ViewModelStoreOwner owner;
    private final Application application;
    private final LifecycleOwner viewLifecycleOwner;
    private final List<HomeContentSection> sectionList;

    public HomeContentSectionAdapter(ViewModelStoreOwner owner,
                                     Application application,
                                     LifecycleOwner viewLifecycleOwner,
                                     List<HomeContentSection> sectionList) {
        this.owner = owner;
        this.application = application;
        this.viewLifecycleOwner = viewLifecycleOwner;
        this.sectionList = sectionList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new MyViewHolder(HomeContentSectionBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(sectionList.get(position));
    }

    @Override
    public int getItemCount() {
        if (sectionList != null) {
            return sectionList.size();
        }
        return 0;
    }

    /**
     * Custom ViewHolder to bind data to the RecyclerView items.
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final HomeContentSectionBinding binding;
        private PosterContentAdapter movieAdapter;
        private AbstractSectionViewModel<? extends AbstractPoster> viewModel;

        public MyViewHolder(@NonNull HomeContentSectionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(HomeContentSection section) {
            initUi(section);
            setViewModel(section);

            if (viewModel.isContentEmpty()) {
                viewModel.fetch();
                binding.shimmerLayout.getRoot().startShimmer();
                binding.shimmerLayout.getRoot().setVisibility(View.VISIBLE);
            }
        }

        private void initUi(HomeContentSection section) {
            Context context = application.getBaseContext();
            binding.sectionTitleTextView.setText(section.sectionTitleStringId);

            movieAdapter = new PosterContentAdapter(context, new ArrayList<>());
            binding.contentSectionRecyclerView.setLayoutManager(new LinearLayoutManager(
                    context, LinearLayoutManager.HORIZONTAL, false));
            binding.contentSectionRecyclerView.setAdapter(movieAdapter);
        }

        private void setViewModel(HomeContentSection section) {
            viewModel = new ViewModelProvider(owner).get(section.viewModelClass);
            viewModel.getContentLiveData().observe(viewLifecycleOwner,
                    (Observer<List<? extends AbstractPoster>>) abstractPosters -> {
                        movieAdapter.setData(abstractPosters);
                        binding.shimmerLayout.getRoot().stopShimmer();
                        binding.shimmerLayout.getRoot().setVisibility(View.GONE);
            });
        }

    }

}
