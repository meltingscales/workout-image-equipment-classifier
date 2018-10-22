package scanfit.modeling;

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
     * @return
     */
    public boolean contains(MuscleGroup m) {
        for (MuscleGroup x : muscleGroups) {
            if (x.equals(m)) {
                return true;
            }
        }
        return false;
    }
}
