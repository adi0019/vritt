package com.example.vritt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vritt.Utility.SharedPrefUtil;
import com.example.vritt.databinding.DayitvaBinding;
import com.example.vritt.databinding.MatraShakaBinding;

public class dayitva extends AppCompatActivity {
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


       // setContentView(R.layout.dayitva);
        getSupportActionBar().setTitle("Dayitva");
       // next=findViewById(R.id.button_submit);
        stateSpinner = findViewById(R.id.spinner_indian_states1);    //Finds a view that was identified by the android:id attribute in xml

        //Populate ArrayAdapter using string array and a spinner layout that we will define
        stateAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_Dayitva_Level, R.layout.spinner_layout);
//        CharSequence spinner1SelectedData  = (CharSequence) stateSpinner.getSelectedItem();
//        CharSequence spinner2SelectedData  = (CharSequence) districtSpinner.getSelectedItem();

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dayitvalevel   = binding.spinnerIndianStates1.getSelectedItem().toString().trim();
                String dayitva   = binding.spinnerIndianDistricts.getSelectedItem().toString().trim();
              //  Toast.makeText(dayitva.this, dayitva, Toast.LENGTH_SHORT).show();

                // always use captial letter in activity name (1st letter)
                SharedPrefUtil.putString("dayitvalevel", dayitvalevel, dayitva.this);
                Log.d("aditya",dayitvalevel);
                SharedPrefUtil.putString("dayitva", dayitva, dayitva.this);

                startActivity(new Intent(dayitva.this, additionaldetails.class));
            }
        });

        // Specify the layout to use when the list of choices appear
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        stateSpinner.setAdapter(stateAdapter);            //Set the adapter to the spinner to populate the State Spinner

        //When any item of the stateSpinner uis selected
        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Define City Spinner but we will populate the options through the selected state
                districtSpinner = findViewById(R.id.spinner_indian_districts);

                selectedState = stateSpinner.getSelectedItem().toString();      //Obtain the selected State

                int parentID = parent.getId();
                if (parentID == R.id.spinner_indian_states1){
                    switch (selectedState){
                        case "Select Your Dayitva Level": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Dayitva, R.layout.spinner_layout);
                            break;
                        case "Mahanagar/Bhag/Nagar ": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Mahanagar_Bhag_Nagar, R.layout.spinner_layout);
                            break;
                        case "Basti/Shakha Level": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Basti_Shakha_Level, R.layout.spinner_layout);
                            break;


                    }
                    districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     // Specify the layout to use when the list of choices appears
                    districtSpinner.setAdapter(districtAdapter);        //Populate the list of Districts in respect of the State selected

                    //To obtain the selected District from the spinner
                    districtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            selectedDistrict = districtSpinner.getSelectedItem().toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                    Button submitButton;                                //To display the selected State and District
                    submitButton = findViewById(R.id.submit);
                  //  tvStateSpinner = findViewById(R.id.textView_);
                   // tvDistrictSpinner = findViewById(R.id.textView_indian_districts);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

}
