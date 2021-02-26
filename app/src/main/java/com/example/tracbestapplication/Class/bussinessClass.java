package com.example.tracbestapplication.Class;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class bussinessClass {
    @SerializedName("code")
    @Expose
    public String code;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public List<Datum> data = null;


public class BusinessDetails {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("user_id")
    @Expose
    public Integer userId;
    @SerializedName("fname")
    @Expose
    public String fname;
    @SerializedName("lname")
    @Expose
    public String lname;
    @SerializedName("business_name")
    @Expose
    public String businessName;
    @SerializedName("category_id")
    @Expose
    public Integer categoryId;
    @SerializedName("business_cat")
    @Expose
    public String businessCat;
    @SerializedName("otp")
    @Expose
    public String otp;
    @SerializedName("num_location")
    @Expose
    public Integer numLocation;
    @SerializedName("dis_access")
    @Expose
    public String disAccess;
    @SerializedName("qr_link")
    @Expose
    public Object qrLink;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("category")
    @Expose
    public Category category;
    @SerializedName("users")
    @Expose
    public Users users;

}

public class Category {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("category_name")
    @Expose
    public String categoryName;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;

}

public class Datum {

    @SerializedName("distance")
    @Expose
    public Double distance;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("business_id")
    @Expose
    public Integer businessId;
    @SerializedName("business_adress")
    @Expose
    public String businessAdress;
    @SerializedName("latitude")
    @Expose
    public String latitude;
    @SerializedName("longitude")
    @Expose
    public String longitude;
    @SerializedName("zip_code")
    @Expose
    public String zipCode;
    @SerializedName("Contact_number")
    @Expose
    public String contactNumber;
    @SerializedName("privacy_policy")
    @Expose
    public String privacyPolicy;
    @SerializedName("term_condition")
    @Expose
    public String termCondition;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("business_details")
    @Expose
    public BusinessDetails businessDetails;

}

public class Users {

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
    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("check_in")
    @Expose
    public String checkIn;
    @SerializedName("check_out")
    @Expose
    public String checkOut;
    @SerializedName("check_time")
    @Expose
    public Object checkTime;
    @SerializedName("checkout_time")
    @Expose
    public Object checkoutTime;
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



