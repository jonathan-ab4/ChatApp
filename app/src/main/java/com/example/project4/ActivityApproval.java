package com.example.project4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.project4.databinding.ActivityApprovalBinding;
import com.example.project4.databinding.ActivityRentDetailsBinding;

public class ActivityApproval extends AppCompatActivity {
    ActivityApprovalBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityApprovalBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());








    }
}