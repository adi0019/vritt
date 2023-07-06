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

public class AdditionalDetails extends AppCompatActivity {
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
            private static final String TAG = "AdditionalDetails";

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
              //  String fName = SharedPrefUtil.getString("fName", "", AdditionalDetails.this);
                String lName = SharedPrefUtil.getString("lName", "", AdditionalDetails.this);
                String prof = SharedPrefUtil.getString("prof", "", AdditionalDetails.this);
                String Add1 = SharedPrefUtil.getString("Add1", "", AdditionalDetails.this);
                String Add2 = SharedPrefUtil.getString("Add2", "", AdditionalDetails.this);
                String Street = SharedPrefUtil.getString("Street", "", AdditionalDetails.this);
                String State = SharedPrefUtil.getString("State", "", AdditionalDetails.this);
                String Pin = SharedPrefUtil.getString("Pin", "", AdditionalDetails.this);
                String City = SharedPrefUtil.getString("City", "", AdditionalDetails.this);
                String Mahanagar = SharedPrefUtil.getString("Mahanagar", "", AdditionalDetails.this);
                String Bhag = SharedPrefUtil.getString("Bhag", "", AdditionalDetails.this);
                String Nagar = SharedPrefUtil.getString("Nagar", "", AdditionalDetails.this);
                String Shaka = SharedPrefUtil.getString("Shaka", "", AdditionalDetails.this);
              //  String email = SharedPrefUtil.getString("email", "", AdditionalDetails.this);
                String etyear = binding.etyear.getText().toString().trim();
                String dob = binding.etDob.getText().toString().trim();
                String no = binding.etPhone.getText().toString().trim();
                String role = "1";
                String dayitva = SharedPrefUtil.getString("Dayitva", "", AdditionalDetails.this);
                String dayitvaLevel = SharedPrefUtil.getString("dayitvalevel", "", AdditionalDetails.this);
                if (no.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Phone Number is Required", Toast.LENGTH_SHORT).show();
                } else if (no.length() < 10) {
                    Toast.makeText(getApplicationContext(), "Phone Number  Should Greater than 10 Digits", Toast.LENGTH_SHORT).show();
                } else {
                    registeruser2( lName,  prof, no, role, Add1, Add2, Street, State, Pin, City, Mahanagar, Bhag, Nagar, Shaka, etyear, dob, dayitva, dayitvaLevel);
                    Log.e("error",   "reg complete after function");

                    // start next

                }

            }
        });

    }

    private void registeruser2( String lName,  String profession, String no, String role, String add1,
                               String add2, String street, String state, String pin,
                               String city, String mahanagar, String bhag, String nagar,
                               String shaka, String etyear, String dob, String dayitva, String dLevel) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("last_name", lName);
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
            jsonObject.put("Dayitva", dayitva);
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
            jsonObject.put("id", SharedPrefUtil.getString(KeyConstants.KEY_USERID, "", AdditionalDetails.this));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("vritt2 register req body", jsonObject.toString());

        OkHttpClient okHttpClient = APIClient.getHttpClientWithToken();
        String api_url = "https://gps.dvertexapp.in/auth/register";

        RequestBody requeestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        Request request = APIClient.getPostRequest(api_url, requeestBody);
        okHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                Log.e("vritt2 register onFail", e.getMessage() + "");
            }

            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) {
                runOnUiThread(() -> {
                    try {
                        if (response.code() == 200) {
                            if (response.body() != null){
                                String data = response.body().string();
                                setData(data);
                            }
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
                // pehle setData(data) tak compile nhi ho rha tha...abi read karega
            }
        });

    }


    private void setData(String data) {
        Log.i("vritt2 register resp data", data); // yaha par
            Toast.makeText(AdditionalDetails.this, "successfully updated", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(AdditionalDetails.this, MainActivity.class);
            startActivity(intent);

        SharedPrefUtil.putBoolean(KeyConstants.IS_KYC_COMPLETED, true, this);


    }

}
