package com.example.cineverse.adapter;

import static com.example.cineverse.utils.constant.Api.TMDB_POSTER_SIZE_URL;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cineverse.data.model.content.PosterMovie;
import com.example.cineverse.databinding.PosterContentItemBinding;

import java.util.List;

public class PosterMovieAdapter
        extends RecyclerView.Adapter<PosterMovieAdapter.MyViewHolder> {

    private final Context context;
    private List<PosterMovie> movieList;

    public PosterMovieAdapter(Context context, List<PosterMovie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    public void setData(List<PosterMovie> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new MyViewHolder(PosterContentItemBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(movieList.get(position));
    }

    @Override
    public int getItemCount() {
        if (movieList != null) {
            return movieList.size();
        }
        return 0;
    }

    /**
     * Custom ViewHolder to bind data to the RecyclerView items.
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final PosterContentItemBinding binding;

        public MyViewHolder(@NonNull PosterContentItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(PosterMovie posterMovie) {
            Glide.with(context)
                    .load(TMDB_POSTER_SIZE_URL + posterMovie.getPosterPath())
                    .into(binding.posterImageView);
        }

    }

}
