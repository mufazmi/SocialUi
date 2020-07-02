package com.socialcodia.socialui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.socialcodia.socialui.R;
import com.socialcodia.socialui.activity.EditProfileActivity;
import com.socialcodia.socialui.adapter.AdapterFeed;
import com.socialcodia.socialui.api.ApiClient;
import com.socialcodia.socialui.model.ModelFeed;
import com.socialcodia.socialui.model.ModelUser;
import com.socialcodia.socialui.model.response.ResponseFeeds;
import com.socialcodia.socialui.model.response.ResponseUser;
import com.socialcodia.socialui.storage.SharedPrefHandler;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private TextView tvUserName,tvUserUsername,tvUserBio,tvFeedsCount,tvFollowersCount,tvFollowingsCount;
    private ImageView userProfileImage;
    private Button btnEditProfile;

    private RecyclerView recyclerView;
    List<ModelFeed> modelFeedList;

    String token,username;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        tvUserName = view.findViewById(R.id.tvUserName);
        tvUserUsername = view.findViewById(R.id.tvUserUsername);
        userProfileImage = view.findViewById(R.id.userProfileImage);
        recyclerView = view.findViewById(R.id.feedRecyclerView);
        btnEditProfile = view.findViewById(R.id.btnEditProfile);
        tvUserBio = view.findViewById(R.id.tvUserBio);
        tvFeedsCount = view.findViewById(R.id.tvFeedsCount);
        tvFollowersCount = view.findViewById(R.id.tvFollowersCount);
        tvFollowingsCount = view.findViewById(R.id.tvFollowingsCount);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        modelFeedList = new ArrayList<>();

        ModelUser modelUser = SharedPrefHandler.getInstance(getContext()).getUser();
        token = modelUser.getToken();
        username = modelUser.getUsername();
        tvUserName.setText(modelUser.getName());
        tvUserUsername.setText("@"+username);
        tvUserBio.setText(modelUser.getBio());
        tvFeedsCount.setText(modelUser.getFeedsCount()+"\n Posts");
        tvFollowersCount.setText(modelUser.getFollowersCount()+"\n Followers");
        tvFollowingsCount.setText(modelUser.getFollowingsCount()+"\n Followings");

        try {
            Picasso.get().load(modelUser.getImage()).into(userProfileImage);
        }
        catch (Exception e)
        {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        getPostByUsername();
        getUser();

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToEditProfile();
            }
        });

        return view;
    }

    private void getUser()
    {
        Call<ResponseUser> call = ApiClient.getInstance().getApi().getMyProfile(token);
        call.enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                if (response.isSuccessful())
                {
                    ResponseUser responseUser = response.body();
                    if (!responseUser.getError())
                    {
                        ModelUser modelUser = responseUser.getUser();
                        modelUser.setToken(token);
                        SharedPrefHandler.getInstance(getContext()).saveUser(modelUser);
                    }
                    else
                    {
                        Toast.makeText(getContext(), responseUser.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getContext(), "Server Not Responding", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendToEditProfile()
    {
        Intent intent = new Intent(getActivity(), EditProfileActivity.class);
        startActivity(intent);
    }

    private void getPostByUsername()
    {
        Call<ResponseFeeds> call = ApiClient.getInstance().getApi().getUserFeeds(username,token);
        call.enqueue(new Callback<ResponseFeeds>() {
            @Override
            public void onResponse(Call<ResponseFeeds> call, Response<ResponseFeeds> response) {
                if (response.isSuccessful())
                {
                    ResponseFeeds responseFeeds = response.body();
                    if (!responseFeeds.getError())
                    {
                        modelFeedList = responseFeeds.getFeeds();
                        AdapterFeed adapterFeed = new AdapterFeed(modelFeedList,getContext());
                        recyclerView.setAdapter(adapterFeed);
                    }
                    else
                    {
                        Toast.makeText(getContext(), responseFeeds.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getContext(), "Server Not Responding", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseFeeds> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}