package com.AltAir.celmembercard.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.AltAir.celmembercard.R;
import com.AltAir.celmembercard.apiServices.ApiService;
import com.AltAir.celmembercard.response.SettingResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    SharedPreferences loginPref2,loginPref;
    SharedPreferences.Editor editor2,editor;
    String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        loginPref2 = getSharedPreferences("login_pref2", Context.MODE_PRIVATE);
        editor2 = loginPref2.edit();

        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        token = loginPref.getString("token", null);
        callSetting();
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getWindow().getContext(), R.color.black));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (token!=null)
                {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
                else
                {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            }
        },3000);
    }
    private void callSetting() {
        Call<SettingResponse> profile_apiCall = ApiService.apiHolders().setting_maint("1");
        profile_apiCall.enqueue(new Callback<SettingResponse>() {
            @Override
            public void onResponse(Call<SettingResponse> call, Response<SettingResponse> response) {
                if (response.code() == 401) {

                } else {
                    if (response.isSuccessful()) {
                        SettingResponse settingResponse = response.body();
                        assert settingResponse != null;
                        String maintenance = settingResponse.getResult().getMaintenance();
                        String version = settingResponse.getResult().getVersion();
                        editor2.putString("maintenance", maintenance);
                        editor2.putString("version", version);
                        editor2.commit();
                    } else {
                        Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SettingResponse> call, Throwable t) {
                Toast.makeText(SplashActivity.this, "" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}