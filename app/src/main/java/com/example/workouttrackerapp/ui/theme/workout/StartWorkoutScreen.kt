package com.example.workouttrackerapp.ui.theme.workout



import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.workouttrackerapp.data.model.Exercise
import com.example.workouttrackerapp.ui.viewmodel.WorkoutViewModel

data class ExerciseInput(
    var sets: Int = 0,
    var reps: Int = 0,
    var weight: Float = 0f
)

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartWorkoutScreen(
    workoutId: Int,
    viewModel: WorkoutViewModel = viewModel(),
    onComplete: () -> Unit,
    modifier: Modifier = Modifier,

    ) {
    val exercises by viewModel.getExercises(workoutId).observeAsState(emptyList())
    val workouts by viewModel.allWorkouts.observeAsState(emptyList())
    val workout = workouts.find { it.workoutId == workoutId }

    if (workout == null) {
        Text("Workout not found")
        return
    }

    val inputStates = remember {
        mutableStateMapOf<Int, ExerciseInput>().apply {
            exercises.forEach { exercise ->
                this[exercise.exerciseId] = ExerciseInput(
                    sets = exercise.sets,
                    reps = exercise.reps,
                    weight = exercise.weight
                )
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Start Workout") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(workout.name, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))

            Column(modifier = Modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = 8.dp)
                ) {
                    items(exercises.size) { index ->
                        val exercise = exercises[index]
                        val exerciseInput = inputStates[exercise.exerciseId] ?: ExerciseInput()

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            Text(exercise.name, style = MaterialTheme.typography.titleMedium)

                            OutlinedTextField(
                                value = exerciseInput.sets.toString(),
                                onValueChange = { newValue ->
                                    inputStates[exercise.exerciseId] = inputStates[exercise.exerciseId]?.copy(
                                        sets = newValue.toIntOrNull() ?: 0
                                    ) ?: ExerciseInput(sets = newValue.toIntOrNull() ?: 0)
                                },
                                label = { Text("Sets") },
                                modifier = Modifier.fillMaxWidth()
                            )

                            OutlinedTextField(
                                value = exerciseInput.reps.toString(),
                                onValueChange = { newValue ->
                                    inputStates[exercise.exerciseId] = inputStates[exercise.exerciseId]?.copy(
                                        reps = newValue.toIntOrNull() ?: 0
                                    ) ?: ExerciseInput(reps = newValue.toIntOrNull() ?: 0)
                                },
                                label = { Text("Reps") },
                                modifier = Modifier.fillMaxWidth()
                            )

                            OutlinedTextField(
                                value = exerciseInput.weight.toString(),
                                onValueChange = { newValue ->
                                    inputStates[exercise.exerciseId] = inputStates[exercise.exerciseId]?.copy(
                                        weight = newValue.toFloatOrNull() ?: 0f
                                    ) ?: ExerciseInput(weight = newValue.toFloatOrNull() ?: 0f)
                                },
                                label = { Text("Weight (lbs)") },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }

                Button(
                    onClick = {
                        val historyExercises = exercises.joinToString("\n") { exercise ->
                            val exerciseInput = inputStates[exercise.exerciseId] ?: ExerciseInput()
                            "${exercise.name}: ${exerciseInput.sets} x ${exerciseInput.reps} @ ${exerciseInput.weight} lbs"
                        }

                        viewModel.logCompletedWorkout(
                            name = workout.name,
                            date = java.time.LocalDate.now().toString(),
                            exercisesSummary = historyExercises
                        )

                        onComplete()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                ) {
                    Text(
                        text = "Complete Workout",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}
