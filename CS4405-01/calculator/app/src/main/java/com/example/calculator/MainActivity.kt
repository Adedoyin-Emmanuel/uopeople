package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.calculator.ui.theme.CalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CalculatorScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun CalculatorScreen(modifier: Modifier = Modifier) {
    val calculator = Calculator(10.0, 5.0)
    val additionResult = calculator.performOperation(Operations.ADD)
    val subtractionResult = calculator.performOperation(Operations.SUBTRACT)
    val multiplicationResult = calculator.performOperation(Operations.MULTIPLY)
    val divisionResult = calculator.performOperation(Operations.DIVIDE)

    Column(modifier = modifier) {
        Text(text = "Operand 1: 10.0")
        Text(text = "Operand 2: 5.0")
        Text(text = "Addition: $additionResult")
        Text(text = "Subtraction: $subtractionResult")
        Text(text = "Multiplication: $multiplicationResult")
        Text(text = "Division: $divisionResult")
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorScreenPreview() {
    CalculatorTheme {
        CalculatorScreen()
    }
}

enum class Operations {
    ADD,
    SUBTRACT,
    MULTIPLY,
    DIVIDE
}

class Calculator(private val operand1: Double, private val operand2: Double) {
    fun performOperation(operation: Operations): Double {
        return when (operation) {
            Operations.ADD -> operand1 + operand2
            Operations.SUBTRACT -> operand1 - operand2
            Operations.MULTIPLY -> operand1 * operand2
            Operations.DIVIDE -> {
                if (operand2 == 0.0) {
                    throw IllegalArgumentException("Division by zero is not allowed.")
                }
                operand1 / operand2
            }
        }
    }
}