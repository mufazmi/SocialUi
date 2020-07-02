package com.socialcodia.socialui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.socialcodia.socialui.R;
import com.socialcodia.socialui.api.ApiClient;
import com.socialcodia.socialui.model.response.DefaultResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText inputEmail;
    private TextView tvLogin;
    private Button btnForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        inputEmail = findViewById(R.id.inputEmail);
        tvLogin = findViewById(R.id.tvLogin);
        btnForgotPassword = findViewById(R.id.btnForgotPassword);

        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToLogin();
            }
        });

    }

    private void sendToLogin()
    {
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
    }

    private void validateData()
    {
        String email = inputEmail.getText().toString().trim();
        if (email.isEmpty())
        {
            inputEmail.setError("Enter Email Address");
            inputEmail.requestFocus();
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.length()>50)
        {
            inputEmail.setError("Enter Valid Email");
            inputEmail.requestFocus();
        }
        else
        {
            doForgotPassword(email);
        }
    }

    private void doForgotPassword(String email)
    {
        btnForgotPassword.setEnabled(false);
        Call<DefaultResponse> call = ApiClient.getInstance().getApi().forgotPassword(email);
        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                DefaultResponse defaultResponse = response.body();
                btnForgotPassword.setEnabled(true);
                if(defaultResponse!=null)
                {
                    if (!defaultResponse.isError())
                    {
                        String message = defaultResponse.getMessage();
                        Toast.makeText(ForgotPasswordActivity.this, message, Toast.LENGTH_SHORT).show();
                        sendToResetPasswordActivityWithEmail(email);
                    }
                    else
                    {
                        String message = defaultResponse.getMessage();
                        Toast.makeText(ForgotPasswordActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(ForgotPasswordActivity.this, "No Response From Server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                btnForgotPassword.setEnabled(true);
                Toast.makeText(ForgotPasswordActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendToResetPasswordActivityWithEmail(String email)
    {
        Intent intent = new Intent(getApplicationContext(),ResetPasswordActivity.class);
        intent.putExtra("intentEmail",email);
        startActivity(intent);
    }
}