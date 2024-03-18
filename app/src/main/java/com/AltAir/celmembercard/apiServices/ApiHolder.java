package com.AltAir.celmembercard.apiServices;

import com.AltAir.celmembercard.response.ContactResponse;
import com.AltAir.celmembercard.response.ForgetpasswordResponse;
import com.AltAir.celmembercard.response.LoginResponse;
import com.AltAir.celmembercard.response.NotificationResponse;
import com.AltAir.celmembercard.response.NotificationUpdateResponse;
import com.AltAir.celmembercard.response.ProfileResponse;
import com.AltAir.celmembercard.response.SettingResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiHolder {
    @POST("login")
    @FormUrlEncoded
    Call<LoginResponse> login(@Field("email") String email,
                              @Field("password") String passWord,
                              @Field("fcm") String fcm);
    @POST("forgotpassword")
    @FormUrlEncoded
    Call<ForgetpasswordResponse> ForgetPassword(@Field("email") String email);
    @POST("resetpassword")
    @FormUrlEncoded
    Call<ForgetpasswordResponse> ResetPassword(@Field("email") String email,
                                               @Field("oldpassword") String oldpassword,
                                               @Field("newpassword") String newpassword,
                                               @Field("c_newpassword") String c_newpassword);
    @GET("membershipcard")
    Call<ProfileResponse> getProfile(@Header("Authorization") String Authorization,
                                     @Query("email") String email);
    @POST("contactus")
    @FormUrlEncoded
    Call<ContactResponse> contact_api(@Header("Authorization") String Authorization,
                                      @Field("email") String email,
                                      @Field("description") String description);
    @GET("notifications")
    Call<NotificationResponse> getNotification(@Header("Authorization") String Authorization);
    @POST("readnotification")
    @FormUrlEncoded
    Call<NotificationUpdateResponse> updateNotification(@Header("Authorization") String Authorization,
                                                        @Field("id") int id);
    @GET("settings")
    Call<SettingResponse> setting_maint(@Query("key") String id);
}
