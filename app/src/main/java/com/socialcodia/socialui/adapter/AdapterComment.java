package com.socialcodia.socialui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.socialcodia.socialui.R;
import com.socialcodia.socialui.model.ModelComment;

import java.util.List;

public class AdapterComment extends RecyclerView.Adapter<AdapterComment.ViewHolder>
{
    private Context context;
    private List<ModelComment> modelCommentList;

    public AdapterComment(Context context, List<ModelComment> modelCommentList) {
        this.context = context;
        this.modelCommentList = modelCommentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelComment modelComment = modelCommentList.get(position);
    }

    @Override
    public int getItemCount() {
        return modelCommentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCommentUserName, tvCommentTimestamp, tvCommentContent, tvCommentLikesCount, btnCommentReply;
        private ImageView ivCommentUserProfileImage, ivCommentOption, btnCommentLike;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCommentUserName = itemView.findViewById(R.id.tvCommentUserName);
            tvCommentTimestamp = itemView.findViewById(R.id.tvCommentTimestamp);
            tvCommentContent = itemView.findViewById(R.id.tvCommentContent);
            tvCommentLikesCount = itemView.findViewById(R.id.tvCommentLikesCount);
            btnCommentReply = itemView.findViewById(R.id.btnCommentReply);
            ivCommentUserProfileImage = itemView.findViewById(R.id.ivCommentUserProfileImage);
            ivCommentOption = itemView.findViewById(R.id.ivCommentOption);
            btnCommentLike = itemView.findViewById(R.id.btnCommentLike);
        }
    }
}
