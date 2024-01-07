package com.example.cineverse.adapter.details;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cineverse.data.model.review.UserReview;
import com.example.cineverse.data.source.user.UserFirebaseSource;
import com.example.cineverse.databinding.ReviewItemLayoutBinding;
import com.example.cineverse.handler.ReviewUiHandler;

import java.util.List;

public class ReviewAdapter
        extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    public interface OnReviewClickListener {
        void onUserReviewClick(UserReview userReview);
    }

    private final Context context;
    private final List<UserReview> userReviewList;
    private final UserFirebaseSource userFirebaseSource;
    private final OnReviewClickListener listener;

    public ReviewAdapter(Context context, List<UserReview> userReviewList, OnReviewClickListener listener) {
        this.context = context;
        this.userReviewList = userReviewList;
        this.listener = listener;
        userFirebaseSource = new UserFirebaseSource(context);
    }

    public void setData(List<UserReview> newUserReviewList) {
        int end = userReviewList.size();
        userReviewList.clear();
        notifyItemRangeRemoved(0, end);
        userReviewList.addAll(newUserReviewList);
        notifyItemRangeInserted(0, userReviewList.size());
    }

    @NonNull
    @Override
    public ReviewAdapter.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ReviewViewHolder(ReviewItemLayoutBinding.inflate(
                inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ReviewViewHolder holder, int position) {
        holder.bind(userReviewList.get(position));
    }

    @Override
    public int getItemCount() {
        return userReviewList.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {

        private final ReviewItemLayoutBinding binding;

        public ReviewViewHolder(@NonNull ReviewItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(UserReview userReview) {
            ReviewUiHandler.setReviewUi(
                    context, userFirebaseSource, binding, userReview);
            binding.getRoot().setOnClickListener(v ->
                    listener.onUserReviewClick(userReview));
        }

    }

}
