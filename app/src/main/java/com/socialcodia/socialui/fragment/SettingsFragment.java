package com.socialcodia.socialui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.socialcodia.socialui.R;
import com.socialcodia.socialui.api.ApiClient;
import com.socialcodia.socialui.model.response.DefaultResponse;
import com.socialcodia.socialui.model.ModelUser;
import com.socialcodia.socialui.storage.SharedPrefHandler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SettingsFragment extends Fragment {

    private EditText inputPassword, inputNewPassword;
    private Button btnUpdatePassword;
    SharedPrefHandler sharedPrefHandler;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings,container,false);

        //Init
        inputNewPassword = view.findViewById(R.id.inputNewPassword);
        inputPassword = view.findViewById(R.id.inputPassword);
        btnUpdatePassword = view.findViewById(R.id.btnUpdatePassword);

        //Init SharePrefHandler
        sharedPrefHandler = SharedPrefHandler.getInstance(getContext());
        ModelUser modelUser = sharedPrefHandler.getUser();
        btnUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });

        return  view;
    }

    private void validateData()
    {
        String password = inputPassword.getText().toString().trim();
        String newPassword = inputNewPassword.getText().toString().trim();
        if (password.isEmpty())
        {
            inputPassword.setError("Enter Password");
            inputPassword.requestFocus();
            return;
        }
        if (password.length()<7 || password.length()>30)
        {
            inputPassword.setError("Password should be greater than 7 character");
            inputPassword.requestFocus();
            return;
        }
        if (newPassword.isEmpty())
        {
            inputNewPassword.setError("Enter New Password");
            inputNewPassword.requestFocus();
            return;
        }
        if (newPassword.length()<7 || newPassword.length()>30)
        {
            inputNewPassword.setError("Password should be greater than 7 character");
            inputNewPassword.requestFocus();
            return;
        }
        else
        {
            doUpdatePassword(password,newPassword);
        }
    }

    private void doUpdatePassword(String password, String newPassword)
    {
        btnUpdatePassword.setEnabled(false);
        ModelUser modelUser
                = sharedPrefHandler.getUser();
        Call<DefaultResponse> call = ApiClient.getInstance().getApi().updatePassword(modelUser.getToken(),password,newPassword);
        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                DefaultResponse defaultResponse = response.body();
                if (defaultResponse!=null)
                {
                    btnUpdatePassword.setEnabled(true);
                    Toast.makeText(getContext(), defaultResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    btnUpdatePassword.setEnabled(true);
                    Toast.makeText(getContext(), "No Response From Server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                btnUpdatePassword.setEnabled(true);
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}