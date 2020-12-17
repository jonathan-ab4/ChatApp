package com.example.project4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.project4.databinding.FragmentCartBinding;
import com.example.project4.databinding.FragmentRentBinding;


public class CartFragment extends Fragment {

    FragmentCartBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCartBinding.inflate(inflater,container,false);




        return binding.getRoot();
    }
}