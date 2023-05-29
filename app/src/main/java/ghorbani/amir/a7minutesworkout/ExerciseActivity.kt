package ghorbani.amir.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.core.graphics.green
import ghorbani.amir.a7minutesworkout.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {

    private var binding : ActivityExerciseBinding? = null
    private var restTimer : CountDownTimer? = null
    private var restProgress = 0

    private var exerciseList : ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarExercise)

        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.toolbarExercise?.setNavigationOnClickListener {
            onBackPressed()
        }

        exerciseList = Constants.defaultExerciseList()

        setupRestView()
    }

    private fun setupRestView(){
        if(restTimer != null){
            restTimer?.cancel()
            restProgress = 0
        }
        setRestProgressBar()
    }

    private fun setRestProgressBar(){
        binding?.progressBar?.progress = restProgress
        restTimer = object : CountDownTimer(10000, 1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressBar?.progress = 10 - restProgress
                binding?.tvTimer?.text = (10 - restProgress).toString()
            }
            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity, "Now we start the exercise",
                    Toast.LENGTH_LONG).show()
                currentExercisePosition++
                when(restProgress){
                    10 -> restTimer?.let {
                        restProgress = 0
                        binding?.progressBar?.max = 30
                        binding?.tvTitle?.text = "Exercise Name"
                        object : CountDownTimer(30000, 1000){
                            override fun onTick(millisUntilFinished: Long) {
                                restProgress++
                                binding?.progressBar?.progress = 30 - restProgress
                                binding?.tvTimer?.text = (30 - restProgress).toString()
                            }

                            override fun onFinish() {
                                Toast.makeText(this@ExerciseActivity, "Exercise is finished",
                                    Toast.LENGTH_LONG).show()
                            }
                        }.start()
                    }
                }
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        if(restTimer != null){
            restTimer?.cancel()
            restProgress = 0
        }
        binding = null
    }
}