package com.example.scud


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [ClassInfo::class],
    version = 1,
    exportSchema = true
)
abstract class ClassInfoDB : RoomDatabase() {

    abstract fun infoDAO(): InfoDAO

    companion object {

        @Volatile
        private var INSTANCE: ClassInfoDB ? = null

        fun getDatabase(context: Context): ClassInfoDB  {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            if (INSTANCE == null) {
                synchronized(this) {
                    // Pass the database to the INSTANCE
                    INSTANCE = buildDatabase(context)
                }
            }
            // Return database.
            return INSTANCE!!
        }

        private fun buildDatabase(context: Context): ClassInfoDB  {
            return Room.databaseBuilder(
                context.applicationContext,
                ClassInfoDB ::class.java,
                "classInfoDB _database"
            )
                .build()
        }
    }
}