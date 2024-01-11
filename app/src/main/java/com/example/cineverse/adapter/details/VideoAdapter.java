package com.example.cineverse.adapter.details;

import static com.example.cineverse.utils.constant.GlobalConstant.YOU_TUBE_TAG;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cineverse.data.model.details.Video;
import com.example.cineverse.databinding.ContentVideoItemBinding;

import java.util.List;
import java.util.Objects;

public class VideoAdapter
        extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    public interface OnVideoClickListener {
        void onVideoClick(Video video);
    }

    private final Context context;
    private final List<Video> videoList;
    private final OnVideoClickListener listener;

    public VideoAdapter(Context context, List<Video> videoList, OnVideoClickListener listener) {
        this.context = context;
        this.videoList = videoList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VideoAdapter.VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new VideoViewHolder(ContentVideoItemBinding.inflate(
                inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapter.VideoViewHolder holder, int position) {
        holder.bind(videoList.get(position));
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {

        private final ContentVideoItemBinding binding;

        public VideoViewHolder(@NonNull ContentVideoItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Video video) {
            String thumbnailul = "https://img.youtube.com/vi/" + video.getKey() + "/hqdefault.jpg";
            Glide.with(context)
                    .load(thumbnailul)
                    .into((ImageView) binding.imageView);
            binding.nameTextView.setText(video.getName());
            if (Objects.equals(video.getSite(), YOU_TUBE_TAG)) {
                itemView.setOnClickListener(v -> listener.onVideoClick(video));
            }
        }

    }

}
