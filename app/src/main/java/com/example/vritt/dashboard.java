package com.example.vritt;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vritt.Utility.ApiClient2;
import com.example.vritt.Utility.SharedPrefUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.internal.concurrent.Task;

public class dashboard extends AppCompatActivity {
    ImageView imageView;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Dashboard");

        setContentView(R.layout.dashboard);
        imageView =findViewById(R.id.imageView3);
        sendData();
// ho gya, aur kuch ?
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(dashboard.this, Attendance.class));
                finish();
            }
        });
    }

    public void sendData() {


        String fname = SharedPrefUtil.getString("fname", "", dashboard.this);
        String email = SharedPrefUtil.getString("email", "", dashboard.this);

        String source = "google";



           boolean task = true;
           if(task==true) {

               registeruser2(fname, email);

           }











//



        }




/// wait

private void registeruser2(String fname,  String email) {
        JSONObject jsonObject = new JSONObject();
        try {
        jsonObject.put("first_name", fname);
        jsonObject.put("email", email);

        // here add all keys and their respective values in json object

        } catch (JSONException e){
        e.printStackTrace();
        }

        Log.i("xoxo", "" +jsonObject.toString());

        OkHttpClient okHttpClient = ApiClient2.withoutToken();
        String api_url = "https://gps.dvertexapp.in/auth/social-signin";

        RequestBody requeestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        Request request = ApiClient2.postData(api_url, requeestBody);

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
        SharedPrefUtil.putString("accessToken", accessToken, dashboard.this);
        String userId =jsonObject.optString("userId");
        SharedPrefUtil.putString("userId",userId, dashboard.this);


        Toast.makeText(dashboard.this, message, Toast.LENGTH_SHORT).show();

        startActivity(new Intent(dashboard.this, dashboard.class));
        } catch (JSONException e) {
        e.printStackTrace();
        }
        }

}
