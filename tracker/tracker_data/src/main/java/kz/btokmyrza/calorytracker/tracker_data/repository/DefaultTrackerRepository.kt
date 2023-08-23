package kz.btokmyrza.calorytracker.tracker_data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kz.btokmyrza.calorytracker.tracker_data.local.TrackerDao
import kz.btokmyrza.calorytracker.tracker_data.mapper.TrackedFoodModelMapper
import kz.btokmyrza.calorytracker.tracker_data.network.OpenFoodAPI
import kz.btokmyrza.calorytracker.tracker_domain.model.TrackableFood
import kz.btokmyrza.calorytracker.tracker_domain.model.TrackedFood
import kz.btokmyrza.calorytracker.tracker_domain.repository.TrackerRepository
import java.time.LocalDate

class DefaultTrackerRepository(
    private val dao: TrackerDao,
    private val api: OpenFoodAPI,
    private val trackedFoodModelMapper: TrackedFoodModelMapper,
) : TrackerRepository {

    override suspend fun searchFood(
        query: String,
        page: Int,
        pageSize: Int,
    ): Result<List<TrackableFood>> = try {
        getSearchFoodResult(query = query, page = page, pageSize = pageSize)
    } catch (e: Exception) {
        e.printStackTrace()
        Result.failure(e)
    }

    override suspend fun insertTrackedFood(trackedFood: TrackedFood) {
        trackedFoodModelMapper.toTrackedFoodEntity(trackedFood).also { dao.insertTrackedFood(it) }
    }

    override suspend fun deleteTrackedFood(trackedFood: TrackedFood) {
        trackedFoodModelMapper.toTrackedFoodEntity(trackedFood).also { dao.deleteTrackedFood(it) }
    }

    override fun getFoodsForDate(localDate: LocalDate): Flow<List<TrackedFood>> =
        dao.getFoodsForDate(
            day = localDate.dayOfMonth,
            month = localDate.monthValue,
            year = localDate.year,
        ).map { entities ->
            entities.map(trackedFoodModelMapper::toTrackedFood)
        }

    private suspend fun getSearchFoodResult(
        query: String,
        page: Int,
        pageSize: Int,
    ): Result<List<TrackableFood>> = api.searchFood(
        query = query,
        page = page,
        pageSize = pageSize,
    ).let { foodSearchResponse ->
        Result.success(trackedFoodModelMapper.toTrackableFood(foodSearchResponse))
    }
}