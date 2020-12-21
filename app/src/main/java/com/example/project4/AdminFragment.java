package com.example.project4;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project4.databinding.AdminRecyclerviewBinding;
import com.example.project4.databinding.FragmentAdminBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AdminFragment extends Fragment {

    ArrayList<Map<String,Object>> userlist = new ArrayList<>();

    FragmentAdminBinding fbinding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView.Adapter<AdminFragment.ViewHolder> adminAdapter;
    List<DocumentSnapshot> users = new ArrayList<>();
//    serial serial1 = new serial();
    AppData appData = new AppData();
    ArrayList<Map<String,String>> pdt = new ArrayList<>();
    String item_name, item_price, item_img, item_desc;
    ArrayList<Map<String,String>> pdt1 = new ArrayList<>();
    ArrayList<List<DocumentSnapshot>> mainlist = new ArrayList<>();
    ArrayList<Integer> pos = new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fbinding = FragmentAdminBinding.inflate(LayoutInflater.from(getContext()),container,false);



//        db.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//
//                if(value.getDocuments().)
//
//
//
//            }
//        });

        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful())
                {

                    users = task.getResult().getDocuments();

                    //mainlist.add((task.getResult().getDocuments()));

                    for(int i =0;i<users.size();i++)
                    {

//                        pdt = (ArrayList<Map<String, String>>) users.get(i).getData().get("myproducts");
//                        int s = pdt.size();
//
//                        ArrayList<Map<String, String>> tempList = new ArrayList<>();
//
//                        for(int j=0;j<s;j++) {
//                            if (pdt.get(j).get("status").equals("0")) {
//
////                                userlist.add(users.get(i).getData());
//
//                                pos.add(j);
//
//                                tempList.add(pdt.get(j));
//
//                            }
//                        }

//                            Map<String, Object> temp = new HashMap<>();
//                            temp.put("name", users.get(i).getData().get("name"));
//                            temp.put("email", users.get(i).getData().get("email"));
//                            temp.put("uid",users.get(i).getData().get("uid"));
//                            temp.put("myproducts", users.get(i).getData().get("myproducts"));
//
//                            userlist.add(temp);



                    }


                    fbinding.adminRecycler.setAdapter(adminAdapter);
                    adminAdapter.notifyDataSetChanged();





//                    for(int i=0;i<users.size();i++)
//                    {
//
//                        pdt = (ArrayList<Map<String, String>>) users.get(i).getData().get("myproducts");
//
//                        if(pdt.get(i).get("status").equals("0"))
//                        {
//
//                            fbinding.adminRecycler.setAdapter(adminAdapter);
//                            adminAdapter.notifyDataSetChanged();
//
//
//                        }
//                        else
//                        {
//                            pdt.remove(pdt.get(i).get("status"));
//
//                        }
//
//
//
//
//
//                    }


                }

            }

        });



        fbinding.adminRecycler.setAdapter(adminAdapter);
        fbinding.adminRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

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

                        appData.users = users;
                        EventBus.getDefault().postSticky(appData);


                        pdt1 = (ArrayList<Map<String, String>>) userlist.get(position).get("myproducts");
                        Intent appIntent = new Intent(getContext(),ActivityApproval.class);
//                        appIntent.putExtra("productlist", (Serializable) serial1.users);
                        appIntent.putExtra("uid", (String) userlist.get(position).get("uid"));
                        startActivity(appIntent);

                    }
                });


            }

            @Override
            public int getItemCount() {
                return users.size();
            }
        };


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