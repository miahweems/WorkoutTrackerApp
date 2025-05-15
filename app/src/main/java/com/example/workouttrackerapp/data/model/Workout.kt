package com.example.workouttrackerapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Workout(
    @PrimaryKey(autoGenerate = true) val workoutId: Int = 0,
    val name: String,
    val dateCreated: String,
    val userId: String
)
