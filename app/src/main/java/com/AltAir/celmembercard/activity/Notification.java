package com.AltAir.celmembercard.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.AltAir.celmembercard.R;
import com.AltAir.celmembercard.adapter.NotificationAdapter;
import com.AltAir.celmembercard.apiServices.ApiService;
import com.AltAir.celmembercard.listener.NotificationListener;
import com.AltAir.celmembercard.response.NotificationResponse;
import com.AltAir.celmembercard.response.NotificationUpdateResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Notification extends AppCompatActivity {
    ImageView back;
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    ProgressDialog progressDialog;
    String token, email;
    NotificationAdapter notificationAdapter;
    List<NotificationResponse.Datum> notification;
    RecyclerView rv_notification;
    LinearLayout ll_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getWindow().getContext(), R.color.black));

        back = findViewById(R.id.back);
        ll_view = findViewById(R.id.ll_view);
        rv_notification = findViewById(R.id.rv_notification);
        //shared Pref
        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        token = loginPref.getString("token", null);
        email = loginPref.getString("email", null);
        //loading
        progressDialog = new ProgressDialog(Notification.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        callNotificationAPi(token);
    }

    private void callNotificationAPi(String token) {
        progressDialog.show();
        String Token = "Bearer " + token;
        Call<NotificationResponse> login_apiCall = ApiService.apiHolders().getNotification(Token);
        login_apiCall.enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    notification = response.body().getData();
                    notificationAdapter = new NotificationAdapter(getApplicationContext(), notification, new NotificationListener() {
                        @Override
                        public void onItemClickedItem(NotificationResponse.Datum item, int position) {
                            int id = item.getId();
                            callUpdateApi(id);
                        }
                    });
                    rv_notification.setAdapter(notificationAdapter);
                    rv_notification.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    progressDialog.dismiss();
                } else {
                    progressDialog.dismiss();
                    ll_view.setVisibility(View.VISIBLE);
                   // Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {
                ll_view.setVisibility(View.VISIBLE);
                //Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void callUpdateApi(int id) {
        progressDialog.show();
        String Token = "Bearer " + token;
        Call<NotificationUpdateResponse> login_apiCall = ApiService.apiHolders().updateNotification(Token,id);
        login_apiCall.enqueue(new Callback<NotificationUpdateResponse>() {
            @Override
            public void onResponse(Call<NotificationUpdateResponse> call, Response<NotificationUpdateResponse> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                   // Toast.makeText(getApplicationContext(), "Check", Toast.LENGTH_SHORT).show();
                    callNotificationAPi(token);
                    progressDialog.dismiss();
                } else {
                    progressDialog.dismiss();
                    //Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NotificationUpdateResponse> call, Throwable t) {
               // Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}