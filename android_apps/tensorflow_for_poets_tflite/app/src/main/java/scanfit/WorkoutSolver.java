package scanfit;

import android.app.Application;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import scanfit.modeling.Equipment;
import scanfit.modeling.MuscleGroup;
import scanfit.modeling.Workout;

import static scanfit.ScanFitLib.loadJSONFromAsset;

@SuppressWarnings("unused")
public class WorkoutSolver extends Application {

    /**
     * The Set of all Equipment that exists.
     */
    public Set<Equipment> equipmentSet = new HashSet<>();

    /**
     * The Set of all MuscleGroups that exist.
     */
    public Set<MuscleGroup> muscleGroupSet = new HashSet<>();

    /**
     * The Set of all Workouts that exist.
     */
    public Set<Workout> workoutSet = new HashSet<>();


    private Context mContext;

    public WorkoutSolver(JSONObject equipmentJson, JSONObject workoutJson) throws JSONException {
        this.setup(equipmentJson, workoutJson);
    }


    public WorkoutSolver(String equipmentJsonPath, String workoutJsonPath) {

        String equipmentJsonData;
        String workoutJsonData;

        try {
            equipmentJsonData = loadJSONFromAsset(mContext, equipmentJsonPath);
            workoutJsonData = loadJSONFromAsset(mContext, workoutJsonPath);

            this.setup(new JSONObject(equipmentJsonData), new JSONObject(workoutJsonData));

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public void setupEquipmentTypes(JSONObject equipmentJSON) throws JSONException {
        Iterator<String> equipmentTypes = equipmentJSON.keys();

        // Setup from list of equipment.
        while (equipmentTypes.hasNext()) {

            // Get a single Equipment type, i.e. 'treadmill'.
            String equipmentType = equipmentTypes.next();
            equipmentSet.add(new Equipment(equipmentType));

            // Get the list of workouts you can perform with the Equipment type.
            JSONArray workouts = (JSONArray) equipmentJSON.get(equipmentType);

            ArrayList<String> workoutMuscleGroups = new ArrayList<>();

            for (int i = 0; i < workouts.length(); i++) {
                workoutMuscleGroups.add(workouts.getString(i));
            }

            System.out.println(equipmentType);
        }

    }

    private void setupWorkoutTypes(JSONObject workoutJSON) {

        Iterator<String> workoutTypes = workoutJSON.keys();

        while (workoutTypes.hasNext()) {
            String workoutType = workoutTypes.next();
            System.out.println(workoutType);
        }
    }


    public void setup(JSONObject equipmentJSON, JSONObject workoutJSON) throws JSONException {
        setupWorkoutTypes(workoutJSON);

        setupEquipmentTypes(equipmentJSON);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    /***
     * Given available equipment and a desired muscle group, return a Set of all exercises that can
     * be performed.
     * @param availableEquipment The Set of Equipment available to you.
     * @param desiredMuscleGroup The MuscleGroup you wish to train.
     * @return A Set of Workouts that you can perform with your equipment.
     */
    public Set<Workout> solve(Set<Equipment> availableEquipment, MuscleGroup desiredMuscleGroup) {
        Set<Workout> candidates = new HashSet<>();

        return candidates;
    }
}
