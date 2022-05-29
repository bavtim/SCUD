package com.example.scud


import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface InfoDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addclassInfo(classInfo: kotlin.collections.MutableList<com.example.scud.ClassInfo>?)

    @Query("SELECT * FROM classInfo ORDER BY classroomName DESC")
    fun getclassInfo(): Flow<List<ClassInfo>>

    @Update
    suspend fun updateNote(classInfo: ClassInfo)

    @Delete
    suspend fun deleteNote(classInfo: ClassInfo)
}


