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

    private val questions = arrayOf("Pregunta1?",
        "Pregunta2?",
        "Pregunta3?"
    )

    private val options = arrayOf(arrayOf("Respuesta1", "Respuesta2", "Respuesta3"),
        arrayOf("Respuesta1", "Respuesta2", "Respuesta3"),
        arrayOf("Respuesta1", "Respuesta2", "Respuesta3")
    )

    private val correctAnswers = arrayOf(1,0,2)
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