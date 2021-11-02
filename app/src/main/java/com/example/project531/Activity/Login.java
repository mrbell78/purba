package com.example.project531.Activity;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project531.R;
import com.example.project531.apicall.Api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {
    private TextView butn_registion;
    private EditText edt_email, edt_password;
    private Button btn_login;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        butn_registion =findViewById(R.id.butn_registion);

        butn_registion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,registration.class));
            }
        });

        edt_email=findViewById(R.id.email_phone);
        edt_password=findViewById(R.id.pass);
        btn_login=findViewById(R.id.btn_login);

        dialog= new ProgressDialog(Login.this);
        dialog.setContentView(R.layout.progressbar_dialog);
        //dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.addContentView(new ProgressBar(Login.this), new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dmcbproject.com/dairy_api/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api apicallingjson = retrofit.create(Api.class);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edt_email.getText().toString().isEmpty()){
                    edt_email.setError("this field can't be empty");
                }
                if(edt_password.getText().toString().isEmpty()){
                    edt_password.setError("this field can't be empty");
                }
                if(!edt_password.getText().toString().isEmpty() && !edt_email.getText().toString().isEmpty()){
                    Log.d(TAG, "onClick: email "+edt_email.getText().toString());
                    Log.d(TAG, "onClick: pass "+edt_password.getText().toString());
                    dialog.show();
                    Call<ResponseBody> login = apicallingjson.loginUser(edt_email.getText().toString(),edt_password.getText().toString());
                    login.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                            if(response.code()==200){
                                Toast.makeText(Login.this, "Login Successful ", Toast.LENGTH_SHORT).show();
                                SharedPreferences.Editor editor = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE).edit();
                                editor.putString("login", "login_success");
                                editor.apply();
                                Intent intent  = new Intent(getApplicationContext(),MainActivity.class);
                                intent.putExtra("phone", String.valueOf(edt_email));
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                dialog.dismiss();
                            }else{
                                Toast.makeText(Login.this, "Wrong Email or password", Toast.LENGTH_SHORT).show();
                                edt_email.setError("error");
                                edt_password.setError("error");
                                dialog.dismiss();
                            }
                        }
                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                        }
                    });
                }
            }
        });

    }



}