package tests;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import scanfit.WorkoutSolver;
import scanfit.modeling.Equipment;
import scanfit.modeling.MuscleGroup;
import scanfit.modeling.Workout;

import static scanfit.ScanFitLib.stringFromFile;

public class testFitnessModel {

    @Test
    public void testWorkout() {
        Workout reallyGoodWorkout = new Workout("run really fast", "legs", "feet");

        if ((!reallyGoodWorkout.contains(new MuscleGroup("legs")))) throw new AssertionError();

        if ((!reallyGoodWorkout.contains(new MuscleGroup("feet")))) throw new AssertionError();

        if ((reallyGoodWorkout.contains(new MuscleGroup("arms")))) throw new AssertionError();

    }

    @Test
    /**
     * Test that I've set up {@link Workout#equals(Workout)} correctly for Java's
     * {@link Set#contains(Object)} method.
     */
    public void testSetSanity() {
        Workout gooderWorkout = new Workout("squat hard", "legs", "buttocks", "scrungus");

        if ((!gooderWorkout.muscleGroups.contains(new MuscleGroup("buttocks")))) {
            throw new AssertionError();
        }
    }

    @Test
    public void testWorkoutSolver() throws JSONException, IOException {

        String fileEquipment = stringFromFile(new File(getClass().getResource("/test_equipment_list.json").getFile()));
        String fileWorkout = stringFromFile(new File(getClass().getResource("/test_workout_types.json").getFile()));

        JSONObject equipmentJSON = new JSONObject(fileEquipment);
        JSONObject workoutJSON = new JSONObject(fileWorkout);

        WorkoutSolver workoutSolver = new WorkoutSolver(equipmentJSON, workoutJSON);

        HashSet<Equipment> availableEquipment = new HashSet<Equipment>() {{
            add(new Equipment("no equipment"));
        }};

        Set<Workout> workouts = workoutSolver.solve(new HashSet<Equipment>(), new MuscleGroup("calves"));

        for (Workout w : workouts) {
            System.out.println(w.toString());
        }
    }

}
