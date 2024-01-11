package com.example.cineverse.adapter.genre;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cineverse.data.model.genre.Genre;
import com.example.cineverse.databinding.GenreListItemBinding;

import java.util.List;

/**
 * The {@link GenreListAdapter} class is a {@link RecyclerView.Adapter} for displaying genre items.
 * It uses a custom ViewHolder to bind data to the genre items and provides a callback interface
 * for handling genre clicks.
 */
public class GenreListAdapter
        extends RecyclerView.Adapter<GenreListAdapter.GenreViewHolder> {

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
    private final OnGenreClickListener listener;

    public GenreListAdapter(List<Genre> genreList, OnGenreClickListener listener) {
        this.genreList = genreList;
        this.listener = listener;
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
        return new GenreViewHolder(GenreListItemBinding.inflate(
                inflater, parent, false));
    }

    @Override
    public int getItemCount() {
        return genreList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull GenreViewHolder holder, int position) {
        holder.bind(genreList.get(position));
    }

    /**
     * Custom ViewHolder for displaying genre items in the RecyclerView.
     */
    public class GenreViewHolder extends RecyclerView.ViewHolder {

        private final GenreListItemBinding binding;

        public GenreViewHolder(@NonNull GenreListItemBinding binding) {
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
            itemView.setOnClickListener(v -> listener.onGenreClick(genre));
        }

    }

}
