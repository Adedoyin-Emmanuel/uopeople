package com.startup.bmicalculator

class Person(val weight: Float, val height: Float, val gender: Gender) {
    fun calculateBMI(): Float {
        // Height in meters
        return weight / (height * height)
    }
}
