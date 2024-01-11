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
import com.example.cineverse.data.model.details.Crew;
import com.example.cineverse.data.model.details.Jobs;
import com.example.cineverse.databinding.CastCrewItemBinding;

import java.util.List;

public class CrewAdapter
        extends RecyclerView.Adapter<CrewAdapter.CrewViewHolder> {

    private final Context context;
    private final List<Crew> crewList;

    public CrewAdapter(Context context, List<Crew> crewList) {
        this.context = context;
        this.crewList = crewList;
    }

    @NonNull
    @Override
    public CrewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new CrewViewHolder(CastCrewItemBinding.inflate(
                inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CrewViewHolder holder, int position) {
        holder.bind(crewList.get(position));
    }

    @Override
    public int getItemCount() {
        return crewList.size();
    }


    public class CrewViewHolder extends RecyclerView.ViewHolder {

        private final CastCrewItemBinding binding;

        public CrewViewHolder(@NonNull CastCrewItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Crew crew) {
            if (crew.getProfilePath() == null) {
                int padding = context.getResources().getDimensionPixelOffset(R.dimen.double_spacing);
                binding.posterImageLayout.posterImageView.setPadding(padding, padding, padding, padding);
                binding.posterImageLayout.posterImageView.setImageResource(R.drawable.outline_person);
            } else {
                binding.posterImageLayout.posterImageView.setPadding(0, 0, 0, 0);
                String imageUrl = TMDB_IMAGE_ORIGINAL_SIZE_URL + crew.getProfilePath();
                Glide.with(context)
                        .load(imageUrl)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(binding.posterImageLayout.posterImageView);
            }

            binding.nameTextView.setText(crew.getName());
            if (crew.getJob() != null) {
                binding.underNameTextView.setText(crew.getJob());
            } else {
                List<Jobs> jobs = crew.getJobs();
                if (jobs != null && jobs.size() != 0) {
                    binding.underNameTextView.setText(jobs.get(0).getJob());
                }
            }
        }

    }

}
