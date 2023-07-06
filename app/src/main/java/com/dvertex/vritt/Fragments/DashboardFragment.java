package com.dvertex.vritt.Fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dvertex.vritt.KeyConstants;
import com.dvertex.vritt.LoginActivity;
import com.dvertex.vritt.R;
import com.dvertex.vritt.SecondActivity;
import com.dvertex.vritt.Utility.SharedPrefUtil;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class DashboardFragment extends Fragment {
    ImageView imageView;
    AlertDialog.Builder builder;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    View view;
    FragmentActivity mContext;
    GoogleSignInClient gsc;
   
    public DashboardFragment() {
        // Required empty public constructor
    }

   
    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_dashboard, container, false);
        mContext = getActivity();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("222842959277-u6qnflootgj7ehqcugmj19s6ccm8oha0.apps.googleusercontent.com")
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(mContext, gso);
        String fName = SharedPrefUtil.getString("userName", "userName", mContext);
        String email = SharedPrefUtil.getString("userEmail", "email", mContext);
        TextView mTextView = view.findViewById(R.id.name);
        mTextView.setText(fName);
        TextView mTextView1 = view.findViewById(R.id.email);
        mTextView1.setText(email);
        
        init();

        
        return  view;
    }

    private void init() {
        imageView =view.findViewById(R.id.imageView3);
        boolean isKyc = SharedPrefUtil.getBoolean(KeyConstants.IS_KYC_COMPLETED, false, mContext);
        if (isKyc){
          //  startActivity(new Intent(mContext, MainActivity.class));
        } else {
            // show dialog to update
            openDialog();
        }

        Button logout = view.findViewById(R.id.btnLogout);
        logout.setOnClickListener(click -> {
            gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    SharedPrefUtil.clearSharedPreferences(mContext);
                    startActivity(new Intent(mContext, LoginActivity.class));
                }
            });

        });


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AttandenceFragment fragment = new AttandenceFragment()  ;
                mContext.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment)
                        .commit();
            }
        });
    }

    private void openDialog() {
        builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Alert")
                .setMessage("Registration is not Complete")
                .setCancelable(true)
                .setNegativeButton("Complete  Registration", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      Intent intent = new Intent(mContext, SecondActivity.class );

                        startActivity(intent); // fill the form
                    }
                })
                .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                    }
                });
        builder.create().show();

    }
}