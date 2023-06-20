package com.example.vritt;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.vritt.Utility.SharedPrefUtil;
import com.example.vritt.databinding.ActivitySecondBinding;
import com.google.android.material.textfield.TextInputEditText;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SecondActivity extends AppCompatActivity {
   String[] items =  {"Student","Job","Business"};
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;
    Button submit;
    TextInputEditText first_name, middle_name, last_name ;
    String phoneno, email, role;
    ActivitySecondBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setTitle("Personal Details");


       autoCompleteTxt = findViewById(R.id.auto_complete_txt1);

        adapterItems = new ArrayAdapter<String>(this,R.layout.list_item,items);
        autoCompleteTxt.setAdapter(adapterItems);

       autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(),"Profession: "+item,Toast.LENGTH_SHORT).show();
           }
        });

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fName = binding.etFirstName.getText().toString().trim();
                String lName = binding.etLastName.getText().toString().trim();
                String prof   = binding.autoCompleteTxt1.getText().toString().trim();
           if(fName.isEmpty() || lName.isEmpty()){
               Toast.makeText(getApplicationContext(), "First Name and Last Name  is Required ",Toast.LENGTH_SHORT).show();
           }



                // maine is project me ek Shared Preference ki calasss add ki hai.. jisme kuch static methods bnaye hue hai
                // unko use krake same time me value ko stoe kar lena..fir last page par jake sari values ko get larlena
                // usne setter or getter methods bane hue hai, example me samjhga deta hu
            else{
                    SharedPrefUtil.putString("fName", fName, SecondActivity.this);
                    SharedPrefUtil.putString("lName", lName, SecondActivity.this);
                    SharedPrefUtil.putString("prof", prof, SecondActivity.this);
                    // class call kro..set karna h to .put() method or uske andar keyName, valueName and Activity ka context pass karna hai

                    startActivity(new Intent(SecondActivity.this, Address.class));
                }
            }
        });


    }

    public  void saveUser(String fName, String lNAme, String phone, String email, String role){

        RequestBody f =RequestBody.create(MediaType.parse("text/plain"),fName);
        RequestBody lname =RequestBody.create(MediaType.parse("text/plain"),lNAme);
        RequestBody e =RequestBody.create(MediaType.parse("text/plain"),email);
        RequestBody no =RequestBody.create(MediaType.parse("text/plain"),phone);
        RequestBody r =RequestBody.create(MediaType.parse("text/plain"),role);

        Call<MyResponse> userResponseCall = ApiClient.getUserService().saveUser(f,lname, e, no, r);
        userResponseCall.enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if(response.isSuccessful()){
                    Toast.makeText(SecondActivity.this, "Saved successfully", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(SecondActivity.this, "Request failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {
                Toast.makeText(SecondActivity.this, "Request failed"+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

        }
