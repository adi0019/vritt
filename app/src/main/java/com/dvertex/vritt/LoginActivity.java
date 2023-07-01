package com.dvertex.vritt;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.dvertex.vritt.Utility.APIClient;
import com.dvertex.vritt.Utility.APIUrl;
import com.dvertex.vritt.Utility.SharedPrefUtil;
import com.dvertex.vritt.databinding.LoginOptionBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class LoginActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 100;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    TextView googleBtn;
    LoginOptionBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LoginOptionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("222842959277-u6qnflootgj7ehqcugmj19s6ccm8oha0.apps.googleusercontent.com")
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this, gso);

        googleBtn = findViewById(R.id.google_btn);

        binding.googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();

            }
        });


    }

    void signIn() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            getUserDetails(task);
        }
    }

    private void getUserDetails(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            String userName = account.getDisplayName();
            String userEmail = account.getEmail();
            SharedPrefUtil.putString("userName", userName, this);
            SharedPrefUtil.putString("userEmail", userEmail, this);
            Log.w("ashish", "signInResult:success code=" + userEmail);
            hitApi(userName, userEmail);


        } catch (ApiException e) {
            Log.w("ashish", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void hitApi(String userName, String userEmail) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("first_name", userName);
            jsonObject.put("email", userEmail);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkHttpClient okHttpClient = APIClient.getHttpClientWithToken();
        String api_url = "https://gps.dvertexapp.in/auth/social-signin";

        RequestBody requeestBody = RequestBody.create(APIUrl.JSON, jsonObject.toString());
        Request request = APIClient.getPostRequest(api_url, requeestBody);
        Log.e("ashish", "" + jsonObject.toString());
        okHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                Log.e("ashish", e.getMessage() + "");
            }

            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) {
                runOnUiThread(() -> {
                    if (response.code() == 200) {
                        try {
                            if (response.body() != null) {
                                String data = response.body().string();
                                setData(data);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });
    }

    private void setData(String data) {
        Log.d("data", data);

        // store the token
        try {
            JSONObject jsonObject = new JSONObject(data);
            String accessToken = jsonObject.optString("accessToken");
            boolean isKYC = jsonObject.optBoolean("isKYC");
            SharedPrefUtil.putString(KeyConstants.KEY_ACCESS_TOKEN, accessToken, this);
            SharedPrefUtil.putBoolean(KeyConstants.IS_KYC_COMPLETED, isKYC, this);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        Bundle bundle = new Bundle();
        bundle.putString("data", data);
        Intent intent = new Intent(LoginActivity.this, MainActivity.class).putExtras(bundle);
        startActivity(intent);
        Toast.makeText(this, "Login successfully", Toast.LENGTH_SHORT).show();
    }


}