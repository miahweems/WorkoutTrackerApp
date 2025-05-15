package com.example.workouttrackerapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.workouttrackerapp.ui.auth.LoginScreen
import com.example.workouttrackerapp.ui.auth.RegisterScreen
import com.example.workouttrackerapp.ui.history.HistoryScreen
import com.example.workouttrackerapp.ui.theme.history.ViewPastWorkoutScreen
import com.example.workouttrackerapp.ui.home.HomeActivity
import com.example.workouttrackerapp.ui.profile.ProfileScreen
import com.example.workouttrackerapp.ui.stats.StatisticsScreen
import com.example.workouttrackerapp.ui.theme.workout.WorkoutListScreen
import com.example.workouttrackerapp.ui.workout.*
import com.example.workouttrackerapp.ui.components.TopNavBar
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.layout.padding
import com.example.workouttrackerapp.ui.theme.workout.EditWorkoutScreen
import com.example.workouttrackerapp.ui.viewmodel.ExerciseViewModel
import com.example.workouttrackerapp.ui.viewmodel.WorkoutViewModel
import androidx.compose.ui.platform.LocalContext
import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.workouttrackerapp.ui.theme.home.HomeScreen
import com.example.workouttrackerapp.ui.theme.viewmodel.ExerciseViewModelFactory
import com.example.workouttrackerapp.ui.theme.viewmodel.WorkoutViewModelFactory
import com.example.workouttrackerapp.ui.theme.workout.StartWorkoutScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val auth = FirebaseAuth.getInstance()
    val startDestination = if (auth.currentUser != null) "home" else "login"

    val context = LocalContext.current
    val workoutViewModel: WorkoutViewModel = viewModel(factory = WorkoutViewModelFactory(context.applicationContext as Application))
    val exerciseViewModel: ExerciseViewModel = viewModel(factory = ExerciseViewModelFactory(context.applicationContext as Application))

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable("login") {
            LoginScreen(
                onLoginSuccess = { navController.navigate("home") },
                onNavigateToRegister = { navController.navigate("register") }
            )
        }

        composable("register") {
            RegisterScreen(
                onRegistrationSuccess = { navController.navigate("home") }
            )
        }

        composable("home") {
            Scaffold(
                topBar = {
                    TopNavBar(
                        navController = navController,
                        currentScreen = "Home",
                        canNavigateBack = false,
                        onBackClick = {}
                    )
                }
            ) { innerPadding ->
                HomeScreen(
                    modifier = Modifier.padding(innerPadding),
                    onNavigateToWorkoutList = { navController.navigate("workoutList") },
                    onNavigateToProfile = { navController.navigate("profile") },
                    onNavigateToHistory = { navController.navigate("history") },
                    onNavigateToStats = { navController.navigate("stats") },
                )
            }
        }

        composable("workoutList") {
            Scaffold(
                topBar = {
                    TopNavBar(
                        navController = navController,
                        currentScreen = "Workout List",
                        canNavigateBack = true,
                        onBackClick = { navController.popBackStack() }
                    )
                }
            ) { innerPadding ->
                WorkoutListScreen(
                    modifier = Modifier.padding(innerPadding),
                    navController = navController,
                    viewModel = workoutViewModel
                )
            }
        }

        composable(
            "workoutDetail/{workoutId}",
            arguments = listOf(navArgument("workoutId") { type = NavType.IntType })
        ) { backStackEntry ->
            val workoutId = backStackEntry.arguments?.getInt("workoutId") ?: -1
            Scaffold(
                topBar = {
                    TopNavBar(
                        navController = navController,
                        currentScreen = "Workout Detail",
                        canNavigateBack = true,
                        onBackClick = { navController.popBackStack() }
                    )
                }
            ) { innerPadding ->
                WorkoutDetailScreen(
                    modifier = Modifier.padding(innerPadding),
                    workoutId = workoutId,
                    viewModel = workoutViewModel,
                    onEdit = { navController.navigate("editWorkout/$workoutId") },
                    onDelete = { navController.popBackStack() },
                    onStartWorkout = { navController.navigate("startWorkout/$workoutId") }
                )
            }
        }

        composable("addWorkout") {
            Scaffold(
                topBar = {
                    TopNavBar(
                        navController = navController,
                        currentScreen = "Add Workout",
                        canNavigateBack = true,
                        onBackClick = { navController.popBackStack() }
                    )
                }
            ) { innerPadding ->
                AddWorkoutScreen(
                    modifier = Modifier.padding(innerPadding),
                    workoutViewModel = workoutViewModel,
                    exerciseViewModel = exerciseViewModel,
                    onWorkoutAdded = { navController.popBackStack() }
                )
            }
        }

        composable(
            "editWorkout/{workoutId}",
            arguments = listOf(navArgument("workoutId") { type = NavType.IntType })
        ) { backStackEntry ->
            val workoutId = backStackEntry.arguments?.getInt("workoutId") ?: -1
            Scaffold(
                topBar = {
                    TopNavBar(
                        navController = navController,
                        currentScreen = "Edit Workout",
                        canNavigateBack = true,
                        onBackClick = { navController.popBackStack() }
                    )
                }
            ) { innerPadding ->
                EditWorkoutScreen(
                    modifier = Modifier.padding(innerPadding),
                    workoutId = workoutId,
                    viewModel = workoutViewModel,
                    exerciseViewModel = exerciseViewModel,
                    onWorkoutUpdated = { navController.popBackStack() },
                    onSave = { navController.popBackStack() }
                )
            }
        }

        composable(
            "startWorkout/{workoutId}",
            arguments = listOf(navArgument("workoutId") { type = NavType.IntType })
        ) { backStackEntry ->
            val workoutId = backStackEntry.arguments?.getInt("workoutId") ?: -1
            Scaffold(
                topBar = {
                    TopNavBar(
                        navController = navController,
                        currentScreen = "Start Workout",
                        canNavigateBack = true,
                        onBackClick = { navController.popBackStack() }
                    )
                }
            ) { innerPadding ->
                StartWorkoutScreen(
                    modifier = Modifier.padding(innerPadding),
                    workoutId = workoutId,
                    viewModel = workoutViewModel,
                    onComplete = { navController.navigate("history") }
                )
            }
        }

        composable("history") {
            Scaffold(
                topBar = {
                    TopNavBar(
                        navController = navController,
                        currentScreen = "History",
                        canNavigateBack = true,
                        onBackClick = { navController.popBackStack() }
                    )
                }
            ) { innerPadding ->
                HistoryScreen(
                    modifier = Modifier.padding(innerPadding),
                    viewModel = workoutViewModel,
                    onWorkoutClick = { workoutHistoryId ->
                        navController.navigate("viewPastWorkout/$workoutHistoryId")
                    }
                )
            }
        }

        composable(
            "viewPastWorkout/{workoutHistoryId}",
            arguments = listOf(navArgument("workoutHistoryId") { type = NavType.IntType })
        ) { backStackEntry ->
            val workoutHistoryId = backStackEntry.arguments?.getInt("workoutHistoryId") ?: -1
            Scaffold(
                topBar = {
                    TopNavBar(
                        navController = navController,
                        currentScreen = "View Past Workout",
                        canNavigateBack = true,
                        onBackClick = { navController.popBackStack() }
                    )
                }
            ) { innerPadding ->
                ViewPastWorkoutScreen(
                    modifier = Modifier.padding(innerPadding),
                    workoutHistoryId = workoutHistoryId,
                    viewModel = workoutViewModel
                )
            }
        }

        composable("stats") {
            Scaffold(
                topBar = {
                    TopNavBar(
                        navController = navController,
                        currentScreen = "Statistics",
                        canNavigateBack = true,
                        onBackClick = { navController.popBackStack() }
                    )
                }
            ) { innerPadding ->
                StatisticsScreen(
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }

        composable("profile") {
            Scaffold(
                topBar = {
                    TopNavBar(
                        navController = navController,
                        currentScreen = "Profile",
                        canNavigateBack = true,
                        onBackClick = { navController.popBackStack() }
                    )
                }
            ) { innerPadding ->
                ProfileScreen(
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

