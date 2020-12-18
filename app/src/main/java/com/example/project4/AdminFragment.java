package com.example.project4;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class AdminFragment extends Fragment {

    FragmentAdminBinding fbinding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView.Adapter<AdminFragment.ViewHolder> adminAdapter;
    List<DocumentSnapshot> users = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fbinding = FragmentAdminBinding.inflate(LayoutInflater.from(getContext()),container,false);

        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful())
                {

                    users = task.getResult().getDocuments();
                    fbinding.adminRecycler.setAdapter(adminAdapter);
                    adminAdapter.notifyDataSetChanged();

                }

            }

        });

        adminAdapter = new RecyclerView.Adapter<ViewHolder>() {
            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new ViewHolder(AdminRecyclerviewBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
            }

            @Override
            public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


                holder.binding.name.setText((CharSequence) users.get(position).getData().get("name"));
                holder.binding.email.setText((CharSequence) users.get(position).getData().get("email"));

                holder.binding.userlayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {







                    }
                });


            }

            @Override
            public int getItemCount() {
                return users.size();
            }
        };

        fbinding.adminRecycler.setAdapter(adminAdapter);
        fbinding.adminRecycler.setLayoutManager(new LinearLayoutManager(getContext()));


        return fbinding.getRoot();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AdminRecyclerviewBinding binding;
        public ViewHolder(AdminRecyclerviewBinding binding) {
            super(binding.getRoot());
            this.binding= binding;
        }
    }



}