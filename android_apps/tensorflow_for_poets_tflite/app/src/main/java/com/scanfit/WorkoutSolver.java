package com.scanfit;

import android.app.Application;
import android.content.Context;

import com.google.common.collect.Sets;
import com.scanfit.modeling.fitness.Equipment;
import com.scanfit.modeling.fitness.MuscleGroup;
import com.scanfit.modeling.fitness.Workout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static com.scanfit.ScanFitLib.loadJSONFromAsset;
import static com.scanfit.ScanFitLib.stringListFromJsonArray;

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

    private void setupEquipmentTypes(JSONObject equipmentJSON) throws JSONException {
        Iterator<String> equipmentTypes = equipmentJSON.keys();

        // Setup from list of equipment.
        while (equipmentTypes.hasNext()) {

            // Get a single Equipment type, i.e. 'treadmill'.
            String equipmentType = equipmentTypes.next();

            Equipment equipment = new Equipment(equipmentType);


            // Get the list of workouts you can perform with the Equipment type.
            JSONArray workouts = (JSONArray) equipmentJSON.get(equipmentType);

            ArrayList<String> workoutMuscleGroups = stringListFromJsonArray(workouts);

//            System.out.println(equipmentType);

            for (String muscleGroup : workoutMuscleGroups) {
                equipment.getExerciseSet().add(muscleGroup);
//                System.out.println("    " + muscleGroup);
            }

            equipmentSet.add(equipment);

        }

    }

    private void setupWorkoutTypes(JSONObject workoutJSON) throws JSONException {


//        System.out.println("WORKOUT <--> MUSCLE GROUP");
        Iterator<String> workoutTypes = workoutJSON.keys();

        while (workoutTypes.hasNext()) {
            String workoutType = workoutTypes.next();
//            System.out.println(workoutType);

            JSONArray muscleGroups = workoutJSON.getJSONArray(workoutType);

            Workout singleWorkout = new Workout(workoutType);

            for (int i = 0; i < muscleGroups.length(); i++) {

                String singleMuscleGroupString = muscleGroups.getString(i);

                MuscleGroup singleMuscleGroup = new MuscleGroup(singleMuscleGroupString);

//                System.out.println("    " + singleMuscleGroupString);

                singleWorkout.muscleGroups.add(singleMuscleGroup);
                this.muscleGroupSet.add(singleMuscleGroup);
            }

            this.workoutSet.add(singleWorkout);
        }

        System.out.println();
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
     * Given a Workout, what is the Set of Equipment it can use?
     */
    public Set<Equipment> requiredEquipment(Workout w) {

        HashSet<Equipment> ret = new HashSet<>();

        for (Equipment equipment : this.equipmentSet) {
            if (equipment.getExerciseSet().contains(w.name)) {
                ret.add(equipment);
            }
        }

        return ret;
    }

    /***
     * Given available Equipment and a desired MuscleGroup, return a Set of all exercises that can
     * be performed.
     * @param availableEquipment The Set of Equipment available to you.
     * @param desiredMuscleGroup The MuscleGroup you wish to train.
     * @return A Set of Workouts that you can perform with your equipment.
     */
    public Set<Workout> solve(Set<Equipment> availableEquipment, MuscleGroup desiredMuscleGroup) {
        Set<Workout> candidates = new HashSet<>();

        System.out.printf("We want to train our '%s', and we have the following equipment available:%n", desiredMuscleGroup.toString());

        for (Equipment e : availableEquipment) {
            System.out.printf("\t%s%n", e.getName());
        }

        // Try all workouts.
        for (Workout potentialWorkout : this.workoutSet) {

            // If the workout affects a MuscleGroup we want to train...
            if (potentialWorkout.contains(desiredMuscleGroup)) {

                // The Set of any Equipment that can be used to do a particular Workout.
                Set<Equipment> requiredEquipment = this.requiredEquipment(potentialWorkout);

                /*
                See if we can perform this Workout given our equipment.
                If the intersection of the required and available equipment is not empty, we can!
                */
                if (!Sets.intersection(requiredEquipment, availableEquipment).isEmpty()) {
                    candidates.add(potentialWorkout);
                }
            }
        }

        return candidates;
    }
}
