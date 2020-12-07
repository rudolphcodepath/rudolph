package com.example.rudolph.LoginFlow;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rudolph.MainActivity;
import com.example.rudolph.Models.User;
import com.example.rudolph.R;
import com.example.rudolph.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity implements LoginService.LoginListener {

    ActivityLoginBinding binding;

    LoginService loginService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setOnLoginClick();

        loginService = new LoginService(this, this);
    }

    public void onClickNewUser(View view) {
        Intent i = new Intent(this, NewUserActivity.class);
        startActivity(i);
    }

    @Override
    public void onSuccess() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onFailure() {
        Toast.makeText(LoginActivity.this, R.string.login_failed, Toast.LENGTH_LONG).show();
        binding.etPassword.setText("");
    }

    private void setOnLoginClick() {
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.etEmail.getText().toString();
                String password = binding.etPassword.getText().toString();
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, R.string.login_incomplete_fields, Toast.LENGTH_LONG).show();
                }
                loginService.signIn(email, password);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginService = null;
    }
}
