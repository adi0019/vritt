package com.dvertex.vritt.Utility;


import android.util.Log;

import androidx.annotation.NonNull;

import com.dvertex.vritt.Application;
import com.dvertex.vritt.KeyConstants;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private static final String TAG = APIClient.class.getSimpleName();
    public static Retrofit retrofit = null;

    public static Retrofit getTokenClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder().readTimeout(15000, TimeUnit.SECONDS);
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Interceptor header = chain -> {
            Request original = chain.request();
            String accessToken = SharedPrefUtil.getString(KeyConstants.KEY_ACCESS_TOKEN.trim(), "", Application.getAppContext());
            accessToken = "bearer" + " " + accessToken.trim();
            Log.d(TAG, "accessToken " + accessToken);

            Request request = original.newBuilder()
                    .header(KeyConstants.KEY_CONTENT_TYPE, KeyConstants.APPLICATION_URL_ENCODED)
                    .header(KeyConstants.KEY_AUTHORIZATION, accessToken)
                    .method(original.method(), original.body())
                    .build();

            return chain.proceed(request);

        };
        httpClient.addInterceptor(header).addInterceptor(loggingInterceptor);

        OkHttpClient client = httpClient.build();

        return new Retrofit.Builder()
                .baseUrl(APIUrl.WEB_SERVICE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }


    public static OkHttpClient getHttpClientWithToken() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder().readTimeout(500, TimeUnit.SECONDS);
        httpClient.addInterceptor(new RequestTokenInterceptor());
        return httpClient.build();
    }

    public static OkHttpClient getHttpClientWithoutToken() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder().readTimeout(500, TimeUnit.SECONDS);
        httpClient.addInterceptor(new RequestWithOutTokenInterceptor());
        return httpClient.build();
    }

    public static Request getPostWithoutTokenRequest(String url, RequestBody requestBody) {
        return new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
    }

    public static Request getPostRequest(String url, RequestBody requestBody) {
        return new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
    }


    public static Request getPatchRequest(String url, RequestBody requestBody) {
        return new Request.Builder()
                .url(url)
                .patch(requestBody)
                .build();
    }

    public static Request getsignupPostRequest(String url, RequestBody requestBody) {
        return new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
    }

    public static Request getRequest(String url) {
        return new Request.Builder()
                .url(url)
                .build();
    }

    public static Request getPutRequest(String url, RequestBody requestBody) {
        return new Request.Builder()
                .url(url)
                .put(requestBody)
                .build();
    }

    public static Request deleteRequest(String url, RequestBody requestBody) {
        return new Request.Builder()
                .url(url)
                .delete(requestBody)
                .build();
    }

    private static class RequestTokenInterceptor implements Interceptor {
        private final String TAG = RequestTokenInterceptor.class.getSimpleName() ;
        @NonNull
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Request request = chain.request();
            okhttp3.Request newRequest;

            String accessToken = SharedPrefUtil.getString(KeyConstants.KEY_ACCESS_TOKEN.trim(), "", Application.getAppContext());
            accessToken = "bearer"+" " + accessToken.trim();
            Log.d(TAG, "accessToken " + accessToken);
            newRequest = request.newBuilder()
                    .addHeader(KeyConstants.KEY_CONTENT_TYPE, KeyConstants.KEY_APPLICATION_JSON)
                    .addHeader(KeyConstants.KEY_AUTHORIZATION, accessToken)
                    .build();
            return chain.proceed(newRequest);//old code

        }
    }

    private static class RequestWithOutTokenInterceptor implements Interceptor {
        private  final String TAG = RequestWithOutTokenInterceptor.class.getSimpleName();
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Request request = chain.request();
            okhttp3.Request newRequest;

            newRequest = request.newBuilder()
                    .addHeader(KeyConstants.KEY_CONTENT_TYPE, KeyConstants.KEY_APPLICATION_JSON)
                    .build();
            return chain.proceed(newRequest);

        }
    }
}