package com.example.workouttrackerapp.ui.theme.workout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.workouttrackerapp.ui.viewmodel.WorkoutViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutListScreen(
    modifier: Modifier = Modifier,
    viewModel: WorkoutViewModel,
    navController: NavHostController
) {
    val workoutsState = viewModel.getWorkoutsForCurrentUser().observeAsState(emptyList())
    val workouts = workoutsState.value

    Scaffold(
        modifier = modifier,
        topBar = { /* TopNavBar is handled already */ }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Button(
                onClick = { navController.navigate("addWorkout") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Workout")
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (workouts.isEmpty()) {
                Text("No workouts yet. Add one!", style = MaterialTheme.typography.titleMedium)
            } else {
                LazyColumn {
                    items(workouts) { workout ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .clickable {
                                    navController.navigate("workoutDetail/${workout.workoutId}")
                                }
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = workout.name,
                                    style = MaterialTheme.typography.titleLarge
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = workout.dateCreated ?: "No Date",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}