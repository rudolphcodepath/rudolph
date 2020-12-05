package com.example.rudolph.Models;

import java.util.Date;
import java.util.List;

public class Person {

    Date birthday;
    String name;
    List<String> interests;

    public Person() {

    }

    public Person(Date birthday, String name, List<String> interests) {
        this.birthday = birthday;
        this.name = name;
        this.interests = interests;
    }
}
