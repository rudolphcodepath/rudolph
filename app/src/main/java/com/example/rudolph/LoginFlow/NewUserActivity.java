package com.example.rudolph.LoginFlow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rudolph.MainActivity;
import com.example.rudolph.Models.User;
import com.example.rudolph.R;
import com.example.rudolph.databinding.ActivityNewUserBinding;

public class NewUserActivity extends AppCompatActivity implements LoginService.LoginListener {

    public static final String TAG = "NewUserActivity";

    ActivityNewUserBinding binding;

    LoginService loginService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNewUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSignUpClick();
        loginService = new LoginService(this, this);

    }


    @Override
    public void onSuccess() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onFailure() {
        Toast.makeText(NewUserActivity.this, R.string.login_new_user_failed,
                Toast.LENGTH_SHORT).show();
    }

    private void setSignUpClick() {
        binding.btnCreateNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.etEmail.getText().toString();
                String password = binding.etPassword.getText().toString();
                String name = binding.etName.getText().toString();
                if (email.isEmpty() || password.isEmpty() || name.isEmpty()) {
                    Toast.makeText(NewUserActivity.this, R.string.login_incomplete_fields, Toast.LENGTH_LONG).show();
                    return;
                }
                name = name.substring(0, 1).toUpperCase() + name.substring(1);
                loginService.signUp(email, password, name);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginService = null;
    }
}
