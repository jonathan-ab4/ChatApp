package com.example.project4;

import android.content.Context;
import android.media.tv.TvInputService;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project4.databinding.PdtRecyclerviewBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PdtAdapter extends RecyclerView.Adapter<PdtAdapter.ViewHolder> {

    ArrayList<Map<String, String>> cat = new ArrayList<>();


    Context context;



    List<DocumentSnapshot> querySnaps;

    public PdtAdapter(Context context, List<DocumentSnapshot> queryDocumentSnapshots) {
        this.context = context;
        this.querySnaps = queryDocumentSnapshots;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(PdtRecyclerviewBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {



        if(position==0)
        {

            FirebaseFirestore db = FirebaseFirestore.getInstance();



            db.collection("misc").document("category").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    if (task.isSuccessful()) {

                        cat = (ArrayList<Map<String, String>>) task.getResult().getData().get("categories");
                        CatAdapter ad = new CatAdapter(context,cat);
                        holder.binding.horiz.setVisibility(View.VISIBLE);
                        holder.binding.horiz.setAdapter(ad);


                    }


                }
            });





        }

        else {

            holder.binding.horiz.setVisibility(View.GONE);


            holder.binding.pdtname.setText((CharSequence) querySnaps.get(position).get("name"));
            holder.binding.pdtprice.setText((CharSequence) querySnaps.get(position).get("price"));
            Glide.with(context).load(querySnaps.get(position).get("image")).into(holder.binding.pdtimg);

        }


    }

    @Override
    public int getItemCount() {
        return querySnaps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        PdtRecyclerviewBinding binding;
        public ViewHolder(PdtRecyclerviewBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
