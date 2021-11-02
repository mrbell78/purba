package com.example.project531;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.project531.Activity.CartActivity;
import com.example.project531.Activity.MainActivity;
import com.example.project531.Activity.profile;
import com.example.project531.adapter.productAdaopter;
import com.example.project531.apicall.Api;
import com.example.project531.responsedata.responseProduct;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class categorySerachActivity extends AppCompatActivity {
    private RecyclerView recyclerView, categoryview;
    private FloatingActionButton floatAction;
    private EditText searchView;
    ProgressDialog dialog;
    CharSequence search="";
    List<responseProduct> bodydataarr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_serach);

        floatAction = findViewById(R.id.button_action);;
        recyclerView = findViewById(R.id.view3);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(categorySerachActivity.this));
        bodydataarr=new ArrayList<>();
        bottomNavigation();
        dialog= new ProgressDialog(categorySerachActivity.this);
        dialog.setContentView(R.layout.progressbar_dialog);
        //dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.addContentView(new ProgressBar(categorySerachActivity.this), new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        String getInvenCode = getIntent().getStringExtra("getInvenCode");
        Retrofit retrofit_textview = new Retrofit.Builder()
                .baseUrl("https://dmcbproject.com/dairy_api/api/product/"+getInvenCode+"/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api apicallingjson2 = retrofit_textview.create(Api.class);
        dialog.show();
        Call<List<responseProduct>> searchapi_textview= apicallingjson2.get_product_list();
        searchapi_textview.enqueue(new Callback<List<responseProduct>>() {
            @Override
            public void onResponse(Call<List<responseProduct>> call, Response<List<responseProduct>> response) {
                List<responseProduct> data4  = response.body();
                bodydataarr.addAll(data4);
                productAdaopter adapter  = new productAdaopter(bodydataarr,categorySerachActivity.this);
                recyclerView.setAdapter(adapter);
                dialog.dismiss();
                searchView =findViewById(R.id.searchView);
                searchView.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        adapter.getFilterdata().filter(charSequence);
                        //    Filter filterdata = productAdaopter.getFilterdata();
                        search = charSequence;
                    }
                    @Override
                    public void afterTextChanged(Editable editable) {
                    }
                });

            }

            @Override
            public void onFailure(Call<List<responseProduct>> call, Throwable t) {

            }
        });
    }


    private void bottomNavigation() {
        LinearLayout homeBtn=findViewById(R.id.homeBtn);
        LinearLayout cartBtn=findViewById(R.id.cartBtn);
        LinearLayout profile=findViewById(R.id.profile);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(categorySerachActivity.this,MainActivity.class));
            }
        });
        floatAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(categorySerachActivity.this, CartActivity.class));
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(categorySerachActivity.this, com.example.project531.Activity.profile.class));
            }
        });
    }

}
