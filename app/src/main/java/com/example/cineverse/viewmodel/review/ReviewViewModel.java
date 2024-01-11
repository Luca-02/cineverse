package com.example.cineverse.viewmodel.review;

import static com.example.cineverse.utils.constant.GlobalConstant.RECENT_LIMIT_COUNT;
import static com.example.cineverse.utils.constant.GlobalConstant.REVIEW_PAGE_COUNT;
import static com.example.cineverse.utils.constant.GlobalConstant.START_TIMESTAMP_VALUE;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.review.ContentUserReview;
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
    private MutableLiveData<UserReview> userReviewOfContentLiveData;
    private MutableLiveData<List<ContentUserReview>> userReviewListLiveData;
    private MutableLiveData<Boolean> addedUserReviewOfContentLiveData;
    private MutableLiveData<Boolean> removedUserReviewOfContentLiveData;
    private MutableLiveData<List<UserReview>> pagedUserReviewOfContentLiveData;
    private MutableLiveData<UserReview> changeLikeOfUserToUserReviewOfContentLiveData;
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

    public MutableLiveData<UserReview> getUserReviewOfContentLiveData() {
        if (userReviewOfContentLiveData == null) {
            userReviewOfContentLiveData = new MutableLiveData<>();
        }
        return userReviewOfContentLiveData;
    }

    public MutableLiveData<List<ContentUserReview>> getUserReviewListLiveData() {
        if (userReviewListLiveData == null) {
            userReviewListLiveData = new MutableLiveData<>();
        }
        return userReviewListLiveData;
    }

    public MutableLiveData<Boolean> getAddedUserReviewOfContentLiveData() {
        if (addedUserReviewOfContentLiveData == null) {
            addedUserReviewOfContentLiveData = new MutableLiveData<>();
        }
        return addedUserReviewOfContentLiveData;
    }

    public MutableLiveData<Boolean> getRemovedUserReviewOfContentLiveData() {
        if (removedUserReviewOfContentLiveData == null) {
            removedUserReviewOfContentLiveData = new MutableLiveData<>();
        }
        return removedUserReviewOfContentLiveData;
    }

    public MutableLiveData<List<UserReview>> getPagedUserReviewOfContentLiveData() {
        if (pagedUserReviewOfContentLiveData == null) {
            pagedUserReviewOfContentLiveData = new MutableLiveData<>();
        }
        return pagedUserReviewOfContentLiveData;
    }

    public MutableLiveData<UserReview> getChangeLikeOfUserToUserReviewOfContentLiveData() {
        if (changeLikeOfUserToUserReviewOfContentLiveData == null) {
            changeLikeOfUserToUserReviewOfContentLiveData = new MutableLiveData<>();
        }
        return changeLikeOfUserToUserReviewOfContentLiveData;
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

    public void getUserReviewOfContent(AbstractContent content) {
        reviewRepository.getUserReviewOfContent(content);
    }

    public void getUserReviewList(String contentType) {
        reviewRepository.getUserReviewList(contentType);
    }

    public void addUserReviewOfContent(AbstractContent content, Review oldReview, Review newReview) {
        if (!newReview.equalsContent(oldReview)) {
            reviewRepository.addUserReviewOfContent(content, newReview);
        }
    }

    public void removeUserReviewOfContent(AbstractContent content, @NonNull Review review) {
        reviewRepository.removeUserReviewOfContent(content, review);
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

    public void addLikeOfUserToUserReviewOfContent(AbstractContent content, UserReview userReview) {
        reviewRepository.addLikeOfUserToUserReviewOfContent(content, userReview);
    }

    public void removedLikeOfUserToUserReviewOfContent(AbstractContent content, UserReview userReview) {
        reviewRepository.removedLikeOfUserToUserReviewOfContent(content, userReview);
    }

    @Override
    public void onContentRating(Double rating) {
        contentRatingLiveData.postValue(rating);
    }

    @Override
    public void onUserReviewOfContent(UserReview userReview) {
        getUserReviewOfContentLiveData().postValue(userReview);
    }

    @Override
    public void onUserReviewList(List<ContentUserReview> contentUserReviewList, String contentType) {
        if (contentUserReviewList != null) {
            getUserReviewListLiveData().postValue(contentUserReviewList);
        }
    }

    @Override
    public void onAddedUserReviewOfContent(UserReview userReview) {
        getAddedUserReviewOfContentLiveData().postValue(userReview != null);
        getUserReviewOfContentLiveData().postValue(userReview);
    }

    @Override
    public void onRemovedUserReviewOfContent(boolean removed) {
        getRemovedUserReviewOfContentLiveData().postValue(removed);
        if (removed) {
            getUserReviewOfContentLiveData().postValue(null);
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
            getChangeLikeOfUserToUserReviewOfContentLiveData().postValue(userReview);
        }
    }

    @Override
    public void onRemovedLikeOfUserToUserReviewOfContent(UserReview userReview, boolean removed) {
        if (removed) {
            getChangeLikeOfUserToUserReviewOfContentLiveData().postValue(userReview);
        }
    }

}
