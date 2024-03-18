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
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.snackbar.Snackbar;
import com.AltAir.celmembercard.R;
import com.AltAir.celmembercard.apiServices.ApiService;
import com.AltAir.celmembercard.response.ForgetpasswordResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    EditText et_old_Password,et_newPassword,et_confirmPassword;
    LinearLayout btn_reset_btn;
    String token,email;
    ImageView back;
    ConstraintLayout rl_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password2);
        rl_layout = findViewById(R.id.rl_layout);
        et_confirmPassword = findViewById(R.id.et_confirmPassword);
        et_old_Password = findViewById(R.id.et_old_Password);
        et_newPassword = findViewById(R.id.et_newPassword);
        btn_reset_btn = findViewById(R.id.btn_reset_btn);
        back = findViewById(R.id.back);
        //loading
        progressDialog = new ProgressDialog(ResetPasswordActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        //sharedPrefrence
        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        token = loginPref.getString("token", null);
        email = loginPref.getString("email",null);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getWindow().getContext(), R.color.black));

        btn_reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String old_password = et_old_Password.getText().toString();
                String newPassword = et_newPassword.getText().toString();
                String ConfirmPassword = et_confirmPassword.getText().toString();
                callResetAPi(old_password,newPassword,ConfirmPassword);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResetPasswordActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });


    }

    private void callResetAPi(String old_password, String newPassword, String confirmPassword) {
        progressDialog.show();
        String Token = "Bearer " + token;
        Call<ForgetpasswordResponse> login_apiCall = ApiService.apiHolders().ResetPassword(email,old_password,newPassword,confirmPassword);
        login_apiCall.enqueue(new Callback<ForgetpasswordResponse>() {
            @Override
            public void onResponse(Call<ForgetpasswordResponse> call, Response<ForgetpasswordResponse> response) {
                if (response.code() == 200) {
                    ForgetpasswordResponse userLogin_response = response.body();
                    assert response.body() != null;
                    Snackbar errorBar;
                    errorBar = Snackbar.make(rl_layout, "Password Change Successfully", Snackbar.LENGTH_LONG);
                    errorBar.setTextColor(getResources().getColor(R.color.white));
                    errorBar.setActionTextColor(getResources().getColor(R.color.white));
                    errorBar.setBackgroundTint(getResources().getColor(R.color.black));
                    errorBar.show();
                    //Toast.makeText(getApplicationContext(), "Password Change Successfully", Toast.LENGTH_SHORT).show();
                    editor.clear();
                    editor.apply();
                    Intent intent = new Intent(ResetPasswordActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                    progressDialog.dismiss();

                } else {
                    progressDialog.dismiss();
                    Snackbar errorBar;
                    errorBar = Snackbar.make(rl_layout, "Something Went Wrong..!Please Try Again", Snackbar.LENGTH_LONG);
                    errorBar.setTextColor(getResources().getColor(R.color.white));
                    errorBar.setActionTextColor(getResources().getColor(R.color.white));
                    errorBar.setBackgroundTint(getResources().getColor(R.color.black));
                    errorBar.show();
                   // Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ForgetpasswordResponse> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar errorBar;
                errorBar = Snackbar.make(rl_layout, "Something Went Wrong..!Please Try Again", Snackbar.LENGTH_LONG);
                errorBar.setTextColor(getResources().getColor(R.color.white));
                errorBar.setActionTextColor(getResources().getColor(R.color.white));
                errorBar.setBackgroundTint(getResources().getColor(R.color.black));
                errorBar.show();
            }
        });

    }
}