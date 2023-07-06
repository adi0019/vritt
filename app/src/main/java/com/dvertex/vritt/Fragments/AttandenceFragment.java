package com.dvertex.vritt.Fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.dvertex.vritt.KeyConstants;
import com.dvertex.vritt.MainActivity;
import com.dvertex.vritt.R;
import com.dvertex.vritt.Utility.APIClient;
import com.dvertex.vritt.Utility.SharedPrefUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class AttandenceFragment extends Fragment {

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    View view;
    FragmentActivity mContext;

    public AttandenceFragment() {
        // Required empty public constructor
    }


    public static AttandenceFragment newInstance(String param1, String param2) {
        AttandenceFragment fragment = new AttandenceFragment();
        return fragment;
    } // now check

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_attandance, container, false);

        mContext = getActivity();

        Button submitat;
        submitat =view.findViewById(R.id.submitat);
        TextView count1;
        count1 =view.findViewById(R.id.count1);
        submitat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String count = count1.getText().toString().trim();
                String userId = SharedPrefUtil.getString(KeyConstants.KEY_USERID,"", mContext);
                registeruser3(count, userId);
            }
        });

        init();

        return  view;
    }

    private void init() {

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
                Log.e("xox", response.code() + "");
                mContext.runOnUiThread(() -> {
                    try {
                        if (response.code() == 200) {
                            if (response.body() != null){
                                String data = response.body().string();
                                setData(data);
                            }
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        });

    }
    private void setData(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            String message = jsonObject.optString("message");

            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(mContext, MainActivity.class));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
