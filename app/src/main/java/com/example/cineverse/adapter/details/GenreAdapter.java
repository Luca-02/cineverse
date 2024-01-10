package com.example.cineverse.adapter.details;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cineverse.data.model.genre.Genre;
import com.example.cineverse.databinding.ContentDetailsChipLayoutBinding;

import java.util.List;

public class GenreAdapter
        extends RecyclerView.Adapter<GenreAdapter.GenreViewHolder> {

    private final List<Genre> genreList;

    public GenreAdapter(List<Genre> genreList) {
        this.genreList = genreList;
    }

    @NonNull
    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new GenreViewHolder(ContentDetailsChipLayoutBinding.inflate(
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

    public static class GenreViewHolder extends RecyclerView.ViewHolder {

        private final ContentDetailsChipLayoutBinding binding;

        public GenreViewHolder(@NonNull ContentDetailsChipLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Genre genre) {
            binding.chip.setText(genre.getName());
        }

    }

}
