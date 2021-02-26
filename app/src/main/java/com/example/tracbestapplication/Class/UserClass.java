package com.example.tracbestapplication.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserClass {

    @SerializedName("code")
    @Expose
    public String code;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public Data data;

    public class Data {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("email")
        @Expose
        public String email;
        @SerializedName("email_verified_at")
        @Expose
        public Object emailVerifiedAt;
        @SerializedName("password")
        @Expose
        public String password;
        @SerializedName("status")
        @Expose
        public Integer status;
        @SerializedName("email_otp")
        @Expose
        public String emailOtp;
        @SerializedName("gender")
        @Expose
        public String gender;
        @SerializedName("fname")
        @Expose
        public String fname;
        @SerializedName("lname")
        @Expose
        public String lname;
        @SerializedName("contact")
        @Expose
        public String contact;
        @SerializedName("address")
        @Expose
        public String address;
        @SerializedName("post_code")
        @Expose
        public String postCode;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;

    }
}
