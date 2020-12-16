package com.example.project4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project4.databinding.CatRecyclerviewBinding;

import java.util.ArrayList;
import java.util.Map;

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.ViewHolder> {

    Context context;
    ArrayList<Map<String,String>> cat;

    public CatAdapter(Context context, ArrayList<Map<String, String>> cat) {
        this.context = context;
        this.cat = cat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(CatRecyclerviewBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.binding.catname.setText(cat.get(position).get("cat"));
        Glide.with(context).load(cat.get(position).get("image")).into(holder.binding.catimg);



    }

    @Override
    public int getItemCount() {
        return cat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CatRecyclerviewBinding binding;

        public ViewHolder(CatRecyclerviewBinding binding) {
            super(binding.getRoot());
            this.binding= binding;
        }
    }
}
