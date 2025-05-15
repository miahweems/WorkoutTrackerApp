package com.example.workouttrackerapp.ui.workout

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.workouttrackerapp.data.model.Exercise
import com.example.workouttrackerapp.ui.theme.WorkoutTrackerAppTheme
import com.example.workouttrackerapp.ui.viewmodel.ExerciseViewModel
import com.example.workouttrackerapp.ui.viewmodel.WorkoutViewModel

class AddWorkoutActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkoutTrackerAppTheme {
                val workoutViewModel: WorkoutViewModel = viewModel()
                val exerciseViewModel: ExerciseViewModel = viewModel()
                AddWorkoutScreen(
                    workoutViewModel = workoutViewModel,
                    exerciseViewModel = exerciseViewModel,
                    onWorkoutAdded = { finish() }
                )
            }
        }
    }
}

@Composable
fun AddWorkoutScreen(
    workoutViewModel: WorkoutViewModel,
    exerciseViewModel: ExerciseViewModel,
    onWorkoutAdded: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var workoutName by remember { mutableStateOf("") }
    var selectedExercises by remember { mutableStateOf(listOf<String>()) }


    val availableExercises by exerciseViewModel.allExercises.observeAsState(emptyList())
    val groupedExercises = availableExercises.groupBy { it.muscleGroup }


    LaunchedEffect(availableExercises) {
        Log.d("EXERCISE_DEBUG", "Loaded exercises: $availableExercises")
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text("Create Workout", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = workoutName,
            onValueChange = { workoutName = it },
            label = { Text("Workout Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (workoutName.isNotBlank() && selectedExercises.isNotEmpty()) {
                    val exerciseObjs = selectedExercises.map {
                        Exercise(
                            workoutOwnerId = 0,
                            name = it,
                            sets = 3,
                            reps = 10,
                            weight = 0f
                        )
                    }
                    workoutViewModel.addWorkout(workoutName, exerciseObjs)
                    onWorkoutAdded()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Workout")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Select Exercises", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            groupedExercises.forEach { (muscleGroup, exercises) ->
                item {
                    Text(
                        text = muscleGroup,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                items(exercises) { availableExercise ->
                    val exerciseName = availableExercise.name
                    val isSelected = selectedExercises.contains(exerciseName)

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedExercises = if (isSelected) {
                                    selectedExercises - exerciseName
                                } else {
                                    selectedExercises + exerciseName
                                }
                            }
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(exerciseName)
                        Checkbox(
                            checked = isSelected,
                            onCheckedChange = {
                                selectedExercises = if (it) {
                                    selectedExercises + exerciseName
                                } else {
                                    selectedExercises - exerciseName
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
