package com.example.workouttrackerapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.workouttrackerapp.data.model.AvailableExercise

@Dao
interface AvailableExerciseDao {
    @Query("SELECT * FROM available_exercises")
    fun getAllExercises(): LiveData<List<AvailableExercise>>

    @Query("SELECT * FROM available_exercises")
    suspend fun getAllExercisesOnce(): List<AvailableExercise>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(exercises: List<AvailableExercise>)

    @Insert
    suspend fun insertExercise(exercise: AvailableExercise)
}
