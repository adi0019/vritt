package com.dvertex.vritt;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.dvertex.vritt.Utility.APIClient;
import com.dvertex.vritt.Utility.SharedPrefUtil;
import com.dvertex.vritt.databinding.AdditionaldetailsBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class additionaldetails extends AppCompatActivity {
    String[] items6 = {"New", "2022", "2021", "2020", "2019", "2018", "2017", "2016", "2015", "2014", "2013", "2012", "2011", "2010", "2009", "2008", "2007", "2006", "2005"};
    AutoCompleteTextView autoCompleteTxt6;
    ArrayAdapter<String> adapterItems6;
    private TextView mDisplayDate;
    Button next;
    AdditionaldetailsBinding binding;


    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @SuppressLint("WrongViewCast")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AdditionaldetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        next = findViewById(R.id.submit2);

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
                String fName = SharedPrefUtil.getString("fName", "", additionaldetails.this);
                String lName = SharedPrefUtil.getString("lName", "", additionaldetails.this);
                String prof = SharedPrefUtil.getString("prof", "", additionaldetails.this);
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
                String dob = binding.etDob.getText().toString().trim();
                String no = binding.etPhone.getText().toString().trim();
                String role = "1";
                String dayitva = SharedPrefUtil.getString("dayitva", "", additionaldetails.this);
                String dayitvaLevel = SharedPrefUtil.getString("dayitvalevel", "", additionaldetails.this);
                if (no.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Phone Number is Required", Toast.LENGTH_SHORT).show();
                } else if (no.length() < 10) {
                    Toast.makeText(getApplicationContext(), "Phone Number  Should Greater than 10 Digits", Toast.LENGTH_SHORT).show();
                } else {
                    registeruser2(fName, lName, email, prof, no, role, Add1, Add2, Street, State, Pin, City, Mahanagar, Bhag, Nagar, Shaka, etyear, dob, dayitva, dayitvaLevel);

                }

            }
        });

    }

    private void registeruser2(String fName, String lName, String email, String profession, String no, String role, String add1,
                               String add2, String street, String state, String pin,
                               String city, String mahanagar, String bhag, String nagar,
                               String shaka, String etyear, String dob, String dayitva, String dLevel) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("first_name", fName);
            jsonObject.put("last_name", lName);
            jsonObject.put("email", email);
            jsonObject.put("phone_number", no);
            jsonObject.put("role", role);
            jsonObject.put("fcm_token", "xyz");
            jsonObject.put("source", "Google");
            jsonObject.put("latitude", "25.1212");
            jsonObject.put("longitude", "775.4545");
            jsonObject.put("address", add1);
            jsonObject.put("profession", profession);
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

        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkHttpClient okHttpClient = APIClient.getHttpClientWithToken();
        String api_url = "https://gps.dvertexapp.in/auth/social-signin";

        RequestBody requeestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        Request request = APIClient.getPostRequest(api_url, requeestBody);

        okHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                Log.e("ERROR", e.getMessage() + "");
            }

            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) {
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

    private void setData(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            String message = jsonObject.optString("message");
            String accessToken = jsonObject.optString("accessToken");
            SharedPrefUtil.putString("accessToken", accessToken, additionaldetails.this);
            String userId = jsonObject.optString("userId");
            SharedPrefUtil.putString("userId", userId, additionaldetails.this);


            Toast.makeText(additionaldetails.this, message, Toast.LENGTH_SHORT).show();

            startActivity(new Intent(additionaldetails.this, MainActivity.class));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
