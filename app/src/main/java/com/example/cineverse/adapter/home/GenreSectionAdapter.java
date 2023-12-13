package com.example.cineverse.adapter.home;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cineverse.data.model.genre.Genre;
import com.example.cineverse.databinding.GenreItemBinding;

import java.util.List;

public class GenreSectionAdapter
        extends RecyclerView.Adapter<GenreSectionAdapter.GenreViewHolder> {

    public interface OnGenreClickListener {
        void onGenreClick(Genre genre);
    }

    private final List<Genre> genreList;
    private final OnGenreClickListener callback;

    public GenreSectionAdapter(List<Genre> genreList, OnGenreClickListener callback) {
        this.genreList = genreList;
        this.callback = callback;
    }

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

    public class GenreViewHolder extends RecyclerView.ViewHolder {

        private final GenreItemBinding binding;

        public GenreViewHolder(@NonNull GenreItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Genre genre) {
            binding.textView.setText(genre.getName());
            itemView.setOnClickListener(v -> callback.onGenreClick(genre));
        }

    }

}
