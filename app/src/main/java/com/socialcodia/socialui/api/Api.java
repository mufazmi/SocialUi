package com.socialcodia.socialui.api;

import com.socialcodia.socialui.model.DefaultResponse;
import com.socialcodia.socialui.model.LoginResponse;
import com.socialcodia.socialui.model.ResponseFeed;
import com.socialcodia.socialui.model.ResponseFeeds;
import com.socialcodia.socialui.model.ResponseUser;
import com.socialcodia.socialui.model.ResponseUsers;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

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
    Call<ResponseUsers> users(
            @Header("token") String token
    );

    @GET("feeds")
    Call<ResponseFeeds> getFeeds(
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

    @FormUrlEncoded
    @POST("deleteFeed")
    Call<DefaultResponse> deleteFeed(
            @Header("token") String token,
            @Field("id") String feedId
    );

    @GET("{username}/feeds")
    Call<ResponseFeeds> getUserFeeds(
            @Path("username") String username,
            @Header("token") String token
    );

    @FormUrlEncoded
    @POST("updateUser")
    Call<DefaultResponse> updateUser(
            @Header("token") String token,
            @Field("name") String name,
            @Field("username") String username,
            @Field("bio") String bio,
            @Field("image") String image
    );

    @FormUrlEncoded
    @POST("postFeed")
    Call<DefaultResponse> postFeed(
            @Header("token") String token,
            @Field("content") String content,
            @Field("image") String image
    );

    @GET("getUser/{username}")
    Call<ResponseUser> getUserByUsername(
            @Header("token") String token,
            @Path("username") String username
    );

    @GET("feed/{id}")
    Call<ResponseFeed> getFeedById(
            @Header("token") String token,
            @Path("id") String id
    );
}
