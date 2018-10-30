package com.scanfit.modeling

import java.io.Serializable

class MuscleGroup(var name: String) : Serializable {

    fun equals(other: MuscleGroup): Boolean {
        return this.name.equals(other.name)
    }

    override fun equals(other: Any?): Boolean {
        try {
            return (other as MuscleGroup).equals(this)
        } catch (e: ClassCastException) {
            return other == this
        }

    }

    override fun hashCode(): Int {
        return name.hashCode()
    }

    override fun toString(): String {
        return name
    }
}
