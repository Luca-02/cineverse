package com.example.cineverse.data.model.review;

import com.example.cineverse.data.model.User;
import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.details.section.IContentDetails;

public class ContentUserReview extends UserReview {

    private AbstractContent content;

    public ContentUserReview(User user, Review review, AbstractContent content) {
        super(user, review);
        this.content = content;
    }

    public AbstractContent getContent() {
        return content;
    }

    public void setContent(AbstractContent content) {
        this.content = content;
    }

}
