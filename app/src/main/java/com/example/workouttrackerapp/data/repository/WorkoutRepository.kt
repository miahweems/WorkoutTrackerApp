package com.example.workouttrackerapp.data.repository

import androidx.lifecycle.LiveData
import com.example.workouttrackerapp.data.dao.WorkoutDao
import com.example.workouttrackerapp.data.model.Exercise
import com.example.workouttrackerapp.data.model.Workout
import com.example.workouttrackerapp.data.model.WorkoutHistory

class WorkoutRepository(private val dao: WorkoutDao) {

    fun getAllWorkouts(userId: String): LiveData<List<Workout>> {
        return dao.getAllWorkouts(userId)
    }

    suspend fun insertWorkout(workout: Workout, userId: String): Long {
        val workoutWithUser = workout.copy(userId = userId)
        return dao.insertWorkout(workoutWithUser)
    }

    suspend fun updateWorkout(workout: Workout) {
        dao.updateWorkout(workout)
    }

    suspend fun deleteWorkout(workout: Workout) {
        dao.deleteWorkout(workout)
    }

    fun getExercisesForWorkout(workoutId: Int): LiveData<List<Exercise>> {
        return dao.getExercisesForWorkout(workoutId)
    }

    suspend fun insertExercise(exercise: Exercise) {
        dao.insertExercise(exercise)
    }

    suspend fun deleteExercisesByWorkoutId(workoutId: Int) {
        dao.deleteExercisesByWorkoutId(workoutId)
    }

    // History
    fun getAllHistory(userId: String): LiveData<List<WorkoutHistory>> {
        return dao.getAllWorkoutHistory(userId)
    }

    suspend fun insertWorkoutHistory(workoutHistory: WorkoutHistory, userId: String) {
        val historyWithUser = workoutHistory.copy(userId = userId)
        dao.insertHistory(historyWithUser)
    }

    fun getHistoryById(id: Int): LiveData<WorkoutHistory> {
        return dao.getHistoryById(id)
    }

}
