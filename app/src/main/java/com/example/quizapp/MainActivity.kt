package com.example.quizapp

import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quizapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private val question = arrayOf("Pregunta1?",
        "Pregunta2?",
        "Pregunta3?"
    )

    private val options = arrayOf(arrayOf("Respuesta1", "Respuesta2", "Respuesta3"),
        arrayOf("Respuesta1", "Respuesta2", "Respuesta3"),
        arrayOf("Respuesta1", "Respuesta2", "Respuesta3"),
    )

    private val correctAnswers = arrayOf(1,0,2)

    private var currentQuestionIndex = 0
    private var score = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun correctButtonColors (buttonIndex:Int){
        when (buttonIndex){
            0 -> binding.optionButton1.setBackgroundColor(Color.GREEN)
            1 -> binding.optionButton2.setBackgroundColor(Color.GREEN)
            2 -> binding.optionButton3.setBackgroundColor(Color.GREEN)

        }
    }

    private fun wrongButtonColors (buttonIndex:Int){
        when (buttonIndex){
            0 -> binding.optionButton1.setBackgroundColor(Color.RED)
            1 -> binding.optionButton2.setBackgroundColor(Color.RED)
            2 -> binding.optionButton3.setBackgroundColor(Color.RED)

        }
    }

    private fun resetButtonColors (buttonIndex:Int){
        when (buttonIndex){
            0 -> binding.optionButton1.setBackgroundColor(Color.rgb(50,59,96))
            1 -> binding.optionButton2.setBackgroundColor(Color.rgb(50,59,96))
            2 -> binding.optionButton3.setBackgroundColor(Color.rgb(50,59,96))

        }
    }
}