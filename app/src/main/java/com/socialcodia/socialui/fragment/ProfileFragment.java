package com.socialcodia.socialui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.socialcodia.socialui.R;
import com.socialcodia.socialui.adapter.AdapterFeed;
import com.socialcodia.socialui.api.ApiClient;
import com.socialcodia.socialui.model.ModelFeed;
import com.socialcodia.socialui.model.ModelUser;
import com.socialcodia.socialui.model.ResponseFeed;
import com.socialcodia.socialui.storage.SharedPrefHandler;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private TextView tvUserName,tvUserUsername;
    private ImageView userProfileImage;

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

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        modelFeedList = new ArrayList<>();

        ModelUser modelUser = SharedPrefHandler.getInstance(getContext()).getUser();
        token = modelUser.getToken();
        username = modelUser.getUsername();
        tvUserName.setText(modelUser.getName());
        tvUserUsername.setText("@"+username);

        try {
            Picasso.get().load(modelUser.getImage()).into(userProfileImage);
        }
        catch (Exception e)
        {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        getPostByUsername();

        return view;
    }

    private void getPostByUsername()
    {
        Call<ResponseFeed> call = ApiClient.getInstance().getApi().getUserFeeds(username,token);
        call.enqueue(new Callback<ResponseFeed>() {
            @Override
            public void onResponse(Call<ResponseFeed> call, Response<ResponseFeed> response) {
                if (response.isSuccessful())
                {
                    ResponseFeed responseFeed = response.body();
                    if (!responseFeed.getError())
                    {
                        modelFeedList = responseFeed.getFeeds();
                        AdapterFeed adapterFeed = new AdapterFeed(modelFeedList,getContext());
                        recyclerView.setAdapter(adapterFeed);
                    }
                    else
                    {
                        Toast.makeText(getContext(), responseFeed.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getContext(), "Server Not Responding", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseFeed> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}