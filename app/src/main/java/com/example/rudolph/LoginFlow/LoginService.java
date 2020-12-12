package com.example.rudolph.LoginFlow;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.rudolph.Constants;
import com.example.rudolph.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginService {

    interface LoginListener {
        void onSuccess();

        void onFailure();
    }

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    LoginListener listener;
    Activity activity;

    public LoginService(Activity activity, LoginListener listener) {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        this.listener = listener;
        this.activity = activity;

        if (mAuth.getCurrentUser() != null) {
            listener.onSuccess();
        }
    }

    public void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser currUser = mAuth.getCurrentUser();
                    User user = new User(currUser.getDisplayName(), currUser.getEmail(), currUser.getUid());
                    mDatabase.child(Constants.USERS).child(user.getUUid()).setValue(user);
                    listener.onSuccess();
                }
                else {
                    String message = task.getException().getMessage();
                    Log.d("LoginService", message);
                    listener.onFailure();
                }
            }
        });
    }

    public void signUp(String email, String password, final String name) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser currUser = mAuth.getCurrentUser();
                            Log.d("NewUserActivity", "successful sign up");
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name).build();

                            currUser.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d("NewUserActivity", "Successfully added name");
                                            }
                                        }
                                    });

                            User user = new User(currUser.getDisplayName(), currUser.getEmail(), currUser.getUid());
                            mDatabase.child(Constants.USERS).child(user.getUUid()).setValue(user);

                            listener.onSuccess();

                        } else {
                            String message = task.getException().getMessage();
                            listener.onFailure();
                        }

                        // ...
                    }
                });
    }
}
