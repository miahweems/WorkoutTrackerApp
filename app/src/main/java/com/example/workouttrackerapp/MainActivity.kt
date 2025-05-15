package com.example.workouttrackerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import com.example.workouttrackerapp.ui.navigation.AppNavHost
import com.example.workouttrackerapp.ui.theme.WorkoutTrackerAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Remove enableEdgeToEdge() unless you have defined it elsewhere.
        setContent {
            WorkoutTrackerAppTheme {
                Surface {
                    AppNavHost()
                }
            }
        }
    }
}
