package com.example.project531.apicall;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public  class RetrofitClient {
    public  Retrofit getRetrofit(){
        Retrofit  retrofit = new Retrofit.Builder()
                .baseUrl("https://dmcbproject.com/dairy_api/api")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }
}
