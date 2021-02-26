package com.example.tracbestapplication.Api;

import com.example.tracbestapplication.Class.UserClass;
import com.example.tracbestapplication.Class.bussinessClass;
import com.example.tracbestapplication.Class.categoryClass;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {


    @GET("users_list")
    Call<ResponseBody> allrecord(
    );
    @FormUrlEncoded
    @POST("userlogin")
    Call<UserClass> login(
            @Field("email") String email,
            @Field("password") String pass
    );
    @FormUrlEncoded
    @POST("emailregister")
    Call<UserClass> emailregister(
            @Field("email") String email
    );
    @FormUrlEncoded
    @POST("resendotp")
    Call<UserClass> resendotp(
            @Field("email") String email
    );
    @FormUrlEncoded
    @POST("verifyemailotp")
    Call<UserClass> verifyemailotp(
            @Field("email") String email,
            @Field("otp") String pass
    );
    @FormUrlEncoded
    @POST("otp_expire")
    Call<UserClass> otp_expire(
            @Field("email") String email
    );
    @FormUrlEncoded
    @POST("profileupdate")
    Call<UserClass> profileupdate(
            @Field("email") String email,
            @Field("fname") String fname,
            @Field("lname") String lname,
            @Field("gender") String gender,
            @Field("contact") String contact,
            @Field("address") String address,
            @Field("post_code") String post_code,
            @Field("password") String password
    );
    @FormUrlEncoded
    @POST("profile")
    Call<UserClass> profile(
            @Field("email") String email
    );
    @FormUrlEncoded
    @POST("report")
    Call<UserClass> report(
            @Field("report_someone") String report_someone,
            @Field("gender") String gender,
            @Field("fname") String fname,
            @Field("lname") String lname,
            @Field("address") String address,
            @Field("post_code") String post_code,
            @Field("local_gp") String local_gp,
            @Field("contact") String contact,
            @Field("email") String email,
            @Field("diagnosis") String diagnosis,
            @Field("symptom_status") String symptom_status,
            @Field("high_temp") String high_temp,
            @Field("conti_cough") String conti_cough,
            @Field("chng_smell") String chng_smell,
            @Field("loss_taste") String loss_taste,
            @Field("smptm_strt_dte") String smptm_strt_dte
    );
    @GET("checkin")
    Call<UserClass> checkin(
            @Query("user_id") String user_id,
            @Query("business_id") String business_id,
            @Query("persons") String persons
    );
    @GET("nearby/business")
    Call<bussinessClass> nearby(
            @Query("latitude") String latitude,
            @Query("longitude") String longitude
    );

    @GET("get_categories")
    Call<categoryClass> get_categories(
    );
    @GET("business/bycategory")
    Call<bussinessClass> businessbycategory(
            @Query("category_id") String category_id,
            @Query("zip_code") String zip_code
    );
}
