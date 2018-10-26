package com.scanfit.modeling;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Workout implements Serializable {

    public String name;
    public Set<MuscleGroup> muscleGroups = new HashSet<>();


    public Workout(String name, String... muscleGroups) {
        this.name = name;

        for (String muscleGroupName : muscleGroups) {
            this.muscleGroups.add(new MuscleGroup(muscleGroupName));
        }
    }

    /***
     * Does this Workout contain a specific MuscleGroup?
     * @param m The MuscleGroup.
     */
    public boolean contains(MuscleGroup m) {
        for (MuscleGroup x : muscleGroups) {
            if (x.equals(m)) {
                return true;
            }
        }
        return false;
    }

    // So we can do comparisons in sets, etc.
    public boolean equals(Workout other) {

        // Same name?
        if (!other.name.equals(this.name)) {
            return false;
        }

        // Same size?
        if (this.muscleGroups.size() != other.muscleGroups.size()) {
            return false;
        }

        Object[] ourMusclegroups = this.muscleGroups.toArray();
        Object[] theirMuscleGroups = other.muscleGroups.toArray();

        // All objects the same?
        for (int i = 0; i < this.muscleGroups.size(); i++) {
            MuscleGroup ours = (MuscleGroup) ourMusclegroups[i];
            MuscleGroup theirs = (MuscleGroup) theirMuscleGroups[i];

            if (!ours.equals(theirs)) {
                return false;
            }
        }

        return true;
    }

    /***
     * Override for Sets/comparison elsewhere.
     */
    @Override
    public boolean equals(Object o) {
        try {
            return this.equals((Workout) o);
        } catch (ClassCastException e) {
            return this.equals(o);
        }
    }


    @Override
    public int hashCode() {
        int start = name.hashCode();

        for (MuscleGroup m : this.muscleGroups) {
            start = start ^ m.hashCode();
        }

        return start;
    }

    @Override
    public String toString() {

        StringBuilder ret = new StringBuilder(String.format("%s: ", this.name));

        for (MuscleGroup m : this.muscleGroups) {
            ret.append(m.toString()).append("; ");
        }

        return ret.toString();
    }
}
