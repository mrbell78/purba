package com.example.project531.Activity;
import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
/*import com.example.project531.adapter.CartListAdapter;*/
import com.example.project531.Helper.ManagementCart;
import com.example.project531.Interface.ChangeNumberItemsListener;
import com.example.project531.R;
import com.example.project531.adapter.CartListAdapter;
import com.example.project531.checkout_activity;

public class CartActivity extends AppCompatActivity implements CartListAdapter.OnItemNumber {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private ManagementCart managementCart;
    private TextView totalFeeTxt, taxTxt, deliveryTxt, totalTxt, emptyTxt, CheckOut;
    private double tax;
    private ScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        managementCart = new ManagementCart(this);
        initView();
        initList();
        bottomNavigation();
        calculateCard();
        CheckOut =  findViewById(R.id.CheckOut);
        CheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartActivity.this, checkout_activity.class));
            }
        });
    }
    private void bottomNavigation() {
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout cartBtn = findViewById(R.id.cartBtn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartActivity.this, MainActivity.class));
            }
        });
        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartActivity.this, CartActivity.class));
            }
        });
    }

    public int  getcount(){
        return  managementCart.getListCart().size();
    }

  private void initList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        adapter = new CartListAdapter(managementCart.getListCart(), this, new ChangeNumberItemsListener() {
            @Override
            public void changed() {
                calculateCard();
            }
        });
        recyclerViewList.setAdapter(adapter);
        if (managementCart.getListCart().isEmpty()) {
            emptyTxt.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        } else {
            emptyTxt.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }
    }

  private void calculateCard() {
      int delivery = Integer.parseInt(String.valueOf(80));     //you can change this item you need price for delivery
     // int total = (managementCart.getTotalFee());
      int total = (int) (Math.round((managementCart.getTotalFee() + delivery) * 100.0) / 100.0);
      int itemTotal = (int) (Math.round(managementCart.getTotalFee() * 100.0) / 100.0);
      totalFeeTxt.setText("TK " + itemTotal);
      deliveryTxt.setText("TK " + delivery);
      totalTxt.setText("TK " + total);
    }

    private void initView() {
        totalFeeTxt = findViewById(R.id.totalFeeTxt);
        deliveryTxt = findViewById(R.id.deliveryTxt);
        totalTxt = findViewById(R.id.totalTxt);
        recyclerViewList = findViewById(R.id.view);
        scrollView = findViewById(R.id.scrollView);
        emptyTxt = findViewById(R.id.emptyTxt);
    }

    @Override
    public void dataSize(Intent intent) {
        Log.d(TAG, "dataSize: the data is "+intent.getIntExtra("quantity",0));
    }
}