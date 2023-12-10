package com.example.cineverse.adapter;

import static com.example.cineverse.utils.constant.Api.TMDB_IMAGE_ORIGINAL_SIZE_URL;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.ui.ContentSection;
import com.example.cineverse.databinding.CarouselContentItemBinding;
import com.example.cineverse.databinding.PosterContentItemBinding;
import com.example.cineverse.exception.ViewTypeNotFoundException;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.carousel.MaskableFrameLayout;

import java.util.List;

public class ContentSectionAdapter
        extends RecyclerView.Adapter<ContentSectionAdapter.ContentViewHolder> {

    private final Context context;
    private final int viewType;
    private final List<AbstractContent> contentList;

    public ContentSectionAdapter(Context context, int viewType, List<AbstractContent> contentList) {
        this.context = context;
        this.viewType = viewType;
        this.contentList = contentList;
    }

    public void setData(List<? extends AbstractContent> newContentList) {
        int end = contentList.size();
        contentList.clear();
        notifyItemRangeRemoved(0, end);
        contentList.addAll(newContentList);
        notifyItemRangeInserted(0, contentList.size());
    }

    @NonNull
    @Override
    public ContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == ContentSection.POSTER_TYPE) {
            return new PosterViewHolder(PosterContentItemBinding.inflate(
                    inflater, parent, false));
        } else if (viewType == ContentSection.CAROUSEL_TYPE) {
            return new CarouselViewHolder(CarouselContentItemBinding.inflate(
                    inflater, parent, false));
        } else {
            throw new ViewTypeNotFoundException(viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ContentViewHolder holder, int position) {
        holder.bind(contentList.get(position), position);
    }

    @Override
    public int getItemCount() {
        if (contentList != null) {
            return contentList.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return viewType;
    }

    public abstract static class ContentViewHolder extends RecyclerView.ViewHolder {

        public ContentViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        abstract void bind(AbstractContent posterMovie, int position);

    }

    /**
     * Custom ViewHolder to bind data to the RecyclerView items.
     */
    public class PosterViewHolder extends ContentViewHolder {

        private final PosterContentItemBinding binding;

        public PosterViewHolder(@NonNull PosterContentItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void bind(AbstractContent posterMovie, int position) {
            String imageUrl = TMDB_IMAGE_ORIGINAL_SIZE_URL + posterMovie.getPosterPath();
            Glide.with(context)
                    .load(imageUrl)
                    .into(binding.posterImageView);
            binding.titleTextView.setText(posterMovie.getName());
        }

    }

    /**
     * Custom ViewHolder to bind data to the RecyclerView items.
     */
    public class CarouselViewHolder extends ContentViewHolder {

        private final CarouselContentItemBinding binding;


        public CarouselViewHolder(@NonNull CarouselContentItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("RestrictedApi")
        @Override
        public void bind(AbstractContent posterMovie, int position) {
            String rank = (position + 1) + "°";
            String imageUrl = TMDB_IMAGE_ORIGINAL_SIZE_URL + posterMovie.getBackdropPath();
            Glide.with(context)
                    .load(imageUrl)
                    .into(binding.carouselImageView);
            binding.rankTextView.setText(rank);
            binding.titleTextView.setText(posterMovie.getName());

            ((MaskableFrameLayout) itemView).setOnMaskChangedListener(maskRect -> {
                float transitionX = maskRect.left;
                float alpha = AnimationUtils
                        .lerp(1F, 0F, 0F, 80F, maskRect.left);

                binding.rankTextView.setTranslationX(transitionX);
                binding.rankTextView.setAlpha(alpha);
                binding.titleTextView.setTranslationX(transitionX);
                binding.titleTextView.setAlpha(alpha);
            });
        }

    }

}
