package com.example.project4;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project4.databinding.CatRecyclerviewBinding;
import com.example.project4.databinding.FragmentRentBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class RentFragment extends Fragment {

    RecyclerView.Adapter<ViewHolder> Padapter;
    RecyclerView.Adapter<CatViewHolder> Cat_adapter;
    String item_name, item_price,img_url,item_desc;
    FragmentRentBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<DocumentSnapshot> pdt_list = new ArrayList<>();
    ArrayList<Map<String, String>> cat = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentRentBinding.inflate(inflater,container,false);

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
                Glide.with(RentFragment.this).load(cat.get(position).get("image")).into(holder.cbinding.catimg);

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

                                            pdt_list = task.getResult().getDocuments();

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
            public RentFragment.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new ViewHolder(com.example.project4.databinding.PdtRecyclerviewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            }

            @Override
            public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

                if (position == 0) {

                    holder.pbinding.horiz.setVisibility(View.VISIBLE);
                    holder.pbinding.horiz.setAdapter(Cat_adapter);

                    holder.pbinding.group.setVisibility(View.GONE);

                }

                else {

                    holder.pbinding.group.setVisibility(View.VISIBLE);

                    //holder.pbinding.horiz.setVisibility(View.GONE);
                    holder.pbinding.pdtname.setText(String.valueOf(pdt_list.get(position - 1).getData().get("name")));
                    holder.pbinding.pdtprice.setText(String.valueOf(pdt_list.get(position - 1).getData().get("price")));
                    Glide.with(RentFragment.this).load(pdt_list.get(position - 1).getData().get("image")).into(holder.pbinding.pdtimg);


                    holder.pbinding.pdtlayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            item_name = pdt_list.get(position - 1).getData().get("name").toString();
                            item_price = pdt_list.get(position - 1).getData().get("price").toString();
                            img_url = pdt_list.get(position-1).getData().get("image").toString();
                            item_desc = pdt_list.get(position-1).getData().get("desc").toString();


                            Intent itemIntent = new Intent(getContext(),ActivityRentDetails.class);
                            itemIntent.putExtra("item_name", item_name);
                            itemIntent.putExtra("item_price", item_price);
                            itemIntent.putExtra("item_img",img_url);
                            itemIntent.putExtra("item_desc",item_desc);
                            startActivity(itemIntent);
                            getActivity().finish();



                        }
                    });


                }


            }

            @Override
            public int getItemCount() {
                return pdt_list.size() + 1;
            }
        };

        binding.rv.setAdapter(Padapter);
        binding.rv.setLayoutManager(new LinearLayoutManager(getContext()));

        db.collection("products")
                .whereEqualTo("cat", "Cameras")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    pdt_list = task.getResult().getDocuments();
                    Padapter.notifyDataSetChanged();
                }

            }
        });

        return binding.getRoot();

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