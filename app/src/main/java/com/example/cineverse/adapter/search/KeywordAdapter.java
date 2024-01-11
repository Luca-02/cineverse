package com.example.cineverse.adapter.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cineverse.data.model.search.Keyword;
import com.example.cineverse.data.model.search.QueryHistory;
import com.example.cineverse.databinding.KeywordItemBinding;

import java.util.List;

public class KeywordAdapter
        extends RecyclerView.Adapter<KeywordAdapter.KeywordViewHolder> {

    private final List<Keyword> keywordList;
    private final OnQueryClickListener queryClickListener;

    public KeywordAdapter(List<Keyword> keywordList, OnQueryClickListener queryClickListener) {
        this.keywordList = keywordList;
        this.queryClickListener = queryClickListener;
    }

    public void setData(List<Keyword> newKeywordList) {
        int end = keywordList.size();
        keywordList.clear();
        notifyItemRangeRemoved(0, end);
        keywordList.addAll(newKeywordList.subList(0, Math.min(10, newKeywordList.size())));
        notifyItemRangeInserted(0, newKeywordList.size());
    }

    @NonNull
    @Override
    public KeywordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new KeywordViewHolder(KeywordItemBinding.inflate(
                inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull KeywordViewHolder holder, int position) {
        holder.bind(keywordList.get(position));
    }

    @Override
    public int getItemCount() {
        return keywordList.size();
    }

    public class KeywordViewHolder extends RecyclerView.ViewHolder {

        private final KeywordItemBinding binding;

        public KeywordViewHolder(KeywordItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Keyword keyword) {
            binding.textView.setText(keyword.getName());
            binding.root.setOnClickListener(v ->
                    queryClickListener.onQueryClick(keyword.getName()));
        }

    }

}
