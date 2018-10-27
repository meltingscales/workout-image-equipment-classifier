package com.scanfit.modeling;

import java.io.Serializable;
import java.util.Date;

public class Person implements Serializable {
    public String gender;
    public BodyType bodyType;
    public Date birthday;
    public Weight weight;
    public Height height;

    public Person(String gender, BodyType bodyType, Date birthday) {

    }
}
