package kz.btokmyrza.calorytracker.tracker_domain.use_case

import kz.btokmyrza.calorytracker.tracker_domain.model.TrackedFood
import kz.btokmyrza.calorytracker.tracker_domain.repository.TrackerRepository

class DeleteTrackedFood(
    private val repository: TrackerRepository,
) {

    suspend operator fun invoke(trackedFood: TrackedFood) =
        repository.deleteTrackedFood(trackedFood = trackedFood)
}