package com.example.vritt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.vritt.Utility.SharedPrefUtil;
import com.example.vritt.databinding.AddressBinding;


public class Address extends AppCompatActivity {
    Button next;
     AddressBinding binding;
    private String selectedDayitva, selectedDayitva_Level;                 //vars to hold the values of selected State and District
    private TextView tvStateSpinner, tvDistrictSpinner;             //declaring TextView to show the errors
    private Spinner DayitvaSpinner, Dayitva_LevelSpinner;                  //Spinners
    private ArrayAdapter<CharSequence> Dayitva_LevelAdapter, DayitvaAdapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AddressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

       // setContentView(R.layout.address);
      //  next = findViewById(R.id.submit2);
        getSupportActionBar().setTitle("Address");


        binding.submit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Add1 = binding.etAdd1.getText().toString().trim();
                String Add2 = binding.etAdd2.getText().toString().trim();
                String Street   = binding.etStreetAdd.getText().toString().trim();
                String State   = binding.etState.getText().toString().trim();
                String Pin   = binding.etPin.getText().toString().trim();
                String City   = binding.etCity.getText().toString().trim();

                // same chiz yaha likho abhi
                // same line copy karne k liye.. line ke end me jakar CTRL + D  press karo
                SharedPrefUtil.putString("Add1", Add1, Address.this);
                SharedPrefUtil.putString("Add2", Add2, Address.this);
                SharedPrefUtil.putString("Street", Street, Address.this);
                SharedPrefUtil.putString("State", State, Address.this);
                SharedPrefUtil.putString("Pin", Pin, Address.this);
                SharedPrefUtil.putString("City", City, Address.this);
                // done, now next pageNO
                // Kisi bhi activity ya classs par jane k liye us class ke name par CTRL + click karo,,(if calss name is existing in the page)


                startActivity(new Intent(Address.this, Matra_shaka.class));
            }
        });

    }



}
