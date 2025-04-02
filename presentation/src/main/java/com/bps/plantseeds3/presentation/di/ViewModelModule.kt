package com.bps.plantseeds3.presentation.di

import com.bps.plantseeds3.domain.repository.SeedRepository
import com.bps.plantseeds3.domain.use_case.GetSeedsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideGetSeedsUseCase(seedRepository: SeedRepository): GetSeedsUseCase {
        return GetSeedsUseCase(seedRepository)
    }
} 