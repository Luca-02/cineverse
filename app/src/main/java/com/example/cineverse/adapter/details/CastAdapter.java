package com.example.cineverse.adapter.details;

import static com.example.cineverse.utils.constant.Api.TMDB_IMAGE_ORIGINAL_SIZE_URL;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.cineverse.data.model.details.Cast;
import com.example.cineverse.databinding.CastItemBinding;

import java.util.List;

public class CastAdapter
        extends RecyclerView.Adapter<CastAdapter.CastViewHolder> {

    private final Context context;
    private final List<Cast> castList;

    public CastAdapter(Context context, List<Cast> castList) {
        this.context = context;
        this.castList = castList;
    }

    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new CastViewHolder(CastItemBinding.inflate(
                inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CastViewHolder holder, int position) {
        holder.bind(castList.get(position));
    }

    @Override
    public int getItemCount() {
        return castList.size();
    }

    public class CastViewHolder extends RecyclerView.ViewHolder {

        private final CastItemBinding binding;

        public CastViewHolder(@NonNull CastItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Cast cast) {
            String imageUrl = TMDB_IMAGE_ORIGINAL_SIZE_URL + cast.getProfilePath();
            Glide.with(context)
                    .load(imageUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.profileImageView);

            binding.nameTextView.setText(cast.getName());
            binding.characterTextView.setText(cast.getCharacter());
        }

    }

}
