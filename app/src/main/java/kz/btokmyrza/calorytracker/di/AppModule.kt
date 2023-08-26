package kz.btokmyrza.calorytracker.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kz.btokmyrza.calorytracker.core.preferences.UserPreferences
import kz.btokmyrza.calorytracker.core.use_case.FilterOutDigits
import kz.btokmyrza.core_data.preferences.DefaultUserPreferences
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(app: Application): SharedPreferences =
        app.getSharedPreferences("shared_pref", MODE_PRIVATE)

    @Provides
    @Singleton
    fun providePreferences(sharedPreferences: SharedPreferences): UserPreferences =
        DefaultUserPreferences(sharedPreferences = sharedPreferences)

    @Provides
    @Singleton
    fun provideFilterOutDigitsUseCase(): FilterOutDigits = FilterOutDigits()
}