package com.example.project531.apicall;

import com.example.project531.responsedata.responseCategory;
import com.example.project531.responsedata.responseProduct;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {
    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> loginUser(
            @Field("phone") String phone_number,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("Order")
    Call<ResponseBody> checkout(
          // @Field("productName") String productName,
           // @Field("subtotal") String totalEachItem2,
           // @Field("quantity") String totalEachItem,
            @Field("grndTotal") String total

    );


    @GET("product")

    Call<List<responseProduct>> get_product_list();
    @GET("category")
    Call<List<responseCategory>> get_category_list();
    @POST("logout")
    Call<ResponseBody> logout();


}
