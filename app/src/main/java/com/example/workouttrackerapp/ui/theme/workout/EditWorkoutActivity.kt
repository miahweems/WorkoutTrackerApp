package com.example.workouttrackerapp.ui.workout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.workouttrackerapp.ui.components.TopNavBar
import com.example.workouttrackerapp.ui.theme.WorkoutTrackerAppTheme
import com.example.workouttrackerapp.ui.viewmodel.WorkoutViewModel
import com.example.workouttrackerapp.ui.viewmodel.ExerciseViewModel
import com.example.workouttrackerapp.ui.theme.workout.EditWorkoutScreen

class EditWorkoutActivity : ComponentActivity() {

    private val viewModel: WorkoutViewModel by viewModels()
    private val exerciseViewModel: ExerciseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val workoutId = intent.getIntExtra("workoutId", -1)

        setContent {
            WorkoutTrackerAppTheme {
                val navController = rememberNavController()
                Scaffold(
                    topBar = {
                        TopNavBar(
                            navController = navController,
                            currentScreen = "Edit Workout",
                            canNavigateBack = true,
                            onBackClick = { navController.popBackStack() }
                        )
                    }
                ) { innerPadding ->
                    EditWorkoutScreen(
                        workoutId = workoutId,
                        viewModel = viewModel,
                        exerciseViewModel = exerciseViewModel,
                        onWorkoutUpdated = { finish() },
                        onSave = { finish() },
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}