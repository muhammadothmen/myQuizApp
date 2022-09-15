package com.othman.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class TheResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_the_result)

        val name : TextView = findViewById(R.id.name)
        val score : TextView = findViewById(R.id.score)
        val congratulations : TextView = findViewById(R.id.congratulations)
        val trophy : ImageView = findViewById(R.id.trophy)
        val btnFinish : Button = findViewById(R.id.btnFinish)
        name.text = intent.getStringExtra(Constants.USER_NAME)
        val totalQuestions = intent.getIntExtra(Constants.TOTAL_QUESTIONS,0)
        val correctAnswers = intent.getIntExtra(Constants.CORRECT_ANSWERS,0)
        if (correctAnswers > 5){
            congratulations.text = "Hey, congratulation"
            trophy.setImageResource(R.drawable.ic_trophy)
        }else{
            congratulations.text = "Hey, bad luck"
            trophy.setImageResource(R.drawable.badluck)
        }
        score.text = "Your score is $correctAnswers out of $totalQuestions"
        btnFinish.setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }
}