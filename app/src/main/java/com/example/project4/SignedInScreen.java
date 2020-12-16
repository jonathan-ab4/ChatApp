package com.example.project4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.project4.databinding.ActivitySignedInScreenBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignedInScreen extends AppCompatActivity {

    ActivitySignedInScreenBinding binding;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignedInScreenBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        alarmMgr = (AlarmManager) getSystemService(this.ALARM_SERVICE);
        Intent intent = new Intent(this, SampleService.class);
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()+1000 * 5, alarmIntent);

        FirebaseUser current = mAuth.getCurrentUser();

        if (current != null) {

            db.collection("users").document(current.getUid())
                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    binding.profilename.setText(documentSnapshot.getString("name"));
                    binding.profileemail.setText(documentSnapshot.getString("email"));
                    Glide.with(getApplicationContext()).load(documentSnapshot.getString("profile_url")).into(binding.profileimage);

                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(SignedInScreen.this, "Error in loading", Toast.LENGTH_SHORT).show();

                        }
                    });

            binding.chatButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i = new Intent(SignedInScreen.this, Chat.class);
                    startActivity(i);

                }
            });


        }


    }
}