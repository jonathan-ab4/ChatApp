package com.example.project4;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project4.databinding.CartRecyclerviewBinding;

import java.util.ArrayList;
import java.util.Map;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    ArrayList<Map<String,String>> myproduct;
    Context context;

    public CartAdapter(ArrayList<Map<String, String>> myproduct, Context context) {
        this.myproduct = myproduct;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(CartRecyclerviewBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        if(myproduct.get(position).get("status").equals("0"))
        {
           holder.binding.cartRecycle.setBackgroundColor(Color.parseColor("#FFE68989"));
        }
        else{
            holder.binding.cartRecycle.setBackgroundColor(Color.parseColor("#FF96E499"));

        }
        holder.binding.pdtname.setText(myproduct.get(position).get("name"));
        holder.binding.pdtprice.setText(myproduct.get(position).get("price"));
        Glide.with(context).load(myproduct.get(position).get("image")).into(holder.binding.pdtimg);



    }

    @Override
    public int getItemCount() {
        return myproduct.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CartRecyclerviewBinding binding;

        public ViewHolder(CartRecyclerviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
