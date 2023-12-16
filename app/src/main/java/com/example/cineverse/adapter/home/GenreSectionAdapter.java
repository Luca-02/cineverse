package com.example.cineverse.adapter.home;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cineverse.data.model.genre.Genre;
import com.example.cineverse.databinding.GenreItemBinding;

import java.util.List;

/**
 * The {@link GenreSectionAdapter} class is a {@link RecyclerView.Adapter} for displaying genre items.
 * It uses a custom ViewHolder to bind data to the genre items and provides a callback interface
 * for handling genre clicks.
 */
public class GenreSectionAdapter
        extends RecyclerView.Adapter<GenreSectionAdapter.GenreViewHolder> {

    /**
     * Callback interface for handling genre clicks.
     */
    public interface OnGenreClickListener {
        /**
         * Invoked when a genre item is clicked.
         *
         * @param genre The clicked {@link Genre}.
         */
        void onGenreClick(Genre genre);
    }

    private final List<Genre> genreList;
    private final OnGenreClickListener callback;

    /**
     * Constructs a {@link GenreSectionAdapter} with the specified genre list and callback.
     *
     * @param genreList The list of {@link Genre} items to be displayed.
     * @param callback  The callback for handling genre clicks.
     */
    public GenreSectionAdapter(List<Genre> genreList, OnGenreClickListener callback) {
        this.genreList = genreList;
        this.callback = callback;
    }

    /**
     * Sets new data for the adapter and notifies observers of the data set change.
     *
     * @param newContentList The new list of {@link Genre} items.
     */
    public void setData(List<Genre> newContentList) {
        int end = genreList.size();
        genreList.clear();
        notifyItemRangeRemoved(0, end);
        genreList.addAll(newContentList);
        notifyItemRangeInserted(0, genreList.size());
    }

    @NonNull
    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new GenreViewHolder(GenreItemBinding.inflate(
                inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GenreViewHolder holder, int position) {
        holder.bind(genreList.get(position));
    }

    @Override
    public int getItemCount() {
        return genreList.size();
    }

    /**
     * Custom ViewHolder for displaying genre items in the RecyclerView.
     */
    public class GenreViewHolder extends RecyclerView.ViewHolder {

        private final GenreItemBinding binding;

        public GenreViewHolder(@NonNull GenreItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        /**
         * Binds data to the ViewHolder.
         *
         * @param genre The {@link Genre} item to bind.
         */
        public void bind(Genre genre) {
            binding.textView.setText(genre.getName());
            itemView.setOnClickListener(v -> callback.onGenreClick(genre));
        }

    }

}
