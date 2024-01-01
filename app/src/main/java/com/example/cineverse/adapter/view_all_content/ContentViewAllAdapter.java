package com.example.cineverse.adapter.view_all_content;

import static com.example.cineverse.utils.constant.Api.TMDB_IMAGE_ORIGINAL_SIZE_URL;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.cineverse.adapter.OnContentClickListener;
import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.databinding.ViewAllContentItemBinding;

import java.util.List;

/**
 * The {@link ContentViewAllAdapter} class is a RecyclerView adapter for displaying content items
 * in a "View All" section. It uses a custom ViewHolder to bind data to the items.
 */
public class ContentViewAllAdapter
        extends RecyclerView.Adapter<ContentViewAllAdapter.ContentViewHolder> {

    private final Context context;
    private final List<AbstractContent> contentList;
    private final OnContentClickListener listener;

    /**
     * Constructs a {@link ContentViewAllAdapter} with the specified context and content list.
     *
     * @param context     The application context.
     * @param contentList The list of {@link AbstractContent} items to be displayed.
     * @param listener The callback for handling content clicks.
     */
    public ContentViewAllAdapter(Context context, List<AbstractContent> contentList,
                                 OnContentClickListener listener) {
        this.context = context;
        this.contentList = contentList;
        this.listener = listener;
    }

    public List<AbstractContent> getData() {
        return contentList;
    }

    /**
     * Sets new data for the adapter and notifies observers of the data set change.
     *
     * @param newContentList The new list of {@link AbstractContent} items.
     */
    public void setData(List<? extends AbstractContent> newContentList) {
        int start = contentList.size();
        contentList.addAll(newContentList);
        notifyItemRangeInserted(start, contentList.size());
    }

    @NonNull
    @Override
    public ContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ContentViewHolder(ViewAllContentItemBinding.inflate(
                inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContentViewAllAdapter.ContentViewHolder holder, int position) {
        holder.bind(contentList.get(position));
    }

    @Override
    public int getItemCount() {
        if (contentList != null) {
            return contentList.size();
        }
        return 0;
    }

    /**
     * Custom ViewHolder for displaying content items in the "View All" section.
     */
    public class ContentViewHolder extends RecyclerView.ViewHolder {

        private final ViewAllContentItemBinding binding;

        public ContentViewHolder(@NonNull ViewAllContentItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        /**
         * Binds data to the ViewHolder.
         *
         * @param content The {@link AbstractContent} item to bind.
         */
        public void bind(AbstractContent content) {
            String imageUrl = TMDB_IMAGE_ORIGINAL_SIZE_URL + content.getBackdropPath();
            Glide.with(context)
                    .load(imageUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into((ImageView) binding.imageView);
            binding.titleTextView.setText(content.getName());
            if (!content.getOverview().isEmpty()) {
                binding.overviewTextView.setVisibility(View.VISIBLE);
                binding.overviewTextView.setText(content.getOverview());
            } else {
                binding.overviewTextView.setVisibility(View.GONE);
            }
            itemView.setOnClickListener(v -> listener.onContentClick(content));
        }

    }

}
