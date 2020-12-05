package com.example.rudolph;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rudolph.Models.Person;
import com.example.rudolph.Models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ProfilesFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseUser currUser;
    private DatabaseReference mDatabaseRef;

    RecyclerView rvPeople;


    public ProfilesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        currUser = mAuth.getCurrentUser();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                Log.d("ProfilesFragment", user.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child(currUser.getUid());
        ref.addListenerForSingleValueEvent(valueEventListener);


        return inflater.inflate(R.layout.fragment_profiles, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvPeople = (RecyclerView) view.findViewById(R.id.rvPeople);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        List<Person> people = getPeople();
        ProfilesAdapter adapter = new ProfilesAdapter(null, getActivity());
        rvPeople.setAdapter(adapter);
        rvPeople.setLayoutManager(layoutManager);

    }

    private List<Person> getPeople() {
        Query peopleQuery = mDatabaseRef.child("people").child(currUser.getUid()).orderByKey();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot personSnapshot : snapshot.getChildren()) {

                }

//                Log.d("ProfilesFragment", person.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("people").child(currUser.getUid());
        ref.addListenerForSingleValueEvent(valueEventListener);
        return null;
    }
}