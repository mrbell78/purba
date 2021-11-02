package com.example.project531;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project531.Activity.MainActivity;
import com.example.project531.Helper.ManagementCart;
import com.example.project531.Interface.ChangeNumberItemsListener;
import com.example.project531.adapter.CartListAdapter;
import com.example.project531.adapter.checkoutAdaptor;
import com.example.project531.apicall.Api;
import com.example.project531.apicall.RetrofitClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class checkout_activity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private ManagementCart managementCart;
    private TextView totalFeeTxt, taxTxt, deliveryTxt, totalTxt, emptyTxt, CheckOut, productName, totalEachItem2, totalEachItem, feeEachItem, total;
    private double tax;
    private EditText total3;
    private ScrollView scrollView;
    String str;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        managementCart = new ManagementCart(this);
        initView();
        initList();
        calculateCard();
        /*Checkout */
        productName= findViewById(R.id.item);
        totalEachItem2= findViewById(R.id.totalEachItem2);
        totalEachItem= findViewById(R.id.totalEachItem);
        feeEachItem= findViewById(R.id.feeEachItem);
        total3= findViewById(R.id.totalTxt2);
        CheckOut= findViewById(R.id.CheckOut);

        RetrofitClient retrofitClient = new RetrofitClient();
        //  Retrofit mb = retrofitClient.getRetrofit();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dmcbproject.com/dairy_api/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Api apicallingjson = retrofit.create(Api.class);
                    Call<ResponseBody> callcoment = apicallingjson.checkout(
                            totalTxt.getText().toString().trim()


                    );
                    callcoment.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.code() == 200) {
                                totalTxt.setText("");
                                Toast.makeText(checkout_activity.this, "Successfully Checkout", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(checkout_activity.this, MainActivity.class);
                                startActivity(intent);
                            }

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                }

        });

    }

    private void initList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        adapter = new checkoutAdaptor(managementCart.getListCart(), this, new ChangeNumberItemsListener() {
            @Override
            public void changed() {
                /*calculateCard();*/
            }
        });
        recyclerViewList.setAdapter(adapter);
   /*     if (managementCart.getListCart().isEmpty()) {
            emptyTxt.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        } else {
            emptyTxt.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }*/
    }


    private void calculateCard() {
        int delivery = Integer.parseInt(String.valueOf(80));     //you can change this item you need price for delivery
        // int total = (managementCart.getTotalFee());
        int total = (int) (Math.round((managementCart.getTotalFee() + delivery) * 100.0) / 100.0);
        int itemTotal = (int) (Math.round(managementCart.getTotalFee() * 100.0) / 100.0);
      //  totalFeeTxt.setText("TK " + itemTotal);
      //  deliveryTxt.setText("TK " + delivery);
        totalTxt.setText("" + total);
    }

    private void initView() {
        totalFeeTxt = findViewById(R.id.totalFeeTxt);
        deliveryTxt = findViewById(R.id.deliveryTxt);
        totalTxt = findViewById(R.id.totalTxt2);
        recyclerViewList = findViewById(R.id.checkout_view);
        scrollView = findViewById(R.id.scrollView2);
        emptyTxt = findViewById(R.id.emptyTxt);
    }

}