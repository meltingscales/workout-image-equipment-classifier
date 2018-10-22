import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import scanfit.modeling.MuscleGroup;
import scanfit.modeling.Workout;

public class testFitnessModel {

    @Test
    public void testWorkout() {
        Workout reallyGoodWorkout = new Workout("run really fast", "legs", "feet");

        assert (reallyGoodWorkout.contains(new MuscleGroup("legs")));

        assert (reallyGoodWorkout.contains(new MuscleGroup("feet")));

        assert (!reallyGoodWorkout.contains(new MuscleGroup("arms")));
    }


}
