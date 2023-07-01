package com.example.vritt;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vritt.Utility.SharedPrefUtil;

import com.example.vritt.databinding.AdditionaldetailsBinding;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class additionaldetails<intent> extends AppCompatActivity {
    String[] items6 =  {"New","2022","2021","2020","2019","2018","2017","2016","2015","2014","2013","2012","2011","2010","2009","2008","2007","2006","2005"};
    AutoCompleteTextView autoCompleteTxt6;
    ArrayAdapter<String> adapterItems6;
    private TextView mDisplayDate;
    Button next;
    AdditionaldetailsBinding binding;


       // Log.d("xoxo", fName + " " + lName);


    private DatePickerDialog.OnDateSetListener mDateSetListener;
    @SuppressLint("WrongViewCast")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AdditionaldetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
       // setContentView(R.layout.additionaldetails);
       // autoCompleteTxt6 = findViewById(R.id.etYear);

        next= findViewById(R.id.submit2);

//        Intent intent = getIntent();
//        if(intent !=null){
//            Bundle bundle = intent.getExtras();
//            String fName = bundle.getString("fName");
//            String lName = bundle.getString("lName");
//        }




//        autoCompleteTxt6 = findViewById(R.id.auto_complete_txt6);

       // adapterItems6 = new ArrayAdapter<String>(this,R.layout.list_item,items6);
      //  autoCompleteTxt6.setAdapter(adapterItems6);

//      //  autoCompleteTxt6.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String item = parent.getItemAtPosition(position).toString();
//                Toast.makeText(getApplicationContext(),": "+item,Toast.LENGTH_SHORT).show();
//            }
//        });
        // next = findViewById(R.id.submit);
        getSupportActionBar().setTitle("Additional Details");


        mDateSetListener = new DatePickerDialog.OnDateSetListener() {


            private static final String TAG = "additionaldetails";

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);
            }
        };
        binding.submit23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // here get all the saved data from shared pref.
                // agar get kar rhe hai to pehle pta hona chahiye ki kya value get kar rhe hai..
                // yaha string leni h to String fname and then getString () or fir keyName jo save krte time dala tha..default value "" (null) chhod deni h
                // ab yaha sabke liye likho
                // i hope likhna samajh aagya hoga iten me se...aage ka m kar deta hu
                // email or phone kis page pr save kiya tha
                String fName = SharedPrefUtil.getString("fName", "", additionaldetails.this);
                String lName = SharedPrefUtil.getString("lName","",additionaldetails.this);
                String prof = SharedPrefUtil.getString("prof","", additionaldetails.this);
                String Add1 = SharedPrefUtil.getString("Add1", "", additionaldetails.this);
                String Add2 = SharedPrefUtil.getString("Add2", "", additionaldetails.this);
                String Street = SharedPrefUtil.getString("Street", "", additionaldetails.this);
                String State = SharedPrefUtil.getString("State", "", additionaldetails.this);
                String Pin = SharedPrefUtil.getString("Pin", "", additionaldetails.this);
                String City = SharedPrefUtil.getString("City", "", additionaldetails.this);
                String Mahanagar = SharedPrefUtil.getString("Mahanagar", "", additionaldetails.this);
                String Bhag = SharedPrefUtil.getString("Bhag", "", additionaldetails.this);
                String Nagar = SharedPrefUtil.getString("Nagar", "", additionaldetails.this);
                String Shaka = SharedPrefUtil.getString("Shaka", "", additionaldetails.this);
                String email = SharedPrefUtil.getString("email", "", additionaldetails.this);
                String etyear = binding.etyear.getText().toString().trim();
                String dob   = binding.etDob.getText().toString().trim();
                String no   = binding.etPhone.getText().toString().trim();
                String role = "1";
                String dayitva = SharedPrefUtil.getString("dayitva", "", additionaldetails.this);
                String dayitvaLevel = SharedPrefUtil.getString("dayitvalevel", "", additionaldetails.this);
                if(no.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Phone Number is Required", Toast.LENGTH_SHORT).show();
                }
                  else  if (no.length() < 10) {
                        Toast.makeText(getApplicationContext(), "Phone Number  Should Greater than 10 Digits", Toast.LENGTH_SHORT).show();
                    }

            else {
                     registeruser2(fName, lName, email, prof, no, role, Add1, Add2, Street, State, Pin, City, Mahanagar, Bhag, Nagar, Shaka, etyear, dob, dayitva, dayitvaLevel);

                 }
