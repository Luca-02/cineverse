package com.example.cineverse.adapter;

import static com.example.cineverse.utils.constant.Api.TMDB_POSTER_SIZE_URL;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cineverse.data.model.content.AbstractPoster;
import com.example.cineverse.databinding.PosterTitleContentItemBinding;

import java.util.List;

public class PosterContentAdapter
        extends RecyclerView.Adapter<PosterContentAdapter.MyViewHolder> {

    private final Context context;
    private List<? extends AbstractPoster> movieList;

    public PosterContentAdapter(Context context, List<AbstractPoster> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<? extends AbstractPoster> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new MyViewHolder(PosterTitleContentItemBinding.inflate(inflater, parent, false));
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

        private final PosterTitleContentItemBinding binding;

        public MyViewHolder(@NonNull PosterTitleContentItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(AbstractPoster posterMovie) {
            Glide.with(context)
                    .load(TMDB_POSTER_SIZE_URL + posterMovie.getPosterPath())
                    .into(binding.posterImageView);

            binding.titleTextView.setText(posterMovie.getName());
        }

    }

}
