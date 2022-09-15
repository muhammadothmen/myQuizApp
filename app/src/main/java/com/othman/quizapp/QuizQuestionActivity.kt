package com.othman.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat


class QuizQuestionActivity : AppCompatActivity(), View.OnClickListener{

    private var randomQuestionList : List<Questions>? =null
    private var randomOptions : List<String>? =null
    private var mCurrentPosition : Int = 1
    private var mCorrectAnswers : Int = 0
    private var selected : Boolean = false
    private var mSelected : Boolean = false
    private var mSelectedOptionPosition : Int = 0
    private var mSelectedOption : String = ""
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
        val options = arrayListOf(optionOne,optionTow,optionThree,optionFour,submitBtn)
        for (option in options) {
            option?.setOnClickListener(this)
        }
        randomQuestionList = Constants.getQuestions().shuffled()
        setQuestion()
    }

    private fun setQuestion() {
        defaultOptionView()
        val question: Questions = randomQuestionList!![mCurrentPosition - 1]
        progressBar?.progress = mCurrentPosition
        progressText?.text = "$mCurrentPosition/${progressBar?.max}"
        questionText?.text = question.question
        randomOptions = listOf(question.optionOne,question.optionTwo,question.optionThree,question.optionFour).shuffled()
        optionOne?.text = randomOptions!![0]
        optionTow?.text = randomOptions!![1]
        optionThree?.text = randomOptions!![2]
        optionFour?.text = randomOptions!![3]
        image?.setImageResource(question.image)
        if (mCurrentPosition == randomQuestionList!!.size){
            submitBtn?.text = "Finish"
        }else{
            submitBtn?.text = "Skip"
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

    private fun selectedOptionView(tv : TextView,selected: Int){
        defaultOptionView()
        mSelectedOptionPosition = selected
        mSelectedOption = tv.text.toString()
        tv.setTextColor(Color.parseColor("#363a43"))
        tv.setTypeface(tv.typeface,Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this,R.drawable.seleted_option_border_bg)
        mSelected = true
        submitBtn?.text = "Check"
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.optionOne -> { if (!selected) optionOne?.let { selectedOptionView(it,1) } }
            R.id.optionTwo -> { if (!selected) optionTow?.let { selectedOptionView(it,2) } }
            R.id.optionThree -> { if (!selected) optionThree?.let { selectedOptionView(it,3) } }
            R.id.optionFour -> { if (!selected) optionFour?.let { selectedOptionView(it,4) } }
            R.id.btnSubmit -> {
                if (!mSelected){
                    mCurrentPosition++
                    selected = false
                    when {
                        mCurrentPosition <= randomQuestionList!!.size -> {setQuestion()}
                        else ->{
                            val intent = Intent(this,TheResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME,mUserName)
                            intent.putExtra(Constants.CORRECT_ANSWERS,mCorrectAnswers)
                            intent.putExtra(Constants.TOTAL_QUESTIONS,randomQuestionList?.size)
                            startActivity(intent)
                            finish()
                    }
                    }
                }else {
                    val question = randomQuestionList!![mCurrentPosition - 1]
                    if (question.correctAnswer != mSelectedOption) {
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                    }else{
                        mCorrectAnswers++
                    }
                    when (question.correctAnswer) {
                        optionOne?.text -> answerView(1, R.drawable.correct_option_border_bg)
                        optionTow?.text -> answerView(2, R.drawable.correct_option_border_bg)
                        optionThree?.text -> answerView(3, R.drawable.correct_option_border_bg)
                        optionFour?.text -> answerView(4, R.drawable.correct_option_border_bg)
                    }
                    selected = true
                    submitBtn?.text = "Next"
                 /*   if (mCurrentPosition == mQuestionList!!.size){
                        submitBtn?.text = "Finish"
                    }else{
                        submitBtn?.text = "Go to the next question"
                    }*/
                    mSelected = false

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