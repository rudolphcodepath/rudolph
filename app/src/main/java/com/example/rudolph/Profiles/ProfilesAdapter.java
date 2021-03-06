package com.example.rudolph.Profiles;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rudolph.Models.Person;
import com.example.rudolph.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ProfilesAdapter extends RecyclerView.Adapter<ProfilesAdapter.ViewHolder> {

    List<Person> people;
    Context context;

    public ProfilesAdapter(List<Person> people, Context context) {
        this.people = people;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View personView = LayoutInflater.from(context).inflate(R.layout.item_profile, parent, false);
        return new ViewHolder(personView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(people.get(position));
    }

    @Override
    public int getItemCount() {
        return people.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProfile;
        TextView tvFirstName;
        TextView tvLastName;
        TextView tvBirthday;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfile = itemView.findViewById(R.id.ivProfile);
            tvFirstName = itemView.findViewById(R.id.tvFirstName);
            tvLastName = itemView.findViewById(R.id.tvLastName);
            tvBirthday = itemView.findViewById(R.id.tvBirthday);
        }

        public void bind(Person person) {
            tvFirstName.setText(person.getFirstName());
            tvLastName.setText(person.getLastName());
            tvBirthday.setText(person.getBirthday());
        }
    }
}
