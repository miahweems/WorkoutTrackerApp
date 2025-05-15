package com.example.workouttrackerapp.ui.workout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.workouttrackerapp.ui.theme.WorkoutTrackerAppTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class WorkoutSessionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val workoutName = intent.getStringExtra("workoutName") ?: "Unknown Workout"

        setContent {
            WorkoutTrackerAppTheme {
                WorkoutSessionScreen(workoutName = workoutName)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutSessionScreen(workoutName: String) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(workoutName, style = MaterialTheme.typography.headlineSmall) })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Workout Session: $workoutName", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { /* Implement workout start logic */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Start Workout")
            }
        }
    }
}
