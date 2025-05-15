package com.example.workouttrackerapp.ui.workout

import androidx.compose.runtime.Composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectWorkoutScreen(
    workouts: List<String> = listOf("Upper Body Strength", "Leg Day", "Full Body Circuit"),
    onWorkoutSelected: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Select Workout", style = MaterialTheme.typography.headlineSmall) }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            items(workouts) { workout ->
                WorkoutItem(workout, onClick = { onWorkoutSelected(workout) })
            }
        }
    }
}

@Composable
fun WorkoutItem(workoutName: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = workoutName, style = MaterialTheme.typography.bodyLarge)
        }
    }
}
