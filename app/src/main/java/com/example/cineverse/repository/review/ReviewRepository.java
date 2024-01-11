package com.example.cineverse.repository.review;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.cineverse.data.model.User;
import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.details.section.MovieDetails;
import com.example.cineverse.data.model.details.section.TvDetails;
import com.example.cineverse.data.model.review.ContentUserReview;
import com.example.cineverse.data.model.review.Review;
import com.example.cineverse.data.model.review.UserReview;
import com.example.cineverse.data.source.details.section.MovieDetailsRemoteDataSource;
import com.example.cineverse.data.source.details.section.TvDetailsRemoteDataSource;
import com.example.cineverse.data.source.review.ReviewFirebaseCallback;
import com.example.cineverse.data.source.review.ReviewFirebaseSource;
import com.example.cineverse.utils.mapper.ContentTypeMappingManager;

import java.util.List;
import java.util.Objects;

public class ReviewRepository
        implements ReviewFirebaseCallback {

    private final Context context;
    private final User currentUser;
    private final ReviewFirebaseSource reviewFirebaseSource;
    private final ReviewFirebaseCallback firebaseCallback;

    public ReviewRepository(Context context, User currentUser, ReviewFirebaseCallback firebaseCallback) {
        this.context = context;
        this.currentUser = currentUser;
        this.firebaseCallback = firebaseCallback;
        reviewFirebaseSource = new ReviewFirebaseSource(context, this);
    }

    public void getContentRating(AbstractContent content) {
        reviewFirebaseSource.getContentRating(content);
    }

    public void getUserReviewOfContent(AbstractContent content) {
        if (currentUser != null) {
            reviewFirebaseSource.getUserReviewOfContent(currentUser, content);
        }
    }

    public void getUserReviewList(String contentType) {
        if (currentUser != null) {
            reviewFirebaseSource.getUserReviewList(currentUser, contentType);
        }
    }

    public void addUserReviewOfContent(AbstractContent content, Review review) {
        if (currentUser != null) {
            reviewFirebaseSource.addUserReviewOfContent(currentUser, content, review);
        }
    }

    public void removeUserReviewOfContent(AbstractContent content, @NonNull Review review) {
        if (currentUser != null) {
            reviewFirebaseSource.removeUserReviewOfContent(currentUser, content, review);
        }
    }

    public void getPagedUserReviewOfContent(AbstractContent content, int pageSize, long lastTimestamp) {
        if (currentUser != null) {
            reviewFirebaseSource.getPagedUserReviewOfContent(currentUser, content, pageSize, lastTimestamp);
        }
    }

    public void addLikeOfUserToUserReviewOfContent(AbstractContent content, UserReview userReview) {
        if (currentUser != null) {
            reviewFirebaseSource.addLikeOfUserToUserReviewOfContent(currentUser, content, userReview);
        }
    }

    public void removedLikeOfUserToUserReviewOfContent(AbstractContent content, UserReview userReview) {
        if (currentUser != null) {
            reviewFirebaseSource.removedLikeOfUserToUserReviewOfContent(currentUser, content, userReview);
        }
    }

    @Override
    public void onContentRating(Double rating) {
        firebaseCallback.onContentRating(rating);
    }

    @Override
    public void onUserReviewOfContent(UserReview userReview) {
        firebaseCallback.onUserReviewOfContent(userReview);
    }

    @Override
    public void onUserReviewList(List<ContentUserReview> contentUserReviewList, String contentType) {
        if (Objects.equals(contentType, ContentTypeMappingManager.ContentType.MOVIE.getType())) {
            UserReviewListCreator<MovieDetails> userWatchlistCreator =
                    new UserReviewListCreator<>(contentUserReviewList, contentType, new MovieDetailsRemoteDataSource(context), firebaseCallback);
            userWatchlistCreator.create();
        } else if (Objects.equals(contentType, ContentTypeMappingManager.ContentType.TV.getType())) {
            UserReviewListCreator<TvDetails> userWatchlistCreator =
                    new UserReviewListCreator<>(contentUserReviewList, contentType, new TvDetailsRemoteDataSource(context), firebaseCallback);
            userWatchlistCreator.create();
        } else {
            firebaseCallback.onUserReviewList(null, contentType);
        }
    }

    @Override
    public void onAddedUserReviewOfContent(UserReview userReview) {
        firebaseCallback.onAddedUserReviewOfContent(userReview);
    }

    @Override
    public void onRemovedUserReviewOfContent(boolean removed) {
        firebaseCallback.onRemovedUserReviewOfContent(removed);
    }

    @Override
    public void onPagedUserReviewOfContent(List<UserReview> userReviewList, long lastTimestamp) {
        firebaseCallback.onPagedUserReviewOfContent(userReviewList, lastTimestamp);
    }

    @Override
    public void onAddedLikeOfUserToUserReviewOfContent(UserReview userReview, boolean added) {
        if (added && userReview != null) {
            userReview.setLikeCount(userReview.getLikeCount() + 1);
            userReview.setUserLikeReview(true);
        }
        firebaseCallback.onAddedLikeOfUserToUserReviewOfContent(userReview, added);
    }

    @Override
    public void onRemovedLikeOfUserToUserReviewOfContent(UserReview userReview, boolean removed) {
        if (removed && userReview != null) {
            userReview.setLikeCount(userReview.getLikeCount() - 1);
            userReview.setUserLikeReview(false);
        }
        firebaseCallback.onRemovedLikeOfUserToUserReviewOfContent(userReview, removed);
    }

    @Override
    public void onNetworkUnavailable() {
        firebaseCallback.onNetworkUnavailable();
    }

}
