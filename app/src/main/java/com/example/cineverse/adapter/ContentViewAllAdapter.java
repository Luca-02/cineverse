package com.example.cineverse.adapter;

import static com.example.cineverse.utils.constant.Api.TMDB_IMAGE_ORIGINAL_SIZE_URL;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.databinding.ViewAllContentItemBinding;
import com.example.cineverse.utils.DateFormatUtils;

import java.util.List;

public class ContentViewAllAdapter
        extends RecyclerView.Adapter<ContentViewAllAdapter.ContentViewHolder> {

    private final Context context;
    private final List<AbstractContent> contentList;

    public ContentViewAllAdapter(Context context, List<AbstractContent> contentList) {
        this.context = context;
        this.contentList = contentList;
    }

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
     * Custom ViewHolder to bind data to the RecyclerView items.
     */
    public class ContentViewHolder extends RecyclerView.ViewHolder {

        private final ViewAllContentItemBinding binding;

        public ContentViewHolder(@NonNull ViewAllContentItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(AbstractContent content) {
            String imageUrl = TMDB_IMAGE_ORIGINAL_SIZE_URL + content.getBackdropPath();
            Glide.with(context)
                    .load(imageUrl)
                    .into(binding.imageView);
            binding.titleTextView.setText(content.getName());

            String formatDate = DateFormatUtils.formatData(context, content.getReleaseDate());
            binding.releaseDateTextView.setText(formatDate);
        }

    }

}
