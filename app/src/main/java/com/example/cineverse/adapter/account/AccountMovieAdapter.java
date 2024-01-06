package com.example.cineverse.adapter.account;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.cineverse.R;
import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.utils.constant.Api;

import java.util.ArrayList;

/**
 * The {@link AccountMovieAdapter}: fetch the movie from the Firebase's Database
 * used to retrieve info: Title, Poster Image ...
 * AbstractContent -> abstract class that represent both Movie and Tv Series objects
 */
public class AccountMovieAdapter extends
        RecyclerView.Adapter<AccountMovieAdapter.AccountMovieViewHolder> {

    private final Context context;
    private final ArrayList<AbstractContent> movieContent;

    public AccountMovieAdapter(Context context, ArrayList<AbstractContent> movieContent) {
        this.context = context;
        this.movieContent = movieContent;
    }

    @NonNull
    @Override
    public AccountMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).
                inflate(R.layout.rview_items_displayed, parent, false);
        return new AccountMovieViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountMovieViewHolder holder, int position) {
        AbstractContent singleMovie = movieContent.get(position);

        holder.movieTitle.setText(singleMovie.getName());

        Glide.with(context)
                .load(Api.TMDB_IMAGE_ORIGINAL_SIZE_URL+
                        singleMovie.getPosterPath())
                .into(holder.movieBk);

        holder.movieOverview.setText(singleMovie.getOverview());
    }

    @Override
    public int getItemCount() {
        return movieContent.size();
    }

    public static class AccountMovieViewHolder extends RecyclerView.ViewHolder{

       private ImageView movieBk;
       private TextView  movieOverview;
       private TextView movieTitle;

        public AccountMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            movieBk = itemView.findViewById(R.id.imageView);
            movieTitle = itemView.findViewById(R.id.textViewTitle);
            movieOverview = itemView.findViewById(R.id.textViewOverview);
        }
    }
}
