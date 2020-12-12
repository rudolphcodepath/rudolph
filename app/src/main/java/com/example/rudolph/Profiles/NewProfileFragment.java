package com.example.rudolph.Profiles;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
<<<<<<< HEAD
import androidx.fragment.app.Fragment;

=======
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
>>>>>>> 713b8e1fcc6f8bed6ad7815f4df6a06b6a31eebc
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
<<<<<<< HEAD
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.rudolph.R;
import com.example.rudolph.databinding.FragmentNewProfileBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

=======
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rudolph.Constants;
import com.example.rudolph.R;
import com.example.rudolph.databinding.FragmentNewProfileBinding;

import java.util.Calendar;

public class NewProfileFragment extends Fragment {

    ProfileService service;

    FragmentNewProfileBinding binding;
    Toolbar toolbar;
    FragmentManager manager;
    FragmentNewProfileBinding binding;

    Spinner spinner;

    int nextId;


    public NewProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        service = new ProfileService();

        manager = getFragmentManager();
        nextId = -1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNewProfileBinding.inflate(inflater, container, false);

        populateSpinner();
        setListeners();
        return binding.getRoot();
    }

    private void populateSpinner() {
        spinner = binding.interests;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.interests_array, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
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

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("NewProfileFragment", parent.getItemAtPosition(position).toString());
                String interest = parent.getItemAtPosition(position).toString();
                if (interest.equals(Constants.SELECT)) {
                    Log.d("NewProfileFragment", "returning");
                    return;
                }
                TextView newInterest = new TextView(getActivity());
                ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                int layoutBelow;
                Log.d("NewProfileFragment", Integer.toString(nextId));

                if (nextId == -1) {
                    layoutBelow = R.id.interests;
                }
                else {
                    layoutBelow = nextId;
                }
                nextId = View.generateViewId();
                newInterest.setId(nextId);

                params.topToBottom = layoutBelow;
                params.startToStart = R.id.constraintLayout;
                params.setMarginStart(Constants.MARGIN_START);
                params.topMargin = Constants.MARGIN_TOP;
                newInterest.setLayoutParams(params);
                newInterest.setText(interest);
                newInterest.setTextColor(getResources().getColor(R.color.black));
                newInterest.setTextSize(18);
                binding.constraintLayout.addView(newInterest);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
               lastName.toString().isEmpty() ||
               birthday.toString().isEmpty()) {
               lastName.isEmpty() ||
               birthday.isEmpty()) {
            Toast.makeText(getActivity(), R.string.login_incomplete_fields, Toast.LENGTH_SHORT).show();
            return;
        }
        service.addPerson(firstName, lastName, birthday);
    }

        manager.beginTransaction().replace(R.id.fLayout, new ProfilesFragment()).commit();
    }

}