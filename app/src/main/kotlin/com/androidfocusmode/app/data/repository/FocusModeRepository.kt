package com.androidfocusmode.app.data.repository

import com.androidfocusmode.app.data.db.FocusModeDao
import com.androidfocusmode.app.data.model.FocusMode
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FocusModeRepository @Inject constructor(
    private val focusModeDao: FocusModeDao
) {
    fun getAllModesFlow(): Flow<List<FocusMode>> {
        return focusModeDao.getAllFlow()
    }

    suspend fun getAllModes(): List<FocusMode> {
        return focusModeDao.getAll()
    }

    suspend fun getModeById(id: Long): FocusMode? {
        return focusModeDao.getById(id)
    }

    suspend fun getActiveModes(): List<FocusMode> {
        return focusModeDao.getActiveModes()
    }

    suspend fun getPredefinedModes(): List<FocusMode> {
        return focusModeDao.getPredefinedModes()
    }

    suspend fun insertMode(focusMode: FocusMode): Long {
        return focusModeDao.insert(focusMode)
    }

    suspend fun updateMode(focusMode: FocusMode) {
        focusModeDao.update(focusMode)
    }

    suspend fun deleteMode(focusMode: FocusMode) {
        focusModeDao.delete(focusMode)
    }

    suspend fun activateMode(modeId: Long) {
        // Deactivate all others
        focusModeDao.deactivateAllExcept(modeId)
        
        // Activate this one
        val mode = focusModeDao.getById(modeId)
        mode?.let {
            focusModeDao.update(it.copy(isActive = true))
        }
    }

    suspend fun deactivateMode(modeId: Long) {
        val mode = focusModeDao.getById(modeId)
        mode?.let {
            focusModeDao.update(it.copy(isActive = false))
        }
    }

    suspend fun deactivateAll() {
        focusModeDao.deactivateAll()
    }

    suspend fun initializePredefinedModes() {
        // TODO: Create predefined modes on first run
        // NORMAL, NIGHT, DRIVING, GYM, WORK
    }
}
