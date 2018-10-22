package scanfit;

import android.app.Application;
import android.content.Context;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import scanfit.modeling.Equipment;
import scanfit.modeling.MuscleGroup;
import scanfit.modeling.Workout;

import static scanfit.ScanFitLib.loadJSONFromAsset;

public class WorkoutSolver extends Application {

    private static Context mContext;
    public Set<Equipment> equipmentSet;
    public Set<MuscleGroup> muscleGroupSet;
    public Set<Workout> workoutSet;

    public WorkoutSolver() {
    }


    public WorkoutSolver(String equipmentJsonPath, String workoutJsonPath) {

        String equipmentJsonData;
        String workoutJsonData;

        try {
            equipmentJsonData = loadJSONFromAsset(mContext, equipmentJsonPath);
            workoutJsonData = loadJSONFromAsset(mContext, workoutJsonPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    /***
     * Given available equipment and desired muscle groups, return a Set of all exercises that can
     * be performed.
     * @param availableEquipment The Set of Equipment available to you.
     * @param desiredMuscleGroups The Set of MuscleGroup(s) you wish to train.
     * @return A Set of Workouts that you can perform with your equipment.
     */
    public Set<Workout> solve(Set<Equipment> availableEquipment, Set<MuscleGroup> desiredMuscleGroups) {
        Set<Workout> candidates = new HashSet<>();

        return candidates;
    }
}
