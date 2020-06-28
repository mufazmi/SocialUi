package com.socialcodia.socialui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.socialcodia.socialui.R;
import com.socialcodia.socialui.api.ApiClient;
import com.socialcodia.socialui.model.DefaultResponse;
import com.socialcodia.socialui.model.ModelFeed;
import com.socialcodia.socialui.storage.SharedPrefHandler;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterFeed extends RecyclerView.Adapter<AdapterFeed.ViewHolder> {

    List<ModelFeed> modelFeedList;
    Context context;


    public AdapterFeed(List<ModelFeed> modelFeedList, Context context) {
        this.modelFeedList = modelFeedList;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.feed_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        ModelFeed feed = modelFeedList.get(position);
        holder.tvUserName.setText(feed.getUserName());
        holder.tvFeedTimestamp.setText(feed.getFeedTimestamp());
        holder.tvFeedContent.setText(feed.getFeedContent());
        String likeCounts = feed.getFeedLikes().toString();
        String commentCounts = feed.getFeedComments().toString();
        String feedId = feed.getFeedId().toString();
        Boolean liked = feed.getLiked();
        holder.tvFeedLike.setText(likeCounts+" Likes");
        holder.tvFeedComment.setText(commentCounts+" Comments");
        if (liked)
        {
            holder.tvLike.setVisibility(View.INVISIBLE);
            holder.tvUnlike.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.tvUnlike.setVisibility(View.INVISIBLE);
            holder.tvLike.setVisibility(View.VISIBLE);
        }
        try {
            Picasso.get().load(feed.getUserImage()).into(holder.userProfileImage);
            Picasso.get().load(feed.getFeedImage()).into(holder.ivFeedImage);
        }
        catch (Exception e)
        {
            Toast.makeText(context, "Image Error" +e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        holder.tvLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLike(feedId,likeCounts,holder);
            }
        });

        holder.tvUnlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doDislike(feedId,likeCounts,holder);
            }
        });

        holder.ivFeedOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFeedActionOptions();
            }
        });
    }

    private void showFeedActionOptions()
    {

    }


    private void doLike(String feedId, String likeCounts,ViewHolder holder)
    {
        int like = Integer.parseInt((likeCounts));
        holder.tvFeedLike.setText(like+1+" Likes");
        holder.tvLike.setVisibility(View.INVISIBLE);
        holder.tvUnlike.setVisibility(View.VISIBLE);
        String token = SharedPrefHandler.getInstance(context).getUser().getToken();
        Call<DefaultResponse> call = ApiClient.getInstance().getApi().doLike(token,Integer.parseInt(feedId));
        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                DefaultResponse defaultResponse = response.body();
                if (defaultResponse==null)
                {
                    Toast.makeText(context, "Server Not Responding", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                holder.tvUnlike.setVisibility(View.INVISIBLE);
                holder.tvLike.setVisibility(View.VISIBLE);
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void doDislike(String feedId, String likeCounts,ViewHolder holder)
    {
        holder.tvUnlike.setVisibility(View.INVISIBLE);
        holder.tvLike.setVisibility(View.VISIBLE);
        int like = Integer.parseInt((likeCounts));
        holder.tvFeedLike.setText(like-1+" Likes");
        String token = SharedPrefHandler.getInstance(context).getUser().getToken();
        Call<DefaultResponse> call = ApiClient.getInstance().getApi().doDislike(token,Integer.parseInt(feedId));
        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                DefaultResponse defaultResponse = response.body();
                if (defaultResponse==null)
                {
                    Toast.makeText(context, "Server Not Responding", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                holder.tvLike.setVisibility(View.INVISIBLE);
                holder.tvUnlike.setVisibility(View.VISIBLE);
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelFeedList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        private ImageView userProfileImage,ivFeedImage,ivFeedOption;
        private TextView tvUserName, tvFeedTimestamp,tvFeedContent,tvFeedLike,tvFeedComment,tvComment,tvLike,tvUnlike,tvShare;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userProfileImage = itemView.findViewById(R.id.userProfileImage);
            ivFeedImage = itemView.findViewById(R.id.ivFeedImage);
            ivFeedOption = itemView.findViewById(R.id.ivFeedOption);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvFeedTimestamp = itemView.findViewById(R.id.tvFeedTimestamp);
            tvFeedContent = itemView.findViewById(R.id.tvFeedContent);
            tvFeedLike = itemView.findViewById(R.id.tvFeedLike);
            tvFeedComment = itemView.findViewById(R.id.tvFeedComment);
            tvComment = itemView.findViewById(R.id.tvComment);
            tvLike = itemView.findViewById(R.id.tvLike);
            tvUnlike = itemView.findViewById(R.id.tvUnlike);
            tvShare = itemView.findViewById(R.id.tvShare);

        }
    }

}
