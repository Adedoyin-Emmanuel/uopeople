package com.startup.bmicalculator

import android.graphics.Color

enum class BMIStatus(val message: String, val color: Int) {
    UNDERWEIGHT("You are underweight", Color.RED),
    NORMAL("Your weight is normal", Color.GREEN),
    OVERWEIGHT("You are overweight", Color.RED);

    companion object {
        fun fromValue(bmi: Float): BMIStatus = when {
            bmi < 18.5 -> UNDERWEIGHT
            bmi < 25 -> NORMAL
            else -> OVERWEIGHT
        }
    }
}
