package alex.carcar.geoquiz

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var questionTextView: TextView

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    private var currentIndex = 0

    // --- Nonexistent State
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.prev_button)
        questionTextView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener {
            checkAnswer(true)
        }

        falseButton.setOnClickListener {
            checkAnswer(false)
        }

        nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

        prevButton.setOnClickListener {
            if (currentIndex == 0) {
                currentIndex = questionBank.size - 1
            } else {
                currentIndex--
            }
            updateQuestion()
        }

        questionTextView.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

        updateQuestion()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    // --- Stopped State
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    // --- Paused State
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    // --- Resumed State
    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        val q:Question = questionBank[currentIndex]
        trueButton.setBackgroundColor(Color.TRANSPARENT)
        falseButton.setBackgroundColor(Color.TRANSPARENT)
        questionTextView.setText(questionTextResId)
        if (q.answered) {
            trueButton.isEnabled = false
            falseButton.isEnabled = false
            if (q.points == 1) { // answered the answer correctly
                if (q.userAnswer!!) {
                    trueButton.setBackgroundColor(Color.GREEN)
                } else {
                    falseButton.setBackgroundColor(Color.GREEN)
                }
            } else {
                if (q.userAnswer!!) {
                    trueButton.setBackgroundColor(Color.RED)
                } else {
                    falseButton.setBackgroundColor(Color.RED)
                }
            }
        } else {
            trueButton.isEnabled = true
            falseButton.isEnabled = true
        }
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer
        val messageResId: Int?
        questionBank[currentIndex].answered = true
        questionBank[currentIndex].userAnswer = userAnswer
        if (userAnswer == correctAnswer) {
            messageResId = R.string.correct_toast
            questionBank[currentIndex].points = 1
        } else {
            messageResId = R.string.incorrect_toast
            questionBank[currentIndex].points = 0
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
        trueButton.isEnabled = false
        falseButton.isEnabled = false
        updateQuestion()
    }

}
