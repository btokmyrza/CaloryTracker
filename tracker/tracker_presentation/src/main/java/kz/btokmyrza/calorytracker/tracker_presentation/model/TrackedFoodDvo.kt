package kz.btokmyrza.calorytracker.tracker_presentation.model

import kz.btokmyrza.calorytracker.tracker_domain.model.MealType
import java.time.LocalDate

data class TrackedFoodDvo(
    val id: Int? = null,
    val name: String,
    val carbs: Int,
    val protein: Int,
    val fat: Int,
    val imageUrl: String?,
    val mealType: MealType,
    val amount: Int,
    val date: LocalDate,
    val calories: Int,
)