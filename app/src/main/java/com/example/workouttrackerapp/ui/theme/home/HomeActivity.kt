package com.example.workouttrackerapp.ui.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.workouttrackerapp.ui.viewmodel.ExerciseViewModel
import com.example.workouttrackerapp.ui.viewmodel.WorkoutViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.workouttrackerapp.ui.navigation.AppNavHost
import com.example.workouttrackerapp.ui.theme.WorkoutTrackerAppTheme

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkoutTrackerAppTheme {
                val viewModel: WorkoutViewModel = viewModel()
                val exerciseViewModel: ExerciseViewModel = viewModel()

                androidx.compose.runtime.LaunchedEffect(Unit) {
                    exerciseViewModel.prepopulateExercisesIfNeeded()
                }

                val navController = rememberNavController()
                AppNavHost(navController = navController)
            }
        }
    }
}
