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

public class RegisterActivity extends AppCompatActivity {

    //Ui Object
    private EditText inputEmail, inputPassword, inputName,inputUsername;
    private TextView tvLogin;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Ui Init
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        inputName = findViewById(R.id.inputName);
        inputUsername = findViewById(R.id.inputUsername);
        tvLogin = findViewById(R.id.tvLogin);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
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
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void validateData()
    {
        String name = inputName.getText().toString().trim();
        String email = inputEmail.getText().toString().trim();
        String username = inputUsername.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();
        if (name.isEmpty())
        {
            inputName.setError("Enter Name");
            inputName.requestFocus();
        }
        else if (name.length()<3 || name.length()> 20)
        {
            inputName.setError("Name Should be between 3 to 20 Character");
            inputName.requestFocus();
        }
        else if (username.isEmpty())
        {
            inputUsername.setError("Enter Username");
            inputUsername.requestFocus();
        }
        else if (username.length()<3 || username.length()>20)
        {
            inputUsername.setError("Enter Username between 4 to 20 character");
            inputUsername.requestFocus();
        }
        else if (email.isEmpty())
        {
            inputEmail.setError("Enter Email");
            inputEmail.requestFocus();
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.length()>50)
        {
            inputEmail.setError("Enter Valid Email");
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
            doRegister(name,username,email,password);
        }
    }

    private void doRegister(String name, String username, String email, String password)
    {
        btnRegister.setEnabled(false);
        Call<DefaultResponse> call = ApiClient.getInstance().getApi().createUser(name,username,email,password);
        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                DefaultResponse defaultResponse = response.body();
                if (defaultResponse!=null)
                {
                    if (!defaultResponse.isError())
                    {
                        btnRegister.setEnabled(true);
                        String message = defaultResponse.getMessage();
                        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                        sendToLoginWithEmailAndPassword(email,password);
                    }
                    else
                    {
                        btnRegister.setEnabled(true);
                        String message = defaultResponse.getMessage();
                        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    btnRegister.setEnabled(true);
                    Toast.makeText(RegisterActivity.this, "No Response From Server", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                btnRegister.setEnabled(true);
                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void sendToLoginWithEmailAndPassword(String email, String password)
    {
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        intent.putExtra("intentEmail",email);
        intent.putExtra("intentPassword",password);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


}