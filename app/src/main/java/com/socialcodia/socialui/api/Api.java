package com.socialcodia.socialui.api;

import com.socialcodia.socialui.model.DefaultResponse;
import com.socialcodia.socialui.model.LoginResponse;
import com.socialcodia.socialui.model.ResponseFeed;
import com.socialcodia.socialui.model.ResponseUser;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("createUser")
    Call<DefaultResponse> createUser(
        @Field("name") String name,
        @Field("username") String username,
        @Field("email") String email,
        @Field("password") String password
    );

    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("forgotPassword")
    Call<DefaultResponse> forgotPassword(
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("resetPassword")
    Call<DefaultResponse> resetPassword(
            @Field("email") String email,
            @Field("otp") int otp,
            @Field("newPassword") String password
    );

    @FormUrlEncoded
    @POST("updatePassword")
    Call<DefaultResponse> updatePassword(
            @Header("token") String token,
            @Field("password") String password,
            @Field("newpassword") String newPassword
    );

    @FormUrlEncoded
    @POST("sendEmailVerfication")
    Call<DefaultResponse> sendEmailVerfication(
            @Field("email") String email
    );

    @GET("users")
    Call<ResponseUser> users(
            @Header("token") String token
    );

    @GET("feeds")
    Call<ResponseFeed> getFeeds(
            @Header("token") String token
    );

    @FormUrlEncoded
    @POST("likeFeed")
    Call<DefaultResponse>  doLike(
            @Header("token")  String token,
            @Field("feedId") int feedId
    );

    @FormUrlEncoded
    @POST("unlikeFeed")
    Call<DefaultResponse> doDislike(
            @Header("token") String token,
            @Field("feedId") int feedId
    );


}
