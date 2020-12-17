package com.example.project4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.project4.databinding.FragmentCartBinding;
import com.example.project4.databinding.FragmentRentBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.Map;


public class CartFragment extends Fragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FragmentCartBinding binding;
    ArrayList<Map<String,String>> myproduct = new ArrayList<>();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    public  CartAdapter adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        binding = FragmentCartBinding.inflate(inflater,container,false);




        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();


        db.collection("cart").document(mAuth.getCurrentUser().getUid())
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (value.exists()) {

                            myproduct = (ArrayList<Map<String, String>>) value.getData().get("myproduct");
                            recyclerview();

                        }

                    }
                });

    }

    private void recyclerview() {

        adapter = new CartAdapter( myproduct,getContext());
        binding.recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
      //  layoutManager.setStackFromEnd(true);
        binding.recyclerView.setLayoutManager(layoutManager);
    }
}