package com.example.project531.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.project531.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class profile extends AppCompatActivity {

    private TextView loginBtn, my_order;
    private FloatingActionButton floatAction;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        LinearLayout homeBtn=findViewById(R.id.homeBtn);
        LinearLayout profile=findViewById(R.id.profile);
        loginBtn =findViewById(R.id.login_btn);
        my_order =findViewById(R.id.my_order);

        floatAction = findViewById(R.id.button_action);;

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(profile.this,MainActivity.class));
            }
        });

        floatAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(profile.this,CartActivity.class));
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(profile.this,profile.class));
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(profile.this,Login.class));
            }
        });
        my_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(profile.this,my_order.class));
            }
        });

    }
}