package com.example.cineverse.adapter.review;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cineverse.R;
import com.example.cineverse.data.model.User;
import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.review.UserReview;
import com.example.cineverse.databinding.ReviewItemLayoutBinding;
import com.example.cineverse.handler.ReviewUiHandler;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ReviewAdapter<T extends UserReview>
        extends RecyclerView.Adapter<ReviewAdapter<T>.ReviewViewHolder> {

    private final Context context;
    private final List<T> reviewList;
    private final OnReviewClickListener<T> listener;

    public ReviewAdapter(Context context, List<T> reviewList, OnReviewClickListener<T> listener) {
        this.context = context;
        this.reviewList = reviewList;
        this.listener = listener;
        setHasStableIds(true);
    }

    public List<T> getData() {
        return reviewList;
    }

    public void setData(List<T> newUserReviewList) {
        clearData();
        reviewList.addAll(newUserReviewList);
        notifyItemRangeInserted(0, reviewList.size());
    }

    public void addPagingData(List<T> newUserReviewList) {
        int start = reviewList.size();
        reviewList.clear();
        reviewList.addAll(newUserReviewList);
        notifyItemRangeInserted(start, reviewList.size());
    }

    public void clearData() {
        int end = reviewList.size();
        reviewList.clear();
        notifyItemRangeRemoved(0, end);
    }

    public void handleChangeLikeToUserReview(UserReview userReview) {
        if (userReview != null) {
            for (int i = 0; i < reviewList.size(); i++) {
                if (userReview.equals(reviewList.get(i))) {
                    reviewList.get(i).updateLike(
                            userReview.getLikeCount(), userReview.isUserLikeReview());
                    List<Object> payload = new ArrayList<>();
                    payload.add(userReview.getLikeCount());
                    payload.add(userReview.isUserLikeReview());
                    notifyItemChanged(i, payload);
                    return;
                }
            }
        }
    }

    public void handleChangeLikeToCurrentUserReview(UserReview userReview) {
        if (userReview != null) {
            for (int i = 0; i < reviewList.size(); i++) {
                if (userReview.getUser().equals(reviewList.get(i).getUser())) {
                    reviewList.get(i).updateLike(
                            userReview.getLikeCount(), userReview.isUserLikeReview());
                    List<Object> payload = new ArrayList<>();
                    payload.add(userReview.getLikeCount());
                    payload.add(userReview.isUserLikeReview());
                    notifyItemChanged(i, payload);
                    return;
                }
            }
        }
    }

    public void sortContentList(int sortIndex) {
        Comparator<UserReview> comparator = UserReview.getComparator(sortIndex);
        if (comparator != null) {
            reviewList.sort(comparator);
            notifyItemRangeChanged(0, reviewList.size());
        }
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ReviewViewHolder(ReviewItemLayoutBinding.inflate(
                inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        holder.bind(reviewList.get(position));
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
        return reviewList.size();
    }

    @Override
    public long getItemId(int position) {
        return reviewList.get(position).getReview().getTimestamp();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {

        private final ReviewItemLayoutBinding binding;

        public ReviewViewHolder(@NonNull ReviewItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(T review) {
            ReviewUiHandler.setReviewUi(context, binding, review, true);
            binding.likeCheckBox.setOnClickListener(v -> {
                if (binding.likeCheckBox.isChecked()) {
                    listener.addLikeToUserReview(review);
                } else {
                    listener.removeLikeToUserReview(review);
                }
            });
            binding.getRoot().setOnClickListener(v ->
                    listener.onUserReviewClick(review));
        }

        public void updateLikeView(long likeCount, boolean userLikeReview) {
            ReviewUiHandler.setLikeData(binding, likeCount, userLikeReview);
        }

    }

}
