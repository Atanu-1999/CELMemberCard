package com.AltAir.celmembercard.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.AltAir.celmembercard.R;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import com.AltAir.celmembercard.apiServices.ApiService;
import com.AltAir.celmembercard.response.NotificationResponse;
import com.AltAir.celmembercard.response.ProfileResponse;
import com.AltAir.celmembercard.utils.Internet_Check;

import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ImageView iv_QR, notification;
    Dialog dialog;
    LinearLayout resetPassword, ll_logOut, about_layout, privacy_layout, tearms_layout, contact_logout;
    SharedPreferences loginPref,loginPref2;
    SharedPreferences.Editor editor,editor2;
    ProgressDialog progressDialog;
    String token, email;
    TextView tv_memberName, tv_dob, tv_fromDate, tv_tillDate, tv_cardNo, txt_profile_name, tv_email, tv_count,tv_cardType;
    CircleImageView iv_image;
    ImageView iv_qr;
    String qr;
    private Dialog noInternetDialog;
    TextView tv_generate;
    String urlWithData;
    String yourUrl = "https://pemindia.in/celmembercard/member-card?";
    int id;
    ImageView rr_card;
    String cardUrl = "https://pemindia.in/celmembercard/public/uploads";
    List<NotificationResponse.Datum> notifications;
    RelativeLayout rl_layout;
    String maintenance,version,versionName,cardType;
    CircleImageView card_type;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getWindow().getContext(), R.color.black));

        tv_cardType = findViewById(R.id.tv_cardType);
        card_type = findViewById(R.id.card_type);
        rl_layout = findViewById(R.id.rl_layout);
        rr_card = findViewById(R.id.rr_card);
        notification = findViewById(R.id.notification);
        tv_generate = findViewById(R.id.tv_generate);
        iv_QR = findViewById(R.id.iv_QR);
        ll_logOut = findViewById(R.id.ll_logOut);
        tv_memberName = findViewById(R.id.tv_memberName);
        tv_dob = findViewById(R.id.tv_dob);
        tv_fromDate = findViewById(R.id.tv_fromDate);
        tv_tillDate = findViewById(R.id.tv_tillDate);
        tv_cardNo = findViewById(R.id.tv_cardNo);
        txt_profile_name = findViewById(R.id.txt_profile_name);
        tv_email = findViewById(R.id.tv_email);
        iv_image = findViewById(R.id.iv_image);
        iv_qr = findViewById(R.id.iv_qr);
        tv_count = findViewById(R.id.tv_count);

        about_layout = findViewById(R.id.about_layout);
        privacy_layout = findViewById(R.id.privacy_layout);
        tearms_layout = findViewById(R.id.tearms_layout);
        contact_logout = findViewById(R.id.contact_logout);

        //shared Pref
        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();

        loginPref2 = getSharedPreferences("login_pref2", Context.MODE_PRIVATE);
        editor2 = loginPref2.edit();

        maintenance = loginPref2.getString("maintenance", null);
        version = loginPref2.getString("version", null);

        token = loginPref.getString("token", null);
        email = loginPref.getString("email", null);
        //loading
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        resetPassword = findViewById(R.id.resetPassword);
        dialog = new Dialog(MainActivity.this);

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

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Notification.class));
            }
        });
        iv_QR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zoomQR();
            }
        });
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ResetPasswordActivity.class);
                startActivity(intent);
            }
        });
        ll_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // session_dismiss();
                LogOut();
            }
        });
        tearms_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("page", "1");
                //bundle.putString("urls", "https://pemindia.in/celmembercard/terms-of-service"+"?cardType=" + cardType);
                bundle.putString("urls", "https://pemindia.in/celmembercard/terms-of-service"+"?userid=" + id);
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);

                startActivity(intent);
            }
        });
        about_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("page", "2");
                bundle.putString("urls", "https://pemindia.in/celmembercard/about-card");
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        privacy_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("page", "3");
                bundle.putString("urls", "https://pemindia.in/celmembercard/privacy-policy" + "?cardType=" + cardType); //https://pemindia.in/celmembercard/privacy-policy?cardType=Gold
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        contact_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ContactUs.class);
                startActivity(intent);
            }
        });

        if (!Internet_Check.isInternetAvailable(MainActivity.this)) {
            noInternetDialog = new Dialog(MainActivity.this);
            noInternetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            noInternetDialog.setContentView(R.layout.no_internet_layout);
            noInternetDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            noInternetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            noInternetDialog.setCancelable(false);

            TextView retryButton = noInternetDialog.findViewById(R.id.retry_button);
            retryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Internet_Check.isInternetAvailable(MainActivity.this)) {
                        noInternetDialog.dismiss();
                        // callApi();
                    }
                }
            });
            noInternetDialog.show();
            noInternetDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            noInternetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        } else {
            callApi();
            callNotificationAPi(token);
        }

        tv_generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlWithData = yourUrl + "userid=" + id;
                Bundle bundle1 = new Bundle();
                bundle1.putString("urls", urlWithData);
                Intent intent = new Intent(MainActivity.this, PDFActivity.class);
                intent.putExtras(bundle1);
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

    private void callApi() {
        progressDialog.show();
        String Token = "Bearer " + token;
        Call<ProfileResponse> login_apiCall = ApiService.apiHolders().getProfile(Token, email);
        login_apiCall.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.code() == 200) {
                    ProfileResponse profileResponse = response.body();
                    assert profileResponse != null;
                    assert response.body() != null;
                    assert profileResponse != null;
                    id = profileResponse.getData().getId();
                    tv_memberName.setText(profileResponse.getData().getName());
                    tv_dob.setText(profileResponse.getData().getDob());
                    tv_fromDate.setText(profileResponse.getData().getDateofissue());
                    tv_tillDate.setText(profileResponse.getData().getValidity());
                    tv_cardNo.setText(profileResponse.getData().getCardnumber());

                    cardType = profileResponse.getData().getCardType();
                    tv_cardType.setText(profileResponse.getData().getCardType());

                    txt_profile_name.setText(profileResponse.getData().getName());
                    tv_email.setText(profileResponse.getData().getEmail());

                    String profile = response.body().getData().getProfile();
                    if (profile!=null) {
                        Picasso.with(getApplicationContext())
                                .load(cardUrl + profile)
                                .into(iv_image);
                        Picasso.with(getApplicationContext())
                                .load(cardUrl + profile)
                                .into(card_type);
                    }else {
                        Picasso.with(getApplicationContext())
                                .load(R.drawable.img)
                                .into(iv_image);
                        Picasso.with(getApplicationContext())
                                .load(R.drawable.img)
                                .into(card_type);
                    }

//                    String cardTypeIcon = response.body().getData().getCardicon();
//                    if (cardTypeIcon != null) {
//                        Picasso.with(getApplicationContext())
//                                .load(cardUrl + cardTypeIcon)
//                                .into(card_type);
//                    }

                    qr = response.body().getData().getQr();
                    if (qr != null) {
                        Picasso.with(getApplicationContext())
                                .load(qr)
                                .into(iv_qr);
                        Picasso.with(getApplicationContext())
                                .load(qr)
                                .into(iv_QR);
                    }
                    String cardImage = response.body().getData().getCardimage();
                    if (cardImage != null) {
                        Picasso.with(getApplicationContext())
                                .load(cardUrl + cardImage)
                                .into(rr_card);
                    }
                    progressDialog.dismiss();
                    // }
                } else if (response.code() == 401) {
                    Snackbar errorBar;
                    errorBar = Snackbar.make(rl_layout, "LogOut Successfully...! Some One Using this Credentials LogIn", Snackbar.LENGTH_LONG);
                    errorBar.setTextColor(getResources().getColor(R.color.white));
                    errorBar.setActionTextColor(getResources().getColor(R.color.white));
                    errorBar.setBackgroundTint(getResources().getColor(R.color.black));
                    errorBar.show();
                    editor.clear();
                    editor.apply();
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();

                } else if (response.code() == 403) {
                    noInternetDialog = new Dialog(MainActivity.this);
                    noInternetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    noInternetDialog.setContentView(R.layout.pending_layout);
                    noInternetDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    noInternetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    noInternetDialog.setCancelable(false);
                    noInternetDialog.show();
                    noInternetDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                    noInternetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                } else {
                    progressDialog.dismiss();
                    //Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                    Snackbar errorBar;
                    errorBar = Snackbar.make(rl_layout, "Somethings Went Wrong...! Please Try Again After SomeTime", Snackbar.LENGTH_LONG);
                    errorBar.setTextColor(getResources().getColor(R.color.white));
                    errorBar.setActionTextColor(getResources().getColor(R.color.white));
                    errorBar.setBackgroundTint(getResources().getColor(R.color.black));
                    errorBar.show();
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar errorBar;
                errorBar = Snackbar.make(rl_layout, "Somethings Went Wrong...! Please Try Again After SomeTime", Snackbar.LENGTH_LONG);
                errorBar.setTextColor(getResources().getColor(R.color.white));
                errorBar.setActionTextColor(getResources().getColor(R.color.white));
                errorBar.setBackgroundTint(getResources().getColor(R.color.black));
                errorBar.show();
            }
        });
    }

    private void session_dismiss() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Are You Sure ?")
                .setMessage("You want to logout!")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editor.clear();
                        editor.apply();
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // If the user chooses not to log out, simply dismiss the dialog
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void zoomQR() {
        dialog.setContentView(R.layout.customqr);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        TextView txt_cancel = dialog.findViewById(R.id.txt_cancel);
        TextView txt_submit = dialog.findViewById(R.id.txt_submit);
        ImageView iv_qrImage = dialog.findViewById(R.id.iv_qrImage);
        dialog.show();

        if (qr != null) {
            Picasso.with(getApplicationContext())
                    .load(qr)
                    .into(iv_qrImage);
        }
        txt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        txt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        callApi();
        callNotificationAPi(token);
    }

    private void LogOut() {
        noInternetDialog = new Dialog(MainActivity.this);
        noInternetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        noInternetDialog.setContentView(R.layout.logout_layout1);
        noInternetDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        noInternetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        noInternetDialog.show();
        TextView et_yes = (TextView) noInternetDialog.findViewById(R.id.et_yes);
        TextView et_cancel = (TextView) noInternetDialog.findViewById(R.id.et_cancel);
        et_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.clear();
                editor.apply();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
        et_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noInternetDialog.dismiss();
            }
        });
        noInternetDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        noInternetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
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
                    notifications = response.body().getData();
                    int count = 0;
                    // tv_count.setText(response.body().getData().get());
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        String read = response.body().getData().get(i).getReadStatus();
                        if (Objects.equals(read, "N")) {
                            count++;
                        }
                    }
                    String counts = String.valueOf(count);
                    tv_count.setText(counts);
                    progressDialog.dismiss();
                } else {
                    progressDialog.dismiss();
                    // ll_view.setVisibility(View.VISIBLE);
                    // Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {
                //  ll_view.setVisibility(View.VISIBLE);
                //Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}