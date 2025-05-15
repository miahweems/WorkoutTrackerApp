package com.example.workouttrackerapp.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = Workout::class,
        parentColumns = ["workoutId"],
        childColumns = ["workoutOwnerId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["workoutOwnerId"])]
)
data class Exercise(
    @PrimaryKey(autoGenerate = true) val exerciseId: Int = 0,
    val workoutOwnerId: Int,
    val name: String,
    val sets: Int,
    val reps: Int,
    val weight: Float
)
