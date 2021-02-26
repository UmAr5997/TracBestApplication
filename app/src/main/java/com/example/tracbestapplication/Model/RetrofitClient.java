package com.example.tracbestapplication.Model;
import com.example.tracbestapplication.Api.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String base_url="https://projects.funtash.net/tracbest/public/api/";
    private static com.example.tracbestapplication.Model.RetrofitClient mInstance;
    private Retrofit retrofit;

    private RetrofitClient()
    {
        retrofit=new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized com.example.tracbestapplication.Model.RetrofitClient getInstance()
    {
        if(mInstance==null)
        {
            mInstance=new com.example.tracbestapplication.Model.RetrofitClient();
        }
        return mInstance;
    }
    public Api getApi()
    {
        return retrofit.create(Api.class);
    }
}
