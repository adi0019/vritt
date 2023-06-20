package com.example.vritt;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vritt.Utility.SharedPrefUtil;
import com.example.vritt.databinding.ActivityMainBinding;
import com.example.vritt.databinding.ActivitySecondBinding;
import com.example.vritt.databinding.LoginOptionBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    TextView googleBtn;
    LoginOptionBinding binding;







    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LoginOptionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
      //  setContentView(R.layout.activity_main);
     //   setContentView(R.layout.login_option);
        getSupportActionBar().hide();

        // spinner ki problem check karl ena apni llog laga karr
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

        String accessToken = SharedPrefUtil.getString("accessToken", "", MainActivity.this);
        Log.i("xoxo", "accesstoken on Main" + accessToken);
        if (!accessToken.isEmpty()){
            startActivity(new Intent(this, dashboard.class));
        }

        googleBtn = findViewById(R.id.google_btn);
        // isse sirf one time login user k i email aaygi,,new hoga wo kaha jayega

        binding.googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
               // String email   = binding.personEmail.getText().toString().trim();
                Bundle bundle = new Bundle( );
              //  bundle.putString("email", email);
            }
        });



    }
    void  signIn(){
        Intent signIntent = gsc.getSignInIntent();
        startActivityForResult(signIntent, 1000);
    }
    protected void  onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                getUserDetails(task);

            }
            catch (ApiException e){
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            } // konsi app h wo unistaall akro
        }
    }

    private void getUserDetails(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            String email = account.getEmail();
            SharedPrefUtil.putString("email", email, MainActivity.this);
            navigateToSecondActivity();
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    void navigateToSecondActivity(){
        finish();
        Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent);
    }
}