package com.example.cineverse.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cineverse.data.model.content.Failure;
import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.ui.ContentSection;
import com.example.cineverse.databinding.CarouselContentSectionBinding;
import com.example.cineverse.databinding.PosterContentSectionBinding;
import com.example.cineverse.exception.ViewTypeNotFoundException;
import com.example.cineverse.viewmodel.logged.verified_account.section.home.AbstractSectionViewModel;
import com.google.android.material.carousel.CarouselLayoutManager;
import com.google.android.material.carousel.CarouselSnapHelper;
import com.google.android.material.carousel.HeroCarouselStrategy;
import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class HomeSectionAdapter
        extends RecyclerView.Adapter<HomeSectionAdapter.SectionViewHolder> {

    public interface OnClickListener {
        void onViewAllClick(@IdRes int sectionTitleStringId,
                            Class<? extends AbstractSectionViewModel> viewModelClass);
    }

    private final ViewModelStoreOwner owner;
    private final Context context;
    private final LifecycleOwner viewLifecycleOwner;
    private final View view;
    private final List<ContentSection> sectionList;
    private final OnClickListener onClickListener;

    public HomeSectionAdapter(ViewModelStoreOwner owner,
                              Context context,
                              LifecycleOwner viewLifecycleOwner,
                              View view,
                              List<ContentSection> sectionList,
                              OnClickListener onClickListener) {
        this.owner = owner;
        this.context = context;
        this.viewLifecycleOwner = viewLifecycleOwner;
        this.view = view;
        this.sectionList = sectionList;
        this.onClickListener = onClickListener;
    }

    public void refresh() {
        for (ContentSection section : sectionList) {
            section.setForceRefresh(true);
        }
        notifyItemRangeChanged(0, sectionList.size());
    }

    @NonNull
    @Override
    public SectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        int viewType = sectionList.get(position).getViewType();

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == ContentSection.POSTER_TYPE) {
            return new PosterSectionViewHolder(PosterContentSectionBinding.inflate(
                    inflater, parent, false));
        } else if (viewType == ContentSection.CAROUSEL_TYPE) {
            return new CarouselSectionViewHolder(CarouselContentSectionBinding.inflate(
                    inflater, parent, false));
        } else {
            throw new ViewTypeNotFoundException(viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull SectionViewHolder holder, int position) {
        holder.bind(sectionList.get(position));
    }

    @Override
    public void onViewRecycled(@NonNull SectionViewHolder holder) {
        super.onViewRecycled(holder);
        if (holder instanceof CarouselSectionViewHolder) {
            ((CarouselSectionViewHolder) holder).clearRecyclerView();
        }
    }

    @Override
    public int getItemCount() {
        if (sectionList != null) {
            return sectionList.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Abstract ViewHolder to bind data to the RecyclerView items.
     */
    public abstract class SectionViewHolder extends RecyclerView.ViewHolder {

        protected ContentSectionAdapter contentSectionAdapter;
        protected AbstractSectionViewModel viewModel;

        public SectionViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setViewAllChipListener(Chip chip, ContentSection section) {
            chip.setOnClickListener(view ->
                    onClickListener.onViewAllClick(section.getSectionTitleStringId(), section.getViewModelClass()));
        }

        public void fetchContent(View view, boolean refresh) {
            if (refresh) {
                viewModel.clearContentLiveDataList();
            }

            if (viewModel.isContentEmpty()) {
                viewModel.fetch();
                if (view != null) {
                    view.setVisibility(View.VISIBLE);
                }
            }
        }

        public Observer<List<? extends AbstractContent>> getContentObserver(View view) {
            return abstractPosters -> {
                contentSectionAdapter.setData(abstractPosters);
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
            };
        }

        public Observer<Failure> getFailureObserver() {
            return failure ->
                    Snackbar.make(view, failure.getStatusMessage(), Snackbar.LENGTH_SHORT)
                            .show();
        }

        protected abstract void bind(ContentSection section);

    }

    /**
     * Custom ViewHolder to bind data to the RecyclerView items.
     */
    public class PosterSectionViewHolder extends SectionViewHolder {

        private final PosterContentSectionBinding binding;

        public PosterSectionViewHolder(@NonNull PosterContentSectionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void bind(ContentSection section) {
            initUi(section);
            setViewModel(section);
            fetchContent(binding.fetchingHorizontalScrollView, section.isForceRefresh());
        }

        private void initUi(ContentSection section) {
            binding.headerLayout.sectionTitleTextView.setText(section.getSectionTitleStringId());
            setRecyclerView();
            setViewAllChipListener(binding.headerLayout.viewAllChip, section);
        }

        private void setRecyclerView() {
            contentSectionAdapter = new ContentSectionAdapter(context, ContentSection.POSTER_TYPE, new ArrayList<>());
            binding.contentSectionRecyclerView.setLayoutManager(new LinearLayoutManager(
                    context, LinearLayoutManager.HORIZONTAL, false));
            binding.contentSectionRecyclerView.setAdapter(contentSectionAdapter);
        }

        private void setViewModel(ContentSection section) {
            viewModel = new ViewModelProvider(owner).get(section.getViewModelClass());
            viewModel.getContentLiveData().observe(viewLifecycleOwner,
                    getContentObserver(binding.fetchingHorizontalScrollView));
            viewModel.getFailureLiveData().observe(viewLifecycleOwner,
                    getFailureObserver());
        }

    }

    /**
     * Custom ViewHolder to bind data to the RecyclerView items.
     */
    public class CarouselSectionViewHolder extends SectionViewHolder {

        private final CarouselContentSectionBinding binding;

        public CarouselSectionViewHolder(@NonNull CarouselContentSectionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void bind(ContentSection section) {
            initUi(section);
            setViewModel(section);
            fetchContent(null, section.isForceRefresh());
        }

        private void initUi(ContentSection section) {
            binding.headerLayout.sectionTitleTextView.setText(section.getSectionTitleStringId());
            setRecyclerView();
            setViewAllChipListener(binding.headerLayout.viewAllChip, section);
        }

        private void setRecyclerView() {
            contentSectionAdapter = new ContentSectionAdapter(context, ContentSection.CAROUSEL_TYPE, new ArrayList<>());
            binding.contentSectionRecyclerView.setLayoutManager(
                    new CarouselLayoutManager(new HeroCarouselStrategy()));

            // Only create the CarouselSnapHelper if it hasn't already been set up
            if (binding.contentSectionRecyclerView.getOnFlingListener() == null) {
                CarouselSnapHelper snapHelper = new CarouselSnapHelper();
                snapHelper.attachToRecyclerView(binding.contentSectionRecyclerView);
            }

            binding.contentSectionRecyclerView.setAdapter(contentSectionAdapter);
        }

        private void setViewModel(ContentSection section) {
            viewModel = new ViewModelProvider(owner).get(section.getViewModelClass());
            viewModel.getContentLiveData().observe(viewLifecycleOwner,
                    getContentObserver(null));
            viewModel.getFailureLiveData().observe(viewLifecycleOwner,
                    getFailureObserver());
        }

        public void clearRecyclerView() {
            binding.contentSectionRecyclerView.setOnFlingListener(null);
        }

    }

}
