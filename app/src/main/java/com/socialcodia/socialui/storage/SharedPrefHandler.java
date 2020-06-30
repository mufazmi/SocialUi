package com.socialcodia.socialui.storage;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.socialcodia.socialui.model.ModelUser;

public class SharedPrefHandler
{
    private static final String SHARED_PREF_NAME = "SocialCodia";
    private static SharedPrefHandler mInstance;
    private SharedPreferences sharedPreferences;
    private Context context;

    public SharedPrefHandler(Context context)
    {
        this.context = context;
    }

    public static synchronized SharedPrefHandler getInstance(Context context)
    {
        if (mInstance==null)
        {
            mInstance = new SharedPrefHandler(context);
        }
        return mInstance;
    }

    public void saveUser(ModelUser modelUser)
    {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(Constants.USER_ID, modelUser.getId());
        editor.putString(Constants.USER_NAME, modelUser.getName());
        editor.putString(Constants.USER_USERNAME, modelUser.getUsername());
        editor.putString(Constants.USER_EMAIL, modelUser.getEmail());
        editor.putString(Constants.USER_BIO,modelUser.getBio());
        editor.putString(Constants.USER_IMAGE,modelUser.getImage());
        editor.putString(Constants.USER_TOKEN, modelUser.getToken());
        editor.putString(Constants.USER_FEEDS_COUNT,modelUser.getFeedsCount());
        editor.putString(Constants.USER_FOLLOWERS_COUNT,modelUser.getFollowersCount());
        editor.putString(Constants.USER_FOLLOWING_COUNT,modelUser.getFollowingsCount());
        editor.apply();
        editor.commit();
    }

    public ModelUser getUser()
    {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return new ModelUser(
                sharedPreferences.getInt(Constants.USER_ID,-1),
                sharedPreferences.getBoolean("following",false),
                sharedPreferences.getString(Constants.USER_NAME,null),
                sharedPreferences.getString(Constants.USER_USERNAME,null),
                sharedPreferences.getString(Constants.USER_EMAIL,null),
                sharedPreferences.getString(Constants.USER_BIO,null),
                sharedPreferences.getString(Constants.USER_IMAGE, null),
                sharedPreferences.getString(Constants.USER_TOKEN,null),
                sharedPreferences.getString(Constants.USER_FEEDS_COUNT,null),
                sharedPreferences.getString(Constants.USER_FOLLOWERS_COUNT,null),
                sharedPreferences.getString(Constants.USER_FOLLOWING_COUNT,null)
                );
    }

    public Boolean isLoggedIn()
    {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        int id = sharedPreferences.getInt(Constants.USER_ID,-1);
        if (id!=-1)
        {
            return true;
        }
        return false;
    }

    public void doLogout()
    {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
