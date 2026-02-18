package com.startup.bmicalculator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
import android.view.View
import android.graphics.Color

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val weightInput = findViewById<EditText>(R.id.weightInput)
        val heightInput = findViewById<EditText>(R.id.heightInput)
        val genderGroup = findViewById<RadioGroup>(R.id.genderGroup)
        val calculateBtn = findViewById<Button>(R.id.calculateBtn)
        val resultText = findViewById<TextView>(R.id.resultText)

        calculateBtn.setOnClickListener {
            val weight = weightInput.text.toString().toFloatOrNull()
            val height = heightInput.text.toString().toFloatOrNull()
            val genderId = genderGroup.checkedRadioButtonId
            val gender = if (genderId == R.id.maleRadio) Gender.MALE else Gender.FEMALE

            if (weight == null || height == null) {
                resultText.text = "Please enter valid weight and height."
                resultText.setTextColor(Color.RED)
                return@setOnClickListener
            }

            val person = Person(weight, height, gender)
            val bmi = person.calculateBMI()
            val status = BMIStatus.fromValue(bmi)
            resultText.text = "BMI: %.2f\n%s".format(bmi, status.message)
            resultText.setTextColor(status.color)
        }
    }
}
