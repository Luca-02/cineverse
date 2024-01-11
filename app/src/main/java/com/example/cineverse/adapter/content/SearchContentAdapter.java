package com.example.cineverse.adapter.content;

import static com.example.cineverse.utils.constant.Api.TMDB_IMAGE_ORIGINAL_SIZE_URL;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.cineverse.R;
import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.databinding.SearchContentItemBinding;

import java.util.List;

/**
 * The {@link ContentViewAllAdapter} class is a RecyclerView adapter for displaying content items
 * in a "Search Content" section. It uses a custom ViewHolder to bind data to the items.
 */
public class SearchContentAdapter
        extends RecyclerView.Adapter<SearchContentAdapter.ContentViewHolder> {

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
    public SearchContentAdapter(Context context, List<AbstractContent> contentList, OnContentClickListener listener) {
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
    public void addPagingData(List<? extends AbstractContent> newContentList) {
        int start = contentList.size();
        contentList.addAll(newContentList);
        notifyItemRangeInserted(start, contentList.size());
    }

    @NonNull
    @Override
    public ContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ContentViewHolder(SearchContentItemBinding.inflate(
                inflater, parent, false));
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

    public class ContentViewHolder extends RecyclerView.ViewHolder {

        private final SearchContentItemBinding binding;

        public ContentViewHolder(@NonNull SearchContentItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(AbstractContent content) {
            if (content.getPosterPath() == null) {
                int padding = context.getResources().getDimensionPixelOffset(R.dimen.double_spacing);
                binding.posterImageLayout.posterImageView.setPadding(padding, padding, padding, padding);
                binding.posterImageLayout.posterImageView.setImageResource(R.drawable.outline_image_not_supported);
            } else {
                binding.posterImageLayout.posterImageView.setPadding(0, 0, 0, 0);
                String imageUrl = TMDB_IMAGE_ORIGINAL_SIZE_URL + content.getPosterPath();
                Glide.with(context)
                        .load(imageUrl)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(binding.posterImageLayout.posterImageView);
            }
            binding.titleTextView.setText(content.getName());
            binding.materialCardView.setOnClickListener(v -> listener.onContentClick(content));
        }

    }

}
