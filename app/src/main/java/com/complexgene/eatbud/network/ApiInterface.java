package com.complexgene.eatbud.network;



import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;


public interface ApiInterface {


    @GET
    Call<ResponseBody> getAddress(@Url String url);

    @GET("menu/details/{date}")
    Call<ResponseBody> getMenuList(@Path("date") String date);


    @POST("auth/login")
    Call<ResponseBody> login(@QueryMap Map<String, String> queryParams);

    @POST("my-available-rides")
    Call<ResponseBody> loadModeOfRides();

    @POST("update-my-available-rides")
    Call<ResponseBody> updateModeOfRides(@QueryMap Map<String, String> queryParams);

    @POST("bookings")
    Call<ResponseBody> loadBookings();


}
