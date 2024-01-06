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
 * The {@link AccountTvSeriesAdapter}: fetch the movie from the Firebase's Database
 * used to retrieve info: Title, Poster Image ...
 * AbstractContent -> abstract class that represent both Movie and Tv Series objects
 */
public class AccountTvSeriesAdapter extends RecyclerView.Adapter<AccountTvSeriesAdapter.AccountTvSeriesViewHolder> {

    private final Context context;
    private final ArrayList<AbstractContent> tvSeriesContent;

    public AccountTvSeriesAdapter(Context context, ArrayList<AbstractContent> tvSeriesContent) {
        this.context = context;
        this.tvSeriesContent = tvSeriesContent;
    }

    @NonNull
    @Override
    public AccountTvSeriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.rview_items_displayed, parent, false);
        return new AccountTvSeriesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountTvSeriesViewHolder holder, int position) {
        AbstractContent singleMovie = tvSeriesContent.get(position);

        holder.tvSeriesTitle.setText(singleMovie.getName());

        Glide.with(context)
                .load(Api.TMDB_IMAGE_ORIGINAL_SIZE_URL+singleMovie.getPosterPath())
                .into(holder.tvSeriesBk);

        holder.tvSeriesOverview.setText(singleMovie.getOverview());
    }

    @Override
    public int getItemCount() {
        return tvSeriesContent.size();
    }

    public static class AccountTvSeriesViewHolder extends RecyclerView.ViewHolder{

        private ImageView tvSeriesBk;
        private TextView tvSeriesTitle;
        private TextView  tvSeriesOverview;

        public AccountTvSeriesViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSeriesBk = itemView.findViewById(R.id.rview_image_item);
            tvSeriesTitle = itemView.findViewById(R.id.rview_name_item);
            tvSeriesOverview = itemView.findViewById(R.id.textViewOverview);
        }
    }
}
