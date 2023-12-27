package com.example.cineverse.data.model.search;

import java.util.Objects;

public class QueryHistory implements Comparable<QueryHistory> {

    private int position;
    private String query;

    public QueryHistory(int position, String query) {
        this.position = position;
        this.query = query;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QueryHistory)) return false;
        QueryHistory that = (QueryHistory) o;
        return getPosition() == that.getPosition() && Objects.equals(getQuery(), that.getQuery());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPosition(), getQuery());
    }

    @Override
    public int compareTo(QueryHistory o) {
        // Compare in descending order based on position
        return Integer.compare(o.position, this.position);
    }

}