//
            }
        });

    }



    /// wait

    private void registeruser2(String fName, String lName, String email, String profession, String no, String role, String add1,
                               String add2, String street, String state, String pin,
                               String city, String mahanagar, String bhag, String nagar,
                               String shaka, String etyear, String dob, String dayitva,String dLevel) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("first_name", fName);
            jsonObject.put("last_name", lName);
            jsonObject.put("email", email);
            jsonObject.put("phone_number", no);
            jsonObject.put("role", role);
            jsonObject.put("fcm_token", "xyz");
            jsonObject.put("source", "Google"); // agar google se login h to
            jsonObject.put("latitude", "25.1212");
            jsonObject.put("longitude", "775.4545");
            jsonObject.put("address", add1);
            jsonObject.put("profession", profession);  // why add 2 ?
            jsonObject.put("street_address", street);
            jsonObject.put("state", state);
            jsonObject.put("pincode", pin);
            jsonObject.put("city", city);
            jsonObject.put("dayitva", dayitva);
            jsonObject.put("dayitva_level", dLevel);
            jsonObject.put("mahanagar", mahanagar);
            jsonObject.put("bhag", bhag);
            jsonObject.put("nagar", nagar);
            jsonObject.put("basti", "nagar");
            jsonObject.put("shakha", shaka);
            jsonObject.put("mandal", "mandal");
            jsonObject.put("matri_shakha", "matri_shakha");
            jsonObject.put("etyear", etyear);
            jsonObject.put("sangh_pravesh_year", "2023");
            jsonObject.put("dob", dob);
            // here add all keys and their respective values in json object

        } catch (JSONException e){
            e.printStackTrace();
        }

        Log.i("xoxo", "" +jsonObject.toString());

        OkHttpClient okHttpClient = ApiClient.withoutToken();
        String api_url = "https://gps.dvertexapp.in/auth/social-signin";

        RequestBody requeestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        Request request = ApiClient.postData(api_url, requeestBody);

        if (okHttpClient != null) {
            okHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                    Log.e("xoxo", e.getMessage() + "");
                }

                @Override
                public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) throws IOException {
                    runOnUiThread(() -> {
                        if (response.isSuccessful()) {
                            String data = null;
                            try {
                                data = response.body().string();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            setData(data);
                        }
                    });


                }
            });
        }

    }

    private void setData(String data) {
        // yah data abi string m h
        try {
            JSONObject jsonObject = new JSONObject(data);
            String message = jsonObject.optString("message");
            String accessToken = jsonObject.optString("accessToken");
            SharedPrefUtil.putString("accessToken", accessToken, additionaldetails.this);
            String userId =jsonObject.optString("userId");
            SharedPrefUtil.putString("userId",userId, additionaldetails.this);


            Toast.makeText(additionaldetails.this, message, Toast.LENGTH_SHORT).show();

            startActivity(new Intent(additionaldetails.this, dashboard.class));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void registeruser(String fname, String lNAme, String email, String phone) {
        Log.i("xoxo", fname + lNAme + email + phone);
        RequestBody bodyFName =RequestBody.create(MediaType.parse("text/plain"),fname);
        RequestBody bodyLName =RequestBody.create(MediaType.parse("text/plain"),lNAme);
        RequestBody bodyEmail =RequestBody.create(MediaType.parse("text/plain"),email);
        RequestBody bodyPhone =RequestBody.create(MediaType.parse("text/plain"),phone);
        RequestBody bodyRole =RequestBody.create(MediaType.parse("text/plain"),"1");
//        RequestBody bodyToken =RequestBody.create(MediaType.parse("text/plain"),"");
//        RequestBody bodyLat =RequestBody.create(MediaType.parse("text/plain"),"25.454545");
//        RequestBody bodyLong =RequestBody.create(MediaType.parse("text/plain"),"75.454545");
//        RequestBody bodyPro =RequestBody.create(MediaType.parse("text/plain"),"Job");
//        RequestBody bodyAddress =RequestBody.create(MediaType.parse("text/plain"),"1");
//        RequestBody bodySAdd =RequestBody.create(MediaType.parse("text/plain"),"1");
//        RequestBody bodyPin =RequestBody.create(MediaType.parse("text/plain"),"1");
//        RequestBody bodyCity =RequestBody.create(MediaType.parse("text/plain"),"1");
//        RequestBody bodyRole =RequestBody.create(MediaType.parse("text/plain"),"1");
//        RequestBody bodyRole =RequestBody.create(MediaType.parse("text/plain"),"1");

        // kuch na kuch to misssing ja raha h api me... retrofit ka setup shi h

        Log.i("xoxo",  ""+ bodyFName.toString() + bodyEmail + bodyLName + bodyRole + bodyPhone);

        Call<MyResponse> userResponseCall = ApiClient.getUserService().saveUser(bodyFName, bodyLName, bodyEmail, bodyPhone, bodyRole);
        userResponseCall.enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(@NonNull Call<MyResponse> call, @NonNull Response<MyResponse> response) {
                Log.i("xoxo", ""+ response.code());
                if(response.isSuccessful()){
                    Toast.makeText(additionaldetails.this, "Saved successfully", Toast.LENGTH_SHORT).show();
                }
                else{
                    Log.i("xoxo", ""+ response.body());
                    Toast.makeText(additionaldetails.this, "Request failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MyResponse> call, @NonNull Throwable t) {
                Log.e("xoxo", ""+ t.getMessage());
                Toast.makeText(additionaldetails.this, "Request failed bcoz "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
