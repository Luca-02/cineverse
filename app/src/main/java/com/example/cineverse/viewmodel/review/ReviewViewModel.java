package com.example.cineverse.viewmodel.review;

import static com.example.cineverse.utils.constant.GlobalConstant.RECENT_LIMIT_COUNT;
import static com.example.cineverse.utils.constant.GlobalConstant.REVIEW_PAGE_COUNT;
import static com.example.cineverse.utils.constant.GlobalConstant.START_TIMESTAMP_VALUE;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.review.Review;
import com.example.cineverse.data.model.review.UserReview;
import com.example.cineverse.data.source.review.ReviewFirebaseCallback;
import com.example.cineverse.repository.review.ReviewRepository;
import com.example.cineverse.viewmodel.verified_account.VerifiedAccountViewModel;

import java.util.ArrayList;
import java.util.List;

public class ReviewViewModel
        extends VerifiedAccountViewModel
        implements ReviewFirebaseCallback {

    private final ReviewRepository reviewRepository;
    private MutableLiveData<Double> contentRatingLiveData;
    private MutableLiveData<UserReview> currentUserReviewOfContentLiveData;
    private MutableLiveData<Boolean> addedCurrentUserReviewOfContentLiveData;
    private MutableLiveData<Boolean> removedCurrentUserReviewOfContentLiveData;
    private MutableLiveData<List<UserReview>> pagedUserReviewOfContentLiveData;
    private MutableLiveData<UserReview> changeLikeOfCurrentUserToUserReviewOfContentLiveData;
    private long lastTimestamp;
    private boolean isLoading;

    public ReviewViewModel(@NonNull Application application) {
        super(application);
        reviewRepository = new ReviewRepository(
                application.getApplicationContext(), userRepository.getCurrentUser(), this);
        lastTimestamp = START_TIMESTAMP_VALUE;
        isLoading = false;
    }

    public MutableLiveData<Double> getContentRatingLiveData() {
        if (contentRatingLiveData == null) {
            contentRatingLiveData = new MutableLiveData<>();
        }
        return contentRatingLiveData;
    }

    public MutableLiveData<UserReview> getCurrentUserReviewOfContentLiveData() {
        if (currentUserReviewOfContentLiveData == null) {
            currentUserReviewOfContentLiveData = new MutableLiveData<>();
        }
        return currentUserReviewOfContentLiveData;
    }

    public MutableLiveData<Boolean> getAddedCurrentUserReviewOfContentLiveData() {
        if (addedCurrentUserReviewOfContentLiveData == null) {
            addedCurrentUserReviewOfContentLiveData = new MutableLiveData<>();
        }
        return addedCurrentUserReviewOfContentLiveData;
    }

    public MutableLiveData<Boolean> getRemovedCurrentUserReviewOfContentLiveData() {
        if (removedCurrentUserReviewOfContentLiveData == null) {
            removedCurrentUserReviewOfContentLiveData = new MutableLiveData<>();
        }
        return removedCurrentUserReviewOfContentLiveData;
    }

    public MutableLiveData<List<UserReview>> getPagedUserReviewOfContentLiveData() {
        if (pagedUserReviewOfContentLiveData == null) {
            pagedUserReviewOfContentLiveData = new MutableLiveData<>();
        }
        return pagedUserReviewOfContentLiveData;
    }

    public MutableLiveData<UserReview> getChangeLikeOfCurrentUserToUserReviewOfContentLiveData() {
        if (changeLikeOfCurrentUserToUserReviewOfContentLiveData == null) {
            changeLikeOfCurrentUserToUserReviewOfContentLiveData = new MutableLiveData<>();
        }
        return changeLikeOfCurrentUserToUserReviewOfContentLiveData;
    }

    public long getLastTimestamp() {
        return lastTimestamp;
    }

    public void setLastTimestamp(long lastTimestamp) {
        this.lastTimestamp = lastTimestamp;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public void getContentRating(AbstractContent content) {
        reviewRepository.getContentRating(content);
    }

    public void getCurrentUserReviewOfContent(AbstractContent content) {
        reviewRepository.getCurrentUserReviewOfContent(content);
    }

    public void addCurrentUserReviewOfContent(AbstractContent content, Review oldReview, Review newReview) {
        if (!newReview.equalsContent(oldReview)) {
            reviewRepository.addCurrentUserReviewOfContent(content, newReview);
        }
    }

    public void removeCurrentUserReviewOfContent(AbstractContent content, @NonNull Review review) {
        reviewRepository.removeCurrentUserReviewOfContent(content, review);
    }

    public void getPagedUserReviewOfContent(AbstractContent content) {
        long timestamp = getLastTimestamp();
        int pageSize = (timestamp == START_TIMESTAMP_VALUE) ? RECENT_LIMIT_COUNT : REVIEW_PAGE_COUNT;
        reviewRepository.getPagedUserReviewOfContent(content, pageSize, getLastTimestamp());
    }

    public void refreshContentReviewOfContent(AbstractContent content) {
        setLastTimestamp(START_TIMESTAMP_VALUE);
        getPagedUserReviewOfContentLiveData().postValue(new ArrayList<>());
        getPagedUserReviewOfContent(content);
    }

    public void addLikeOfCurrentUserToUserReviewOfContent(AbstractContent content, UserReview userReview) {
        reviewRepository.addLikeOfCurrentUserToUserReviewOfContent(content, userReview);
    }

    public void removeLikeOfCurrentUserToUserReviewOfContent(AbstractContent content, UserReview userReview) {
        reviewRepository.removeLikeOfCurrentUserToUserReviewOfContent(content, userReview);
    }

    @Override
    public void onContentRating(Double rating) {
        contentRatingLiveData.postValue(rating);
    }

    @Override
    public void onUserReviewOfContent(UserReview userReview) {
        getCurrentUserReviewOfContentLiveData().postValue(userReview);
    }

    @Override
    public void onAddedUserReviewOfContent(UserReview userReview) {
        getAddedCurrentUserReviewOfContentLiveData().postValue(userReview != null);
        getCurrentUserReviewOfContentLiveData().postValue(userReview);
    }

    @Override
    public void onRemovedUserReviewOfContent(boolean removed) {
        getRemovedCurrentUserReviewOfContentLiveData().postValue(removed);
        if (removed) {
            getCurrentUserReviewOfContentLiveData().postValue(null);
        }
    }

    @Override
    public void onPagedUserReviewOfContent(List<UserReview> userReviewList, long lastTimestamp) {
        if (userReviewList != null) {
            setLastTimestamp(lastTimestamp);
            List<UserReview> list = getPagedUserReviewOfContentLiveData().getValue();
            if (list == null) {
                list = new ArrayList<>(userReviewList);
                getPagedUserReviewOfContentLiveData().postValue(list);
            } else {
                list.addAll(userReviewList);
                getPagedUserReviewOfContentLiveData().postValue(list);
            }
        }
    }

    @Override
    public void onAddedLikeOfUserToUserReviewOfContent(UserReview userReview, boolean added) {
        if (added) {
            getChangeLikeOfCurrentUserToUserReviewOfContentLiveData().postValue(userReview);
        }
    }

    @Override
    public void onRemovedLikeOfUserToUserReviewOfContent(UserReview userReview, boolean removed) {
        if (removed) {
            getChangeLikeOfCurrentUserToUserReviewOfContentLiveData().postValue(userReview);
        }
    }

}
