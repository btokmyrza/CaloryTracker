package kz.btokmyrza.calorytracker.tracker_data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import kz.btokmyrza.calorytracker.tracker_data.local.model.TrackedFoodEntity

@Database(
    entities = [TrackedFoodEntity::class],
    version = 1,
)
abstract class TrackerDatabase: RoomDatabase() {
    abstract val dao: TrackerDao
}