package com.example.workouttrackerapp.ui.workout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectExercisesScreen(
    navController: NavHostController,
    exercises: List<String> = listOf("Squats", "Bench Press", "Deadlift", "Pull-ups", "Lunges"),
    onConfirmSelection: (List<String>) -> Unit
) {
    var selectedExercises by remember { mutableStateOf(emptyList<String>()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Select Exercises", style = MaterialTheme.typography.headlineSmall) }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            LazyColumn {
                items(exercises) { exercise ->
                    ExerciseItem(
                        exercise = exercise,
                        isSelected = selectedExercises.contains(exercise),
                        onSelectionChange = { isSelected ->
                            selectedExercises = if (isSelected) {
                                selectedExercises + exercise
                            } else {
                                selectedExercises - exercise
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    onConfirmSelection(selectedExercises)
                    navController.navigate("addWorkout") // Navigate after selection
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Confirm Selection")
            }
        }
    }
}

@Composable
fun ExerciseItem(exercise: String, isSelected: Boolean, onSelectionChange: (Boolean) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onSelectionChange(!isSelected) },
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = exercise, style = MaterialTheme.typography.bodyLarge)
            Checkbox(checked = isSelected, onCheckedChange = onSelectionChange)
        }
    }
}
