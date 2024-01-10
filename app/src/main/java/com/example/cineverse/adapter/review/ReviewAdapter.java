package com.example.cineverse.adapter.review;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cineverse.data.model.review.UserReview;
import com.example.cineverse.databinding.ReviewItemLayoutBinding;
import com.example.cineverse.handler.ReviewUiHandler;

import java.util.ArrayList;
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

    public void handleChangeLikeToUserReview(UserReview userReview) {
        if (userReview != null) {
            for (int i = 0; i < userReviewList.size(); i++) {
                if (userReview.equals(userReviewList.get(i))) {
                    List<Object> payload = new ArrayList<>();
                    payload.add(userReview.getLikeCount());
                    payload.add(userReview.isUserLikeReview());
                    notifyItemChanged(i, payload);
                    return;
                }
            }
        }
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
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position, @NonNull List<Object> payloads) {
        if(payloads.size() == 2) {
            Object likeCount = payloads.get(0);
            Object userLikeReview = payloads.get(1);
            if (likeCount instanceof Long && userLikeReview instanceof Boolean) {
                holder.updateLikeView((long) likeCount, (boolean) userLikeReview);
            }
        } else {
            onBindViewHolder(holder, position);
        }
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
            ReviewUiHandler.setReviewUi(context, binding, userReview, true);
            binding.likeCheckBox.setOnClickListener(v -> {
                if (binding.likeCheckBox.isChecked()) {
                    listener.addLikeToUserReview(userReview);
                } else {
                    listener.removeLikeToUserReview(userReview);
                }
            });
            binding.getRoot().setOnClickListener(v ->
                    listener.onUserReviewClick(userReview));
        }

        public void updateLikeView(long likeCount, boolean userLikeReview) {
            ReviewUiHandler.setLikeData(binding, likeCount, userLikeReview);
        }

    }

}
