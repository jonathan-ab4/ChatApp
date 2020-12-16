package com.example.project4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.project4.databinding.ActivityRentBinding;
import com.example.project4.databinding.CatRecyclerviewBinding;
import com.example.project4.databinding.PdtRecyclerviewBinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project4.databinding.ActivityRentBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Act_rent extends AppCompatActivity {

    RecyclerView.Adapter<ViewHolder> Padapter;

    RecyclerView.Adapter<CatViewHolder> Cat_adapter;

    ActivityRentBinding binding;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<DocumentSnapshot> docs = new ArrayList<>();
    ArrayList<Map<String, String>> cat = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRentBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        db.collection("misc").document("category").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {

                    cat = (ArrayList<Map<String, String>>) task.getResult().getData().get("categories");
                    Cat_adapter.notifyDataSetChanged();


                }
            }
        });


        Cat_adapter = new RecyclerView.Adapter<CatViewHolder>() {

            @NonNull
            @Override
            public CatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new CatViewHolder(CatRecyclerviewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            }

            @Override
            public void onBindViewHolder(@NonNull CatViewHolder holder, int position) {

                holder.cbinding.catname.setText(cat.get(position).get("cat"));
                Glide.with(Act_rent.this).load(cat.get(position).get("image")).into(holder.cbinding.catimg);

                holder.cbinding.catimg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        db.collection("products")
                                .whereEqualTo("cat", cat.get(position).get("cat"))
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            docs = task.getResult().getDocuments();

                                            binding.rv.setAdapter(Padapter);

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
        };


        Padapter = new RecyclerView.Adapter<ViewHolder>() {
            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new ViewHolder(PdtRecyclerviewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            }

            @Override
            public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

                if (position == 0) {

                    holder.pbinding.horiz.setVisibility(View.VISIBLE);
                    holder.pbinding.horiz.setAdapter(Cat_adapter);


                } else {

                    //holder.pbinding.horiz.setVisibility(View.GONE);
                    holder.pbinding.pdtname.setText(String.valueOf(docs.get(position - 1).getData().get("name")));
                    holder.pbinding.pdtprice.setText(String.valueOf(docs.get(position - 1).getData().get("price")));
                    Glide.with(Act_rent.this).load(docs.get(position - 1).getData().get("image")).into(holder.pbinding.pdtimg);


                }


            }

            @Override
            public int getItemCount() {
                return docs.size()+1;
            }
        };

        binding.rv.setAdapter(Padapter);
        binding.rv.setLayoutManager(new LinearLayoutManager(this));

        db.collection("products")
                .whereEqualTo("cat", "Cameras")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    docs = task.getResult().getDocuments();
                    Padapter.notifyDataSetChanged();
                }

            }
        });

    }


    public class CatViewHolder extends RecyclerView.ViewHolder {

        CatRecyclerviewBinding cbinding;


        public CatViewHolder(CatRecyclerviewBinding cbinding) {
            super(cbinding.getRoot());
            this.cbinding = cbinding;
        }


    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        com.example.project4.databinding.PdtRecyclerviewBinding pbinding;

        public ViewHolder(com.example.project4.databinding.PdtRecyclerviewBinding pbinding) {
            super(pbinding.getRoot());
            this.pbinding = pbinding;
        }
    }
}