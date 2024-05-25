package com.example.quizapp

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quizapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private val questions = arrayOf(
        "¿Cuál es el resultado de la siguiente ecuación: 2x + 5 = 11?",
        "¿Cuál es el valor de 'y' en la ecuación: 3y - 7 = 14?",
        "¿Cuál es la solución de la ecuación: x^2 - 4 = 0?",
        "¿Cuál es el resultado de la siguiente ecuación: 3(x + 2) = 15?",
        "¿Cuál es el valor de 'a' en la ecuación: 2(a - 3) = 10?",
        "¿Cuál es el resultado de la siguiente ecuación: 4x - 8 = 12?",
        "¿Cuál es el valor de 'b' en la ecuación: 5b + 10 = 35?",
        "¿Cuál es la solución de la ecuación: 2x^2 - 18 = 0?",
        "¿Cuál es el resultado de la siguiente ecuación: 3(2y - 4) = 6?",
        "¿Cuál es el valor de 'c' en la ecuación: 4(c + 5) = 24?"
    )

    private val options = arrayOf(
        arrayOf("3", "4", "5"),
        arrayOf("7", "8", "9"),
        arrayOf("2", "-2", "4"),
        arrayOf("3", "4", "5"),
        arrayOf("6", "7", "8"),
        arrayOf("5", "6", "7"),
        arrayOf("5", "6", "7"),
        arrayOf("3", "4", "5"),
        arrayOf("2", "3", "4"),
        arrayOf("3", "4", "5")
    )

    private val correctAnswers = arrayOf(1, 2, 1, 0, 1, 2, 1, 2, 0, 1)

    private val explanations = arrayOf(
        "Para resolver la ecuación 2x + 5 = 11, restamos 5 a ambos lados para obtener 2x = 6. Luego, dividimos por 2 para obtener x = 3.",
        "Para resolver la ecuación 3y - 7 = 14, sumamos 7 a ambos lados para obtener 3y = 21. Finalmente, dividimos por 3 para obtener y = 7.",
        "Para resolver la ecuación x^2 - 4 = 0, sumamos 4 a ambos lados para obtener x^2 = 4. Luego, tomamos la raíz cuadrada para obtener x = ±2.",
        "Para resolver la ecuación 3(x + 2) = 15, primero distribuimos el 3 para obtener 3x + 6 = 15. Luego, restamos 6 a ambos lados para obtener 3x = 9, y dividimos por 3 para obtener x = 3.",
        "Para resolver la ecuación 2(a - 3) = 10, primero distribuimos el 2 para obtener 2a - 6 = 10. Luego, sumamos 6 a ambos lados para obtener 2a = 16, y dividimos por 2 para obtener a = 8.",
        "Para resolver la ecuación 4x - 8 = 12, sumamos 8 a ambos lados para obtener 4x = 20. Luego, dividimos por 4 para obtener x = 5.",
        "Para resolver la ecuación 5b + 10 = 35, primero restamos 10 a ambos lados para obtener 5b = 25. Luego, dividimos por 5 para obtener b = 5.",
        "Para resolver la ecuación 2x^2 - 18 = 0, primero sumamos 18 a ambos lados para obtener 2x^2 = 18. Luego, dividimos por 2 para obtener x^2 = 9, y tomamos la raíz cuadrada para obtener x = ±3.",
        "Para resolver la ecuación 3(2y - 4) = 6, primero distribuimos el 3 para obtener 6y - 12 = 6. Luego, sumamos 12 a ambos lados para obtener 6y = 18, y dividimos por 6 para obtener y = 3.",
        "Para resolver la ecuación 4(c + 5) = 24, primero distribuimos el 4 para obtener 4c + 20 = 24. Luego, restamos 20 a ambos lados para obtener 4c = 4, y dividimos por 4 para obtener c = 1."
    )

    private var currentQuestionIndex = 0
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        displayQuestion()

        binding.optionButton1.setOnClickListener {
            checkAnswer(0)
        }
        binding.optionButton2.setOnClickListener {
            checkAnswer(1)
        }
        binding.optionButton3.setOnClickListener {
            checkAnswer(2)
        }

        binding.restartButton.setOnClickListener {
            restartQuiz()
        }

        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun correctButtonColors(buttonIndex: Int) {
        when (buttonIndex) {
            0 -> binding.optionButton1.setBackgroundColor(Color.GREEN)
            1 -> binding.optionButton2.setBackgroundColor(Color.GREEN)
            2 -> binding.optionButton3.setBackgroundColor(Color.GREEN)
        }
    }

    private fun wrongButtonColors(buttonIndex: Int) {
        when (buttonIndex) {
            0 -> binding.optionButton1.setBackgroundColor(Color.RED)
            1 -> binding.optionButton2.setBackgroundColor(Color.RED)
            2 -> binding.optionButton3.setBackgroundColor(Color.RED)
        }
    }

    private fun resetButtonColors() {
        val defaultColor = resources.getColor(R.color.default_button_color, theme)
        binding.optionButton1.setBackgroundColor(defaultColor)
        binding.optionButton2.setBackgroundColor(defaultColor)
        binding.optionButton3.setBackgroundColor(defaultColor)
    }

    private fun showResults() {
        Toast.makeText(this, "Your score: $score out of ${questions.size}", Toast.LENGTH_LONG).show()
        binding.restartButton.isEnabled = true
    }

    private fun displayQuestion() {
        binding.questionText.text = questions[currentQuestionIndex]
        binding.optionButton1.text = options[currentQuestionIndex][0]
        binding.optionButton2.text = options[currentQuestionIndex][1]
        binding.optionButton3.text = options[currentQuestionIndex][2]
        resetButtonColors()
        binding.explanationText.text = ""
    }

    private fun checkAnswer(selectedAnswerIndex: Int) {
        val correctAnswerIndex = correctAnswers[currentQuestionIndex]

        if (selectedAnswerIndex == correctAnswerIndex) {
            score++
            correctButtonColors(selectedAnswerIndex)
        } else {
            wrongButtonColors(selectedAnswerIndex)
            correctButtonColors(correctAnswerIndex)
        }

        binding.explanationText.text = explanations[currentQuestionIndex]

        if (currentQuestionIndex < questions.size - 1) {
            currentQuestionIndex++
            binding.questionText.postDelayed({ displayQuestion() }, 1000)
        } else {
            showResults()
        }
    }

    private fun restartQuiz() {
        currentQuestionIndex = 0
        score = 0
        displayQuestion()
        binding.restartButton.isEnabled = false
    }
}