package com.example.project4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.widget.Space;
import android.widget.Toast;

import com.example.project4.databinding.ActivitySplashScreenBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

public class SplashScreen extends AppCompatActivity {

    ActivitySplashScreenBinding binding;

    AppData appData = new AppData();

    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashScreenBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());


        mAuth = FirebaseAuth.getInstance();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                FirebaseUser current = mAuth.getCurrentUser();

                if (current != null) {
                    Toast.makeText(SplashScreen.this, "You are already signed in", Toast.LENGTH_SHORT).show();
                    appData.uname = current.getDisplayName();
                    appData.purl = current.getPhotoUrl().toString();
                    EventBus.getDefault().postSticky(appData);

                    Intent i = new Intent(SplashScreen.this, SignedInScreen.class);
                    startActivity(i);
                    finish();

                } else {
                    Toast.makeText(SplashScreen.this, "Please sign in", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(SplashScreen.this, Login.class);
                    startActivity(i);
                    finish();


                }


            }
        }, 2500);


    }


}