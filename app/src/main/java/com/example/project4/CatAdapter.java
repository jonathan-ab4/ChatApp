package com.example.project4;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project4.databinding.CatRecyclerviewBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.ViewHolder> {

    RecyclerView rv;

    Context context;
    ArrayList<Map<String,String>> cat;

    public CatAdapter(Context context, ArrayList<Map<String, String>> cat, RecyclerView rv) {
        this.context = context;
        this.cat = cat;
        this.rv = rv;
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


        holder.binding.catimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                FirebaseFirestore db = FirebaseFirestore.getInstance();

                db.collection("products")
                        .whereEqualTo("cat", cat.get(position).get("cat"))
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                pdt = (Map<String, String>) document.getData().get("");
                                    Log.v("abcd",String.valueOf(task.getResult().size()));

                                    rv.setAdapter(new PdtAdapter(context,task.getResult().getDocuments(),rv));


//                            }
                                }
                                else {



                                }
                            }
                        });

            }
        });



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
