package com.example.project531.Activity;
import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.project531.Domain.FoodDomain;
import com.example.project531.Helper.ManagementCart;
import com.example.project531.R;
import com.example.project531.responsedata.responseProduct;
import com.squareup.picasso.Picasso;

public class ShowDetailActivity extends AppCompatActivity {
    private TextView addToCartBtn;
    private TextView titleTxt, feeTxt, descriptionTxt, numberOrderTxt, totalPriceTxt, starTxt, caloryTxt, timeTxt;
    private ImageView plusBtn, minusBtn, picFood;
    private responseProduct object;
    private int numberOrder = 1;
    private ManagementCart managementCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);
        managementCart = new ManagementCart(this);
        iniView();
        getBundle();
    }

    private void getBundle() {
        object = (responseProduct) getIntent().getSerializableExtra("object");
        Picasso.with(this)
                .load(object.getImageFile())
                .into(picFood);
        titleTxt.setText(object.getPro_name());
        feeTxt.setText("Tk " + object.getSalePrice());
        descriptionTxt.setText(object.getDescription());
        numberOrderTxt.setText(String.valueOf(numberOrder));
        totalPriceTxt.setText(""+Math.round(numberOrder * object.getSalePrice()));

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOrder = numberOrder + 1;
                numberOrderTxt.setText(String.valueOf(numberOrder));
                totalPriceTxt.setText(""+Math.round(numberOrder * object.getSalePrice()));
            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOrder > 1) {
                    numberOrder = numberOrder - 1;
                }
                numberOrderTxt.setText(String.valueOf(numberOrder));
                totalPriceTxt.setText(""+Math.round(numberOrder * object.getSalePrice()));
            }
        });

       addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             object.setNumberInCart(numberOrder);
              managementCart.insertFood(object);
                Log.d(TAG, "onResponse: the value is "+object);
                startActivity(new Intent(ShowDetailActivity.this,CartActivity.class));
            }
        });

    }
    private void iniView() {
        addToCartBtn = findViewById(R.id.addToCartBtn);
        titleTxt = findViewById(R.id.titleTxt);
        feeTxt = findViewById(R.id.priceTxt);
        descriptionTxt = findViewById(R.id.descriptionTxt);
        numberOrderTxt = findViewById(R.id.numberItemTxt);
        plusBtn = findViewById(R.id.plusCardBtn);
        minusBtn = findViewById(R.id.minusCardBtn);
        picFood = findViewById(R.id.foodPic);
        totalPriceTxt = findViewById(R.id.totalPriceTxt);
    }
}