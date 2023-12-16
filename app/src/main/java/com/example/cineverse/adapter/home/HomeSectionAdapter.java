package com.example.cineverse.adapter.home;

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

import com.example.cineverse.data.model.api.Failure;
import com.example.cineverse.data.model.genre.Genre;
import com.example.cineverse.data.model.ui.ContentSection;
import com.example.cineverse.databinding.CarouselContentSectionBinding;
import com.example.cineverse.databinding.GenreSectionBinding;
import com.example.cineverse.databinding.PosterContentSectionBinding;
import com.example.cineverse.exception.NotAssignableViewModelException;
import com.example.cineverse.exception.ContentSectionViewTypeNotFoundException;
import com.example.cineverse.viewmodel.verified_account.section.home.AbstractSectionViewModel;
import com.example.cineverse.viewmodel.verified_account.section.home.content.AbstractSectionContentViewModel;
import com.example.cineverse.viewmodel.verified_account.section.home.genre.AbstractSectionGenreViewModel;
import com.google.android.material.carousel.CarouselLayoutManager;
import com.google.android.material.carousel.CarouselSnapHelper;
import com.google.android.material.carousel.HeroCarouselStrategy;
import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@link HomeSectionAdapter} class is a {@link RecyclerView.Adapter} for displaying content sections
 * on the home screen. It uses custom ViewHolders to bind data to different types of content sections,
 * such as posters, carousels, and genres.
 */
