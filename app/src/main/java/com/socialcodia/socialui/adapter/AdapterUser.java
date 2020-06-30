package com.socialcodia.socialui.adapter;


import android.content.Context;
import android.content.Intent;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.socialcodia.socialui.R;
import com.socialcodia.socialui.activity.ProfileActivity;
import com.socialcodia.socialui.model.ModelUser;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterUser extends RecyclerView.Adapter<AdapterUser.ViewHolder>
{
    private List<ModelUser> modelUserList;
    private Context context;

    public AdapterUser(List<ModelUser> modelUserList, Context context) {
        this.modelUserList = modelUserList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_user,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = modelUserList.get(position).getName();
        String email = modelUserList.get(position).getEmail();
        String image = modelUserList.get(position).getImage();
        String username = modelUserList.get(position).getUsername();
        holder.tvUserEmail.setText(email);
        holder.tvUserName.setText(name);

        try {
            Picasso.get().load(image).into(holder.userProfileImage);
        }
        catch (Exception e)
        {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        holder.userCardRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToProfileActivity(username);
            }
        });
    }

    private void sendToProfileActivity(String username)
    {
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra("IntentUsername",username);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return modelUserList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tvUserName, tvUserEmail;
        private ImageView userProfileImage;
        private CardView userCardRow;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvUserEmail = itemView.findViewById(R.id.tvUserEmail);
            userProfileImage = itemView.findViewById(R.id.userProfileImage);
            userCardRow = itemView.findViewById(R.id.userCardRow);
        }
    }

}
