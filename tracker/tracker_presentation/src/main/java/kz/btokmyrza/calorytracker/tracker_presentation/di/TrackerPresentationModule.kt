package kz.btokmyrza.calorytracker.tracker_presentation.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kz.btokmyrza.calorytracker.tracker_presentation.mapper.TrackedFoodDvoMapper

@Module
@InstallIn(ViewModelComponent::class)
object TrackerPresentationModule {

    @ViewModelScoped
    @Provides
    fun provideTrackedFoodDvoMapper(): TrackedFoodDvoMapper = TrackedFoodDvoMapper()
}