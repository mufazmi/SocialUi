package com.socialcodia.socialui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.socialcodia.socialui.R;
import com.socialcodia.socialui.adapter.AdapterFeed;
import com.socialcodia.socialui.api.ApiClient;
import com.socialcodia.socialui.model.ModelFeed;
import com.socialcodia.socialui.model.response.ResponseFeeds;
import com.socialcodia.socialui.storage.SharedPrefHandler;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    private RecyclerView feedRecyclerView;
    List<ModelFeed> modelFeedList;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        feedRecyclerView = view.findViewById(R.id.feedRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        feedRecyclerView.setLayoutManager(layoutManager);
        modelFeedList = new ArrayList<>();
        getFeeds();

        return view;
    }

    private void getFeeds()
    {
        Call<ResponseFeeds> call = ApiClient.getInstance().getApi().getFeeds(SharedPrefHandler.getInstance(getContext()).getUser().getToken());
        call.enqueue(new Callback<ResponseFeeds>() {
            @Override
            public void onResponse(Call<ResponseFeeds> call, Response<ResponseFeeds> response) {
                ResponseFeeds responseFeeds = response.body();
                if (responseFeeds !=null)
                {
                    if (!responseFeeds.getError())
                    {
                        if (responseFeeds.getFeeds()!=null)
                        {
                            modelFeedList = responseFeeds.getFeeds();
                            AdapterFeed adapterFeed = new AdapterFeed(modelFeedList,getContext());
                            feedRecyclerView.setAdapter(adapterFeed);
                        }
                        else
                        {
                            Toast.makeText(getContext(), "Server Doesn't Return Any Feeds In Response", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {

                        Toast.makeText(getContext(), "Error :"+ responseFeeds.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getContext(), "No Response From Server", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseFeeds> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}