package com.dvertex.vritt;

import okhttp3.RequestBody;

import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UserService {

    @Multipart
    @POST("auth/social-signin")
    Call<MyResponse> saveUser(@Part("first_name") RequestBody f, @Part("last_name") RequestBody lname, @Part("email") RequestBody email,
                                @Part("phone_number") RequestBody no, @Part("role") RequestBody r);

}
