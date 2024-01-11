package com.example.cineverse.adapter.details;

import static com.example.cineverse.utils.constant.Api.TMDB_IMAGE_ORIGINAL_SIZE_URL;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.cineverse.R;
import com.example.cineverse.data.model.details.Cast;
import com.example.cineverse.data.model.details.Role;
import com.example.cineverse.databinding.CastCrewItemBinding;

import java.util.List;

public class CastAdapter
        extends RecyclerView.Adapter<CastAdapter.CastViewHolder> {

    private final Context context;
    private final List<Cast> castList;

    public CastAdapter(Context context, List<Cast> castList, boolean preview) {
        this.context = context;
        if (preview) {
            this.castList = castList.subList(0, Math.min(castList.size(), 20));
        } else {
            this.castList = castList;
        }
    }

    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new CastViewHolder(CastCrewItemBinding.inflate(
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

        private final CastCrewItemBinding binding;

        public CastViewHolder(@NonNull CastCrewItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Cast cast) {
            if (cast.getProfilePath() == null) {
                int padding = context.getResources().getDimensionPixelOffset(R.dimen.double_spacing);
                binding.posterImageLayout.posterImageView.setPadding(padding, padding, padding, padding);
                binding.posterImageLayout.posterImageView.setImageResource(R.drawable.outline_person);
            } else {
                binding.posterImageLayout.posterImageView.setPadding(0, 0, 0, 0);
                String imageUrl = TMDB_IMAGE_ORIGINAL_SIZE_URL + cast.getProfilePath();
                Glide.with(context)
                        .load(imageUrl)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(binding.posterImageLayout.posterImageView);
            }

            binding.nameTextView.setText(cast.getName());

            if (cast.getCharacter() != null) {
                binding.underNameTextView.setText(cast.getCharacter());
            } else {
                List<Role> roles = cast.getRoles();
                if (roles != null && roles.size() != 0) {
                    binding.underNameTextView.setText(roles.get(0).getCharacter());
                }
            }
        }

    }

}
