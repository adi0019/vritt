package com.example.vritt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;



import androidx.appcompat.app.AppCompatActivity;

import com.example.vritt.Utility.SharedPrefUtil;
import com.example.vritt.databinding.ActivityMainBinding;
import com.example.vritt.databinding.ActivitySecondBinding;
import com.example.vritt.databinding.AddressBinding;
import com.example.vritt.databinding.MatraShakaBinding;


public class Matra_shaka extends AppCompatActivity {
     MatraShakaBinding binding;

    Button next;
    String[] items2 =  {"Noida Mahanagar"};
    String[] items3 =  {"Rajju Bhaiya Bhag"};
    String[] items4 =  {"Krishn Nagar"};
    String[] items5 =  {"Balram Shakha","Guru Golwalkar Shakha","Guru Teg Bahadur Shakha","Shri Hanuman Shakha","Shri Vivekanand Shakha","Vedvyas Shakha","Veer Savarkar Shakha","Shri Krishn Shakha","Maharan Pratap Milan","Keshav Shakha"};
    AutoCompleteTextView autoCompleteTxt2,autoCompleteTxt3,autoCompleteTxt4,autoCompleteTxt5;
    ArrayAdapter<String> adapterItems2,adapterItems3,adapterItems4,adapterItems5;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MatraShakaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

// getIntetn() delete isliye because pcihe wali activity se koi bi data intenet ke throuh nhi send kr rhe so it will be crash
       // setContentView(R.layout.matra_shaka);
        next = findViewById(R.id.submit22);


            getSupportActionBar().setTitle("Matra Shaka");
            autoCompleteTxt2 = findViewById(R.id.auto_complete_txt2);

            adapterItems2 = new ArrayAdapter<String>(this, R.layout.list_item, items2);
            autoCompleteTxt2.setAdapter(adapterItems2);

            autoCompleteTxt2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String item = parent.getItemAtPosition(position).toString();
                    Toast.makeText(getApplicationContext(), "Mahanagar: " + item, Toast.LENGTH_SHORT).show();
                }
            });
            autoCompleteTxt3 = findViewById(R.id.auto_complete_txt3);

            adapterItems3 = new ArrayAdapter<String>(this, R.layout.list_item, items3);
            autoCompleteTxt3.setAdapter(adapterItems3);

            autoCompleteTxt3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String item = parent.getItemAtPosition(position).toString();
                    Toast.makeText(getApplicationContext(), "Mahanagar: " + item, Toast.LENGTH_SHORT).show();
                }
            });
            autoCompleteTxt4 = findViewById(R.id.auto_complete_txt4);

            adapterItems4 = new ArrayAdapter<String>(this, R.layout.list_item, items4);
            autoCompleteTxt4.setAdapter(adapterItems4);

            autoCompleteTxt4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String item = parent.getItemAtPosition(position).toString();
                    Toast.makeText(getApplicationContext(), "Mahanagar: " + item, Toast.LENGTH_SHORT).show();
                }
            });
            autoCompleteTxt5 = findViewById(R.id.auto_complete_txt5);

            adapterItems5 = new ArrayAdapter<String>(this, R.layout.list_item, items5);
            autoCompleteTxt5.setAdapter(adapterItems5);

            autoCompleteTxt5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String item = parent.getItemAtPosition(position).toString();
                    Toast.makeText(getApplicationContext(), "Mahanagar: " + item, Toast.LENGTH_SHORT).show();
                }
            });

            binding.submit22.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String Mahanagar = binding.autoCompleteTxt2.getText().toString().trim();
                    String Bhag = binding.autoCompleteTxt3.getText().toString().trim();
                    String Nagar   = binding.autoCompleteTxt4.getText().toString().trim();
                    String Shaka   = binding.autoCompleteTxt5.getText().toString().trim();

                    SharedPrefUtil.putString("Mahanagar", Mahanagar, Matra_shaka.this);
                    SharedPrefUtil.putString("Bhag", Bhag, Matra_shaka.this);
                    SharedPrefUtil.putString("Nagar", Nagar, Matra_shaka.this);
                    SharedPrefUtil.putString("Shaka", Shaka, Matra_shaka.this);

                    // done, now next page
                    startActivity(new Intent(Matra_shaka.this, dayitva.class));
                }
            });
        }

}
