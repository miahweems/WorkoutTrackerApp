package com.example.workouttrackerapp.ui.workout

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.example.workouttrackerapp.ui.history.HistoryActivity
import com.example.workouttrackerapp.ui.theme.WorkoutTrackerAppTheme
import com.example.workouttrackerapp.ui.theme.workout.StartWorkoutScreen
import com.example.workouttrackerapp.ui.viewmodel.WorkoutViewModel

class StartWorkoutActivity : ComponentActivity() {

    private val viewModel: WorkoutViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val workoutId = intent.getIntExtra("workoutId", -1)

        setContent {
            WorkoutTrackerAppTheme {
                StartWorkoutScreen(
                    workoutId = workoutId,
                    viewModel = viewModel,
                    onComplete = {
                        val intent = Intent(this, HistoryActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                )
            }
        }
    }
}
