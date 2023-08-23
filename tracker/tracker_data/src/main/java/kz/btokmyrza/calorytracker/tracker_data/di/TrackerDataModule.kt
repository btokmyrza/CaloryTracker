package kz.btokmyrza.calorytracker.tracker_data.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kz.btokmyrza.calorytracker.tracker_data.local.TrackerDatabase
import kz.btokmyrza.calorytracker.tracker_data.mapper.TrackedFoodModelMapper
import kz.btokmyrza.calorytracker.tracker_data.network.OpenFoodAPI
import kz.btokmyrza.calorytracker.tracker_data.repository.DefaultTrackerRepository
import kz.btokmyrza.calorytracker.tracker_domain.repository.TrackerRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TrackerDataModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    fun provideMoshiConverterFactory(): MoshiConverterFactory = MoshiConverterFactory.create()

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

    @Provides
    @Singleton
    fun provideOpenFoodApi(
        converterFactory: MoshiConverterFactory,
        client: OkHttpClient,
    ): OpenFoodAPI = Retrofit.Builder()
        .baseUrl(OpenFoodAPI.BASE_URL)
        .addConverterFactory(converterFactory)
        .client(client)
        .build()
        .create()

    @Provides
    @Singleton
    fun provideTrackerDatabase(app: Application): TrackerDatabase {
        return Room.databaseBuilder(
            context = app,
            klass = TrackerDatabase::class.java,
            name = "tracker_db",
        ).build()
    }

    @Provides
    @Singleton
    fun provideTrackedFoodModelMapper() = TrackedFoodModelMapper()

    @Provides
    @Singleton
    fun provideTrackerRepository(
        api: OpenFoodAPI,
        db: TrackerDatabase,
        trackedFoodModelMapper: TrackedFoodModelMapper,
    ): TrackerRepository = DefaultTrackerRepository(
        ioDispatcher = Dispatchers.IO,
        dao = db.dao,
        api = api,
        trackedFoodModelMapper = trackedFoodModelMapper,
    )
}