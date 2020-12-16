package com.example.project4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import com.example.project4.databinding.ActivityChatBinding;
import com.example.project4.databinding.ActivityRentalBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Map;

public class rental extends AppCompatActivity {

    ActivityRentalBinding binding;
    ArrayList<Map<String, String>> cat = new ArrayList<>();
    Map<String,String> pdt;
    AppData appData = EventBus.getDefault().getStickyEvent(AppData.class);
    public CatAdapter adapter;
   // public PdtAdapter pdtdapter;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage;
    StorageReference storageReference;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRentalBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());




        db.collection("misc").document("category").addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (value.exists()) {

                            cat = (ArrayList<Map<String, String>>) value.getData().get("categories");
                            CatRecyclerView();

                        }

                    }
                });

        db.collection("products")
                .whereEqualTo("cat", "Accessories")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                               // pdt = document.getData().get("")
                                ProductRecyclerView();
                            }
                        } else {

                        }
                    }
                });




    }

    private void CatRecyclerView() {

        adapter = new CatAdapter(this, cat);
        binding.horiz.setAdapter(adapter);


    }
    private void ProductRecyclerView() {

        //pdtdapter = new CatAdapter(this, pdt);
        //binding.horiz.setAdapter(pdtdapter);


    }


}