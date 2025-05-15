package com.example.workouttrackerapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.workouttrackerapp.data.model.*

@Dao
interface WorkoutDao {

    @Insert
    suspend fun insertWorkout(workout: Workout): Long

    @Insert
    suspend fun insertExercise(exercise: Exercise)

    @Insert
    suspend fun insertWorkoutHistory(history: WorkoutHistory)

    @Query("SELECT * FROM Workout WHERE userId = :userId")
    fun getAllWorkouts(userId: String): LiveData<List<Workout>>

    @Query("SELECT * FROM Exercise WHERE workoutOwnerId = :workoutId")
    fun getExercisesForWorkout(workoutId: Int): LiveData<List<Exercise>>

    @Query("SELECT * FROM WorkoutHistory WHERE userId = :userId ORDER BY id DESC")
    fun getAllWorkoutHistory(userId: String): LiveData<List<WorkoutHistory>>

    @Query("SELECT * FROM WorkoutHistory WHERE id = :historyId")
    fun getWorkoutHistoryById(historyId: Int): LiveData<WorkoutHistory>

    @Delete
    suspend fun deleteWorkout(workout: Workout)

    @Query("DELETE FROM Exercise WHERE workoutOwnerId = :workoutId")
    suspend fun deleteExercisesByWorkoutId(workoutId: Int)



    @Insert
    suspend fun insertHistory(workoutHistory: WorkoutHistory)

    @Query("SELECT * FROM WorkoutHistory WHERE id = :id")
    fun getHistoryById(id: Int): LiveData<WorkoutHistory>




    @Update
    suspend fun updateWorkout(workout: Workout)

}

