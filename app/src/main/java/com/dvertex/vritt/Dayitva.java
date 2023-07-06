package com.dvertex.vritt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dvertex.vritt.Utility.SharedPrefUtil;
import com.dvertex.vritt.databinding.DayitvaBinding;

public class Dayitva extends AppCompatActivity {
    private String selectedDistrict, selectedState;                 //vars to hold the values of selected State and District
    private TextView tvStateSpinner, tvDistrictSpinner;             //declaring TextView to show the errors
    private Spinner stateSpinner, districtSpinner;                  //Spinners
    private ArrayAdapter<CharSequence> stateAdapter, districtAdapter;
    Button submit;
    Button next;
    DayitvaBinding binding;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DayitvaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setTitle("Dayitva");

        stateSpinner = findViewById(R.id.spinner_indian_states1);
        districtSpinner = findViewById(R.id.spinner_indian_districts);

        stateAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_Dayitva_Level, R.layout.spinner_layout);

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dayitvalevel = binding.spinnerIndianStates1.getSelectedItem().toString().trim();
                String dayitva = binding.spinnerIndianDistricts.getSelectedItem().toString().trim();

                SharedPrefUtil.putString("dayitvalevel", dayitvalevel, Dayitva.this);
                SharedPrefUtil.putString("Dayitva", dayitva, Dayitva.this);

                Log.d("aditya", dayitvalevel + " & " + dayitva);
                startActivity(new Intent(Dayitva.this, AdditionalDetails.class));
            }
        });

        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(stateAdapter);            //Set the adapter to the spinner to populate the State Spinner

        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedState = stateSpinner.getSelectedItem().toString();      //Obtain the selected State

                int parentID = parent.getId();
                if (parentID == R.id.spinner_indian_states1) {
                    switch (selectedState) {
                        case "Select Your Dayitva Level":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_Dayitva, R.layout.spinner_layout);

                            districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     // Specify the layout to use when the list of choices appears
                            districtSpinner.setAdapter(districtAdapter);
                            break;
                        case "Mahanagar/Bhag/Nagar ":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_Mahanagar_Bhag_Nagar, R.layout.spinner_layout);

                            districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     // Specify the layout to use when the list of choices appears
                            districtSpinner.setAdapter(districtAdapter);
                            break;
                        case "Basti/Shakha Level":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_Basti_Shakha_Level, R.layout.spinner_layout);

                            districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     // Specify the layout to use when the list of choices appears
                            districtSpinner.setAdapter(districtAdapter);
                            break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        districtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDistrict = districtSpinner.getSelectedItem().toString();
                Toast.makeText(Dayitva.this, selectedDistrict + " selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



    }

}
