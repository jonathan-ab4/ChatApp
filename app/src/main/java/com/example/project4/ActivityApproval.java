package com.example.project4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.project4.databinding.ActivityApprovalBinding;
import com.example.project4.databinding.ActivityRentDetailsBinding;
import com.example.project4.databinding.AdminRecyclerviewBinding;
import com.example.project4.databinding.ApprovalRecyclerviewBinding;
import com.example.project4.databinding.CartRecyclerviewBinding;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

public class ActivityApproval extends AppCompatActivity {
    ActivityApprovalBinding binding;
    ArrayList<Map<String,String>> pdt1;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView.Adapter<ActivityApproval.ViewHolder> AppAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityApprovalBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        Intent appIntent = getIntent();
        pdt1 = (ArrayList<Map<String, String>>) appIntent.getSerializableExtra("productlist");

        AppAdapter=new RecyclerView.Adapter<ViewHolder>() {
            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                return new ViewHolder(ApprovalRecyclerviewBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));

            }

            @Override
            public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


                holder.cbinding.pdtname.setText(pdt1.get(position).get("name"));
                holder.cbinding.pdtprice.setText(pdt1.get(position).get("price"));

            }

            @Override
            public int getItemCount() {
                return pdt1.size();
            }
        };



        binding.apprRecyclerview.setAdapter(AppAdapter);
        binding.apprRecyclerview.setLayoutManager(new LinearLayoutManager(this));


    }




    public class ViewHolder extends RecyclerView.ViewHolder {
        ApprovalRecyclerviewBinding cbinding;
        public ViewHolder(ApprovalRecyclerviewBinding cbinding) {
            super(cbinding.getRoot());
            this.cbinding= cbinding;
        }
    }
}