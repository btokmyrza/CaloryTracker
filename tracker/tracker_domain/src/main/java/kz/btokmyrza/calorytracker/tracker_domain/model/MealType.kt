package kz.btokmyrza.calorytracker.tracker_domain.model

private const val BREAKFAST = "BREAKFAST"
private const val LUNCH = "LUNCH"
private const val DINNER = "DINNER"
private const val SNACK = "SNACK"

sealed class MealType(val name: String) {
    data object Breakfast : MealType(BREAKFAST)
    data object Lunch : MealType(LUNCH)
    data object Dinner : MealType(DINNER)
    data object Snack : MealType(SNACK)

    companion object {
        fun fromString(name: String): MealType = when (name) {
            BREAKFAST -> Breakfast
            LUNCH -> Lunch
            DINNER -> Dinner
            SNACK -> Snack
            else -> Breakfast
        }
    }
}