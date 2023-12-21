package com.example.cineverse.utils.utils_account.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cineverse.R;
import com.example.cineverse.data.model.account_model.MovieModel;

import java.util.List;

public class RVItem_AccountAdapter extends RecyclerView.Adapter<RVItem_AccountAdapter.MyViewHolder> {

    private Context mContext;
    private List<MovieModel> mData;

    private final int item_to_display;

    public RVItem_AccountAdapter(Context mContext, List<MovieModel> mData, int nItem) {
        this.mContext = mContext;
        this.mData = mData;
        this.item_to_display = nItem;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.rview_items_displayed, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.scoreMovie.setText(String.format("%.1f", (mData.get(position).getScoreMovie())));
        holder.name.setText(mData.get(position).getTitle());

        //Glide to display the Image
        Glide.with(mContext)
                .load("https://image.tmdb.org/t/p/w500"+mData.get(position).getImgUrl())
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return Math.min(item_to_display, mData.size());
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private final TextView scoreMovie;
        private final TextView name;
        private final ImageView img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            scoreMovie = itemView.findViewById(R.id.rview_score_item);
            name = itemView.findViewById(R.id.rview_name_item);
            img = itemView.findViewById(R.id.rview_image_item);
        }
    }

}



