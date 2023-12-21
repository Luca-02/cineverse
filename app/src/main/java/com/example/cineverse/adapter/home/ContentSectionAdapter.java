package com.example.cineverse.adapter.home;

import static com.example.cineverse.utils.constant.Api.TMDB_IMAGE_ORIGINAL_SIZE_URL;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.cineverse.adapter.OnContentClickListener;
import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.ui.ContentSection;
import com.example.cineverse.databinding.CarouselContentItemBinding;
import com.example.cineverse.databinding.PosterContentItemBinding;
import com.example.cineverse.exception.ContentSectionViewTypeNotFoundException;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.carousel.MaskableFrameLayout;

import java.util.List;

/**
 * The {@link ContentSectionAdapter} class is a {@link RecyclerView.Adapter} that displays a list of
 * content items with different view types such as posters and carousels.
 * It uses custom ViewHolders to bind data and handles different content sections.
 */
public class ContentSectionAdapter
        extends RecyclerView.Adapter<ContentSectionAdapter.ContentViewHolder> {

    private final Context context;
    private final ContentSection.ViewType viewType;
    private final List<AbstractContent> contentList;
    private final OnContentClickListener listener;

    /**
     * Constructs a {@link ContentSectionAdapter} with the specified context, view type, and content list.
     *
     * @param context     The application context.
     * @param viewType    The type of view to be displayed (e.g., posters or carousels).
     * @param contentList The list of {@link AbstractContent} items to be displayed.
     * @param listener    The callback for handling content clicks.
     */
    public ContentSectionAdapter(Context context, ContentSection.ViewType viewType,
                                 List<AbstractContent> contentList, OnContentClickListener listener) {
        this.context = context;
        this.viewType = viewType;
        this.contentList = contentList;
        this.listener = listener;
    }

    /**
     * Sets new data for the adapter and notifies observers of the data set change.
     *
     * @param newContentList The new list of {@link AbstractContent} items.
     */
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
        if (viewType == ContentSection.ViewType.POSTER_TYPE.getViewType()) {
            return new PosterViewHolder(PosterContentItemBinding.inflate(
                    inflater, parent, false));
        } else if (viewType == ContentSection.ViewType.CAROUSEL_TYPE.getViewType()) {
            return new CarouselViewHolder(CarouselContentItemBinding.inflate(
                    inflater, parent, false));
        } else {
            throw new ContentSectionViewTypeNotFoundException(this.viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ContentViewHolder holder, int position) {
        holder.bind(contentList.get(position));
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
        return viewType.getViewType();
    }

    /**
     * Abstract base class for RecyclerView ViewHolders.
     */
    public abstract static class ContentViewHolder extends RecyclerView.ViewHolder {

        public ContentViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        /**
         * Binds data to the ViewHolder.
         *
         * @param content The {@link AbstractContent} item to bind.
         */
        public abstract void bind(AbstractContent content);

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
        public void bind(AbstractContent content) {
            String imageUrl = TMDB_IMAGE_ORIGINAL_SIZE_URL + content.getPosterPath();
            Glide.with(context)
                    .load(imageUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.posterImageView);
            binding.titleTextView.setText(content.getName());
            binding.materialCardView.setOnClickListener(v -> listener.onContentClick(content));
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
        public void bind(AbstractContent content) {
            String imageUrl = TMDB_IMAGE_ORIGINAL_SIZE_URL + content.getBackdropPath();
            Glide.with(context)
                    .load(imageUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.carouselImageView);
            binding.titleTextView.setText(content.getName());

            ((MaskableFrameLayout) itemView).setOnMaskChangedListener(maskRect -> {
                float transitionX = maskRect.left;
                float alpha = AnimationUtils
                        .lerp(1F, 0F, 0F, 80F, maskRect.left);
                binding.titleTextView.setTranslationX(transitionX);
                binding.titleTextView.setAlpha(alpha);
            });
            binding.carouselItemContainer.setOnClickListener(v -> listener.onContentClick(content));
        }

    }

}
