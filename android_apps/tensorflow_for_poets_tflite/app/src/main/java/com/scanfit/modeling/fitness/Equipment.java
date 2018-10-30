package com.scanfit.modeling.fitness;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;

public class Equipment implements Serializable, Comparable<Equipment> {

    /***
     * What is this Equipment called?
     */
    public String name;

    /***
     * Set of all exercise NAMES you can do with this Equipment.
     */
    public HashSet<String> exerciseSet = new HashSet<>();

    public Equipment(String name, String... exerciseNames) {
        this.name = name;

        Collections.addAll(exerciseSet, exerciseNames);
    }

    public Equipment(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(@NonNull Equipment o) {
        return name.compareTo(o.name);
    }

    @Override
    public int hashCode() {
        int start = name.hashCode();

        for (String s : exerciseSet) {
            start = start ^ s.hashCode();
        }

        return start;
    }

    public boolean equals(Equipment e) {

        if (!this.name.equals(e.name)) {
            return false;
        }

        return this.exerciseSet.containsAll(e.exerciseSet);
    }

    @Override
    public boolean equals(Object o) {
        try {
            return ((Equipment) o).equals(this);
        } catch (ClassCastException e) {
            return o.equals(this);
        }
    }

    @Override
    public String toString() {
        return this.name;
    }
}
