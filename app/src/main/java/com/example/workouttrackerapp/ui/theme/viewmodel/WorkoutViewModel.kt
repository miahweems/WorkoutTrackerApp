package com.example.workouttrackerapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.workouttrackerapp.data.AppDatabase
import com.example.workouttrackerapp.data.model.Exercise
import com.example.workouttrackerapp.data.model.Workout
import com.example.workouttrackerapp.data.model.WorkoutHistory
import com.example.workouttrackerapp.data.repository.WorkoutRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import java.util.Date
import java.text.SimpleDateFormat
import java.util.Locale


class WorkoutViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: WorkoutRepository
    val allWorkouts: LiveData<List<Workout>>
    val workoutHistory: LiveData<List<WorkoutHistory>>

    // Private property to get current userId
    private val userId: String
        get() = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    init {
        val dao = AppDatabase.getDatabase(application).workoutDao()
        repository = WorkoutRepository(dao)
        allWorkouts = repository.getAllWorkouts(userId)
        workoutHistory = repository.getAllHistory(userId)
    }

    fun addWorkout(name: String, exercises: List<Exercise>) {
        viewModelScope.launch {
            val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val workout = Workout(
                userId = userId,
                name = name,
                dateCreated = currentDate
            )
            val workoutId = repository.insertWorkout(workout, userId)
            exercises.forEach { exercise ->
                repository.insertExercise(exercise.copy(workoutOwnerId = workoutId.toInt()))
            }
        }
    }

    fun deleteWorkout(workout: Workout) {
        viewModelScope.launch {
            repository.deleteWorkout(workout)
        }
    }

    fun updateWorkoutWithExercises(workout: Workout, exercises: List<Exercise>) {
        viewModelScope.launch {
            repository.updateWorkout(workout)
            repository.deleteExercisesByWorkoutId(workout.workoutId)
            exercises.forEach { repository.insertExercise(it) }
        }
    }

    fun getExercises(workoutId: Int): LiveData<List<Exercise>> {
        return repository.getExercisesForWorkout(workoutId)
    }

    fun logCompletedWorkout(name: String, date: String, exercisesSummary: String) {
        viewModelScope.launch {
            val history = WorkoutHistory(
                name = name,
                date = date,
                exercises = exercisesSummary,
                userId = userId
            )
            repository.insertWorkoutHistory(history, userId)
        }
    }

    fun getHistoryById(id: Int): LiveData<WorkoutHistory> {
        return repository.getHistoryById(id)
    }

    fun getWorkoutsForCurrentUser(): LiveData<List<Workout>> {
        return repository.getAllWorkouts(userId)
    }
}
