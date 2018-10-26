package scanfit.modeling;

import java.io.Serializable;

public class MuscleGroup implements Serializable {
    public String name;

    public MuscleGroup(String name) {
        this.name = name;
    }

    public boolean equals(MuscleGroup other) {
        return this.name.equalsIgnoreCase(other.name);
    }

    @Override
    public boolean equals(Object other) {
        try {
            return ((MuscleGroup) other).equals(this);
        } catch (ClassCastException e) {
            return other.equals(this);
        }
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}
