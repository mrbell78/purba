package com.example.project531.Activity;
import static android.content.ContentValues.TAG;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project531.Helper.DataModel;
import com.example.project531.Helper.ManagementCart;
import com.example.project531.Helper.SessionManager;
import com.example.project531.Helper.TinyDB;
import com.example.project531.adapter.DrawerItemCustomAdapter;
import com.example.project531.adapter.productAdaopter;
import com.example.project531.R;
import com.example.project531.apicall.Api;
import com.example.project531.responsedata.responseCategory;
import com.example.project531.responsedata.responseProduct;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private RecyclerView recyclerView, categoryview, recyclerView2;
    private TextView action_cart, category_id, count_item;
    private FloatingActionButton floatAction;
    private EditText searchView;
    private TinyDB tinyDB;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    private ActivityMainBinding binding;
    List<responseProduct> bodydataarr;
    List<responseProduct> count;
    List<responseCategory> bodydataarr2;
    List<responseProduct> Categoryserach;
    CharSequence search="";
    ProgressDialog dialog;
    ManagementCart var;
    private String[] mNavigationDrawerItemTitles;
    private ListView mDrawerList;
    private ManagementCart managementCart;
    Toolbar toolbar;
    SessionManager sessionManager;
    CartActivity countdata;
    ManagementCart mngCarddata;
    BadgeDrawable badgeDrawable;





    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            int  totalsize = intent.getIntExtra("quantity",0);

            Toast.makeText(MainActivity.this,"the data size is=="+totalsize ,Toast.LENGTH_SHORT).show();
        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*sessionManager = new  SessionManager(this);
        sessionManager.checkLogin();
        HashMap<String, String> customer = sessionManager.getUserDetails();
        String phone =customer.get(sessionManager.KEY_PHONE);
        String email =customer.get(sessionManager.KEY_EMAIL);*/
        drawerLayout = findViewById(R.id.drawer_layout);
      // NavigationView navigationView = findViewById(R.id.app_bar_main);
       // navigationView.setNavigationItemSelectedListener(this);
        floatAction = findViewById(R.id.button_action);;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar ,  R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
     //   getSupportActionBar().setDisplayShowHomeEnabled(true);
        bottomNavigation();
        dialog= new ProgressDialog(MainActivity.this);
        dialog.setContentView(R.layout.progressbar_dialog);
       // dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.addContentView(new ProgressBar(MainActivity.this), new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
      /*daynamic menu*/
        countdata=new CartActivity();
        mNavigationDrawerItemTitles= getResources().getStringArray(R.array.navigation_drawer_items_array);
        categoryview =  findViewById(R.id.left_drawer);
        categoryview.hasFixedSize();
        categoryview.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        /*daynamic menu end*/
        recyclerView = findViewById(R.id.view2);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        bodydataarr=new ArrayList<>();
        bodydataarr2=new ArrayList<>();

        mngCarddata= new ManagementCart(this);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));

        Categoryserach=new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dmcbproject.com/dairy_api/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api apicallingjson = retrofit.create(Api.class);
        Call<List<responseProduct>> callcoment= apicallingjson.get_product_list();
      //  Call<List2<responseCategory>> callcoment2= apicallingjson.get_category_list();
        dialog.show();
        callcoment.enqueue(new Callback<List<responseProduct>>() {
            @Override
            public void onResponse(Call<List<responseProduct>> call, Response<List<responseProduct>> response) {
                List<responseProduct> data  = response.body();
                bodydataarr.clear();
                bodydataarr.addAll( response.body());
                productAdaopter adapter  = new productAdaopter(bodydataarr,MainActivity.this);
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
                Log.d(TAG, "Error");
            }
        });
        /*category*/
        Call<List<responseCategory>> category= apicallingjson.get_category_list();
        category.enqueue(new Callback<List<responseCategory>>() {
            @Override
            public void onResponse(Call<List<responseCategory>> call, Response<List<responseCategory>> response) {
                List<responseCategory> data2  = response.body();
                Log.d(TAG, "onResponse: the value is "+data2.size());
                bodydataarr2.addAll(data2);
                DrawerItemCustomAdapter adapter2 = new DrawerItemCustomAdapter(bodydataarr2,MainActivity.this);
                categoryview.setAdapter(adapter2);
            }
            @Override
            public void onFailure(Call<List<responseCategory>> call, Throwable t) {

            }
        });
        /*category on click */
        String getInvenCode = getIntent().getStringExtra("getInvenCode");
        Retrofit retrofit_textview = new Retrofit.Builder()
                .baseUrl("https://dmcbproject.com/dairy_api/api/product/"+getInvenCode+"/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api apicallingjson2 = retrofit_textview.create(Api.class);
        Call<List<responseProduct>> searchapi_textview= apicallingjson2.get_product_list();
        searchapi_textview.enqueue(new Callback<List<responseProduct>>() {
            @Override
            public void onResponse(Call<List<responseProduct>> call, Response<List<responseProduct>> response) {
                List<responseProduct> data4 =new ArrayList<>();
                bodydataarr.clear();
                data4.clear();
                data4.addAll( response.body());
                productAdaopter adapter  = new productAdaopter(data4,MainActivity.this);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<List<responseProduct>> call, Throwable t) {
            }
        });
    }
    /*daymanic dwaer menu*/

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    private void bottomNavigation() {

        LinearLayout homeBtn=findViewById(R.id.homeBtn);
        LinearLayout cartBtn=findViewById(R.id.cartBtn);
        LinearLayout profile=findViewById(R.id.profile);
        floatAction.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressLint("UnsafeExperimentalUsageError")
            @Override
            public void onGlobalLayout() {

               badgeDrawable = BadgeDrawable.create(MainActivity.this);

                badgeDrawable.setBadgeGravity(BadgeDrawable.TOP_END);
                badgeDrawable.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                badgeDrawable.setBadgeTextColor(getResources().getColor(R.color.white));
                badgeDrawable.setNumber(mngCarddata.getListCart().size());
                BadgeUtils.attachBadgeDrawable(badgeDrawable, floatAction, findViewById(R.id.layout));
                floatAction.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MainActivity.class));
            }
        });
        floatAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,CartActivity.class));
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,profile.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater menuInflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.top_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        action_cart =findViewById(R.id.action_settings);
        switch (item.getItemId()){
            case R.id.action_settings:
             //   logout();
                break;
            default:
                return super.onContextItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }







}