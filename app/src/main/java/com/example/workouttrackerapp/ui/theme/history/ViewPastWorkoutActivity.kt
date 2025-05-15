package com.example.workouttrackerapp.ui.history

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.workouttrackerapp.ui.theme.WorkoutTrackerAppTheme
import com.example.workouttrackerapp.ui.theme.history.ViewPastWorkoutScreen
import com.example.workouttrackerapp.ui.viewmodel.WorkoutViewModel

class ViewPastWorkoutActivity : ComponentActivity() {

    private val viewModel: WorkoutViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val workoutHistoryId = intent.getIntExtra("workoutHistoryId", -1)

        setContent {
            WorkoutTrackerAppTheme {
                ViewPastWorkoutScreen(
                    viewModel = viewModel,
                    workoutHistoryId = workoutHistoryId
                )
            }
        }
    }
}
