package com.example.workouttrackerapp.ui.workout

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import com.example.workouttrackerapp.ui.theme.WorkoutTrackerAppTheme
import com.example.workouttrackerapp.ui.viewmodel.WorkoutViewModel

class WorkoutDetailActivity : ComponentActivity() {

    private val viewModel: WorkoutViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val workoutId = intent.getIntExtra("workoutId", -1)

        setContent {
            WorkoutTrackerAppTheme {
                WorkoutDetailScreen(
                    workoutId = workoutId,
                    viewModel = viewModel,
                    onEdit = {
                        val intent = Intent(this, EditWorkoutActivity::class.java)
                        intent.putExtra("workoutId", workoutId)
                        startActivity(intent)
                    },
                    onStartWorkout = {
                        val intent = Intent(this, StartWorkoutActivity::class.java)
                        intent.putExtra("workoutId", workoutId)
                        startActivity(intent)
                    },
                    onDelete = {
                        viewModel.allWorkouts.value?.find { it.workoutId == workoutId }?.let {
                            viewModel.deleteWorkout(it)
                            finish()
                        }
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutDetailScreen(
    workoutId: Int,
    viewModel: WorkoutViewModel,
    onEdit: () -> Unit,
    onStartWorkout: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val workouts by viewModel.allWorkouts.observeAsState(emptyList())
    val workout = workouts.find { it.workoutId == workoutId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (workout != null) {
                        Text(workout.name)
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                IconButton(onClick = { onEdit() }) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit Workout")
                }
                IconButton(onClick = {
                    if (workout != null) {
                        viewModel.deleteWorkout(workout)
                    }
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
            workout?.let {
                Text("Exercises:", style = MaterialTheme.typography.headlineSmall)

                Spacer(modifier = Modifier.height(16.dp))

                val exercises by viewModel.getExercises(workoutId).observeAsState(emptyList())
                exercises.forEach { exercise ->
                    Text(
                        text = "- ${exercise.name}",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onStartWorkout,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Start Workout")
                }
            } ?: Text("Workout not found.")
        }
    }
}
