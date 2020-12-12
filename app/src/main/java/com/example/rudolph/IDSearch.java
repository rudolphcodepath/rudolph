package com.example.rudolph;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class IDSearch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_d_search);
    }

    public void handleSelection(View view) {
        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
    }
}