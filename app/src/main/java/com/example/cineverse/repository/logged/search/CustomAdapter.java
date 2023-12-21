package com.example.cineverse.repository.logged.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.cineverse.R;

import java.util.List;

public class CustomAdapter extends BaseAdapter implements ListAdapter {

    private final Context context;
    private final List<String> list;
    private final OnDeleteItemClickListener deleteItemClickListener;

    public CustomAdapter(Context context, List<String> list, OnDeleteItemClickListener deleteItemClickListener) {
        this.context = context;
        this.list = list;
        this.deleteItemClickListener = deleteItemClickListener;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void updateData(List<String> newData) {
        list.clear();
        list.addAll(newData);
        notifyDataSetChanged(); // Notifica l'adapter del cambiamento nei dati
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.search_suggestions_item, null);
        }

        TextView listItemText = (TextView) view.findViewById(R.id.list_item_string);
        listItemText.setText(list.get(position));

        Button deleteBtn = (Button) view.findViewById(R.id.delete_btn);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deleteItemClickListener != null) {
                    deleteItemClickListener.onDeleteItemClick(position);
                }
            }
        });
        return view;
    }
}

