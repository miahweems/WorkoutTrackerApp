package com.example.workouttrackerapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavBar(
    navController: NavHostController,
    currentScreen: String,
    canNavigateBack: Boolean,
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (canNavigateBack) {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", modifier = Modifier.size(20.dp))
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                }
                Text(currentScreen, style = MaterialTheme.typography.titleSmall)
            }
        },
        actions = {
            TextButton(onClick = { navController.navigate("home") }) {
                Text("Home", style = MaterialTheme.typography.labelSmall)
            }
            TextButton(onClick = { navController.navigate("workoutList") }) {
                Text("Workout", style = MaterialTheme.typography.labelSmall)
            }
            TextButton(onClick = { navController.navigate("profile") }) {
                Text("Profile", style = MaterialTheme.typography.labelSmall)
            }
            TextButton(onClick = { navController.navigate("history") }) {
                Text("History", style = MaterialTheme.typography.labelSmall)
            }
        }
    )
}