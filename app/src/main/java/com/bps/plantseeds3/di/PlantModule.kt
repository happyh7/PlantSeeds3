package com.bps.plantseeds3.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object PlantModule {
    
    @Provides
    @ViewModelScoped
    fun provideGardenId(): String {
        // Returnera ett tomt ID som standard, det kommer att uppdateras av ViewModel
        return ""
    }
} 