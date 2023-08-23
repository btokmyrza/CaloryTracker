package kz.btokmyrza.calorytracker.tracker_presentation.mapper

import kz.btokmyrza.calorytracker.tracker_domain.model.TrackedFood
import kz.btokmyrza.calorytracker.tracker_presentation.model.TrackedFoodDvo

class TrackedFoodDvoMapper {

    fun toTrackedFoodDvo(from: TrackedFood): TrackedFoodDvo = TrackedFoodDvo(
        id = from.id,
        name = from.name,
        carbs = from.carbs,
        protein = from.protein,
        fat = from.fat,
        imageUrl = from.imageUrl,
        mealType = from.mealType,
        amount = from.amount,
        date = from.date,
        calories = from.calories,
    )

    fun toTrackedFood(from: TrackedFoodDvo): TrackedFood = TrackedFood(
        id = from.id,
        name = from.name,
        carbs = from.carbs,
        protein = from.protein,
        fat = from.fat,
        imageUrl = from.imageUrl,
        mealType = from.mealType,
        amount = from.amount,
        date = from.date,
        calories = from.calories,
    )
}