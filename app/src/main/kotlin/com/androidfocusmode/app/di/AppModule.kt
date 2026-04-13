package com.androidfocusmode.app.di

import android.content.Context
import com.androidfocusmode.app.data.db.FocusModeDatabase
import com.androidfocusmode.app.data.db.FocusModeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideFocusModeDatabase(
        @ApplicationContext context: Context
    ): FocusModeDatabase {
        return FocusModeDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun provideFocusModeDao(database: FocusModeDatabase): FocusModeDao {
        return database.focusModeDao()
    }
}
