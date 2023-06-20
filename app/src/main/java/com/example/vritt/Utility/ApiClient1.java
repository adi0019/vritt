package com.example.vritt.Utility;

import com.example.vritt.ApiClient;
import com.example.vritt.Application;
import com.example.vritt.Attendance;
import com.example.vritt.UserService;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient1 {
    public static Retrofit getRetrofit1() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder().readTimeout(15000, TimeUnit.SECONDS);
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Interceptor header = chain -> {
            Request original = chain.request();


            Request request = original.newBuilder()
                    .header( "Content-Type", "application/form-data")

                    .method(original.method(), original.body())
                    .build();

            return chain.proceed(request);

        };
        httpClient.addInterceptor(header).addInterceptor(loggingInterceptor);

        OkHttpClient client = httpClient.build();

        return new Retrofit.Builder()
                .baseUrl("https://gps.dvertexapp.in/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();


    }

    //post method
    public  static Request postData1(String baseUrl, RequestBody requestBody){
        return  new Request.Builder().url(baseUrl).post(requestBody).build()    ;
    }


    public static UserService getUserService1(){
        UserService userService = getRetrofit1().create(UserService.class);

        return  userService;
    }

    public static OkHttpClient withToken1(){
        OkHttpClient.Builder okhttp = new OkHttpClient.Builder().readTimeout(500, TimeUnit.SECONDS);
        okhttp.addInterceptor(new ApiClient1.RequestWithToken1());


        return okhttp.build()  ;
    }

    public static class RequestWithToken1 implements Interceptor {

        public Response intercept(Chain chains) throws IOException {
            Request request = chains.request();
            Request newReq;
            String accessToken = SharedPrefUtil.getString("accessToken", "", Application.getAppContext());
            newReq = request.newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization",  "bearer " + accessToken).build();


            return  chains.proceed((newReq));
        }
    }


}
