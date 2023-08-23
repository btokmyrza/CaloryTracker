package kz.btokmyrza.calorytracker.tracker_data.mapper

import kz.btokmyrza.calorytracker.tracker_data.local.model.TrackedFoodEntity
import kz.btokmyrza.calorytracker.tracker_data.network.model.FoodSearchResponse
import kz.btokmyrza.calorytracker.tracker_domain.model.MealType
import kz.btokmyrza.calorytracker.tracker_domain.model.TrackableFood
import kz.btokmyrza.calorytracker.tracker_domain.model.TrackedFood
import java.time.LocalDate
import kotlin.math.roundToInt

class TrackedFoodModelMapper {

    fun toTrackedFood(from: TrackedFoodEntity): TrackedFood = TrackedFood(
        id = from.id,
        name = from.name,
        carbs = from.carbs,
        protein = from.protein,
        fat = from.fat,
        imageUrl = from.imageUrl,
        mealType = MealType.fromString(from.type),
        amount = from.amount,
        date = LocalDate.of(from.year, from.month, from.dayOfMonth),
        calories = from.calories,
    )

    fun toTrackedFoodEntity(from: TrackedFood): TrackedFoodEntity = TrackedFoodEntity(
        id = from.id,
        name = from.name,
        carbs = from.carbs,
        protein = from.protein,
        fat = from.fat,
        imageUrl = from.imageUrl,
        type = from.mealType.name,
        amount = from.amount,
        dayOfMonth = from.date.dayOfMonth,
        month = from.date.monthValue,
        year = from.date.year,
        calories = from.calories,
    )

    fun toTrackableFood(from: FoodSearchResponse): List<TrackableFood> = from.products.map {
        TrackableFood(
            name = it.productName.orEmpty(),
            carbsPer100g = it.nutriments.carbohydrates100g.roundToInt(),
            proteinPer100g = it.nutriments.proteins100g.roundToInt(),
            fatPer100g = it.nutriments.proteins100g.roundToInt(),
            caloriesPer100g = it.nutriments.energyKcal100g.roundToInt(),
            imageUrl = it.imageFrontThumbUrl,
        )
    }
}