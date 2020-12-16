package com.example.project4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project4.databinding.PdtRecyclerviewBinding;

import java.util.Map;

public class PdtAdapter extends RecyclerView.Adapter<PdtAdapter.ViewHolder> {

    Context context;
    Map<String,String> pdt;

    public PdtAdapter(Context context, Map<String, String> pdt) {
        this.context = context;
        this.pdt = pdt;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(PdtRecyclerviewBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.binding.pdtname.setText(pdt.get("name"));
        holder.binding.pdtname.setText(pdt.get("price"));
        Glide.with(context).load(pdt.get("image")).into(holder.binding.pdtimg);



    }

    @Override
    public int getItemCount() {
        return pdt.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        PdtRecyclerviewBinding binding;
        public ViewHolder(PdtRecyclerviewBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
