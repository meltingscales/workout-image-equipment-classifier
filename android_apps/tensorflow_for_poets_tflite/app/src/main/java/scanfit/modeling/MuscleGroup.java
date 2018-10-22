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
}
