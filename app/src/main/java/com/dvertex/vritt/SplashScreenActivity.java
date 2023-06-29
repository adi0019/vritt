package com.dvertex.vritt;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.dvertex.vritt.Utility.SharedPrefUtil;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();

        String accessToken = SharedPrefUtil.getString(KeyConstants.KEY_ACCESS_TOKEN, "", SplashScreenActivity.this);
        if (!accessToken.isEmpty()) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                finish();
            }, 2000);
        }

    }

}
