package com.example.cineverse.adapter.search;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cineverse.data.model.search.QueryHistory;
import com.example.cineverse.databinding.SearchSuggestionsItemBinding;

import java.util.List;

public class SearchHistoryAdapter
        extends RecyclerView.Adapter<SearchHistoryAdapter.QueryViewHolder> {

    public interface OnSearchQueryListener extends OnQueryClickListener {
        void onDeleteClick(QueryHistory queryHistory);
        void onDataChange(int dataSize);
    }

    private final List<QueryHistory> queryList;
    private final OnSearchQueryListener searchQueryListener;

    public SearchHistoryAdapter(List<QueryHistory> queryList, OnSearchQueryListener deleteItemClickListener) {
        this.queryList = queryList;
        this.searchQueryListener = deleteItemClickListener;
    }

    public void setData(List<QueryHistory> newQueryHistory) {
        int end = queryList.size();
        queryList.clear();
        notifyItemRangeRemoved(0, end);
        queryList.addAll(newQueryHistory);
        notifyItemRangeInserted(0, newQueryHistory.size());
        searchQueryListener.onDataChange(queryList.size());
    }

    public void addQuery(QueryHistory queryHistory) {
        queryList.add(0, queryHistory);
        notifyItemInserted(0);
        searchQueryListener.onDataChange(queryList.size());
    }

    public void deleteQuery(QueryHistory queryHistory) {
        int position = queryList.indexOf(queryHistory);
        queryList.remove(queryHistory);
        notifyItemRemoved(position);
        searchQueryListener.onDataChange(queryList.size());
    }

    @NonNull
    @Override
    public SearchHistoryAdapter.QueryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new QueryViewHolder(SearchSuggestionsItemBinding.inflate(
                inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchHistoryAdapter.QueryViewHolder holder, int position) {
        holder.bind(queryList.get(position));
    }

    @Override
    public int getItemCount() {
        return queryList.size();
    }

    public class QueryViewHolder extends RecyclerView.ViewHolder {

        private final SearchSuggestionsItemBinding binding;

        public QueryViewHolder(SearchSuggestionsItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(QueryHistory queryHistory) {
            binding.listItemString.setText(queryHistory.getQuery());
            binding.deleteBtn.setOnClickListener(v -> {
                searchQueryListener.onDeleteClick(queryHistory);
                deleteQuery(queryHistory);
            });
            binding.root.setOnClickListener(v ->
                    searchQueryListener.onQueryClick(queryHistory.getQuery()));
        }

    }

}
