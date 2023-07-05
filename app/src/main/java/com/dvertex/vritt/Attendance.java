package com.dvertex.vritt;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.dvertex.vritt.Utility.APIClient;
import com.dvertex.vritt.Utility.SharedPrefUtil;
import com.dvertex.vritt.databinding.AttendanceBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class Attendance extends AppCompatActivity {
    AttendanceBinding binding;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AttendanceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setTitle("Dandaprahar Count");

        binding.submitat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String count = binding.count1.getText().toString().trim();

               // String accessToken = SharedPrefUtil.getString("accessToken", "", Attendance.this);
              //  String userId = SharedPrefUtil.getString("userId","", Attendance.this);

              //  registeruser3(count, userId);
            }
        });
    }
    private void registeruser3(String count, String userId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("count", count);
            jsonObject.put("userId", userId);


        } catch (JSONException e){
            e.printStackTrace();
        }

        OkHttpClient okHttpClient = APIClient.getHttpClientWithToken();
        String api_url = "https://gps.dvertexapp.in/dandhprahar/count";

        RequestBody requeestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        Request request = APIClient.getPostRequest(api_url, requeestBody);

        okHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                Log.e("xoxo1", e.getMessage() + "");
            }

            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) {
                runOnUiThread(() -> {
                    if (response.code() == 200) {
                        try {
                            String data = response.body().string();
                            setData(data);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

    }
    private void setData(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            String message = jsonObject.optString("message");

            Toast.makeText(Attendance.this, message, Toast.LENGTH_SHORT).show();
            finish();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
