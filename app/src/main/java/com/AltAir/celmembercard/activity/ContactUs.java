package com.AltAir.celmembercard.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.AltAir.celmembercard.R;
import com.AltAir.celmembercard.apiServices.ApiService;
import com.AltAir.celmembercard.response.ContactResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactUs extends AppCompatActivity {

    ImageView back;
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    ProgressDialog progressDialog;
    String token,email;
    TextView btn_submit;
    EditText et_message;
    RelativeLayout rl_layout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getWindow().getContext(), R.color.black));

        //shared Pref
        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        token = loginPref.getString("token", null);
        email = loginPref.getString("email",null);
        //loading
        progressDialog = new ProgressDialog(ContactUs.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        rl_layout = findViewById(R.id.rl_layout);

        et_message = findViewById(R.id.et_message);
        btn_submit = findViewById(R.id.btn_submit);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validation()) {
                    callLoginApi(email);
                }
            }
        });
    }
    private boolean validation() {
        if (et_message.getText().toString().trim().length() == 0) {
            et_message.setError("Please Enter Your Concern");
            et_message.requestFocus();
            return false;
        }
        return true;
    }
    private void callLoginApi(String email) {
        String description = et_message.getText().toString();
        String Token = "Bearer " + token;
        Call<ContactResponse> contact_api = ApiService.apiHolders().contact_api(Token,email,description);
        contact_api.enqueue(new Callback<ContactResponse>() {
            @Override
            public void onResponse(Call<ContactResponse> call, Response<ContactResponse> response) {
                if (response.isSuccessful()){
                   // et_message.setText("");
                    progressDialog.dismiss();

                    Snackbar errorBar;
                    errorBar = Snackbar.make(rl_layout, "Information Send Successfully", Snackbar.LENGTH_LONG);
                    errorBar.setTextColor(getResources().getColor(R.color.white));
                    errorBar.setActionTextColor(getResources().getColor(R.color.white));
                    errorBar.setBackgroundTint(getResources().getColor(R.color.black));
                    errorBar.show();

                   // Toast.makeText(ContactUs.this, "Information Send Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ContactUs.this,MainActivity.class);
                    startActivity(intent);
                }
                else{
                    progressDialog.dismiss();
                    Snackbar errorBar;
                    errorBar = Snackbar.make(rl_layout, "Somethings Went Wrong..!Please Try Again", Snackbar.LENGTH_LONG);
                    errorBar.setTextColor(getResources().getColor(R.color.white));
                    errorBar.setActionTextColor(getResources().getColor(R.color.white));
                    errorBar.setBackgroundTint(getResources().getColor(R.color.black));
                    errorBar.show();
                }
            }

            @Override
            public void onFailure(Call<ContactResponse> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar errorBar;
                errorBar = Snackbar.make(rl_layout, "Somethings Went Wrong..!Please Try Again", Snackbar.LENGTH_LONG);
                errorBar.setTextColor(getResources().getColor(R.color.white));
                errorBar.setActionTextColor(getResources().getColor(R.color.white));
                errorBar.setBackgroundTint(getResources().getColor(R.color.black));
                errorBar.show();
            }
        });
    }
}