public class HomeSectionAdapter
        extends RecyclerView.Adapter<HomeSectionAdapter.SectionViewHolder> {

    /**
     * Callback interface for handling section clicks.
     */
    public interface OnSectionClickListener {
        /**
         * Invoked when the "View All" chip is clicked.
         *
         * @param section The clicked {@link ContentSection}.
         */
        void onViewAllClick(ContentSection section);

        /**
         * Invoked when a genre chip is clicked.
         *
         * @param section The clicked {@link ContentSection}.
         * @param genre   The clicked {@link Genre}.
         */
        void onGenreClick(ContentSection section, Genre genre);
    }

    private final ViewModelStoreOwner owner;
    private final Context context;
    private final LifecycleOwner viewLifecycleOwner;
    private final View rootView;
    private final List<ContentSection> sectionList;
    private final OnSectionClickListener listener;

    /**
     * Constructs a {@link HomeSectionAdapter} with the specified parameters.
     *
     * @param owner              The ViewModelStoreOwner.
     * @param context            The application context.
     * @param viewLifecycleOwner The LifecycleOwner for observing LiveData.
     * @param rootView           The root view of the RecyclerView.
     * @param sectionList        The list of {@link ContentSection} items to be displayed.
     * @param listener           The callback for handling section clicks.
     */
    public HomeSectionAdapter(ViewModelStoreOwner owner,
                              Context context,
                              LifecycleOwner viewLifecycleOwner,
                              View rootView,
                              List<ContentSection> sectionList,
                              OnSectionClickListener listener) {
        this.owner = owner;
        this.context = context;
        this.viewLifecycleOwner = viewLifecycleOwner;
        this.rootView = rootView;
        this.sectionList = sectionList;
        this.listener = listener;
    }

    /**
     * Refreshes all content sections by setting the force refresh flag.
     */
    public void refresh() {
        for (ContentSection section : sectionList) {
            section.setForceRefresh(true);
        }
        notifyItemRangeChanged(0, sectionList.size());
    }

    @NonNull
    @Override
    public SectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        ContentSection.ViewType viewType = sectionList.get(position).getViewType();

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == ContentSection.ViewType.POSTER_TYPE) {
            return new PosterSectionViewHolder(PosterContentSectionBinding.inflate(
                    inflater, parent, false));
        } else if (viewType == ContentSection.ViewType.CAROUSEL_TYPE) {
            return new CarouselSectionViewHolder(CarouselContentSectionBinding.inflate(
                    inflater, parent, false));
        } else if (viewType == ContentSection.ViewType.GENRE_TYPE) {
            return new GenreSectionViewHolder(GenreSectionBinding.inflate(
                    inflater, parent, false));
        } else {
            throw new ContentSectionViewTypeNotFoundException(viewType);
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

        public SectionViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        /**
         * Gets an Observer for handling API failure.
         *
         * @return The Observer for handling API failure.
         */
        protected Observer<Failure> getFailureObserver() {
            return failure ->
                    Snackbar.make(rootView,
                            failure.getStatusMessage(), Snackbar.LENGTH_SHORT).show();
        }

        /**
         * Fetches content from the ViewModel and updates the view.
         *
         * @param viewModel The ViewModel for the content section.
         * @param view      The view to be updated.
         * @param refresh   True if the content should be refreshed.
         */
        protected void fetchContent(AbstractSectionViewModel viewModel, View view, boolean refresh) {
            if (refresh) {
                viewModel.emptyContentLiveDataList();
            }

            if (viewModel.isContentEmpty()) {
                viewModel.fetch();
                if (view != null) {
                    view.setVisibility(View.VISIBLE);
                }
            }
        }

        /**
         * Binds data to the ViewHolder.
         *
         * @param section The {@link ContentSection} to bind.
         */
        public abstract void bind(ContentSection section);

    }

    /**
     * Abstract ViewHolder to bind data to the RecyclerView items.
     */
    public abstract class ContentSectionViewHolder extends SectionViewHolder {

        protected ContentSectionAdapter contentSectionAdapter;
        protected AbstractSectionContentViewModel viewModel;

        public ContentSectionViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        protected void setViewModel(ContentSection section, View fetchingView) {
            Class<? extends AbstractSectionViewModel> viewModelClass = section.getViewModelClass();
            if (AbstractSectionContentViewModel.class.isAssignableFrom(viewModelClass)) {
                viewModel = (AbstractSectionContentViewModel) new ViewModelProvider(owner).get(section.getViewModelClass());
            } else {
                throw new NotAssignableViewModelException(AbstractSectionContentViewModel.class, viewModelClass);
            }

            viewModel.getContentLiveData().observe(viewLifecycleOwner, abstractPosters -> {
                contentSectionAdapter.setData(abstractPosters);
                if (fetchingView != null) {
                    fetchingView.setVisibility(View.GONE);
                }
            });
            viewModel.getFailureLiveData().observe(viewLifecycleOwner, getFailureObserver());
        }

        protected void setViewAllChipListener(Chip chip, ContentSection section) {
            chip.setOnClickListener(view ->
                    listener.onViewAllClick(section));
        }

    }

    /**
     * Custom ViewHolder to bind data to the RecyclerView items.
     */
    public class PosterSectionViewHolder extends ContentSectionViewHolder {

        private final PosterContentSectionBinding binding;

        public PosterSectionViewHolder(@NonNull PosterContentSectionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void bind(ContentSection section) {
            initUi(section);
            setViewModel(section, binding.fetchingHorizontalScrollView);
            fetchContent(viewModel, binding.fetchingHorizontalScrollView, section.isForceRefresh());
        }

        private void initUi(ContentSection section) {
            binding.headerLayout.sectionTitleTextView.setText(section.getSectionTitleStringId());
            setRecyclerView();
            setViewAllChipListener(binding.headerLayout.viewAllChip, section);
        }

        private void setRecyclerView() {
            contentSectionAdapter = new ContentSectionAdapter(
                    context, ContentSection.ViewType.POSTER_TYPE, new ArrayList<>());
            binding.contentSectionRecyclerView.setLayoutManager(new LinearLayoutManager(
                    context, LinearLayoutManager.HORIZONTAL, false));
            binding.contentSectionRecyclerView.setAdapter(contentSectionAdapter);
        }

    }

    /**
     * Custom ViewHolder to bind data to the RecyclerView items.
     */
    public class CarouselSectionViewHolder extends ContentSectionViewHolder {

        private final CarouselContentSectionBinding binding;

        public CarouselSectionViewHolder(@NonNull CarouselContentSectionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void bind(ContentSection section) {
            initUi(section);
            setViewModel(section, null);
            fetchContent(viewModel, null, section.isForceRefresh());
        }

        private void initUi(ContentSection section) {
            binding.headerLayout.sectionTitleTextView.setText(section.getSectionTitleStringId());
            setRecyclerView();
            setViewAllChipListener(binding.headerLayout.viewAllChip, section);
        }

        private void setRecyclerView() {
            contentSectionAdapter = new ContentSectionAdapter(
                    context, ContentSection.ViewType.CAROUSEL_TYPE, new ArrayList<>());
            binding.contentSectionRecyclerView.setLayoutManager(
                    new CarouselLayoutManager(new HeroCarouselStrategy()));

            // Only create the CarouselSnapHelper if it hasn't already been set up
            if (binding.contentSectionRecyclerView.getOnFlingListener() == null) {
                CarouselSnapHelper snapHelper = new CarouselSnapHelper();
                snapHelper.attachToRecyclerView(binding.contentSectionRecyclerView);
            }

            binding.contentSectionRecyclerView.setAdapter(contentSectionAdapter);
        }

        public void clearRecyclerView() {
            binding.contentSectionRecyclerView.setOnFlingListener(null);
        }

    }

    /**
     * Custom ViewHolder to bind data to the RecyclerView items.
     */
    public class GenreSectionViewHolder extends SectionViewHolder {

        private final GenreSectionBinding binding;
        protected GenreSectionAdapter genreSectionAdapter;
        protected AbstractSectionGenreViewModel viewModel;

        public GenreSectionViewHolder(@NonNull GenreSectionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void bind(ContentSection section) {
            setRecyclerView(section);
            setViewModel(section);
            fetchContent(viewModel, null, section.isForceRefresh());
        }

        private void setViewModel(ContentSection section) {
            Class<? extends AbstractSectionViewModel> viewModelClass = section.getViewModelClass();
            if (AbstractSectionGenreViewModel.class.isAssignableFrom(viewModelClass)) {
                viewModel = (AbstractSectionGenreViewModel) new ViewModelProvider(owner).get(section.getViewModelClass());
            } else {
                throw new NotAssignableViewModelException(AbstractSectionGenreViewModel.class, viewModelClass);
            }

            viewModel.getContentLiveData().observe(viewLifecycleOwner, abstractPosters -> {
                genreSectionAdapter.setData(abstractPosters);
            });
            viewModel.getFailureLiveData().observe(viewLifecycleOwner, getFailureObserver());
        }

        private void setRecyclerView(ContentSection section) {
            genreSectionAdapter = new GenreSectionAdapter(new ArrayList<>(),
                    genre -> listener.onGenreClick(section, genre));
            binding.contentSectionRecyclerView.setLayoutManager(new LinearLayoutManager(
                    context, LinearLayoutManager.HORIZONTAL, false));
            binding.contentSectionRecyclerView.setAdapter(genreSectionAdapter);
        }

    }

}
