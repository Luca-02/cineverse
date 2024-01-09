package com.example.cineverse.adapter.review;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.cineverse.R;
import com.example.cineverse.data.model.User;
import com.example.cineverse.data.model.review.Review;
import com.example.cineverse.data.model.review.UserReview;
import com.example.cineverse.data.source.user.UserFirebaseSource;
import com.example.cineverse.databinding.ReviewItemLayoutBinding;
import com.example.cineverse.handler.ReviewUiHandler;
import com.example.cineverse.service.firebase.FirebaseCallback;
import com.example.cineverse.utils.constant.GlobalConstant;

import java.util.List;

public class ReviewAdapter
        extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private final Context context;
    private final List<UserReview> userReviewList;
    private final OnReviewClickListener listener;

    public ReviewAdapter(Context context, List<UserReview> userReviewList, OnReviewClickListener listener) {
        this.context = context;
        this.userReviewList = userReviewList;
        this.listener = listener;
        setHasStableIds(true);
    }

    public List<UserReview> getData() {
        return userReviewList;
    }

    public void setData(List<UserReview> newUserReviewList) {
        clearData();
        userReviewList.addAll(newUserReviewList);
        notifyItemRangeInserted(0, userReviewList.size());
    }

    public void addPagingData(List<UserReview> newUserReviewList) {
        int start = userReviewList.size();
        userReviewList.clear();
        userReviewList.addAll(newUserReviewList);
        notifyItemRangeInserted(start, userReviewList.size());
    }

    public void clearData() {
        int end = userReviewList.size();
        userReviewList.clear();
        notifyItemRangeRemoved(0, end);
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

    @Override
    public long getItemId(int position) {
        return userReviewList.get(position).getReview().getTimestamp();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {

        private final ReviewItemLayoutBinding binding;

        public ReviewViewHolder(@NonNull ReviewItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(UserReview userReview) {
            ReviewUiHandler.setReviewUi(context, binding, userReview);
            binding.getRoot().setOnClickListener(v ->
                    listener.onUserReviewClick(userReview));
        }

    }

}
