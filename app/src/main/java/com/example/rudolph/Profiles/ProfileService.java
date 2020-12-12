package com.example.rudolph.Profiles;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.rudolph.Constants;
import com.example.rudolph.Models.Person;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProfileService {

    public static final String TAG = "ProfileService";

    FirebaseAuth auth;
    DatabaseReference reference;

    public interface ProfilesCallback {
        void onCallback(Map<String, String> toAdd);
        void finishedLoading();
    }


    public ProfileService() {
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();
    }

    public void addPerson(String firstName, String lastName, String birthday) {
        Person newPerson = new Person(birthday, firstName, lastName, null);
        String key = reference.child(Constants.PEOPLE).child(auth.getCurrentUser().getUid()).push().getKey();
        reference.child(Constants.PEOPLE).child(auth.getCurrentUser().getUid()).child(key).setValue(newPerson);
    }

    public void getPeople(final ProfilesCallback callback) {
        DatabaseReference peopleRef = FirebaseDatabase.getInstance().getReference().child(Constants.PEOPLE).child(auth.getCurrentUser().getUid());

        peopleRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dsp : snapshot.getChildren()) {
                    callback.onCallback((Map<String, String>) dsp.getValue());
                }
                callback.finishedLoading();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "onCancelled");
            }
        });
    }
}
