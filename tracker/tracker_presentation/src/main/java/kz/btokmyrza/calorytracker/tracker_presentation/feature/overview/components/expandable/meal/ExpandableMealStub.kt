package kz.btokmyrza.calorytracker.tracker_presentation.feature.overview.components.expandable.meal

import kz.btokmyrza.calorytracker.core.R
import kz.btokmyrza.calorytracker.core.util.UiText
import kz.btokmyrza.calorytracker.tracker_domain.model.MealType
import kz.btokmyrza.calorytracker.tracker_presentation.model.MealDvo
import kz.btokmyrza.calorytracker.tracker_presentation.model.TrackedFoodDvo
import java.time.LocalDate

internal val stubTrackedFoods = listOf(
    TrackedFoodDvo(
        name = "Burger 1",
        carbs = 40,
        protein = 30,
        fat = 30,
        imageUrl = null,
        mealType = MealType.Breakfast,
        amount = 100,
        date = LocalDate.now(),
        calories = 100,
    ),
    TrackedFoodDvo(
        name = "Burger 2",
        carbs = 12,
        protein = 33,
        fat = 44,
        imageUrl = null,
        mealType = MealType.Dinner,
        amount = 123,
        date = LocalDate.now(),
        calories = 333,
    ),
)

internal val stubMealBreakfast = MealDvo(
    name = UiText.StringResource(R.string.breakfast),
    drawableRes = R.drawable.ic_breakfast,
    mealType = MealType.Breakfast,
    carbs = 40,
    protein = 30,
    fat = 30,
    calories = 100,
    isExpanded = false,
)