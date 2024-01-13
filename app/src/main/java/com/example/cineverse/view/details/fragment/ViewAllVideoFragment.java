package com.example.cineverse.view.details.fragment;

import static com.example.cineverse.utils.constant.GlobalConstant.YOU_TUBE_PROVIDER;
import static com.example.cineverse.utils.constant.GlobalConstant.YOU_TUBE_WATCH_URL;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cineverse.adapter.details.VideoAdapter;
import com.example.cineverse.data.model.details.ContentVideos;
import com.example.cineverse.data.model.details.Video;
import com.example.cineverse.databinding.FragmentViewAllVideoBinding;

public class ViewAllVideoFragment extends Fragment
        implements VideoAdapter.OnVideoClickListener {

    public static final String CONTENT_VIDEOS_TAG = "ContentVideos";

    private FragmentViewAllVideoBinding binding;
    private ContentVideos contentVideos;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentViewAllVideoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getExtras();
        if (contentVideos != null && contentVideos.getResults() != null) {
            VideoAdapter videoAdapter = new VideoAdapter(requireContext(), contentVideos.getResults(), this);
            binding.videoRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            binding.videoRecyclerView.setAdapter(videoAdapter);
            binding.materialToolbar.setNavigationOnClickListener(v ->
                    requireActivity().getOnBackPressedDispatcher().onBackPressed());
            if (contentVideos.getResults().size() == 0) {
                binding.emptyContentLayout.getRoot().setVisibility(View.VISIBLE);
            } else {
                binding.emptyContentLayout.getRoot().setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Retrieves extra data from the intent bundle, including the title string ID and genre.
     */
    private void getExtras() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            contentVideos = bundle.getParcelable(CONTENT_VIDEOS_TAG);
        }
    }

    @Override
    public void onVideoClick(Video video) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(YOU_TUBE_PROVIDER + video.getKey()));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(YOU_TUBE_WATCH_URL + video.getKey()));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }

}