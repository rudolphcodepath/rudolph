package com.example.rudolph.Profiles;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.rudolph.R;
import com.example.rudolph.databinding.FragmentNewProfileBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class NewProfileFragment extends Fragment {

    ProfileService service;

    FragmentManager manager;

    FragmentNewProfileBinding binding;
    Toolbar toolbar;

    public NewProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        service = new ProfileService();
        manager = getFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNewProfileBinding.inflate(inflater, container, false);
        setListeners();
        return binding.getRoot();
    }

    private void setListeners() {
        Calendar calendar = Calendar.getInstance();
        binding.birthdayPicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                StringBuilder dateText = new StringBuilder();
                dateText.append(monthOfYear+1).append("/");
                dateText.append(dayOfMonth).append("/");
                dateText.append(year);
                binding.enterBirthday.setText(dateText);
            }
        });
        binding.enterBirthday.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    binding.birthdayPicker.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).setSupportActionBar(binding.toolbar);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_top_bar_new_person, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        tryToAddPerson();
        return super.onOptionsItemSelected(item);
    }

    private void tryToAddPerson() {
        String firstName = binding.firstName.getText().toString();
        String lastName = binding.lastName.getText().toString();
        String birthday = binding.enterBirthday.getText().toString();
        if (
               firstName.isEmpty() ||
               lastName.isEmpty() ||
               birthday.isEmpty()) {
            Toast.makeText(getActivity(), R.string.login_incomplete_fields, Toast.LENGTH_SHORT).show();
            return;
        }
        service.addPerson(firstName, lastName, birthday);
        manager.beginTransaction().replace(R.id.fLayout, new ProfilesFragment()).commit();
    }

}