package com.example.project4;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.project4.databinding.ActivityChatBinding;
import com.example.project4.databinding.ActivityRentalBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Map;

public class rental extends AppCompatActivity {

    ActivityRentalBinding binding;
    ArrayList<Map<String, String>> cat = new ArrayList<>();
    AppData appData = EventBus.getDefault().getStickyEvent(AppData.class);
    public CatAdapter adapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage;
    StorageReference storageReference;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRentalBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        db.collection("misc").document("category").addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (value.exists()) {

                            cat = (ArrayList<Map<String, String>>) value.getData().get("categories");
                            CatRecyclerView();

                        }

                    }
                });


    }

    private void CatRecyclerView() {

        adapter = new CatAdapter(this, cat);
        binding.horiz.setAdapter(adapter);


    }
}