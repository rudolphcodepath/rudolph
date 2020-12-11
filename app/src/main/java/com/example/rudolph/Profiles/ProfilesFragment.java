package com.example.rudolph.Profiles;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.rudolph.Constants;
import com.example.rudolph.Models.Person;
import com.example.rudolph.Models.User;
import com.example.rudolph.R;
import com.example.rudolph.databinding.FragmentProfilesBinding;
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
import java.util.Map;


public class ProfilesFragment extends Fragment {

    public static final String TAG = "ProfilesFragment";

    private FirebaseAuth mAuth;
    private FirebaseUser currUser;
    private DatabaseReference mDatabaseRef;

    FragmentManager fragmentManager;
    FragmentProfilesBinding binding;

    List<Person> people;

    public interface ProfilesCallback {
        void onCallback(Map<String, String> toAdd);

        void finishedLoading();
    }


    public ProfilesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        currUser = mAuth.getCurrentUser();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        fragmentManager = getFragmentManager();
        people = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfilesBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
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

        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).setSupportActionBar(binding.toolbar);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "inside onViewCreated");
        final ProfilesAdapter adapter = new ProfilesAdapter(people, getContext());


        getPeople(new ProfilesCallback() {
            @Override
            public void onCallback(Map<String, String> toAdd) {
                Log.d(TAG, toAdd.toString());
                people.add(new Person(toAdd.get("birthday"), toAdd.get("firstName"), toAdd.get("lastName"), null));
                Log.d(TAG, "added person");
            }

            @Override
            public void finishedLoading() {
                binding.rvPeople.setAdapter(adapter);
            }

        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_top_bar_profiles, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        fragmentManager.beginTransaction().replace(R.id.fLayout, new NewProfileFragment()).commit();
        return super.onOptionsItemSelected(item);
    }

    private void getPeople(final ProfilesCallback callback) {
        Log.d(TAG, "inside getPeople");
        DatabaseReference peopleRef = FirebaseDatabase.getInstance().getReference().child(Constants.PEOPLE).child(currUser.getUid());
        String test = peopleRef.toString();
        Log.d(TAG, "test " + test);
        peopleRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "inside onDataChange");
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
        Log.d(TAG, "returning");
    }

}