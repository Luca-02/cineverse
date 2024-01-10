package com.example.cineverse.adapter.details;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cineverse.data.model.details.Crew;
import com.example.cineverse.databinding.CrewDepartmentItemBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CrewDepartmentAdapter
        extends RecyclerView.Adapter<CrewDepartmentAdapter.CrewDepartmentViewHolder> {

    private final Context context;
    private final List<String> departmentList;
    private final Map<String, List<Crew>> crewMap;

    public CrewDepartmentAdapter(Context context, Map<String, List<Crew>> crewMap) {
        this.context = context;
        this.crewMap = crewMap;
        departmentList = new ArrayList<>(crewMap.keySet());
        Collections.sort(departmentList);
    }

    @NonNull
    @Override
    public CrewDepartmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new CrewDepartmentViewHolder(CrewDepartmentItemBinding.inflate(
                inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CrewDepartmentViewHolder holder, int position) {
        String department = departmentList.get(position);
        holder.bind(department, crewMap.get(department));
    }

    @Override
    public int getItemCount() {
        return crewMap.size();
    }

    public class CrewDepartmentViewHolder extends RecyclerView.ViewHolder {

        private final CrewDepartmentItemBinding binding;

        public CrewDepartmentViewHolder(@NonNull CrewDepartmentItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(String department, List<Crew> crew) {
            binding.departmentTextView.setText(department);
            CrewAdapter crewAdapter = new CrewAdapter(context, crew);
            binding.crewRecyclerView.setLayoutManager(new LinearLayoutManager(
                    context, LinearLayoutManager.HORIZONTAL, false));
            binding.crewRecyclerView.setAdapter(crewAdapter);
        }

    }

}
