package scanfit.modeling;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.HashMap;

public class Equipment implements Serializable, Comparable<Equipment> {
    public String name;

    public Equipment(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(@NonNull Equipment o) {
        return name.compareTo(o.name);
    }
}
