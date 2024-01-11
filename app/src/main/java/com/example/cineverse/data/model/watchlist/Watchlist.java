package com.example.cineverse.data.model.watchlist;

public class Watchlist {

    private int contentId;
    private Long timestamp;

    public Watchlist(int contentId, Long timestamp) {
        this.contentId = contentId;
        this.timestamp = timestamp;
    }

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Watchlist{" +
                "contentId='" + contentId + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

}
