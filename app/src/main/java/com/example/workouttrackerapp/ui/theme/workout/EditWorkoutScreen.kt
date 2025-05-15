package com.example.workouttrackerapp.ui.theme.workout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.workouttrackerapp.data.model.Exercise
import com.example.workouttrackerapp.ui.viewmodel.ExerciseViewModel
import com.example.workouttrackerapp.ui.viewmodel.WorkoutViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditWorkoutScreen(
    workoutId: Int,
    viewModel: WorkoutViewModel = viewModel(),
    exerciseViewModel: ExerciseViewModel = viewModel(),
    onWorkoutUpdated: () -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier
) {
    val workouts by viewModel.allWorkouts.observeAsState(emptyList())
    val currentWorkout = workouts.find { it.workoutId == workoutId }

    val exerciseListState = exerciseViewModel.allExercises.observeAsState(emptyList())
    val exerciseList = exerciseListState.value
    val selectedExercises by viewModel.getExercises(workoutId).observeAsState(emptyList())

    var workoutName by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(currentWorkout?.name) {
        if (workoutName.isEmpty()) {
            workoutName = currentWorkout?.name ?: ""
        }
    }

    val selectedNames = remember { mutableStateListOf<String>() }

    LaunchedEffect(selectedExercises) {
        selectedNames.clear()
        selectedNames.addAll(selectedExercises.map { it.name })
    }


        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text("Edit Workout", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = workoutName,
                onValueChange = { workoutName = it },
                label = { Text("Workout Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    val updatedExercises = selectedNames.map { exerciseName ->
                        Exercise(
                            workoutOwnerId = workoutId,
                            name = exerciseName,
                            sets = 3,
                            reps = 10,
                            weight = 0f
                        )
                    }
                    currentWorkout?.let {
                        viewModel.updateWorkoutWithExercises(it.copy(name = workoutName), updatedExercises)
                        onWorkoutUpdated()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Changes")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Select Exercises:", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            val groupedExercises = exerciseList.groupBy { it.muscleGroup }

            LazyColumn {
                groupedExercises.forEach { (muscleGroup, exercises) ->
                    item {
                        Text(
                            text = muscleGroup,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                    items(exercises) { exercise ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(exercise.name, style = MaterialTheme.typography.bodyLarge)
                            Checkbox(
                                checked = selectedNames.contains(exercise.name),
                                onCheckedChange = {
                                    if (it) selectedNames.add(exercise.name) else selectedNames.remove(exercise.name)
                                }
                            )
                        }
                    }
                }
            }

        }
    }
