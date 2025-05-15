package com.example.workouttrackerapp.ui.workout

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.workouttrackerapp.data.model.Exercise
import com.example.workouttrackerapp.ui.viewmodel.WorkoutViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutDetailScreen(
    modifier: Modifier = Modifier,
    workoutId: Int,
    viewModel: WorkoutViewModel = viewModel(),
    navController: NavController,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onStartWorkout: () -> Unit,
) {
    val workouts by viewModel.allWorkouts.observeAsState(emptyList())
    val exercises by viewModel.getExercises(workoutId).observeAsState(emptyList())

    val workout = workouts.find { it.workoutId == workoutId }

    if (workout == null) {
        Text("Workout not found")
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(workout.name) }
            )
        },
        bottomBar = {
            BottomAppBar {
                IconButton(onClick = { onEdit() }) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit Workout")
                }
                IconButton(onClick = {
                    viewModel.deleteWorkout(workout)
                    onDelete()
                }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete Workout")
                }
                IconButton(onClick = { onStartWorkout() }) {
                    Icon(Icons.Default.PlayArrow, contentDescription = "Start Workout")
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text("Exercises:", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            exercises.forEach { exercise ->
                ExerciseDetailItem(exercise = exercise)
            }
        }
    }
}

@Composable
fun ExerciseDetailItem(exercise: Exercise) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Name: ${exercise.name}")
            Text("Sets: ${exercise.sets}")
            Text("Reps: ${exercise.reps}")
            Text("Weight: ${exercise.weight} lbs")
        }
    }
}
