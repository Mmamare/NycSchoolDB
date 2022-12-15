package com.example.nycschooldb.di.module

import com.example.nycschooldb.repository.NycRepoImpl
import com.example.nycschooldb.repository.NycRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepoModule {

    @Binds
    abstract fun proVideRepository(repoImpl: NycRepoImpl): NycRepository
}