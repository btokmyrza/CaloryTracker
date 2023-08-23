package kz.btokmyrza.calorytracker.tracker_domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kz.btokmyrza.calorytracker.core.preferences.Preferences
import kz.btokmyrza.calorytracker.tracker_domain.repository.TrackerRepository
import kz.btokmyrza.calorytracker.tracker_domain.use_case.CalculateMealNutrients
import kz.btokmyrza.calorytracker.tracker_domain.use_case.DeleteTrackedFood
import kz.btokmyrza.calorytracker.tracker_domain.use_case.GetFoodsForDate
import kz.btokmyrza.calorytracker.tracker_domain.use_case.SearchFood
import kz.btokmyrza.calorytracker.tracker_domain.use_case.TrackFood
import kz.btokmyrza.calorytracker.tracker_domain.use_case.TrackerUseCases

@Module
@InstallIn(ViewModelComponent::class)
object TrackerDomainModule {

    @ViewModelScoped
    @Provides
    fun provideTrackerUseCases(
        repository: TrackerRepository,
        preferences: Preferences,
    ): TrackerUseCases = TrackerUseCases(
        trackFood = TrackFood(repository = repository),
        searchFood = SearchFood(repository = repository),
        getFoodsForDate = GetFoodsForDate(repository = repository),
        deleteTrackedFood = DeleteTrackedFood(repository = repository),
        calculateMealNutrients = CalculateMealNutrients(preferences = preferences),
    )
}