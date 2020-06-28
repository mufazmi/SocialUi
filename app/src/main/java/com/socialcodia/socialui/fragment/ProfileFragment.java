package com.socialcodia.socialui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.socialcodia.socialui.R;
import com.socialcodia.socialui.model.ModelUser;
import com.socialcodia.socialui.storage.SharedPrefHandler;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {

    private TextView tvUserName,tvUserUsername;
    private ImageView userProfileImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        tvUserName = view.findViewById(R.id.tvUserName);
        tvUserUsername = view.findViewById(R.id.tvUserUsername);
        userProfileImage = view.findViewById(R.id.userProfileImage);

        ModelUser modelUser = SharedPrefHandler.getInstance(getContext()).getUser();

        tvUserName.setText(modelUser.getName());
        tvUserUsername.setText("@"+modelUser.getUsername());

        try {
            Picasso.get().load(modelUser.getImage()).into(userProfileImage);
        }
        catch (Exception e)
        {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        return view;
    }
}