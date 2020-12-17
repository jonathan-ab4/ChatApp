package com.example.project4;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.project4.databinding.ActivityRentDetailsBinding;

import java.util.Calendar;
import java.util.Date;

public class ActivityRentDetails  extends AppCompatActivity {

    ActivityRentDetailsBinding binding;

    String item_name,item_price,item_img,item_desc;
    int st_date,en_date;
    int flag=0;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(ActivityRentDetails.this, ActivityRent.class);
        startActivity(intent);
        //super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityRentDetailsBinding.inflate(LayoutInflater.from(this));
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());


        Intent itemIntent = getIntent();
        item_name = itemIntent.getStringExtra("item_name");
        item_price = itemIntent.getStringExtra("item_price");
        item_img = itemIntent.getStringExtra("item_img");
        item_desc = itemIntent.getStringExtra("item_desc");


        Glide.with(this).load(item_img).into(binding.pdtimg);
        binding.name.setText(item_name);
        binding.price.setText(item_price);
        binding.desc.setText(item_desc);

        binding.end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(ActivityRentDetails.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                                st_date = day + (month + 1) + year;

                                binding.end.setText(day + "/" + (month + 1) + "/" + year);
                                if(st_date>=en_date )
                                {
                                    binding.start.setText("End Date");

                                }

                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();





            }
        });

        binding.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(ActivityRentDetails.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                                en_date = day + (month + 1) + year;
                                if (st_date < en_date) {
                                    flag=1;
                                    binding.start.setText(day + "/" + (month + 1) + "/" + year);
                                } else
                                    Toast.makeText(ActivityRentDetails.this, "Start Date should be earlier than End Date ", Toast.LENGTH_SHORT).show();

                            }

                        }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();





            }
        });

        binding.rent1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ActivityRentDetails.this, ActivityRent.class);
                intent.putExtra("cart_flag",1);
                startActivity(intent);
                finish();


            }
        });

    }
}
