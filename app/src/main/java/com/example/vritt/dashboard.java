package com.example.vritt;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class dashboard extends AppCompatActivity {
    ImageView imageView;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Dashboard");

        setContentView(R.layout.dashboard);
        imageView =findViewById(R.id.imageView3);
// ho gya, aur kuch ?
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(dashboard.this, Attendance.class));
                finish();
            }
        });
    }
}
