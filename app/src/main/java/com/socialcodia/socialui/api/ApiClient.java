package com.socialcodia.socialui.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "http://172.16.0.11/SocialApi/public/";
//    private static final String BASE_URL = "http://10.0.2.2/SocialApi/public/";
    private static ApiClient mInstance;
    private Retrofit retrofit;

    private ApiClient()
    {
        Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static synchronized ApiClient getInstance()
    {
        if (mInstance == null)
        {
            mInstance = new ApiClient();
        }
        return mInstance;
    }

    public Api getApi()
    {
        return retrofit.create(Api.class);
    }

}
