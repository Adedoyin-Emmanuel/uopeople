package com.example.bmiapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bmiapp.ui.theme.BmiappTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BmiappTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BmiCalculatorScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

enum class BmiCategory {
    UNDERWEIGHT,
    NORMAL,
    OVERWEIGHT
}

data class BmiResult(val bmi: Float, val category: BmiCategory)

class BmiCalculator {
    fun calculateBmi(weightKg: Float, heightCm: Float): BmiResult? {
        if (heightCm <= 0f) return null
        val heightM = heightCm / 100
        val bmi = weightKg / (heightM * heightM)
        val category = when {
            bmi < 18.5f -> BmiCategory.UNDERWEIGHT
            bmi < 25f -> BmiCategory.NORMAL
            else -> BmiCategory.OVERWEIGHT
        }
        return BmiResult(bmi, category)
    }
}

@Composable
fun BmiCalculatorScreen(modifier: Modifier = Modifier) {
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var bmiResult by remember { mutableStateOf<BmiResult?>(null) }
    val bmiCalculator = remember { BmiCalculator() }

    Column(
        modifier = modifier.padding(16.dp).fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = weight,
            onValueChange = { weight = it },
            label = { Text("Weight (kg)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = height,
            onValueChange = { height = it },
            label = { Text("Height (cm)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val weightKg = weight.toFloatOrNull()
            val heightCm = height.toFloatOrNull()
            if (weightKg != null && heightCm != null) {
                bmiResult = bmiCalculator.calculateBmi(weightKg, heightCm)
            }
        }) {
            Text("Calculate BMI")
        }
        Spacer(modifier = Modifier.height(16.dp))
        bmiResult?.let { result ->
            val (message, color) = when (result.category) {
                BmiCategory.UNDERWEIGHT -> "You are underweight" to Color.Red
                BmiCategory.NORMAL -> "Your weight is normal" to Color.Green
                BmiCategory.OVERWEIGHT -> "You are overweight" to Color.Red
            }
            Text(
                text = "Your BMI is %.1f".format(result.bmi),
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = message,
                color = color,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BmiCalculatorScreenPreview() {
    BmiappTheme {
        BmiCalculatorScreen()
    }
}
