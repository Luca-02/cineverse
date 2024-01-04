package com.example.cineverse.view.details.fragment;

import static com.example.cineverse.utils.constant.Api.TMDB_IMAGE_ORIGINAL_SIZE_URL;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.cineverse.R;
import com.example.cineverse.adapter.details.CastAdapter;
import com.example.cineverse.adapter.details.GenreAdapter;
import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.details.section.ContentDetailsApiResponse;
import com.example.cineverse.data.model.details.section.MovieDetails;
import com.example.cineverse.data.model.details.section.TvDetails;
import com.example.cineverse.databinding.FragmentContentDetailsBinding;
import com.example.cineverse.utils.DateFormatUtils;
import com.example.cineverse.utils.mapper.ContentTypeMappingManager;
import com.example.cineverse.view.details.ContentDetailsActivity;
import com.example.cineverse.viewmodel.details.AbstractContentDetailsViewModel;
import com.example.cineverse.viewmodel.details.section.MovieDetailsViewModel;
import com.example.cineverse.viewmodel.details.section.TvDetailsViewModel;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.material.snackbar.Snackbar;

public class ContentDetailsFragment extends Fragment {

    private FragmentContentDetailsBinding binding;
    private AbstractContentDetailsViewModel<? extends ContentDetailsApiResponse> viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentContentDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setViewModel();
        initContentDetails();
        binding.materialToolbar.setNavigationOnClickListener(v ->
                requireActivity().getOnBackPressedDispatcher().onBackPressed());
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
        String contentType =
                ((ContentDetailsActivity) requireActivity()).getContentType();

        if (contentType.equals(ContentTypeMappingManager.ContentType.MOVIE.getType())) {
            viewModel = new ViewModelProvider(this).get(MovieDetailsViewModel.class);
        } else if (contentType.equals(ContentTypeMappingManager.ContentType.TV.getType())) {
            viewModel = new ViewModelProvider(this).get(TvDetailsViewModel.class);
        }

        if (viewModel != null) {
            viewModel.getContentDetailsLiveData().observe(getViewLifecycleOwner(), this::handleContentDetails);
            viewModel.getFailureLiveData().observe(getViewLifecycleOwner(), failure -> {
                if (failure != null) {
                    Snackbar.make(binding.getRoot(),
                            failure.getStatusMessage(), Snackbar.LENGTH_SHORT).show();
                    viewModel.getFailureLiveData().setValue(null);
                }
            });
            viewModel.getAddedToWatchlistLiveData().observe(getViewLifecycleOwner(), this::handleAddedToWatch);
            viewModel.getNetworkErrorLiveData().observe(getViewLifecycleOwner(), bool -> {
                if (bool != null && bool) {
                    ((ContentDetailsActivity) requireActivity()).openNetworkErrorActivity();
                    viewModel.getNetworkErrorLiveData().setValue(null);
                }
            });
        }
    }

    private void initContentDetails() {
        int contentId =
                ((ContentDetailsActivity) requireActivity()).getContentId();
        viewModel.fetchDetails(contentId);
    }

    private void handleContentDetails(ContentDetailsApiResponse contentDetailsApiResponse) {
        if (MovieDetails.class.isAssignableFrom(contentDetailsApiResponse.getClass())) {
            handleMovieDetails((MovieDetails) contentDetailsApiResponse);
        } else if (TvDetails.class.isAssignableFrom(contentDetailsApiResponse.getClass())) {
            handleTvDetails((TvDetails) contentDetailsApiResponse);
        }
    }

    private void handleMovieDetails(MovieDetails movieDetails) {
        setContentUi(movieDetails);
        setContentDetailsUi(movieDetails);
        String subtitleString = String.format(
                getString(R.string.n_minutes), movieDetails.getRuntime()) +
                " • " +
                movieDetails.getStatus();
        binding.subtitleTextView.setText(subtitleString);
    }

    private void handleTvDetails(TvDetails tvDetails) {
        setContentUi(tvDetails);
        setContentDetailsUi(tvDetails);
        String subtitleString = String.format(
                getString(R.string.n_season), tvDetails.getSeasons().size()) +
                " · " +
                tvDetails.getStatus();
        binding.subtitleTextView.setText(subtitleString);
    }

    private void setContentUi(AbstractContent content) {
        String backdropImageUrl = TMDB_IMAGE_ORIGINAL_SIZE_URL + content.getBackdropPath();
        Glide.with(requireActivity())
                .load(backdropImageUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.backdropImageView);

        String posterImageUrl = TMDB_IMAGE_ORIGINAL_SIZE_URL + content.getPosterPath();
        Glide.with(requireActivity())
                .load(posterImageUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.posterImageView);

        binding.titleTextView.setText(content.getName());
        binding.releaseDateChip.setText(
                DateFormatUtils.formatData(requireActivity(), content.getReleaseDate()));
        binding.originalLanguageChip.setText(content.getCountryName());
        if (content.getOverview().isEmpty()) {
            binding.overviewTextView.setVisibility(View.GONE);
        } else {
            binding.overviewTextView.setText(content.getOverview());
        }

        binding.ToWatchButton.setOnClickListener(v ->
                viewModel.addContentToWatchlist(content));
    }

    private void setContentDetailsUi(ContentDetailsApiResponse contentDetails) {
        if (contentDetails.getTagline().isEmpty()) {
            binding.taglineTextView.setVisibility(View.GONE);
        } else {
            binding.taglineTextView.setText(contentDetails.getTagline());
        }

        GenreAdapter genreAdapter = new GenreAdapter(contentDetails.getGenres());
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(requireContext());
        binding.genreRecyclerView.setLayoutManager(layoutManager);
        binding.genreRecyclerView.setAdapter(genreAdapter);

        CastAdapter castAdapter = new CastAdapter(requireContext(), contentDetails.getCredits().getCast());
        binding.castRecyclerView.setLayoutManager(new LinearLayoutManager(
                requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.castRecyclerView.setAdapter(castAdapter);
    }

    private void handleAddedToWatch(Boolean added) {

    }

}