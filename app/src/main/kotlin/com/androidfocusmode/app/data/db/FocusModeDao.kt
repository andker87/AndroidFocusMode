package com.androidfocusmode.app.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.androidfocusmode.app.data.model.FocusMode
import kotlinx.coroutines.flow.Flow

@Dao
interface FocusModeDao {
    @Insert
    suspend fun insert(focusMode: FocusMode): Long

    @Update
    suspend fun update(focusMode: FocusMode)

    @Delete
    suspend fun delete(focusMode: FocusMode)

    @Query("SELECT * FROM focus_modes WHERE id = :id")
    suspend fun getById(id: Long): FocusMode?

    @Query("SELECT * FROM focus_modes ORDER BY isPredefined DESC, name ASC")
    fun getAllFlow(): Flow<List<FocusMode>>

    @Query("SELECT * FROM focus_modes ORDER BY isPredefined DESC, name ASC")
    suspend fun getAll(): List<FocusMode>

    @Query("SELECT * FROM focus_modes WHERE isActive = 1")
    suspend fun getActiveModes(): List<FocusMode>

    @Query("SELECT * FROM focus_modes WHERE isPredefined = 1")
    suspend fun getPredefinedModes(): List<FocusMode>

    @Query("UPDATE focus_modes SET isActive = 0 WHERE id != :id")
    suspend fun deactivateAllExcept(id: Long)

    @Query("UPDATE focus_modes SET isActive = 0")
    suspend fun deactivateAll()

    @Query("SELECT COUNT(*) FROM focus_modes")
    suspend fun count(): Int
}
