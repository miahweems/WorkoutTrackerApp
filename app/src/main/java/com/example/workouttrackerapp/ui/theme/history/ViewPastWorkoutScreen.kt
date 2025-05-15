package com.example.workouttrackerapp.ui.theme.history

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.livedata.observeAsState
import com.example.workouttrackerapp.ui.viewmodel.WorkoutViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewPastWorkoutScreen(
    workoutHistoryId: Int,
    viewModel: WorkoutViewModel,
    modifier: Modifier = Modifier,


    ) {
    val historyItem by viewModel.getHistoryById(workoutHistoryId).observeAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Past Workout") })
        }
    ) { innerPadding ->
        historyItem?.let { history ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                Text("Date: ${history.date}", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Workout Name: ${history.name}", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(16.dp))
                Text("Exercises:", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(history.exercises)
            }
        } ?: run {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                Text("Workout History Not Found")
            }
        }
    }
}
