package com.example.workouttrackerapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.workouttrackerapp.data.dao.WorkoutDao
import com.example.workouttrackerapp.data.model.*
import com.example.workouttrackerapp.data.model.AvailableExercise
import com.example.workouttrackerapp.data.dao.AvailableExerciseDao

@Database(
    entities = [Workout::class, Exercise::class, WorkoutHistory::class, AvailableExercise::class],
    version = 7,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun workoutDao(): WorkoutDao
    abstract fun availableExerciseDao(): AvailableExerciseDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "workout_db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
