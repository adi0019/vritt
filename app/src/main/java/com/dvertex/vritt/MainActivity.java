package com.dvertex.vritt;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;
import androidx.fragment.app.FragmentManager;

import com.dvertex.vritt.Fragments.AttandenceFragment;
import com.dvertex.vritt.Fragments.DashboardFragment;
import com.dvertex.vritt.Utility.SharedPrefUtil;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    AlertDialog.Builder builder;
    FragmentContainer fragmentContainer;
    FragmentManager fragmentManager;
    Fragment fragment;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Dashboard");
        setContentView(R.layout.activity_main);

        attachFragment(new DashboardFragment());

    }

    private void attachFragment(Fragment fragment){
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment, "TAG").commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {

       fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
       
       if (fragment instanceof AttandenceFragment){
           Toast.makeText(this, "Yes its Attand Frag", Toast.LENGTH_SHORT).show();
           DashboardFragment fragment1 = new DashboardFragment()    ;
           attachFragment(fragment1);
       } else {
           Toast.makeText(this, "No it is not", Toast.LENGTH_SHORT).show();
       }
    }
}
