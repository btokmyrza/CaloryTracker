package kz.btokmyrza.calorytracker.tracker_domain.use_case

import kz.btokmyrza.calorytracker.tracker_domain.model.MealType
import kz.btokmyrza.calorytracker.tracker_domain.model.TrackableFood
import kz.btokmyrza.calorytracker.tracker_domain.model.TrackedFood
import kz.btokmyrza.calorytracker.tracker_domain.repository.TrackerRepository
import java.time.LocalDate
import kotlin.math.roundToInt

class TrackFood(
    private val repository: TrackerRepository,
) {

    suspend operator fun invoke(
        food: TrackableFood,
        amount: Int,
        mealType: MealType,
        date: LocalDate,
    ) {
        repository.insertTrackedFood(
            trackedFood = TrackedFood(
                name = food.name,
                carbs = getNutrientPer100grams(
                    nutrientPer100g = food.carbsPer100g,
                    amount = amount,
                ),
                protein = getNutrientPer100grams(
                    nutrientPer100g = food.proteinPer100g,
                    amount = amount,
                ),
                fat = getNutrientPer100grams(
                    nutrientPer100g = food.fatPer100g,
                    amount = amount,
                ),
                calories = getNutrientPer100grams(
                    nutrientPer100g = food.caloriesPer100g,
                    amount = amount,
                ),
                imageUrl = food.imageUrl,
                mealType = mealType,
                amount = amount,
                date = date,
            ),
        )
    }

    private fun getNutrientPer100grams(nutrientPer100g: Int, amount: Int): Int =
        ((nutrientPer100g / 100f) * amount).roundToInt()
}