package com.example.rudolph;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rudolph.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    EditText etEmail;
    EditText etPassword;
    Button btnLogin;
    Button btnNewUser;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnNewUser = findViewById(R.id.btnNewUser);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser currUser = mAuth.getCurrentUser();
        updateUI(currUser);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please fill out all fields", Toast.LENGTH_LONG).show();
                    return;
                }

                signIn(email, password);
            }
        });

    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                }
                else {
                    String message = task.getException().getMessage();
                    Log.d("LoginActivity", message);
                    Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_LONG).show();
                    updateUI(null);
                }
            }
        });
    }

    private void updateUI(FirebaseUser currUser) {
        if (currUser != null) {
            User user = new User(currUser.getDisplayName(), currUser.getEmail(), currUser.getUid());
            mDatabase.child("users").child(user.getUUid()).setValue(user);
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        }
        else {
            etPassword.setText("");
        }
    }


    public void onClickNewUser(View view) {
        Intent i = new Intent(this, NewUserActivity.class);
        startActivity(i);
    }
}
