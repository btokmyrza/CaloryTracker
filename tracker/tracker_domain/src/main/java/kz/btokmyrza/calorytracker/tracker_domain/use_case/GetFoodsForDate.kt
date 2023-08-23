package kz.btokmyrza.calorytracker.tracker_domain.use_case

import kotlinx.coroutines.flow.Flow
import kz.btokmyrza.calorytracker.tracker_domain.model.TrackedFood
import kz.btokmyrza.calorytracker.tracker_domain.repository.TrackerRepository
import java.time.LocalDate

class GetFoodsForDate(
    private val repository: TrackerRepository,
) {

    operator fun invoke(date: LocalDate): Flow<List<TrackedFood>> =
        repository.getFoodsForDate(localDate = date)
}