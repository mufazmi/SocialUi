package com.socialcodia.socialui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.GnssClock;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.socialcodia.socialui.R;
import com.socialcodia.socialui.api.ApiClient;
import com.socialcodia.socialui.model.ModelFeed;
import com.socialcodia.socialui.model.ModelUser;
import com.socialcodia.socialui.model.ResponseFeed;
import com.socialcodia.socialui.model.ResponseFeeds;
import com.socialcodia.socialui.storage.SharedPrefHandler;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedActivity extends AppCompatActivity {

    private TextView tvCommentUserName, tvCommentTimestamp, tvCommentContent, tvCommentLikesCount, btnCommentReply, tvFeedTimestamp, tvFeedContent, tvUserName, tvFeedLike, tvFeedComment, tvLike, tvUnlike, tvComment, tvShare;
    private ImageView ivCommentUserProfileImage, ivCommentOption, btnCommentLike, btnAddComment, ivFeedOption, ivFeedImage, userProfileImage;
    private EditText inputComment;
    private Intent intent;
    String token,feedId, comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        tvCommentUserName = findViewById(R.id.tvCommentUserName);
        tvCommentTimestamp = findViewById(R.id.tvCommentTimestamp);
        tvCommentContent = findViewById(R.id.tvCommentContent);
        tvCommentLikesCount = findViewById(R.id.tvCommentLikesCount);
        btnCommentReply = findViewById(R.id.btnCommentReply);
        ivCommentUserProfileImage = findViewById(R.id.ivCommentUserProfileImage);
        ivCommentOption = findViewById(R.id.ivCommentOption);
        btnCommentLike = findViewById(R.id.btnCommentLike);
        btnAddComment = findViewById(R.id.btnAddComment);
        userProfileImage = findViewById(R.id.userProfileImage);
        tvFeedTimestamp = findViewById(R.id.tvFeedTimestamp);
        tvFeedContent = findViewById(R.id.tvFeedContent);
        tvUserName = findViewById(R.id.tvUserName);
        tvFeedLike = findViewById(R.id.tvFeedLike);
        tvFeedComment = findViewById(R.id.tvFeedComment);
        tvLike = findViewById(R.id.tvLike);
        tvUnlike = findViewById(R.id.tvUnlike);
        tvComment = findViewById(R.id.tvComment);
        tvShare = findViewById(R.id.tvShare);
        userProfileImage = findViewById(R.id.userProfileImage);
        ivFeedOption = findViewById(R.id.ivFeedOption);
        ivFeedImage = findViewById(R.id.ivFeedImage);

        ModelUser modelUser = SharedPrefHandler.getInstance(getApplicationContext()).getUser();
        token = modelUser.getToken();

        if (intent.getStringExtra("IntentFeedId")!=null)
        {
            feedId = intent.getStringExtra("IntentFeedId");
        }

        btnAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });

        getFeed();

    }

    private void getFeed()
    {
        Call<ResponseFeed> call = ApiClient.getInstance().getApi().getFeedById(token,feedId);
        call.enqueue(new Callback<ResponseFeed>() {
            @Override
            public void onResponse(Call<ResponseFeed> call, Response<ResponseFeed> response) {
                if (response.isSuccessful())
                {
                    ResponseFeed responseFeed = response.body();
                    if (!responseFeed.getError())
                    {
                        ModelFeed modelFeed = responseFeed.getFeed();
                        int feedId = modelFeed.getFeedId();
                        String feedContent = modelFeed.getFeedContent();
                        String feedImage = modelFeed.getFeedImage();
                        String feedTimestamp = modelFeed.getFeedTimestamp();
                        int userId = modelFeed.getUserId();
                        String userName = modelFeed.getUserName();
                        String userImage = modelFeed.getUserImage();
                        Boolean liked = modelFeed.getLiked();
                        int feedLikes = modelFeed.getFeedLikes();
                        int feedComments = modelFeed.getFeedComments();

                        tvUserName.setText(userName);
                        tvFeedContent.setText(feedContent);
                        tvFeedTimestamp.setText(feedTimestamp);
                        tvFeedLike.setText(feedLikes);
                        tvFeedComment.setText(feedComments);

                        try {
                            Picasso.get().load(userImage).into(userProfileImage);
                        }
                        catch (Exception e)
                        {
                            Toast.makeText(FeedActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        try {
                            Picasso.get().load(feedImage).into(ivFeedImage);
                        }
                        catch (Exception e)
                        {
                            Toast.makeText(FeedActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        if (liked)
                        {
                            tvLike.setVisibility(View.GONE);
                            tvUnlike.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            tvLike.setVisibility(View.VISIBLE);
                            tvUnlike.setVisibility(View.GONE);
                        }

                    }
                    else
                    {
                        Toast.makeText(FeedActivity.this, responseFeed.getMessage(), Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                }
                else
                {
                    Toast.makeText(FeedActivity.this, "Server Not Responding", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseFeed> call, Throwable t) {
                Toast.makeText(FeedActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void validateData()
    {
        comment = inputComment.getText().toString().trim();
        if (comment.isEmpty())
        {
            inputComment.setError("Can't Add Empty Comment");
            inputComment.requestFocus();
        }
        else
        {
            addComment(feedId,comment);
        }
    }

    private void addComment(String feedId, String comment)
    {

    }
}