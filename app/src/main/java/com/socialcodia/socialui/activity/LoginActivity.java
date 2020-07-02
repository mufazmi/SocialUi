package com.socialcodia.socialui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.socialcodia.socialui.R;
import com.socialcodia.socialui.api.ApiClient;
import com.socialcodia.socialui.model.response.DefaultResponse;
import com.socialcodia.socialui.model.response.LoginResponse;
import com.socialcodia.socialui.model.ModelUser;
import com.socialcodia.socialui.storage.SharedPrefHandler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText inputEmail, inputPassword;
    private TextView tvRegister, tvForgotPassword;
    private SharedPrefHandler sharedPrefHandler;
    private Button btnLogin,sendEmailVerificationLinkAgain;
    String email, password;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Ui Init
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        tvRegister = findViewById(R.id.tvRegister);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        sendEmailVerificationLinkAgain = findViewById(R.id.sendEmailVerificationLinkAgain);
        // Intent Data

        intent = getIntent();
        if (intent.getStringExtra("intentEmail")!=null )
        {
            String intentEmail = intent.getStringExtra("intentEmail");
            inputEmail.setText(intentEmail);
        }
        if (intent.getStringExtra("intentPassword")!=null)
        {
            String intentPassword = intent.getStringExtra("intentPassword");
            inputPassword.setText(intentPassword);
        }

        //Share Pref init
        sharedPrefHandler = new SharedPrefHandler(getApplicationContext());

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToRegister();
            }
        });

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToForgotActivity();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });

        sendEmailVerificationLinkAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Please wait...", Toast.LENGTH_SHORT).show();
                Call<DefaultResponse> call = ApiClient.getInstance().getApi().sendEmailVerfication(email);
                call.enqueue(new Callback<DefaultResponse>() {
                    @Override
                    public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                        DefaultResponse defaultResponse = response.body();
                        if (defaultResponse!=null)
                        {
                            sendEmailVerificationLinkAgain.setVisibility(View.GONE);
                            String message = defaultResponse.getMessage();
                            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, "No Response From Server", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<DefaultResponse> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        isLoggedIn();
    }

    private void isLoggedIn()
    {
        if (SharedPrefHandler.getInstance(getApplicationContext()).isLoggedIn())
        {
            sendToMain();
        }
    }

    private void sendToRegister()
    {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }

    private void validateData()
    {
        email = inputEmail.getText().toString().trim();
        password = inputPassword.getText().toString().trim();
        if (email.isEmpty())
        {
            inputEmail.setError("Enter Email");
            inputEmail.requestFocus();
        }
        else if (email.length()<3 || email.length()>50)
        {
            inputEmail.setError("Enter Valid Email or Username");
            inputEmail.requestFocus();
        }
        else if (password.isEmpty())
        {
            inputPassword.setError("Enter Password");
            inputPassword.requestFocus();
        }
        else if (password.length()<7 || password.length()>20)
        {
            inputPassword.setError("Password should be in between 7 to 20 Character");
            inputPassword.requestFocus();
        }
        else
        {
            doLogin(email,password);
        }
    }

    private void doLogin(String email, String password)
    {
        btnLogin.setEnabled(false);
        Call<LoginResponse> call = ApiClient.getInstance().getApi().login(email,password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                if (loginResponse!=null)
                {
                    if (loginResponse.getError())
                    {
                        btnLogin.setEnabled(true);
                        String message = loginResponse.getMessage();
                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
                        if (loginResponse.getMessage().equals("Email Is Not Verified"))
                        {
                            sendEmailVerificationLinkAgain.setVisibility(View.VISIBLE);
                        }
                    }
                    else
                    {
                        btnLogin.setEnabled(true);
                        ModelUser modelUser = loginResponse.getUser();
                        if (modelUser !=null)
                        {
                            SharedPrefHandler.getInstance(getApplicationContext()).saveUser(modelUser);
                            sendToMain();
                            Toast.makeText(LoginActivity.this,loginResponse.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Server Not Responding Any Data", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else
                {
                    btnLogin.setEnabled(true);
                    Toast.makeText(LoginActivity.this, "No Response From Server", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                btnLogin.setEnabled(true);
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void sendToMain()
    {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void sendToForgotActivity()
    {
        Intent intent = new Intent(getApplicationContext(),ForgotPasswordActivity.class);
        startActivity(intent);
    }

}