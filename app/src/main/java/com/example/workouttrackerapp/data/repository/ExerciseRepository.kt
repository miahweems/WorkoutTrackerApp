package com.example.workouttrackerapp.data.repository

import com.example.workouttrackerapp.data.dao.AvailableExerciseDao
import com.example.workouttrackerapp.data.model.AvailableExercise

class ExerciseRepository(private val dao: AvailableExerciseDao) {
    fun getAllExercises() = dao.getAllExercises()

    suspend fun insertAll(exercises: List<AvailableExercise>) = dao.insertAll(exercises)

    suspend fun getAllExercisesOnce(): List<AvailableExercise> = dao.getAllExercisesOnce()
}