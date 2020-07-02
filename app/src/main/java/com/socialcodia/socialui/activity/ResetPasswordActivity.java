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

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText inputEmail, inputOTP, inputPassword;
    private TextView tvForgotPassword;
    private Button btnResetPassword;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        inputEmail = findViewById(R.id.inputEmail);
        inputOTP = findViewById(R.id.inputOtp);
        inputPassword = findViewById(R.id.inputPassword);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        btnResetPassword = findViewById(R.id.btnResetPassword);

        //intent
        intent = getIntent();
        if (intent.getStringExtra("intentEmail")!=null)
        {
            String intentEmail = intent.getStringExtra("intentEmail");
            inputEmail.setText(intentEmail);
        }

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToForgotPassword();
            }
        });

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });

    }

    private void sendToForgotPassword()
    {
        Intent intent = new Intent(getApplicationContext(),ForgotPasswordActivity.class);
        startActivity(intent);
    }

    private void validateData()
    {
        String email = inputEmail.getText().toString().trim();
        String otp = inputOTP.getText().toString().trim();
        String password = inputPassword.getText().toString();

        if (email.isEmpty())
        {
            inputEmail.setText("Enter Email");
            inputEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.length()>50)
        {
            inputEmail.setError("Enter Valid Email");
            inputEmail.requestFocus();
            return;
        }
        if (password.isEmpty())
        {
            inputPassword.setError("Enter Password");
            inputPassword.requestFocus();
            return;
        }
        if (password.length()<7 || password.length()>20)
        {
            inputPassword.setError("Password Should Be In Between 7 to 20 Character");
            inputPassword.requestFocus();
            return;
        }
        if (otp.isEmpty())
        {
            inputOTP.setError("Enter OTP");
            inputOTP.requestFocus();
            return;
        }
        if (otp.length() != 6)
        {
            inputOTP.setError("Enter 6 Digits OTP");
            inputOTP.requestFocus();
        }
        else
        {
            int OTP = Integer.parseInt(otp);
            doResetPassword(email,OTP,password);
        }
    }

    private void doResetPassword(String email, int otp, String password)
    {
        btnResetPassword.setEnabled(false);
        Call<DefaultResponse> call = ApiClient.getInstance().getApi().resetPassword(email,otp,password);
        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                btnResetPassword.setEnabled(true);
            DefaultResponse defaultResponse = response.body();
                if (defaultResponse!=null)
                {
                    if (!defaultResponse.isError())
                    {
                        String message = defaultResponse.getMessage();
                        Toast.makeText(ResetPasswordActivity.this, message, Toast.LENGTH_SHORT).show();
                        sendToLoginWithEmail(email);
                    }
                    else
                    {
                        String message = defaultResponse.getMessage();
                        Toast.makeText(ResetPasswordActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(ResetPasswordActivity.this, "No Response From Server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                btnResetPassword.setEnabled(true);
                Toast.makeText(ResetPasswordActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendToLoginWithEmail(String email)
    {
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        intent.putExtra("intentEmail",email);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}