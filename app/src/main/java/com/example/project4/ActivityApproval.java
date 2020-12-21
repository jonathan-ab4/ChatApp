package com.example.project4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.project4.databinding.ActivityApprovalBinding;
import com.example.project4.databinding.ActivityRentDetailsBinding;
import com.example.project4.databinding.AdminRecyclerviewBinding;
import com.example.project4.databinding.ApprovalRecyclerviewBinding;
import com.example.project4.databinding.ApprovePopupBinding;
import com.example.project4.databinding.CartRecyclerviewBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ActivityApproval extends AppCompatActivity {
    ActivityApprovalBinding binding;
    List<DocumentSnapshot> pdt1;
    ArrayList<Map<String,Object>> mainlist;
    ArrayList<Integer> pos;
    ArrayList<Map<String,String>> cart = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView.Adapter<ActivityApproval.ViewHolder> AppAdapter;

    PopupWindow popupWindow;
    View layout;

    EditText amount;
    Button approve;

    String uid;

    ApprovePopupBinding apbinding;
    AppData appData = EventBus.getDefault().getStickyEvent(AppData.class);


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityApprovalBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        uid = getIntent().getStringExtra("uid");
        for(DocumentSnapshot doc: appData.users){
            if(doc.getId().equals(uid)){
                cart = (ArrayList<Map<String, String>>) doc.getData().get("myproducts");
            }
        }


        Intent appIntent = getIntent();
        pdt1 = appData.users;

        Toast.makeText(this, uid, Toast.LENGTH_SHORT).show();

        //pos = (ArrayList<Integer>) appIntent.getSerializableExtra("pos");

        //mainlist = (ArrayList<Map<String, Object>>) appIntent.getSerializableExtra("mainlist");


        LayoutInflater inflater = (LayoutInflater) ActivityApproval.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout = inflater.inflate(R.layout.approve_popup, null);
        popupWindow = new PopupWindow(layout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        amount = layout.findViewById(R.id.amount);
        approve = layout.findViewById(R.id.approve);





        AppAdapter=new RecyclerView.Adapter<ViewHolder>() {
            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                return new ViewHolder(ApprovalRecyclerviewBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));

            }

            @Override
            public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


//
//
                if(cart.get(position).get("status").equals("0"))
                {
                    holder.cbinding.apprRecyclerview.setVisibility(View.VISIBLE);
                    holder.cbinding.pdtname.setText(cart.get(position).get("name"));
                    holder.cbinding.pdtprice.setText(cart.get(position).get("price"));
                    Glide.with(getApplicationContext()).load(cart.get(position).get("image")).into(holder.cbinding.pdtimg);

                    holder.cbinding.apprRecyclerview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            popupWindow.showAtLocation(view, Gravity.CENTER,0,0);

                            //approve.setEnabled(false);


                            approve.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    if(amount.getText().toString().trim().equals(""))
                                    {
                                        Toast.makeText(ActivityApproval.this, "Please enter amount", Toast.LENGTH_SHORT).show();

                                        amount.setFocusable(true);
                                    }
                                    else
                                    {
                                        Toast.makeText(ActivityApproval.this, "Amount Saved", Toast.LENGTH_SHORT).show();


//                                    pdt1.get(position).put("price","Rs"+amount.getText().toString());
//                                    pdt1.get(position).put("status","1");

                                        cart.get(position).put("price", amount.getText().toString());
                                        cart.get(position).put("status", "1");
//
                                        db.collection("users").document(uid).update("myproducts",cart)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                        if(task.isSuccessful())
                                                        {

                                                            Toast.makeText(ActivityApproval.this, "Price changed", Toast.LENGTH_SHORT).show();

                                                        }

                                                        else
                                                        {

                                                            Toast.makeText(ActivityApproval.this, "Failed:"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                        }

                                                    }
                                                });

//                                    db.collection("users").document().update((Map<String, Object>) FieldValue.arrayUnion())

                                    }


                                }
                            });

                        }
                    });









                }

                else

                {
                    holder.cbinding.apprRecyclerview.setVisibility(View.GONE);


                }



            }



            @Override
            public int getItemCount() {
                return cart.size();
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