package com.example.project4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.project4.databinding.ActivityRentBinding;

import android.view.LayoutInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

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
                     case R.id.page_3:
                        selectedFragment = new NewAdminFrag();
                        break;
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