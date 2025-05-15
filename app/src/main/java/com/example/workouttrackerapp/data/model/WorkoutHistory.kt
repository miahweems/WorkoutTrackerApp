package com.example.workouttrackerapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WorkoutHistory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val date: String,
    val exercises: String,
    val userId: String  // NEW FIELD to track which user completed the workout
)
