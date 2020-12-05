package com.example.rudolph.Models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {

    String name;
    String UUid;
    String email;

    List<Person> people;


    public User() {

    }

    public User(String name, String email, String UUid) {
        this.name = name;
        this.UUid = UUid;
        this.email = email;

        people = new ArrayList<>();
        List<String> interests = new ArrayList<>();
        interests.add("fishing");
        people.add(0, new Person(new Date(), "Test", interests));
    }

    public String getUUid() {
        return UUid;
    }

    public String getEmail() {
        return email;
    }


}
