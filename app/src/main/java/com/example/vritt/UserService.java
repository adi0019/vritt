package com.example.vritt;

import okhttp3.RequestBody;

import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UserService {

    @Multipart
    //@POST("auth/social-signin")
    Call<MyResponse> saveUser(@Part("first_name") RequestBody f, @Part("last_name") RequestBody lname, @Part("email") RequestBody email,
                                @Part("phone_number") RequestBody no, @Part("role") RequestBody r/*, @Part("source") RequestBody s,
                                @Part("fcm_token") RequestBody fcm, @Part("latitude") RequestBody lat, @Part("longitude") RequestBody lon, @Part("profession") RequestBody prof,
                                @Part("address") RequestBody address, @Part("street_address") RequestBody stradd, @Part("pincode") RequestBody pin, @Part("city") RequestBody city,
                                @Part("state") RequestBody state, @Part("dayitya_level") RequestBody day_level, @Part("dayitva") RequestBody day, @Part("sangh_pravesh_year") RequestBody pravesh, @Part("dob") RequestBody dob,
                                @Part("matri_shakha") RequestBody matri, @Part("bhag") RequestBody bhag, @Part("nagar") RequestBody nagar, @Part("basti") RequestBody basti, @Part("shakha") RequestBody shakha, @Part("khand") RequestBody khand,
                                @Part("mandal") RequestBody mandal, @Part("gaon") RequestBody gaon*/);

//    Call<UserResponse> saveUser(RequestBody f, RequestBody lname, RequestBody email);
//
//    Call<UserResponse> saveUser(RequestBody f, RequestBody lname, RequestBody e, RequestBody no, RequestBody rol);
}
