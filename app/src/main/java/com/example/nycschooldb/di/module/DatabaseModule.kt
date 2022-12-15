package com.example.nycschooldb.di.module

import android.content.Context
import androidx.room.Room
import com.example.nycschooldb.model.local.NYCSDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): NYCSDatabase{
        return Room.databaseBuilder(
            context, NYCSDatabase::class.java, "School_RoomDb"
        ).build()
    }
}