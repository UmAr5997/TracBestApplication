package com.example.tracbestapplication.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class categoryClass {

        @SerializedName("code")
        @Expose
        public String code;
        @SerializedName("message")
        @Expose
        public String message;
        @SerializedName("data")
        @Expose
        public List<Datum> data = null;


    public class Datum {

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
}
