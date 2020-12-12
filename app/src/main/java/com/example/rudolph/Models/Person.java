package com.example.rudolph.Models;

import java.util.Date;
import java.util.List;

public class Person {

    String birthday;
    String firstName;
    String lastName;
    List<String> interests;

    public String getBirthday() {
        return birthday;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<String> getInterests() {
        return interests;
    }

    public Person() {

    }

    public Person(String birthday, String firstName, String lastName, List<String> interests) {
        this.birthday = birthday;
        this.firstName = firstName;
        this.lastName = lastName;
        this.interests = interests;
    }
}
