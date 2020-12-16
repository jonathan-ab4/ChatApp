package com.example.project4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.project4.databinding.ActivityChatBinding;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Chat extends AppCompatActivity {


    ActivityChatBinding binding;
    ArrayList<Map<String, String>> chatList = new ArrayList<>();
    AppData appData = EventBus.getDefault().getStickyEvent(AppData.class);
    public ChatAdapter adapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    FirebaseStorage storage;
    StorageReference storageReference;

    private Uri filePath;



    StorageReference uploadTask;

    private final int PICK_IMAGE_REQUEST = 71;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        Uri uri = getIntent().getData();
        if (uri != null) {
            List<String> params = uri.getPathSegments();
            String id = params.get(params.size() - 1);
            Toast.makeText(this, "Id:" + id, Toast.LENGTH_SHORT).show();
        }

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        binding.cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                try {
//                    requestPermissionForReadExtertalStorage();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }


                chooseImage();


            }
        });


        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (binding.sendMessage.getText().toString().trim().equals("")) {

                    //Toast.makeText(Chat.this, "Enter characters", Toast.LENGTH_SHORT).show();


                } else {


                    Map<String, String> chats = new HashMap<>();


                    chats.put("username", appData.uname);
                    chats.put("text", binding.sendMessage.getText().toString());
                    chats.put("time", Long.toString(System.currentTimeMillis()));
                    chats.put("profile", appData.purl);

                    db.collection("chats").document("allchats").update("chats", FieldValue.arrayUnion(chats))
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {


                                        //Toast.makeText(Chat.this, "Message Sent", Toast.LENGTH_SHORT).show();
                                        display();
                                        Log.d("ChatFirebase:", "DocumentSnapshot successfully written!");


                                    } else {
                                        //Toast.makeText(Chat.this, "Error", Toast.LENGTH_SHORT).show();

                                    }

                                }
                            });


                    binding.sendMessage.setText("");


                }
            }


        });


    }

    public boolean checkPermissionForReadExternalStorage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    public void requestPermissionForReadExternalStorage() throws Exception {
        try {
            ActivityCompat.requestPermissions((Activity) this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    10);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private void chooseImage() {


        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 11);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 12) {
//            uploadTask = storageReference.child("images").child(Long.toString(System.currentTimeMillis())).putFile(data.getData());


        }

        if (requestCode == 11 && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();

//            Uri file = Uri.fromFile(new File(filePath.toString()));


            uploadTask = storageReference.child("images").child(Long.toString(System.currentTimeMillis()));
            uploadTask.putFile(data.getData()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    uploadTask.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            Toast.makeText(Chat.this, "Url:" + uri, Toast.LENGTH_SHORT).show();
                            Map<String, String> chats = new HashMap<>();
                            chats.put("username", appData.uname);
                            chats.put("image", uri.toString());
                            chats.put("time", Long.toString(System.currentTimeMillis()));
                            chats.put("profile", appData.purl);

                            db.collection("chats").document("allchats").update("chats", FieldValue.arrayUnion(chats))
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()) {
                                                display();
                                            } else {

                                            }


                                        }
                                    });


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });


        } else {
            Log.v("Upload:", "Failed");

        }

    }

    private void display() {


        db.collection("chats").document("allchats").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.isSuccessful()) {

                            DocumentSnapshot docSnapshot = task.getResult();
                            if (docSnapshot != null) {


                                ChatRecyclerView();


                            }


                        } else {
                            Toast.makeText(Chat.this, "Something went wrong!", Toast.LENGTH_SHORT).show();

                        }

                    }
                });


    }


    private void ChatRecyclerView() {

        adapter = new ChatAdapter(this, chatList);
        binding.recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        binding.recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    protected void onStart() {
        super.onStart();


        db.collection("chats").document("allchats")
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (value.exists()) {

                            chatList = (ArrayList<Map<String, String>>) value.getData().get("chats");
                            ChatRecyclerView();

                        }

                    }
                });

    }

}