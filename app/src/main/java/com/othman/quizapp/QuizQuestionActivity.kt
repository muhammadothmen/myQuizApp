package com.othman.quizapp

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat

class QuizQuestionActivity : AppCompatActivity(), View.OnClickListener{

    private var mQuestionList : ArrayList<Questions>? = null
    private var mCurrentPosition : Int = 1
    private var mCorrectAnswers : Int = 0

    private var mSelectedOptionPosition : Int =0
    private var mUserName : String? = null
    private var progressBar : ProgressBar? = null
    private var progressText : TextView? = null
    private var optionOne : TextView? = null
    private var optionTow : TextView? = null
    private var optionThree : TextView? = null
    private var optionFour : TextView? = null
    private var submitBtn : Button? = null
    private var questionText : TextView? = null
    private var image : ImageView? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_question)

        mUserName = intent.getStringExtra(Constants.USER_NAME)
        progressText = findViewById(R.id.progressText)
        progressBar = findViewById(R.id.progressBar)
        optionOne = findViewById(R.id.optionOne)
        optionTow = findViewById(R.id.optionTwo)
        optionThree = findViewById(R.id.optionThree)
        optionFour = findViewById(R.id.optionFour)
        image = findViewById(R.id.image)
        questionText = findViewById(R.id.question)
        submitBtn = findViewById(R.id.btnSubmit)

        optionOne?.setOnClickListener(this)
        optionTow?.setOnClickListener(this)
        optionThree?.setOnClickListener(this)
        optionFour?.setOnClickListener(this)
        submitBtn?.setOnClickListener(this)
        mQuestionList = Constants.getQuestions()
        setQuestion()
    }

    private fun setQuestion() {

        defaultOptionView()
        var question: Questions = mQuestionList!![mCurrentPosition - 1]
        progressBar?.progress = mCurrentPosition
        progressText?.text = "$mCurrentPosition/${progressBar?.max}"
        questionText?.text = question.question
        optionOne?.text = question.optionOne
        optionTow?.text = question.optionTwo
        optionThree?.text = question.optionThree
        optionFour?.text = question.optionFour
        image?.setImageResource(question.image)

        if (mCurrentPosition == mQuestionList!!.size){
            submitBtn?.text = "Finish"
        }else{
            submitBtn?.text = "Next"
        }

    }

    private fun defaultOptionView(){

        val options = arrayListOf(optionOne,optionTow,optionThree,optionFour)
        for (option in options){
            option?.setTextColor(Color.parseColor("#7a8089"))
            option?.typeface = Typeface.DEFAULT
            option?.background = ContextCompat.getDrawable(this,R.drawable.default_option_border_bg)

        }

    }

    private fun selectedOptionView(tv : TextView,selectedOptionNum : Int){
        defaultOptionView()
        mSelectedOptionPosition = selectedOptionNum
        tv.setTextColor(Color.parseColor("#363a43"))
        tv.setTypeface(tv.typeface,Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this,R.drawable.seleted_option_border_bg)


    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.optionOne -> { optionOne?.let { selectedOptionView(it,1) } }
            R.id.optionTwo -> { optionTow?.let { selectedOptionView(it,2) } }
            R.id.optionThree -> { optionThree?.let { selectedOptionView(it,3) } }
            R.id.optionFour -> { optionFour?.let { selectedOptionView(it,4) } }
            R.id.btnSubmit -> {

                if (mSelectedOptionPosition == 0){
                    mCurrentPosition++
                    when {
                        mCurrentPosition <= mQuestionList!!.size -> {setQuestion()}
                        else ->{
                            val intent = Intent(this,TheResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME,mUserName)
                            intent.putExtra(Constants.CORRECT_ANSWERS,mCorrectAnswers)
                            intent.putExtra(Constants.TOTAL_QUESTIONS,mQuestionList?.size)
                            startActivity(intent)
                            finish()

                    }
                    }
                }else {
                    var question = mQuestionList?.get(mCurrentPosition - 1)
                    if (question!!.correctAnswer != mSelectedOptionPosition) {
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                    }else{
                        mCorrectAnswers++
                    }
                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)
                    if (mCurrentPosition == mQuestionList!!.size){
                        submitBtn?.text = "Finish"
                    }else{
                        submitBtn?.text = "Go to the next question"
                    }
                    mSelectedOptionPosition = 0
                }
            }

        }
    }

    private fun answerView (answer : Int , drawableView : Int){

        when(answer){

            1 -> {optionOne?.background = ContextCompat.getDrawable(this,drawableView)}
            2 -> {optionTow?.background = ContextCompat.getDrawable(this,drawableView)}
            3 -> {optionThree?.background = ContextCompat.getDrawable(this,drawableView)}
            4 -> {optionFour?.background = ContextCompat.getDrawable(this,drawableView)}

        }

    }

}