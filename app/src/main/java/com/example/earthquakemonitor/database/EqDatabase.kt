package com.example.earthquakemonitor.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.earthquakemonitor.Earthquake

@Database(entities = [Earthquake::class], version = 1)
abstract class EqDatabase : RoomDatabase() {
    abstract val eqDao: EqDao
}

//Singleton: Variable que se instancia solo una vez y se puede usar en toda ocasión
private lateinit var INSTANCE: EqDatabase

fun getDatabase(context: Context): EqDatabase {
    synchronized(EqDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                EqDatabase::class.java,
                "earthquake_db"
            ).build()
        }
        return INSTANCE
    }
}
