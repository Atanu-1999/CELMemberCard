package com.AltAir.celmembercard.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.material.snackbar.Snackbar;
import com.AltAir.celmembercard.R;
import com.AltAir.celmembercard.apiServices.ApiService;
import com.AltAir.celmembercard.response.ForgetpasswordResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordActivity extends AppCompatActivity {
    LinearLayout btn_reset_btn;
    ProgressDialog progressDialog;
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    EditText et_email;
    ConstraintLayout rl_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getWindow().getContext(), R.color.black));

        initi();

        btn_reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_email.getText().toString();
                if (validation()) {
                    callApI(email);
                }
            }
        });
    }

    private void callApI(String email) {
        progressDialog.show();
        Call<ForgetpasswordResponse> login_apiCall = ApiService.apiHolders().ForgetPassword(email);
        login_apiCall.enqueue(new Callback<ForgetpasswordResponse>() {
            @Override
            public void onResponse(Call<ForgetpasswordResponse> call, Response<ForgetpasswordResponse> response) {
                if (response.code() == 200) {
                    ForgetpasswordResponse userLogin_response = response.body();
                    assert response.body() != null;
//                    Toast.makeText(getApplicationContext(), "Password Successfully Send to Email ", Toast.LENGTH_SHORT).show();
                    Snackbar errorBar;
                    errorBar = Snackbar.make(rl_layout, "Password Successfully Send to Email", Snackbar.LENGTH_LONG);
                    errorBar.setTextColor(getResources().getColor(R.color.white));
                    errorBar.setActionTextColor(getResources().getColor(R.color.white));
                    errorBar.setBackgroundTint(getResources().getColor(R.color.black));
                    errorBar.show();

                    Intent intent = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                    startActivity(intent);
                    progressDialog.dismiss();

                } else if (response.code() == 401){
                    progressDialog.dismiss();
                    Snackbar errorBar;
                    errorBar = Snackbar.make(rl_layout, "Email not Matched", Snackbar.LENGTH_LONG);
                    errorBar.setTextColor(getResources().getColor(R.color.white));
                    errorBar.setActionTextColor(getResources().getColor(R.color.white));
                    errorBar.setBackgroundTint(getResources().getColor(R.color.black));
                    errorBar.show();
                   // Toast.makeText(getApplicationContext(), "Email not Matched", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ForgetpasswordResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    private boolean validation() {
        if (et_email.getText().toString().trim().length() == 0) {
            et_email.setError("Please Enter Email Id");
            et_email.requestFocus();
            return false;
        }
        return true;
    }

    private void initi() {
        rl_layout = findViewById(R.id.rl_layout);
        et_email = findViewById(R.id.et_email);
        btn_reset_btn = findViewById(R.id.btn_reset_btn);
        //loading
        progressDialog = new ProgressDialog(ForgetPasswordActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        //sharedPrefrence
        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
    }
}