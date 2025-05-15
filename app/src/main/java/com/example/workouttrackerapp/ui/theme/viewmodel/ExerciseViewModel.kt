
package com.example.workouttrackerapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.workouttrackerapp.data.AppDatabase
import com.example.workouttrackerapp.data.model.AvailableExercise
import com.example.workouttrackerapp.data.repository.ExerciseRepository
import kotlinx.coroutines.launch

class ExerciseViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).availableExerciseDao()
    private val repo = ExerciseRepository(dao)
    val allExercises = repo.getAllExercises()

    fun insertAll(exercises: List<AvailableExercise>) {
        viewModelScope.launch {
            repo.insertAll(exercises)
        }
    }

    fun prepopulateExercisesIfNeeded() {
        viewModelScope.launch {
            val existingExercises = repo.getAllExercisesOnce()
            if (existingExercises.isEmpty()) {
                val defaultExercises = listOf(
                    AvailableExercise(name = "Bicep Curl", muscleGroup = "Arms"),
                    AvailableExercise(name = "Tricep Dip", muscleGroup = "Arms"),
                    AvailableExercise(name = "Lat Pulldown", muscleGroup = "Back"),
                    AvailableExercise(name = "Leg Press", muscleGroup = "Legs"),
                    AvailableExercise(name = "Overhead Press", muscleGroup = "Shoulders"),
                    AvailableExercise(name = "Lateral Raise", muscleGroup = "Shoulders"),
                    AvailableExercise(name = "Chest Fly", muscleGroup = "Chest"),
                    AvailableExercise(name = "Romanian Deadlift", muscleGroup = "Hamstrings"),
                    AvailableExercise(name = "Bulgarian Split Squat", muscleGroup = "Legs"),
                    AvailableExercise(name = "Cable Row", muscleGroup = "Back")
                )
                repo.insertAll(defaultExercises)
            }
        }
    }
}
