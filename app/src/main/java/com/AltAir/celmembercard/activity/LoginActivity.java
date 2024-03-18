package com.AltAir.celmembercard.activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.messaging.FirebaseMessaging;
import com.AltAir.celmembercard.utils.DeviceUtils;
import com.AltAir.celmembercard.R;
import com.AltAir.celmembercard.apiServices.ApiService;
import com.AltAir.celmembercard.response.LoginResponse;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    LinearLayout btn_login;
    TextView tv_reset;
    ProgressDialog progressDialog;
    SharedPreferences loginPref,loginPref2;
    SharedPreferences.Editor editor,editor2;
    EditText et_email, et_password;
    String deviceId,fb_token,maintenance,version,versionName;
    private static final int NOTIFICATION_PERMISSION_REQUEST_CODE = 123;
    ConstraintLayout rl_layout;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initi();
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getWindow().getContext(), R.color.black));

        // Get the package manager instance
        PackageManager packageManager = getPackageManager();

        try {
            // Get the package information
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);

            // Retrieve the version information
            versionName = packageInfo.versionName;
            // int versionCode = packageInfo.versionCode;


        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (!Objects.equals(maintenance, "false") || !Objects.equals(version, versionName)){
            //popUpMaintencae(maintenance,version);
        }

        if (!isNotificationPermissionGranted()) {
            // Request notification permission
            requestNotificationPermission();
        }

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        // Get new FCM registration token
                        fb_token = task.getResult();
                        // Log and toast
//                        String msg = getString(R.string.next, token);
                        Log.d(TAG, fb_token);
//                        Toast.makeText(OTP_Verify.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_email.getText().toString();
                String passWord = et_password.getText().toString();
                if (validation()) {
                    callLoginApi(email, passWord);
                }
            }
        });
        tv_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });

    }

    private void popUpMaintencae(String maintenance, String version) {
        dialog.setContentView(R.layout.maintance);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        TextView tv_version = dialog.findViewById(R.id.tv_version);
        TextView tv_update = dialog.findViewById(R.id.tv_update);
        dialog.show();
        if (maintenance.equals("true")){
            tv_version.setText("Under Maintenance");
            tv_update.setVisibility(View.GONE);
        }
        else {
            tv_version.setText("New Version Available");
        }
        tv_update .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.choicest.kitchen"));
//                startActivity(intent);
            }
        });
    }

    private void callLoginApi(String email, String passWord) {
        progressDialog.show();
        Call<LoginResponse> login_apiCall = ApiService.apiHolders().login(email, passWord,fb_token);
        login_apiCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getCode().equals("200")){
                        LoginResponse userLogin_response = response.body();
                        assert response.body() != null;
                        String token = response.body().getToken();
                        editor.putString("token", token);
                        editor.putString("email",email);
                        // editor.putString("fcm",fb_token);
                        editor.commit();

                        Snackbar errorBar;
                        errorBar = Snackbar.make(rl_layout, "Successfully Login", Snackbar.LENGTH_LONG);
                        errorBar.setTextColor(getResources().getColor(R.color.white));
                        errorBar.setActionTextColor(getResources().getColor(R.color.white));
                        errorBar.setBackgroundTint(getResources().getColor(R.color.black));
                        errorBar.show();

//                        Toast.makeText(getApplicationContext(), "Successfully Login", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                        et_email.setText("");
                        et_password.setText("");
                        progressDialog.dismiss();
                    } else if (response.body().getCode().equals("401") ) {
                        Snackbar errorBar;
                        errorBar = Snackbar.make(rl_layout, "Check Email Or Password", Snackbar.LENGTH_LONG);
                        errorBar.setTextColor(getResources().getColor(R.color.white));
                        errorBar.setActionTextColor(getResources().getColor(R.color.white));
                        errorBar.setBackgroundTint(getResources().getColor(R.color.black));
                        errorBar.show();

                       // Toast.makeText(LoginActivity.this, "Check Email Or Password", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                    else {
                        Snackbar errorBar;
                        errorBar = Snackbar.make(rl_layout, "Something Went Wrong Please Try Again After Sometime", Snackbar.LENGTH_LONG);
                        errorBar.setTextColor(getResources().getColor(R.color.white));
                        errorBar.setActionTextColor(getResources().getColor(R.color.white));
                        errorBar.setBackgroundTint(getResources().getColor(R.color.black));
                        errorBar.show();

                       // Toast.makeText(LoginActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                } else {
                    progressDialog.dismiss();
                    Snackbar errorBar;
                    errorBar = Snackbar.make(rl_layout, "Something Went Wrong Please Try Again After Sometime", Snackbar.LENGTH_LONG);
                    errorBar.setTextColor(getResources().getColor(R.color.white));
                    errorBar.setActionTextColor(getResources().getColor(R.color.white));
                    errorBar.setBackgroundTint(getResources().getColor(R.color.black));
                    errorBar.show();
                    //Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar errorBar;
                errorBar = Snackbar.make(rl_layout, "Something Went Wrong Please Try Again After Sometime", Snackbar.LENGTH_LONG);
                errorBar.setTextColor(getResources().getColor(R.color.white));
                errorBar.setActionTextColor(getResources().getColor(R.color.white));
                errorBar.setBackgroundTint(getResources().getColor(R.color.black));
                errorBar.show();
            }
        });
    }

    private void initi() {
        dialog = new Dialog(LoginActivity.this);
        deviceId = DeviceUtils.getDeviceId(getApplicationContext());
        btn_login = findViewById(R.id.btn_login);
        tv_reset = findViewById(R.id.tv_reset);
        et_password = findViewById(R.id.et_password);
        et_email = findViewById(R.id.et_email);
        rl_layout = findViewById(R.id.rl_layout);
        //loading
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        //sharedPrefrence
        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();

        loginPref2 = getSharedPreferences("login_pref2", Context.MODE_PRIVATE);
        editor2 = loginPref2.edit();

        maintenance = loginPref2.getString("maintenance", null);
        version = loginPref2.getString("version", null);
    }

    private boolean validation() {
        if (et_email.getText().toString().trim().length() == 0) {
            et_email.setError("Please Enter Email Id");
            et_email.requestFocus();
            return false;
        } else if (et_password.getText().toString().trim().length() == 0) {
            et_password.setError("Please Enter Password");
            et_password.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }

    private boolean isNotificationPermissionGranted() {
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        return notificationManager.areNotificationsEnabled();
    }

    private void requestNotificationPermission() {
        Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                .putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName())
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivityForResult(intent, NOTIFICATION_PERMISSION_REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE) {
            // Check if the user granted notification permission
            if (isNotificationPermissionGranted()) {
                // Permission granted, proceed with your app logic
            } else {
                // Permission not granted, handle accordingly
            }
        }
    }
}