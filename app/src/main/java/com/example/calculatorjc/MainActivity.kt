package com.example.calculatorjc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculatorjc.ui.theme.CalculadoraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculadoraTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    CalculadoraScreen()
                }
            }
        }
    }
}

@Composable
fun CalculadoraScreen() {
    var displayText by remember { mutableStateOf("0") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Display(text = displayText)
        Spacer(modifier = Modifier.height(16.dp))
        Teclado(onButtonClick = { button ->
            displayText = handleInput(displayText, button)
        })
    }
}

@Composable
fun Display(text: String) {
    Box(
        contentAlignment = Alignment.CenterEnd,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(24.dp)
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.End
        )
    }
}

@Composable
fun Teclado(onButtonClick: (String) -> Unit) {
    val buttons = listOf(
        listOf("7", "8", "9", "÷"),
        listOf("4", "5", "6", "×"),
        listOf("1", "2", "3", "-"),
        listOf("C", "0", ".", "+"),
        listOf("=")
    )

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        for (row in buttons) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for (buttonText in row) {
                    Button(onClick = { onButtonClick(buttonText) }) {
                        Text(text = buttonText)
                    }
                }
            }
        }
    }
}

fun handleInput(currentText: String, input: String): String {
    return when (input) {
        "C" -> "0"
        "=" -> calculateResult(currentText)
        "." -> if (currentText.contains(".")) currentText else currentText + input
        "÷", "×", "-", "+" -> if (currentText.endsWith("÷") || currentText.endsWith("×") || currentText.endsWith("-") || currentText.endsWith("+")) {
            currentText.dropLast(1) + input
        } else {
            currentText + input
        }
        else -> if (currentText == "0") input else currentText + input
    }
}

fun calculateResult(expression: String): String {
    return try {
        val replacedExpression = expression
            .replace("÷", "/")
            .replace("×", "*")

        val result = evaluateExpression(replacedExpression)
        if (result % 1 == 0.0) {
            result.toInt().toString()
        } else {
            result.toString()
        }
    } catch (e: Exception) {
        "Erro"
    }
}

fun evaluateExpression(expression: String): Double {
    val tokens = expression.split("(?<=[-+/])|(?=[-+/])".toRegex()).filter { it.isNotBlank() }
    var result = tokens.first().toDouble()

    var i = 1
    while (i < tokens.size) {
        val operator = tokens[i]
        val nextNumber = tokens[i + 1].toDouble()

        result = when (operator) {
            "+" -> result + nextNumber
            "-" -> result - nextNumber
            "*" -> result * nextNumber
            "/" -> result / nextNumber
            else -> result
        }
        i += 2
    }

    return result
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CalculadoraTheme {
        CalculadoraScreen()
    }
}