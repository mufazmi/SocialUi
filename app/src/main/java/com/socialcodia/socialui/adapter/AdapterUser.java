package com.socialcodia.socialui.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.socialcodia.socialui.R;
import com.socialcodia.socialui.model.ModelUser;

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
        holder.tvUserEmail.setText(email);
        holder.tvUserName.setText(name);
    }

    @Override
    public int getItemCount() {
        return modelUserList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tvUserName, tvUserEmail;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvUserEmail = itemView.findViewById(R.id.tvUserEmail);
        }
    }

}
