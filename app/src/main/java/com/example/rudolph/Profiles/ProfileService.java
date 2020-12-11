package com.example.rudolph.Profiles;

import com.example.rudolph.Constants;
import com.example.rudolph.Models.Person;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileService {

    FirebaseAuth auth;
    DatabaseReference reference;


    public ProfileService() {
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();
    }

    public void addPerson(String firstName, String lastName, String birthday) {
        Person newPerson = new Person(birthday, firstName, lastName, null);
        String key = reference.child(Constants.PEOPLE).child(auth.getCurrentUser().getUid()).push().getKey();
        reference.child(Constants.PEOPLE).child(auth.getCurrentUser().getUid()).child(key).setValue(newPerson);
    }
}
