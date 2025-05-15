package com.example.workouttrackerapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.workouttrackerapp.data.model.Exercise

@Composable
fun ExerciseItem(exercise: Exercise) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(exercise.name, style = MaterialTheme.typography.titleMedium)
        Text("Sets: ${exercise.sets}, Reps: ${exercise.reps}, Weight: ${exercise.weight} lbs")
    }
}
