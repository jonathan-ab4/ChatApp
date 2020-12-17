package com.example.project4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.project4.databinding.ActivityRentBinding;
import com.example.project4.databinding.CatRecyclerviewBinding;
import com.example.project4.databinding.PdtRecyclerviewBinding;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ActivityRent extends AppCompatActivity {


    ActivityRentBinding binding;
    Fragment selectedFragment = new RentFragment();
    Fragment selectedFragment1 = new CartFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRentBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();


        Bundle bundle = getIntent().getExtras();

        if(bundle!=null)
        {

            if(bundle.getInt("cart_flag")== 1)
            {

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment1).commit();
                binding.botnav.setSelectedItemId(R.id.page_2);

            }
            else
            {

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();


            }

        }





        binding.botnav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.page_1:
                        selectedFragment = new RentFragment();
                        break;
                    case R.id.page_2:
                        selectedFragment = new CartFragment();
                        break;
//                    case R.id.page_3:
//                        selectedFragment = new AcademyFragment();
//                        break;
//
//                    case R.id.page_4:
//                        selectedFragment = new ProfileFragment();
//                        break;

                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();


                return true;
            }
        });

    }
}