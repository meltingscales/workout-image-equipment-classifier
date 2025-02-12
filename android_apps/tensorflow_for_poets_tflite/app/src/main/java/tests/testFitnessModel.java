package tests;

import com.scanfit.WorkoutSolver;
import com.scanfit.modeling.fitness.Equipment;
import com.scanfit.modeling.fitness.MuscleGroup;
import com.scanfit.modeling.fitness.Workout;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

import static com.scanfit.ScanFitLib.stringFromFile;

public class testFitnessModel extends TestCase {

    JSONObject equipmentJSON;
    JSONObject workoutJSON;
    WorkoutSolver workoutSolver;

    @Override
    @Before
    public void setUp() throws FileNotFoundException, JSONException {
        equipmentJSON = new JSONObject(stringFromFile(new File(getClass().getResource("/test_equipment_list.json").getFile())));
        workoutJSON = new JSONObject(stringFromFile(new File(getClass().getResource("/test_workout_types.json").getFile())));

        workoutSolver = new WorkoutSolver(equipmentJSON, workoutJSON);
    }

    @Override
    @After
    public void tearDown() {

    }


    @Test
    public void testWorkout() {
        Workout reallyGoodWorkout = new Workout("run really fast", "legs", "feet");

        assertTrue(reallyGoodWorkout.contains(new MuscleGroup("legs")));
        assertTrue(reallyGoodWorkout.contains(new MuscleGroup("feet")));

        assertFalse(reallyGoodWorkout.contains(new MuscleGroup("arms")));

    }

    @Test
    /**
     * Test that I've set up {@link Workout#equals(Workout)} correctly for Java's
     * {@link Set#contains(Object)} method.
     */
    public void testSetSanity() {
        Workout gooderWorkout = new Workout("squat hard", "legs", "buttocks", "scrungus");

        assertTrue(gooderWorkout.muscleGroups.contains(new MuscleGroup("buttocks")));

        assertFalse(gooderWorkout.muscleGroups.contains(new MuscleGroup("nose")));
    }

    @Test
    public void testRequiredEquipment() {

        Workout walkToDaPark = new Workout("walking", "thighs", "legs", "calves");
        Equipment noEquipment = new Equipment("no equipment", "walking", "jogging", "running", "sit-ups", "push-ups");

        //We should be able to perform 'walking' with 'no equipment'.
        assertTrue(workoutSolver.requiredEquipment(walkToDaPark).contains(noEquipment));
    }

    @Test
    public void testWorkoutSolver() throws JSONException {


        HashSet<Equipment> availableEquipment = new HashSet<Equipment>() {{
            add(new Equipment("no equipment",
                    "walking",
                    "jogging",
                    "running",
                    "sit-ups",
                    "push-ups"));
        }};

        System.out.println("Available workouts:");
        Set<Workout> workouts = workoutSolver.solve(availableEquipment, new MuscleGroup("calves"));

        for (Workout w : workouts) {
            System.out.println(w.toString());
        }


        // It SHOULD contain walking and jogging, as it uses 'no equipment'.
        assertTrue(workouts.contains(new Workout("walking", "thighs", "legs", "calves")));
        assertTrue(workouts.contains(new Workout("jogging", "thighs", "legs", "calves")));

        // It should NOT contain 'rowing', as we have NO EQUIPMENT.
        assertTrue(!workouts.contains(new Workout("rowing", "thighs", "legs", "calves")));
    }

}
