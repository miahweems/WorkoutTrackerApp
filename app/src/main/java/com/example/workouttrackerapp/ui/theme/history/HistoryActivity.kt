package com.example.workouttrackerapp.ui.history

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.livedata.observeAsState
import com.example.workouttrackerapp.ui.viewmodel.WorkoutViewModel
import com.example.workouttrackerapp.ui.theme.WorkoutTrackerAppTheme

class HistoryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkoutTrackerAppTheme {
                val viewModel: WorkoutViewModel = viewModel()
                HistoryScreen(
                    viewModel = viewModel,
                    onWorkoutClick = { historyId ->
                        val intent = Intent(this, ViewPastWorkoutActivity::class.java)
                        intent.putExtra("workoutHistoryId", historyId)
                        startActivity(intent)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    viewModel: WorkoutViewModel,
    onWorkoutClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val historyList by viewModel.workoutHistory.observeAsState(emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Workout History") }
            )
        },
        modifier = modifier
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            items(historyList) { history ->
                WorkoutHistoryItem(
                    date = history.date,
                    name = history.name,
                    onClick = { onWorkoutClick(history.id) }
                )
            }
        }
    }
}

@Composable
fun WorkoutHistoryItem(
    date: String,
    name: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = date.ifBlank { "No Date" },
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
