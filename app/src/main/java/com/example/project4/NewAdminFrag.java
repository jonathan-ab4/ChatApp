package com.example.project4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project4.databinding.AdminRecyclerviewBinding;
import com.example.project4.databinding.FragmentAdminBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class NewAdminFrag extends Fragment {

    FragmentAdminBinding fbinding;

    AppData appData = new AppData();
    List<DocumentSnapshot> users = new ArrayList<>();
    RecyclerView.Adapter<Hello> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fbinding = FragmentAdminBinding.inflate(LayoutInflater.from(getContext()), container, false);


        FirebaseFirestore db = FirebaseFirestore.getInstance();





        adapter = new RecyclerView.Adapter<Hello>() {
            @NonNull
            @Override
            public Hello onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new Hello(AdminRecyclerviewBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
            }

            @Override
            public void onBindViewHolder(@NonNull Hello holder, int position) {
                    holder.binding.name.setText(String.valueOf(users.get(position).getData().get("name")));
                    holder.binding.email.setText(String.valueOf(users.get(position).getData().get("email")));
                    holder.binding.userlayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent1 = new Intent(getContext(), ActivityApproval.class);
                            intent1.putExtra("uid", users.get(position).getId());
                            startActivity(intent1);
                        }
                    });
            }

            @Override
            public int getItemCount() {
                return users.size();
            }
        };

        db.collection("users")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    users = task.getResult().getDocuments();
                    appData.users = users;
                    EventBus.getDefault().postSticky(appData);



                    adapter.notifyDataSetChanged();


                }
            }
        });


        fbinding.adminRecycler.setAdapter(adapter);
        fbinding.adminRecycler.setLayoutManager(new LinearLayoutManager(getContext()));


        return fbinding.getRoot();

    }






    public class Hello extends RecyclerView.ViewHolder {
        AdminRecyclerviewBinding binding;
        public Hello(AdminRecyclerviewBinding binding) {
            super(binding.getRoot());
            this.binding= binding;
        }
    }
}
