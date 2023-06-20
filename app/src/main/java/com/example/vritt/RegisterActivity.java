package com.example.vritt;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vritt.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.llPersonal.personalLayout.setVisibility(View.GONE);
        binding.loginoption1.loginoption.setVisibility(View.VISIBLE);
        binding.address1.address.setVisibility(View.GONE);
        binding.matraShaka1.matraShaka.setVisibility(View.GONE);
        binding.dayitva1.dayitva.setVisibility(View.GONE);
        binding.address1.address.setVisibility(View.GONE);



    }
}