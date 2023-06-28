package com.dvertex.vritt;

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

public class ApiClient {

    public static Retrofit getRetrofit() {
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
    public  static Request postData(String baseUrl, RequestBody requestBody){
        return  new Request.Builder().url(baseUrl).post(requestBody).build()    ;
    }


    public static  UserService getUserService(){
        UserService userService = getRetrofit().create(UserService.class);

        return  userService;
    }

    public static OkHttpClient withoutToken(){
        OkHttpClient.Builder okhttp = new OkHttpClient.Builder().readTimeout(500, TimeUnit.SECONDS);
        okhttp.addInterceptor(new RequestWithoutToken());

        return okhttp.build()  ;
    }

    public static class RequestWithoutToken implements Interceptor {

        public Response intercept(Chain chains) throws IOException {
            Request request = chains.request();
            Request newReq;
            newReq = request.newBuilder(

            ).addHeader("Content-Type", "application/form-data").build();
            return  chains.proceed((newReq));
        }
    }



}
