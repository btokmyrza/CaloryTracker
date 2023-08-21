package kz.btokmyrza.calorytracker.onboarding_presentation.feature.nutrient_goal

sealed class NutrientGoalEvent {
    data class OnCarbRatioChanged(val ratio: String) : NutrientGoalEvent()
    data class OnProteinRatioChanged(val ratio: String) : NutrientGoalEvent()
    data class OnFatRatioChanged(val ratio: String) : NutrientGoalEvent()
    data object OnNextClicked : NutrientGoalEvent()
}