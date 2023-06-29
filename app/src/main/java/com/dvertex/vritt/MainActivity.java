package com.dvertex.vritt;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Dashboard");
        setContentView(R.layout.activity_main);
        imageView =findViewById(R.id.imageView3);

        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        if (data != null){
        try {
            JSONObject jsonObject = new JSONObject(data);
            boolean isKyc = jsonObject.optBoolean("isKYC");
            if (isKyc){
                // keep as it is
            } else {
                // show dialog to update
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }}


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Attendance.class));
                finish();
            }
        });
    }
}
