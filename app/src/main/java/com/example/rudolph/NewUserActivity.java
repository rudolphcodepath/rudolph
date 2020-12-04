package com.example.rudolph;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class NewUserActivity extends AppCompatActivity {

    public static final String TAG = "NewUserActivity";

    private FirebaseAuth mAuth;

    EditText etName;
    EditText etEmail;
    EditText etPassword;
    Button btnNewUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_user);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnNewUser = findViewById(R.id.btnCreateNewUser);

        mAuth = FirebaseAuth.getInstance();

        btnNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String name = etName.getText().toString();
                if (email.isEmpty() || password.isEmpty() || name.isEmpty()) {
                    Toast.makeText(NewUserActivity.this, "Please fill out all fields", Toast.LENGTH_LONG).show();
                    return;
                }
                name = name.substring(0, 1).toUpperCase() + name.substring(1);
                createAccount(email, password, name);
            }
        });

    }

    private void createAccount(String email, String password, final String name) {
        Log.d(TAG, "inside");
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.d("NewUserActivity", "successful sign up");
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name).build();


                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d("NewUserActivity", "Successfully added name");
                                            }
                                        }
                                    });
                            updateUI(user);

                        } else {
                            String message = task.getException().getMessage();
                            Log.d(TAG, message);
                            // If sign in fails, display a message to the user.
                            Toast.makeText(NewUserActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
    }
}
