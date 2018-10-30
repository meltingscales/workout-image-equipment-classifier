package com.scanfit.modeling.fitness;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;

public class Equipment implements Serializable, Comparable<Equipment> {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashSet<String> getExerciseSet() {
        return exerciseSet;
    }

    public void setExerciseSet(HashSet<String> exerciseSet) {
        this.exerciseSet = exerciseSet;
    }

    /***
     * What is this Equipment called?
     */
    private String name;

    /***
     * Set of all exercise NAMES you can do with this Equipment.
     */
    private HashSet<String> exerciseSet = new HashSet<>();

    public Equipment(String name, String... exerciseNames) {
        this.setName(name);

        Collections.addAll(getExerciseSet(), exerciseNames);
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
        int start = getName().hashCode();

        for (String s : getExerciseSet()) {
            start = start ^ s.hashCode();
        }

        return start;
    }

    public boolean equals(Equipment e) {

        if (!this.getName().equals(e.getName())) {
            return false;
        }

        return this.getExerciseSet().containsAll(e.getExerciseSet());
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
        return this.getName();
    }
}
